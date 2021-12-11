package bomb;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import mygame.GamePanel;
import tiles.Brick;
import tiles.Grass;
import tiles.Tile;

public class BombManager {

    private final int BOMB_CAP = 5;

    private GamePanel gamePanel;
    private Queue<Bomb> bombArray = new ArrayDeque<>();
    private int bombCount = 1;
    private int range = 1;

    public BombManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        getBombImage();
    }

    private void getBombImage() {
        try {
            bomb_exploding[0] = ImageIO.read(new FileInputStream("res/bomb/bomb.png"));
            bomb_exploding[1] = ImageIO.read(new FileInputStream("res/bomb/bomb_1.png"));
            bomb_exploding[2] = ImageIO.read(new FileInputStream("res/bomb/bomb_2.png"));
            bomb_exploded[0] = ImageIO.read(new FileInputStream("res/bomb/bomb_exploded.png"));
            bomb_exploded[1] = ImageIO.read(new FileInputStream("res/bomb/bomb_exploded1.png"));
            bomb_exploded[2] = ImageIO.read(new FileInputStream("res/bomb/bomb_exploded2.png"));
            explosion_horizontal[0] = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal.png"));
            explosion_horizontal[1] = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal1.png"));
            explosion_horizontal[2] = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal2.png"));
            explosion_vertical[0] = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical.png"));
            explosion_vertical[1] = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical1.png"));
            explosion_vertical[2] = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical2.png"));
            explosion_horizontal_left_last[0] = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal_left_last.png"));
            explosion_horizontal_left_last[1] = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal_left_last1.png"));
            explosion_horizontal_left_last[2] = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal_left_last2.png"));
            explosion_horizontal_right_last[0] = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal_right_last.png"));
            explosion_horizontal_right_last[1] = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal_right_last1.png"));
            explosion_horizontal_right_last[2] = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal_right_last2.png"));
            explosion_vertical_top_last[0] = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical_top_last.png"));
            explosion_vertical_top_last[1] = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical_top_last1.png"));
            explosion_vertical_top_last[2] = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical_top_last2.png"));
            explosion_vertical_down_last[0] = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical_down_last.png"));
            explosion_vertical_down_last[1] = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical_down_last1.png"));
            explosion_vertical_down_last[2] = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical_down_last2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Bomb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void increaseBombCount() {
        if (bombCount < BOMB_CAP) {
            bombCount++;
        }
    }

    public void increaseRange() {
        range++;
    }

    private int fitTilePosition(int coord) {
        return coord / GamePanel.TILESIZE;
    }

    public void addBomb() {
        if (bombArray.size() < bombCount) {
            int x = fitTilePosition(gamePanel.getPlayer().getX());
            int y = fitTilePosition(gamePanel.getPlayer().getY());
            bombArray.add(new Bomb(gamePanel, x, y));
            Grass grass = (Grass) gamePanel.getTileManager().getTileAt(x, y);
            grass.setHasBomb(true);
        }
    }

    public void draw(Graphics2D g2, GamePanel gamePanel) {
        for (Bomb bomb : bombArray) {
            drawBomb(g2, gamePanel, bomb);
        }
        try {
            if (bombArray.peek().isExpired()) {
                Bomb bomb = bombArray.peek();
                Grass grass = (Grass) gamePanel.getTileManager().getTileAt(bomb.getX(), bomb.getY());
                grass.setHasBomb(false);
                bombArray.poll();
            }
        } catch (NullPointerException ex) {

        }
    }

    public void drawBomb(Graphics2D g2, GamePanel gamePanel, Bomb bomb) {
        BufferedImage image;
        long remainingTime = bomb.getExplosionTime() - System.nanoTime();
        if (remainingTime > 0) {
            if (remainingTime > START_TIME_BOMB_1) {
                image = bomb_exploding[0];
            } else if (remainingTime > START_TIME_BOMB_2) {
                image = bomb_exploding[1];
            } else if (remainingTime > START_TIME_EXPLOSION) {
                image = bomb_exploding[2];
            } else if (remainingTime > START_TIME_EXPLOSION_1) {
                image = bomb_exploded[0];
                drawExplosion(0, g2, bomb);
            } else if (remainingTime > START_TIME_EXPLOSION_2) {
                image = bomb_exploded[1];
                drawExplosion(1, g2, bomb);
                gamePanel.playSoundEffect(1);
            } else {
                image = bomb_exploded[2];
                drawExplosion(2, g2, bomb);
                gamePanel.playSoundEffect(1);
            }
            gamePanel.drawTile(bomb.getX(), bomb.getY(), image, g2);
        } else {
            bomb.setExpired(true);
            drawExplosion(3, g2, bomb);
        }
    }

    private void drawExplosion(int number, Graphics2D g2, Bomb bomb) {
        BufferedImage mid_flame;
        BufferedImage end_flame;
        for (int j = 1; j <= 4; j++) {
            switch (j) {
                case 1 -> {
                    mid_flame = explosion_vertical[number];
                    end_flame = explosion_vertical_top_last[number];
                }
                case 2 -> {
                    mid_flame = explosion_vertical[number];
                    end_flame = explosion_vertical_down_last[number];
                }
                case 3 -> {
                    mid_flame = explosion_horizontal[number];
                    end_flame = explosion_horizontal_left_last[number];
                }
                case 4 -> {
                    mid_flame = explosion_horizontal[number];
                    end_flame = explosion_horizontal_right_last[number];
                }
                default -> {
                    mid_flame = bomb_exploded[0];
                    end_flame = bomb_exploded[0];
                }
            }
            int tileX = 0;
            int tileY = 0;
            for (int i = 1; i <= gamePanel.getBombManager().getRange(); i++) {
                switch (j) {
                    case 1 -> {
                        tileX = bomb.getX();
                        tileY = bomb.getY() - i;
                    }
                    case 2 -> {
                        tileX = bomb.getX();
                        tileY = bomb.getY() + i;
                    }
                    case 3 -> {
                        tileX = bomb.getX() - i;
                        tileY = bomb.getY();
                    }
                    case 4 -> {
                        tileX = bomb.getX() + i;
                        tileY = bomb.getY();
                    }
                }
                if (gamePanel.getTileManager().getTileAt(tileX, tileY) == null) {
                    break;
                } else {
                    Tile tile = gamePanel.getTileManager().getTileAt(tileX, tileY);
                    if (!tile.canExplode() || number == 3) {
                        if (tile instanceof Brick) {
                            Brick brick = (Brick) tile;
                            brick.setExplosionStage(number);
                            break;
                        } else if (tile instanceof Grass) {
                            Grass grass = (Grass) tile;
                            grass.setHasFlame(false);
                        } else {
                            break;
                        }
                    } else {
                        Grass grass = (Grass) tile;
                        grass.setHasFlame(true);
                        if (i == gamePanel.getBombManager().getRange()) {
                            gamePanel.drawTile(tileX, tileY, end_flame, g2);
                        } else {
                            gamePanel.drawTile(tileX, tileY, mid_flame, g2);
                        }
                    }
                }
            }
        }
    }

    public int getRange() {
        return range;
    }

    private final long START_TIME_BOMB_1 = 500000000;
    private final long START_TIME_BOMB_2 = 400000000;
    private final long START_TIME_EXPLOSION = 300000000;
    private final long START_TIME_EXPLOSION_1 = 200000000;
    private final long START_TIME_EXPLOSION_2 = 100000000;
    protected final long TIME_UNTIL_EXPLOSION_ENDS = 2000000000;

    private BufferedImage bomb_exploding[] = new BufferedImage[4];
    private BufferedImage bomb_exploded[] = new BufferedImage[4];
    private BufferedImage explosion_horizontal[] = new BufferedImage[4];
    private BufferedImage explosion_vertical[] = new BufferedImage[4];
    private BufferedImage explosion_horizontal_left_last[] = new BufferedImage[4];
    private BufferedImage explosion_horizontal_right_last[] = new BufferedImage[4];
    private BufferedImage explosion_vertical_top_last[] = new BufferedImage[4];
    private BufferedImage explosion_vertical_down_last[] = new BufferedImage[4];
}
