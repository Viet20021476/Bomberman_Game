package tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import mygame.GamePanel;

public class TileManager {

    private final int WIDTH = 31;
    private final int HEIGHT = 14;

    private ArrayList<BufferedImage> imageList = new ArrayList<>();
    GamePanel gamePanel;
    private Tile[][] tileMap = new Tile[WIDTH][HEIGHT];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        getTileImage();
        loadTileMap();
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
        } catch (IOException ex) {
            Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadTileMap() {
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

    public void draw(Graphics2D g2) {
        for (int i = 0; i < HEIGHT - 1; i++) {
            for (int j = 0; j < WIDTH; j++) {
                int index = -1;
                if (tileMap[j][i] instanceof Wall) {
                    index = WALL;
                } else if (tileMap[j][i] instanceof Grass) {
                    index = GRASS;
                } else if (tileMap[j][i] instanceof BombPowerUp) {
                    index = POWERUP_BOMBS;
                } else if (tileMap[j][i] instanceof FlamePowerUp) {
                    index = POWERUP_FLAMES;
                } else if (tileMap[j][i] instanceof SpeedPowerUp) {
                    index = POWERUP_SPEED;
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

    private final int GRASS = 0;
    private final int WALL = 1;
    private final int BRICK = 2;
    private final int BRICK_EXPLODED = 3;
    private final int BRICK_EXPLODED_1 = 4;
    private final int BRICK_EXPLODED_2 = 5;
    private final int POWERUP_BOMBS = 6;
    private final int POWERUP_FLAMES = 7;
    private final int POWERUP_SPEED = 8;
}
