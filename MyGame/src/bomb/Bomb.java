package bomb;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import mygame.GamePanel;
import tiles.Tile;
import tiles.Brick;
import tiles.TileManager;

public class Bomb {
    private TileManager tileManager;
    private BombManager bombManager;
    private boolean collision = false;
    private int x;
    private int y;
    private boolean expired = false;
    private long explosionTime;

    public Bomb(TileManager tileManager, BombManager bombManager, int x, int y) {
        this.tileManager = tileManager;
        this.bombManager = bombManager;
        this.x = x;
        this.y = y;
        this.explosionTime = System.nanoTime() + TIME_UNTIL_EXPLOSION_ENDS;
        getBombImage();
    }
    
    private void getBombImage() {
        try {
            bomb[0] = ImageIO.read(new FileInputStream("res/bomb/bomb.png"));
            bomb[1] = ImageIO.read(new FileInputStream("res/bomb/bomb_1.png"));
            bomb[2] = ImageIO.read(new FileInputStream("res/bomb/bomb_2.png"));
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
    
    private void drawExplosion(int number, Graphics2D g2) {
        BufferedImage mid_flame;
        BufferedImage end_flame;
        for (int j = 1; j <= 4; j++) {
            switch (j) {
                case 1:
                    mid_flame = explosion_vertical[number];
                    end_flame = explosion_vertical_top_last[number];
                    break;
                case 2:
                    mid_flame = explosion_vertical[number];
                    end_flame = explosion_vertical_down_last[number];
                    break;
                case 3:
                    mid_flame = explosion_horizontal[number];
                    end_flame = explosion_horizontal_left_last[number];
                    break;
                case 4:
                    mid_flame = explosion_horizontal[number];
                    end_flame = explosion_horizontal_right_last[number];
                    break;
                default:
                    mid_flame = bomb_exploded[0];
                    end_flame = bomb_exploded[0];
            }
            int tileX = 0;
            int tileY = 0;
            int coordsX = 0;
            int coordsY = 0;
            for (int i = 1; i <= bombManager.getRange(); i++) {
                switch (j) {
                    case 1:
                        tileX = x / GamePanel.TILESIZE;
                        tileY = y / GamePanel.TILESIZE - i - 1;
                        coordsX = x;
                        coordsY = y - GamePanel.TILESIZE * i;
                        break;
                    case 2:
                        tileX = x / GamePanel.TILESIZE;
                        tileY = y / GamePanel.TILESIZE + i - 1;
                        coordsX = x;
                        coordsY = y + GamePanel.TILESIZE * i;
                        break;
                    case 3:
                        tileX = x / GamePanel.TILESIZE - i;
                        tileY = y / GamePanel.TILESIZE - 1;
                        coordsX = x - GamePanel.TILESIZE * i;
                        coordsY = y;
                        break;
                    case 4:
                        tileX = x / GamePanel.TILESIZE + i;
                        tileY = y / GamePanel.TILESIZE - 1;
                        coordsX = x + GamePanel.TILESIZE * i;
                        coordsY = y;
                        break;
                }
                if (tileManager.getTileAt(tileX, tileY) == null) {
                    break;
                } else {
                    Tile tile = tileManager.getTileAt(tileX, tileY);
                    if (!tile.canExplode() || number == 3) {
                        if (tile instanceof Brick) {
                            Brick brick = (Brick) tile;
                            brick.setExplosionStage(number);
                        }
                        break;
                    } else if (i == bombManager.getRange()) {
                        g2.drawImage(end_flame, coordsX, coordsY, 
                                GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                    } else {
                        g2.drawImage(mid_flame, coordsX, coordsY, 
                                GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                    }
                }
            }
        }
    }
    
    public void draw(Graphics2D g2, GamePanel gamePanel) {
        BufferedImage image;
        if (explosionTime - System.nanoTime() > 0) {
            if (explosionTime - System.nanoTime() > START_TIME_BOMB_1) {
                image = bomb[0];
            } else if (explosionTime - System.nanoTime() > START_TIME_BOMB_2) {
                image = bomb[1];
            } else if (explosionTime - System.nanoTime() > START_TIME_EXPLOSION) {
                image = bomb[2];
            } else if (explosionTime - System.nanoTime() > START_TIME_EXPLOSION_1){
                image = bomb_exploded[0];
                drawExplosion(0, g2);
            } else if (explosionTime - System.nanoTime() > START_TIME_EXPLOSION_2){
                image = bomb_exploded[1];
                drawExplosion(1, g2);
            } else {
                image = bomb_exploded[2];
                drawExplosion(2, g2);
            }
            gamePanel.drawTile(x, y, image, g2);
        } else {
            expired = true;
            drawExplosion(3, g2);
        }
    }
    
    public boolean isExpired() {
        return expired;
    }
    
    private final long START_TIME_BOMB_1 = 500000000;
    private final long START_TIME_BOMB_2 = 400000000;
    private final long START_TIME_EXPLOSION = 300000000;
    private final long START_TIME_EXPLOSION_1 = 200000000;
    private final long START_TIME_EXPLOSION_2 = 100000000;
    private final long TIME_UNTIL_EXPLOSION_ENDS = 2000000000;
    
    private BufferedImage bomb[] = new BufferedImage[4];
    private BufferedImage bomb_exploded[] = new BufferedImage[4];
    private BufferedImage explosion_horizontal[] = new BufferedImage[4];
    private BufferedImage explosion_vertical[] = new BufferedImage[4];
    private BufferedImage explosion_horizontal_left_last[] = new BufferedImage[4];
    private BufferedImage explosion_horizontal_right_last[] = new BufferedImage[4];
    private BufferedImage explosion_vertical_top_last[] = new BufferedImage[4];
    private BufferedImage explosion_vertical_down_last[] = new BufferedImage[4];
}
