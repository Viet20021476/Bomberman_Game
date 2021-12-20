package Entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import mygame.GamePanel;
import tiles.Grass;
import tiles.PowerUp;
import tiles.Tile;

public class EntityManager {

    private ArrayList<Entity> entityList = new ArrayList<>();
    private GamePanel gamePanel;
    private int playerIndex;

    public EntityManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        getImage();
    }

    private void getImage() {
        try {
            enemyLeft[BALLOOM][0] = ImageIO.read(new FileInputStream("res/enemies/balloom_left1.png"));
            enemyLeft[BALLOOM][1] = ImageIO.read(new FileInputStream("res/enemies/balloom_left2.png"));
            enemyLeft[BALLOOM][2] = ImageIO.read(new FileInputStream("res/enemies/balloom_left3.png"));
            enemyRight[BALLOOM][0] = ImageIO.read(new FileInputStream("res/enemies/balloom_right1.png"));
            enemyRight[BALLOOM][1] = ImageIO.read(new FileInputStream("res/enemies/balloom_right2.png"));
            enemyRight[BALLOOM][2] = ImageIO.read(new FileInputStream("res/enemies/balloom_right3.png"));
            enemyLeft[ONEAL][0] = ImageIO.read(new FileInputStream("res/enemies/oneal_left1.png"));
            enemyLeft[ONEAL][1] = ImageIO.read(new FileInputStream("res/enemies/oneal_left2.png"));
            enemyLeft[ONEAL][2] = ImageIO.read(new FileInputStream("res/enemies/oneal_left3.png"));
            enemyRight[ONEAL][0] = ImageIO.read(new FileInputStream("res/enemies/oneal_right1.png"));
            enemyRight[ONEAL][1] = ImageIO.read(new FileInputStream("res/enemies/oneal_right2.png"));
            enemyRight[ONEAL][2] = ImageIO.read(new FileInputStream("res/enemies/oneal_right3.png"));
            genericEnemyDead[0] = ImageIO.read(new FileInputStream("res/enemies/mob_dead1.png"));
            genericEnemyDead[1] = ImageIO.read(new FileInputStream("res/enemies/mob_dead2.png"));
            genericEnemyDead[2] = ImageIO.read(new FileInputStream("res/enemies/mob_dead3.png"));
            specificEnemyDead[BALLOOM] = ImageIO.read(new FileInputStream("res/enemies/balloom_dead.png"));
            specificEnemyDead[ONEAL] = ImageIO.read(new FileInputStream("res/enemies/oneal_dead.png"));
            playerUp[0] = ImageIO.read(new FileInputStream("res/player/player_up.png"));
            playerUp[1] = ImageIO.read(new FileInputStream("res/player/player_up1.png"));
            playerUp[2] = ImageIO.read(new FileInputStream("res/player/player_up2.png"));
            playerDown[0] = ImageIO.read(new FileInputStream("res/player/player_down.png"));
            playerDown[1] = ImageIO.read(new FileInputStream("res/player/player_down1.png"));
            playerDown[2] = ImageIO.read(new FileInputStream("res/player/player_down2.png"));
            playerLeft[0] = ImageIO.read(new FileInputStream("res/player/player_left.png"));
            playerLeft[1] = ImageIO.read(new FileInputStream("res/player/player_left1.png"));
            playerLeft[2] = ImageIO.read(new FileInputStream("res/player/player_left2.png"));
            playerRight[0] = ImageIO.read(new FileInputStream("res/player/player_right.png"));
            playerRight[1] = ImageIO.read(new FileInputStream("res/player/player_right1.png"));
            playerRight[2] = ImageIO.read(new FileInputStream("res/player/player_right2.png"));
            playerDead[0] = ImageIO.read(new FileInputStream("res/player/player_dead.png"));
            playerDead[1] = ImageIO.read(new FileInputStream("res/player/player_dead1.png"));
            playerDead[2] = ImageIO.read(new FileInputStream("res/player/player_dead2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addEntity(Entity e) {
        entityList.add(e);
    }

    public void update() {
        for (int i = 0; i < entityList.size(); i++) {
            Entity e = entityList.get(i);
            if (!e.isDead()) {
                e.setDirection();
                for (int j = i + 1; j < entityList.size(); j++) {
                    Entity e1 = entityList.get(i);
                    Entity e2 = entityList.get(j);
                    if (gamePanel.getCollisionDetect().checkEntity(e1, e2)) {
                        if (i == playerIndex || j == playerIndex) {
                            entityList.get(playerIndex).die(START_TIME_DEAD_1);
                        } else {
                            // doi huong
                            Enemy[] enemy = new Enemy[2];
                            enemy[0] = (Enemy) e1;
                            enemy[1] = (Enemy) e2;
                            for (int k = 0; k < 2; k++) {
                                switch (enemy[k].getDirection()) {
                                    case "up" -> {
                                        enemy[k].setDirection("down");
                                        enemy[k].setTargetY(enemy[k].getTargetY() + GamePanel.TILESIZE);
                                    }
                                    case "down" -> {
                                        enemy[k].setDirection("up");
                                        enemy[k].setTargetY(enemy[k].getTargetY() - GamePanel.TILESIZE);
                                    }
                                    case "left" -> {
                                        enemy[k].setDirection("right");
                                        enemy[k].setTargetX(enemy[k].getTargetX() + GamePanel.TILESIZE);
                                    }
                                    case "right" -> {
                                        enemy[k].setDirection("left");
                                        enemy[k].setTargetX(enemy[k].getTargetX() - GamePanel.TILESIZE);
                                    }
                                }
                            }
                        }
                    }
                }
                e.setXY();
                Tile tile = e.getCurrentTile();
                try {
                    if (tile instanceof Grass) {
                        Grass grass = (Grass) tile;
                        if (grass.hasFlame()) {
                            if (e instanceof Player) {
                                entityList.get(i).die(START_TIME_DEAD_1);
                            } else {
                                entityList.get(i).die(TIME_UNTIL_DEAD_ENDS);
                            }
                        }
                    }
                    if (e instanceof Player && tile instanceof PowerUp) {
                        PowerUp pow = (PowerUp) tile;
                        pow.usePower(gamePanel);
                    }
                } catch (NullPointerException ex) {

                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < entityList.size(); i++) {
            Entity e = entityList.get(i);
            if (e.isDead()) {

                BufferedImage image;
                long remainingTime = e.getDeadTime() - System.nanoTime();
                if (remainingTime > 0) {
                    if (e instanceof Player) {
                        if (remainingTime > START_TIME_DEAD_2) {
                            gamePanel.getSound().stop();
                            image = playerDead[0];
                        } else if (remainingTime > START_TIME_DEAD_3) {
                            gamePanel.getSound().stop();
                            image = playerDead[1];
                        } else {
                            gamePanel.numOfPlayerLives--;
                            image = playerDead[2];
                            if (gamePanel.numOfPlayerLives < 0) {
                                gamePanel.getSound().stop();
                                gamePanel.playMusic(4);
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                gamePanel.gameState = gamePanel.loseState;
                                gamePanel.getSound().stop();
                                gamePanel.playMusic(5);

                            } else {
                                Timer timer
                                        = new Timer(6000, event -> {
                                            gamePanel.gameState = gamePanel.playState;
                                            gamePanel.getSound().stop();
                                            gamePanel.playMusic(2);
                                            entityList.clear();
                                            gamePanel.loadTileMap();
                                            gamePanel.getTimeAndScore().resetTime();
                                        });
                                timer.setRepeats(false);
                                timer.start();
                                gamePanel.getSound().stop();
                                gamePanel.playMusic(4);
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                gamePanel.gameState = gamePanel.loadingState;
                                gamePanel.loadLevel();
                                gamePanel.getSound().stop();
                                gamePanel.playMusic(3);

                            }
                        }
                    } else {
                        if (remainingTime > START_TIME_DEAD_1) {
                            if (e instanceof Balloom) {
                                image = specificEnemyDead[BALLOOM];

                            } else {
                                image = specificEnemyDead[ONEAL];
                            }
                        } else if (remainingTime > START_TIME_DEAD_2) {
                            image = genericEnemyDead[0];
                        } else if (remainingTime > START_TIME_DEAD_3) {
                            image = genericEnemyDead[1];
                        } else {
                            image = genericEnemyDead[2];
                        }
                    }
                    gamePanel.draw(e.getX(), e.getY(), image, g2);
                } else {
                    if (i < playerIndex) {
                        playerIndex--;
                    }
                    entityList.remove(i);
                    gamePanel.getTimeAndScore().increaseScore(100);
                    i--;
                }
            } else if (e instanceof Player) {
                Player p = (Player) e;
                BufferedImage bufferedImage = null;
                switch (e.direction) {
                    case "up" -> {
                        bufferedImage = playerUp[e.getSpriteNum()];
                    }
                    case "down" -> {
                        bufferedImage = playerDown[e.getSpriteNum()];
                    }
                    case "left" -> {
                        bufferedImage = playerLeft[e.getSpriteNum()];
                    }
                    case "right" -> {
                        bufferedImage = playerRight[e.getSpriteNum()];
                    }
                    default -> {
                    }
                }

                int tempScreenX = p.screenX;
                int tempScreenY = p.screenY;

                if (p.screenX > p.getX()) {
                    tempScreenX = p.getX();
                }

                if (p.screenY > p.getY()) {
                    tempScreenY = p.getY();
                }

                if (gamePanel.getScreenWidth() - p.screenX
                        > gamePanel.getMapWidth() - p.getX()) {
                    tempScreenX = gamePanel.getScreenWidth()
                            - (gamePanel.getMapWidth() - p.getX());
                }

                if (gamePanel.getScreenHeight() - gamePanel.getPlayer().screenY
                        > gamePanel.getMapHeight() - p.getY()) {
                    tempScreenY = gamePanel.getScreenHeight()
                            - (gamePanel.getMapHeight() - p.getY());
                }

                g2.drawImage(bufferedImage, tempScreenX, tempScreenY + 48, GamePanel.TILESIZE, GamePanel.TILESIZE, null);
            } else if (e instanceof Balloom) {
                BufferedImage bufferedImage = null;
                switch (e.direction) {
                    case "up" -> {
                        bufferedImage = enemyLeft[BALLOOM][e.getSpriteNum()];
                    }
                    case "down" -> {
                        bufferedImage = enemyRight[BALLOOM][e.getSpriteNum()];
                    }
                    case "left" -> {
                        bufferedImage = enemyLeft[BALLOOM][e.getSpriteNum()];
                    }
                    case "right" -> {
                        bufferedImage = enemyRight[BALLOOM][e.getSpriteNum()];
                    }
                    default -> {
                    }
                }
                gamePanel.draw(e.getX(), e.getY(), bufferedImage, g2);
            } else if (e instanceof Oneal) {
                BufferedImage bufferedImage = null;
                switch (e.direction) {
                    case "up" -> {
                        bufferedImage = enemyLeft[ONEAL][e.getSpriteNum()];
                    }
                    case "down" -> {
                        bufferedImage = enemyRight[ONEAL][e.getSpriteNum()];
                    }
                    case "left" -> {
                        bufferedImage = enemyLeft[ONEAL][e.getSpriteNum()];
                    }
                    case "right" -> {
                        bufferedImage = enemyRight[ONEAL][e.getSpriteNum()];
                    }
                    default -> {
                    }
                }
                gamePanel.draw(e.getX(), e.getY(), bufferedImage, g2);
            }
        }
    }

    public boolean hasNoEnemy() {
        return entityList.size() == 1
                && entityList.get(0) instanceof Player;
    }

    public BufferedImage[] getBufferedImagePlayerRight() {
        return playerRight;
    }

    public void addPlayer(Player p) {
        entityList.add(p);
        playerIndex = entityList.size() - 1;
    }

    public Player getPlayer() {

        return (Player) entityList.get(playerIndex);
    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    private BufferedImage[][] enemyLeft = new BufferedImage[2][3];
    private BufferedImage[][] enemyRight = new BufferedImage[2][3];
    private BufferedImage[] genericEnemyDead = new BufferedImage[3];
    private BufferedImage[] specificEnemyDead = new BufferedImage[2];

    private BufferedImage[] playerLeft = new BufferedImage[3];
    private BufferedImage[] playerRight = new BufferedImage[3];
    private BufferedImage[] playerUp = new BufferedImage[3];
    private BufferedImage[] playerDown = new BufferedImage[3];
    private BufferedImage[] playerDead = new BufferedImage[3];

    private final int BALLOOM = 0;
    private final int ONEAL = 1;

    private final long START_TIME_DEAD_1 = 300000000;
    private final long START_TIME_DEAD_2 = 200000000;
    private final long START_TIME_DEAD_3 = 100000000;
    protected final long TIME_UNTIL_DEAD_ENDS = 1000000000;

    public long getSTART_TIME_DEAD_1() {
        return START_TIME_DEAD_1;
    }

}
