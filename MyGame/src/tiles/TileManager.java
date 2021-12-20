package tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import mygame.GamePanel;

public class TileManager {

    private int WIDTH;
    private int HEIGHT;

    private ArrayList<BufferedImage> imageList = new ArrayList<>();
    GamePanel gamePanel;
    private Tile[][] tileMap;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        getTileImage();
    }

    private void getTileImage() {
        try {
            imageList.add(ImageIO.read(new FileInputStream("res/tiles/grass.png")));
            imageList.add(ImageIO.read(new FileInputStream("res/tiles/wall.png")));
            imageList.add(ImageIO.read(new FileInputStream("res/tiles/brick.png")));
            imageList.add(ImageIO.read(new FileInputStream("res/tiles/brick_exploded.png")));
            imageList.add(ImageIO.read(new FileInputStream("res/tiles/brick_exploded1.png")));
            imageList.add(ImageIO.read(new FileInputStream("res/tiles/brick_exploded2.png")));
            imageList.add(ImageIO.read(new FileInputStream("res/tiles/powerup_bombs.png")));
            imageList.add(ImageIO.read(new FileInputStream("res/tiles/powerup_flames.png")));
            imageList.add(ImageIO.read(new FileInputStream("res/tiles/powerup_speed.png")));
            imageList.add(ImageIO.read(new FileInputStream("res/tiles/portal.png")));
        } catch (IOException ex) {
            Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                int index = -1;
                if (tileMap[j][i] instanceof Wall) {
                    index = WALL;
                } else if (tileMap[j][i] instanceof Grass) {
                    index = GRASS;
                } else if (tileMap[j][i] instanceof PowerUp) {
                    PowerUp pow = (PowerUp) tileMap[j][i];
                    if (pow.isUsed()) {
                        tileMap[j][i] = new Grass();
                        index = GRASS;
                    } else if (tileMap[j][i] instanceof BombPowerUp) {
                        index = POWERUP_BOMBS;
                    } else if (tileMap[j][i] instanceof FlamePowerUp) {
                        index = POWERUP_FLAMES;
                    } else if (tileMap[j][i] instanceof SpeedPowerUp) {
                        index = POWERUP_SPEED;
                    } else if (tileMap[j][i] instanceof Portal) {
                        index = PORTAL;
                    }
                } else if (tileMap[j][i] instanceof Brick) {
                    Brick brick = (Brick) tileMap[j][i];
                    if (brick.getExplosionStage() == 0) {
                        index = BRICK;
                    } else {
                        gamePanel.drawTile(j, i, imageList.get(GRASS), g2);
                        if (brick.getExplosionStage() == 3) {
                            switch (brick.getPowerUp()) {
                                case "BOMB" -> {
                                    index = POWERUP_BOMBS;
                                    tileMap[j][i] = new BombPowerUp();
                                }
                                case "FLAME" -> {
                                    index = POWERUP_FLAMES;
                                    tileMap[j][i] = new FlamePowerUp();
                                }
                                case "SPEED" -> {
                                    index = POWERUP_SPEED;
                                    tileMap[j][i] = new SpeedPowerUp();
                                }
                                case "PORTAL" -> {
                                    index = PORTAL;
                                    tileMap[j][i] = new Portal();
                                }
                                default -> {
                                    index = GRASS;
                                    tileMap[j][i] = new Grass();
                                }
                            }
                        } else {
                            index = brick.getExplosionStage() + 3;
                        }
                    }
                }
                if (index != -1) {
                    gamePanel.drawTile(j, i, imageList.get(index), g2);
                }
            }
        }
    }

    public Tile getTileAt(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return null;
        } else {
            return tileMap[x][y];
        }
    }
    
    public void setWidth(int width) {
        this.WIDTH = width;
    }
    
    public void setHeight(int height) {
        this.HEIGHT = height;
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }
    
    public void setTileMap(Tile[][] tileMap) {
        this.tileMap = tileMap;
    }
    
    

    private final int GRASS = 0;
    private final int WALL = 1;
    private final int BRICK = 2;
    private final int BRICK_EXPLODED = 3;
    private final int BRICK_EXPLODED_1 = 4;
    private final int BRICK_EXPLODED_2 = 5;
    private final int POWERUP_BOMBS = 6;
    private final int POWERUP_FLAMES = 7;
    private final int POWERUP_SPEED = 8;
    private final int PORTAL = 9;
}
