package mygame;

import Entities.Balloom;
import Entities.EntityManager;
import Entities.Oneal;
import Entities.Player;
import bomb.BombManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import tiles.Brick;
import tiles.Grass;
import tiles.Tile;
import tiles.TileManager;
import tiles.Wall;

public class GamePanel extends JPanel implements Runnable {

    private static final int ORIGINALTILESIZE = 16;
    private static final int SCALE = 3;
    public static final int TILESIZE = ORIGINALTILESIZE * SCALE;

    //public final int screenWidth = TILESIZE * maxScreenCol;
    //public final int screenHeight = TILESIZE * maxScreenRow - TILESIZE;
    public final int screenWidth = 768;
    public final int screenHeight = 624;

    // LARGE MAP SETTINGS
    public int maxMapCol;
    public int maxMapRow;
    public int mapWidth;
    public int mapHeight;

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int loadingState = 1;
    public final int playState = 2;

    //FPS
    int FPS = 60;

    public KeyHandle keyHandle = new KeyHandle(this);
    Thread gameThread;
    public Player player = new Player(this, keyHandle);
    private ScreenState screenState = new ScreenState(this);
    private EntityManager entityManager = new EntityManager(this);
    private TileManager tileManager = new TileManager(this);
    private BombManager bombManager = new BombManager(this);
    public CollisionDetect collisionDetect = new CollisionDetect(this);
    private Scroller scroller = new Scroller(this);
    private Sound sound = new Sound();

    public void setupGame() {
        tileManager.setHeight(maxMapRow);
        tileManager.setWidth(maxMapCol);
        gameState = titleState;
        playMusic(0);
    }

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandle);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {

            long currentTime = System.nanoTime();

            if (this.gameState == playState) {
                //1.Update: update information such as character positions
                update();
            }

            //2.Draw : draw the screen with the updated information
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void update() {
        entityManager.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (gameState == titleState || gameState == loadingState) {
            screenState.draw(g2);
        } else {
            scroller.updateOffset();
            tileManager.draw(g2);
            bombManager.draw(g2, this);
            entityManager.draw(g2);
        }
        g2.dispose();
    }

    public void loadTileMap(Tile[][] tileMap) {
        ArrayList<String> temp = new ArrayList<>();
        File file = new File("res/maps/map_2.txt");
        try {
            Scanner readFile = new Scanner(file);
            int col = 0;
            int row = 0;

            String s1 = readFile.nextLine();
            String[] input = s1.split(" ");
            maxMapRow = Integer.parseInt(input[1]);
            maxMapCol = Integer.parseInt(input[2]);
            mapWidth = TILESIZE * maxMapCol;
            mapHeight = TILESIZE * maxMapRow;
            //tileMap = new Tile[maxMapCol][maxMapRow];
            
            while (readFile.hasNextLine()) {
                String s = readFile.nextLine();
                while (s.length() < Integer.parseInt(input[2])) {
                    s += " ";
                }
                temp.add(s);
            }

            for (String s : temp) {
                for (int i = 0; i < s.length(); i++) {
                    switch (s.charAt(i)) {
                        case ' ':
                            tileMap[col][row] = new Grass();
                            System.out.println("y");
                            break;
                        case '#':
                            tileMap[col][row] = new Wall();
                            break;
                        case '*':
                            tileMap[col][row] = new Brick("NONE");
                            break;
                        case 'b':
                            tileMap[col][row] = new Brick("BOMB");
                            break;
                        case 'f':
                            tileMap[col][row] = new Brick("FLAME");
                            break;
                        case 's':
                            tileMap[col][row] = new Brick("SPEED");
                            break;
                        case 'x':
                            tileMap[col][row] = new Brick("PORTAL");
                            break;
                        case 'p':
                            entityManager.addEntity(player);
                            player.setX(col * TILESIZE);
                            player.setY(row * TILESIZE);
                            tileMap[col][row] = new Grass();
                            break;
                        case '1':
                            Balloom b = new Balloom(this, col * TILESIZE, row * TILESIZE);
                            entityManager.addEntity(b);
                            tileMap[col][row] = new Grass();
                            break;
                        case '2':
                            Oneal o = new Oneal(this, col * TILESIZE, row * TILESIZE);
                            entityManager.addEntity(o);
                            tileMap[col][row] = new Grass();
                            break;
                        default:
                            tileMap[col][row] = new Grass();
                    }
                    col++;
                }
                col = 0;
                row++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public BombManager getBombManager() {
        return bombManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void drawTile(int x, int y, BufferedImage image, Graphics2D g2) {
        draw(x * TILESIZE, y * TILESIZE, image, g2);
    }

    public void draw(int x, int y, BufferedImage image, Graphics2D g2) {
        int newX = x + scroller.getOffsetX();
        int newY = y + scroller.getOffsetY();

        if (!outOfBounds(newX, newY)) {
            g2.drawImage(image, newX, newY, TILESIZE, TILESIZE, null);
            //System.out.println(newX + " " + newY);
        } 
    }

    private boolean outOfBounds(int x, int y) {
        return x < -TILESIZE
                || x > screenWidth + TILESIZE
                || y < -TILESIZE
                || y > screenHeight + TILESIZE;
    }

    public void playSoundEffect(int i) {
        sound.setSound(i);
        sound.play();
    }

    public void playMusic(int i) {
        sound.setSound(i);
        sound.play();
        sound.loop();
    }

    public ScreenState getScreenState() {
        return this.screenState;
    }

    public Sound getSound() {
        return this.sound;
    }

    public void goToNextLevel() {
        System.out.println("next level");
    }
}
