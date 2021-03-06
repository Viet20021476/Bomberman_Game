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
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;
import tiles.Brick;
import tiles.Grass;
import tiles.Tile;
import tiles.TileManager;
import tiles.Wall;

public class GamePanel extends JPanel implements Runnable {

    private static final int ORIGINALTILESIZE = 16;
    private static final int SCALE = 3;
    public static final int TILESIZE = ORIGINALTILESIZE * SCALE;

    private final int screenWidth = 768;
    private final int screenHeight = 624;

    // LARGE MAP SETTINGS
    private int maxMapCol;
    private int maxMapRow;
    private int mapWidth;
    private int mapHeight;

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int loadingState = 1;
    public final int playState = 2;
    public final int loseState = 3;
    public int numOfPlayerLives = 3;

    //FPS
    private int FPS = 60;

    private int level = 1;

    private KeyHandle keyHandle = new KeyHandle(this);
    private Thread gameThread;
    private ScreenState screenState = new ScreenState(this);
    private EntityManager entityManager = new EntityManager(this);
    private TileManager tileManager = new TileManager(this);
    private BombManager bombManager = new BombManager(this);
    private CollisionDetect collisionDetect = new CollisionDetect(this);
    private Scroller scroller = new Scroller(this);
    private Sound sound = new Sound();
    private BombSound bombSound = new BombSound();
    private TimeAndScore timeAndScore = new TimeAndScore(this);
    private Timer timer;

    public void setupGame() {
        gameState = titleState;
        playMusic(0);
    }

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandle);
        this.setFocusable(true);
        initializeTimer();
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
        if (gameState == titleState || gameState == loadingState
                || gameState == loseState) {
            screenState.draw(g2);
        } else {
            scroller.updateOffset();
            tileManager.draw(g2);
            bombManager.draw(g2, this);
            entityManager.draw(g2);
            timeAndScore.draw(g2);
            timer.start();
        }
        g2.dispose();
    }

    public Tile[][] loadTileMap() {
        Tile[][] tileMap = null;
        ArrayList<String> temp = new ArrayList<>();
        File file = new File("res/maps/map_" + level + ".txt");
        try {
            Scanner readFile = new Scanner(file);
            int col = 0;
            int row = 0;

            String s1 = readFile.nextLine();
            String[] input = s1.split(" ");
            maxMapRow = Integer.parseInt(input[1]);
            maxMapCol = Integer.parseInt(input[2]);
            mapWidth = TILESIZE * maxMapCol;
            mapHeight = TILESIZE * maxMapRow + 48;

            while (readFile.hasNextLine()) {
                String s = readFile.nextLine();
                while (s.length() < Integer.parseInt(input[2])) {
                    s += " ";
                }
                temp.add(s);
            }

            tileMap = new Tile[maxMapCol][maxMapRow];

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
                        case 'x':
                            tileMap[col][row] = new Brick("PORTAL");
                            break;
                        case 'p':
                            Player p = new Player(this, col * TILESIZE, row * TILESIZE);
                            entityManager.addPlayer(p);
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

        return tileMap;
    }

    public Player getPlayer() {
        return entityManager.getPlayer();
    }

    public KeyHandle getKeyHandle() {
        return keyHandle;
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

    public CollisionDetect getCollisionDetect() {
        return collisionDetect;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void drawTile(int x, int y, BufferedImage image, Graphics2D g2) {
        draw(x * TILESIZE, y * TILESIZE, image, g2);
    }

    public void draw(int x, int y, BufferedImage image, Graphics2D g2) {
        int newX = x + scroller.getOffsetX();
        int newY = y + scroller.getOffsetY();

        if (!outOfBounds(newX, newY)) {
            g2.drawImage(image, newX, newY + 48, TILESIZE, TILESIZE, null);
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

    public void playBombSound(int i) {
        bombSound.setbombSound(i);
        bombSound.play();
    }

    public ScreenState getScreenState() {
        return this.screenState;
    }

    public Sound getSound() {
        return this.sound;
    }

    public BombSound getBombSound() {
        return bombSound;
    }

    public void goToNextLevel() {
        level++;
        Timer timer
                = new Timer(3000, event -> {
                    gameState = playState;
                    getSound().stop();
                    playMusic(2);
                    loadLevel();
                });
        timer.setRepeats(false);
        timer.start();
        gameState = loadingState;
        getSound().stop();
        playMusic(3);
        entityManager.getEntityList().clear();
        getTimer().stop();
        initializeTimer();
        getTimeAndScore().resetTime();
        resetLives();
    }

    public int getMaxMapCol() {
        return maxMapCol;
    }

    public void initializeTimer() {
        this.timer = new Timer(1000, (ActionEvent e) -> {
            if (timeAndScore.countdown > 0) {
                timeAndScore.countdown--;
            } else {
                getPlayer().die(entityManager.getSTART_TIME_DEAD_1());
            }
        });
    }

    public TimeAndScore getTimeAndScore() {
        return timeAndScore;
    }

    public void loadLevel() {
        tileManager.setTileMap(loadTileMap());
        tileManager.setHeight(maxMapRow);
        tileManager.setWidth(maxMapCol);
    }

    public void resetLives() {
        numOfPlayerLives = 3;
    }

    public Timer getTimer() {
        return timer;
    }

    public int getLevel() {
        return level;
    }

    public void resetLevel() {
        level = 1;
    }
}
