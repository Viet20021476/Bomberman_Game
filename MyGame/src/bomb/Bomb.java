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
    private boolean collision = false;
    private int x;
    private int y;
    private boolean alreadyBombed = false;
    private long explosionTime = 0;
    private int range = 1;

    public Bomb(TileManager tileManager) {
        this.tileManager = tileManager;
        getBombImage();
    }
    
    private void getBombImage() {
        try {
            bomb = ImageIO.read(new FileInputStream("res/bomb/bomb.png"));
            bomb_1 = ImageIO.read(new FileInputStream("res/bomb/bomb_1.png"));
            bomb_2 = ImageIO.read(new FileInputStream("res/bomb/bomb_2.png"));
            bomb_exploded = ImageIO.read(new FileInputStream("res/bomb/bomb_exploded.png"));
            bomb_exploded_1 = ImageIO.read(new FileInputStream("res/bomb/bomb_exploded1.png"));
            bomb_exploded_2 = ImageIO.read(new FileInputStream("res/bomb/bomb_exploded2.png"));
            explosion_horizontal = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal.png"));
            explosion_horizontal_1 = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal1.png"));
            explosion_horizontal_2 = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal2.png"));
            explosion_vertical = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical.png"));
            explosion_vertical_1 = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical1.png"));
            explosion_vertical_2 = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical2.png"));
            explosion_horizontal_left_last = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal_left_last.png"));
            explosion_horizontal_left_last_1 = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal_left_last1.png"));
            explosion_horizontal_left_last_2 = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal_left_last2.png"));
            explosion_horizontal_right_last = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal_right_last.png"));
            explosion_horizontal_right_last_1 = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal_right_last1.png"));
            explosion_horizontal_right_last_2 = ImageIO.read(new FileInputStream("res/bomb/explosion_horizontal_right_last2.png"));
            explosion_vertical_top_last = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical_top_last.png"));
            explosion_vertical_top_last_1 = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical_top_last1.png"));
            explosion_vertical_top_last_2 = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical_top_last2.png"));
            explosion_vertical_down_last = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical_down_last.png"));
            explosion_vertical_down_last_1 = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical_down_last1.png"));
            explosion_vertical_down_last_2 = ImageIO.read(new FileInputStream("res/bomb/explosion_vertical_down_last2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Bomb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int fitTilePosition(int coord) {
        return coord / GamePanel.TILESIZE * GamePanel.TILESIZE;
    }
    
    private void drawExplosion(int number, Graphics2D g2) {
        BufferedImage vertical_flame;
        BufferedImage horizontal_flame;
        BufferedImage top_flame_end;
        BufferedImage bottom_flame_end;
        BufferedImage left_flame_end;
        BufferedImage right_flame_end;
        switch (number) {
            case 0:
                vertical_flame = explosion_vertical;
                horizontal_flame = explosion_horizontal;
                top_flame_end = explosion_vertical_top_last;
                bottom_flame_end = explosion_vertical_down_last;
                left_flame_end = explosion_horizontal_left_last;
                right_flame_end = explosion_horizontal_right_last;
                break;
            case 1:
                vertical_flame = explosion_vertical_1;
                horizontal_flame = explosion_horizontal_1;
                top_flame_end = explosion_vertical_top_last_1;
                bottom_flame_end = explosion_vertical_down_last_1;
                left_flame_end = explosion_horizontal_left_last_1;
                right_flame_end = explosion_horizontal_right_last_1;
                break;
            case 2:
                vertical_flame = explosion_vertical_2;
                horizontal_flame = explosion_horizontal_2;
                top_flame_end = explosion_vertical_top_last_2;
                bottom_flame_end = explosion_vertical_down_last_2;
                left_flame_end = explosion_horizontal_left_last_2;
                right_flame_end = explosion_horizontal_right_last_2;
                break;
            default:
                vertical_flame = explosion_vertical_2;
                horizontal_flame = explosion_horizontal_2;
                top_flame_end = explosion_vertical_top_last_2;
                bottom_flame_end = explosion_vertical_down_last_2;
                left_flame_end = explosion_horizontal_left_last_2;
                right_flame_end = explosion_horizontal_right_last_2;
        }
        
        // check right tile
        for (int i = 1; i <= range; i++) {
            int tileX = x / GamePanel.TILESIZE + i;
            int tileY = y / GamePanel.TILESIZE - 1;
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
                } else if (i == range) {
                    g2.drawImage(right_flame_end, x + GamePanel.TILESIZE * i, y, 
                            GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                } else {
                    g2.drawImage(horizontal_flame, x + GamePanel.TILESIZE * i, y, 
                            GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                }
            }
        }
        
        // check left tile
        for (int i = 1; i <= range; i++) {
            int tileX = x / GamePanel.TILESIZE - i;
            int tileY = y / GamePanel.TILESIZE - 1;
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
                } else if (i == range) {
                    g2.drawImage(left_flame_end, x - GamePanel.TILESIZE * i, y, 
                            GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                } else {
                    g2.drawImage(horizontal_flame, x - GamePanel.TILESIZE * i, y, 
                            GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                }
            }
        }
        
        // check above tile
        for (int i = 1; i <= range; i++) {
            int tileX = x / GamePanel.TILESIZE;
            int tileY = y / GamePanel.TILESIZE - i - 1;
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
                } else if (i == range) {
                    g2.drawImage(top_flame_end, x, y - GamePanel.TILESIZE * i, 
                            GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                } else {
                    g2.drawImage(vertical_flame, x, y - GamePanel.TILESIZE * i, 
                            GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                }
            }
        }
        
        // check below tile
        for (int i = 1; i <= range; i++) {
            int tileX = x / GamePanel.TILESIZE;
            int tileY = y / GamePanel.TILESIZE + i - 1;
            if (tileManager.getTileAt(tileX, tileY) == null) {
                break;
            } else {
                Tile tile = tileManager.getTileAt(tileX, tileY);
                if (!tile.canExplode() || number == 3) {
                    if (tile instanceof Brick) {
                        Brick brick = (Brick) tileManager.getTileAt(tileX, tileY);
                        brick.setExplosionStage(number);
                    }
                    break;
                } else if (i == range) {
                    g2.drawImage(bottom_flame_end, x, y + GamePanel.TILESIZE * i, 
                            GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                } else {
                    g2.drawImage(vertical_flame, x, y + GamePanel.TILESIZE * i, 
                            GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                }
            }
        }
    }
    
    public void draw(Graphics2D g2, GamePanel gamePanel) {
        BufferedImage image;
        if (gamePanel.keyHandle.bombed == true && !alreadyBombed) {
            x = fitTilePosition(gamePanel.player.x + 5);
            y = fitTilePosition(gamePanel.player.y + 10);
            image = bomb;
            g2.drawImage(image, x, y, GamePanel.TILESIZE, GamePanel.TILESIZE, null);
            
            alreadyBombed = true;
            explosionTime = System.nanoTime() + TIME_UNTIL_EXPLOSION_ENDS;
            
        } else if (alreadyBombed && explosionTime - System.nanoTime() > 0) {
            if (explosionTime - System.nanoTime() > START_TIME_BOMB_1) {
                image = bomb;
            } else if (explosionTime - System.nanoTime() > START_TIME_BOMB_2) {
                image = bomb_1;
            } else if (explosionTime - System.nanoTime() > START_TIME_EXPLOSION) {
                image = bomb_2;
            } else if (explosionTime - System.nanoTime() > START_TIME_EXPLOSION_1){
                image = bomb_exploded;
                drawExplosion(0, g2);
            } else if (explosionTime - System.nanoTime() > START_TIME_EXPLOSION_2){
                image = bomb_exploded_1;
                drawExplosion(1, g2);
            } else {
                image = bomb_exploded_2;
                drawExplosion(2, g2);
            }
            g2.drawImage(image, x, y, GamePanel.TILESIZE, GamePanel.TILESIZE, null);
        } else {
            alreadyBombed = false;
            drawExplosion(3, g2);
        }
    }
    
    public boolean isAlreadyBombed() {
        return alreadyBombed;
    }
    
    private final long START_TIME_BOMB_1 = 500000000;
    private final long START_TIME_BOMB_2 = 400000000;
    private final long START_TIME_EXPLOSION = 300000000;
    private final long START_TIME_EXPLOSION_1 = 200000000;
    private final long START_TIME_EXPLOSION_2 = 100000000;
    private final long TIME_UNTIL_EXPLOSION_ENDS = 800000000;
    
    private BufferedImage bomb;
    private BufferedImage bomb_1;
    private BufferedImage bomb_2;
    private BufferedImage bomb_exploded;
    private BufferedImage bomb_exploded_1;
    private BufferedImage bomb_exploded_2;
    private BufferedImage explosion_horizontal;
    private BufferedImage explosion_horizontal_1;
    private BufferedImage explosion_horizontal_2;
    private BufferedImage explosion_vertical;
    private BufferedImage explosion_vertical_1;
    private BufferedImage explosion_vertical_2;
    private BufferedImage explosion_horizontal_left_last;
    private BufferedImage explosion_horizontal_left_last_1;
    private BufferedImage explosion_horizontal_left_last_2;
    private BufferedImage explosion_horizontal_right_last;
    private BufferedImage explosion_horizontal_right_last_1;
    private BufferedImage explosion_horizontal_right_last_2;
    private BufferedImage explosion_vertical_top_last;
    private BufferedImage explosion_vertical_top_last_1;
    private BufferedImage explosion_vertical_top_last_2;
    private BufferedImage explosion_vertical_down_last;
    private BufferedImage explosion_vertical_down_last_1;
    private BufferedImage explosion_vertical_down_last_2;
}
