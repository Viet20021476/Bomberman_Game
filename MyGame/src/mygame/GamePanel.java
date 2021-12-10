package mygame;

import Entities.Balloom;
import Entities.EntityManager;
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

    final int maxScreenCol = 16;
    final int maxScreenRow = 14;
    public final int screenWidth = TILESIZE * maxScreenCol;
    public final int screenHeight = TILESIZE * maxScreenRow;

    // LARGE MAP SETTINGS
    public final int maxMapCol = 31;
    public final int maxMapRow = 14;
    public final int mapWidth = TILESIZE * maxMapCol;
    public final int mapHeight = TILESIZE * maxMapRow;
    public final int newScreenWidth = TILESIZE * maxMapCol;
    public final int newScreenHeight = TILESIZE * maxMapRow;

    //FPS
    int FPS = 60;

    public KeyHandle keyHandle = new KeyHandle(this);
    Thread gameThread;
    public Player player = new Player(this, keyHandle);
    private EntityManager entityManager = new EntityManager(this);
    private TileManager tileManager = new TileManager(this);
    private BombManager bombManager = new BombManager(this);
    public CollisionDetect collisionDetect = new CollisionDetect(this);
    private Scroller scroller = new Scroller(this);

    public void setupGame() {

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

            //1.Update: update information such as character positions
            update();

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
        scroller.updateOffset();
        tileManager.draw(g2);
        bombManager.draw(g2, this);
        entityManager.draw(g2);
        g2.dispose();
    }

    public void loadTileMap(Tile[][] tileMap) {
        ArrayList<String> temp = new ArrayList<>();
        File file = new File("res/maps/map_1.txt");
        try {
            Scanner readFile = new Scanner(file);
            int col = 0;
            int row = 0;
            while (readFile.hasNextLine()) {
                String s = readFile.nextLine();
                while (s.length() < WIDTH) {
                    s += " ";
                }
                temp.add(s);
            }

            for (String s : temp) {
                for (int i = 0; i < s.length(); i++) {
                    switch (s.charAt(i)) {
                        case ' ':
                            tileMap[col][row] = new Grass();
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
                        case 'p':
                            entityManager.addEntity(player);
                            player.setX(col * TILESIZE);
                            player.setY(row * TILESIZE);
                        case '1':
                            Balloom b = new Balloom(this, col * TILESIZE, row * TILESIZE);
                            entityManager.addEntity(b);
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

    public void drawTile(int x, int y, BufferedImage image, Graphics2D g2) {
        draw(x * TILESIZE, y * TILESIZE, image, g2);
    }

    public void draw(int x, int y, BufferedImage image, Graphics2D g2) {
        int newX = x + scroller.getOffsetX();
        int newY = y + scroller.getOffsetY();

        if (player.screenX > player.getSolidArea().x) {
            newX = x;
        }

        if (player.screenY > player.getSolidArea().y) {
            newY = y;
        }

        if (screenWidth - player.screenX > mapWidth - player.getSolidArea().x) {
            newX = screenWidth - (mapWidth - x);
        }

        if (screenHeight - player.screenY > mapHeight - player.getSolidArea().y) {
            newY = screenHeight - (mapHeight - y);
        }

        if (!outOfBounds(newX, newY)) {

            g2.drawImage(image, newX, newY, TILESIZE, TILESIZE, null);
        }
    }

    private boolean outOfBounds(int x, int y) {
        return x < -TILESIZE
                || x > screenWidth + TILESIZE
                || y < -TILESIZE
                || y > screenHeight + TILESIZE;
    }
}
