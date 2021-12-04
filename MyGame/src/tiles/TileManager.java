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
    private ArrayList<BufferedImage> imageList = new ArrayList<>();
    GamePanel gamePanel;
    private Tile[][] tileMap = new Tile[16][13];
    
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
                while (s.length() < 16) {
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
                            tileMap[col][row] = new Brick();
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
        int m = 0;
        int n = GamePanel.TILESIZE;

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 16; j++) {
                if (tileMap[j][i] instanceof Wall) {
                    g2.drawImage(imageList.get(WALL), m, n, GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                } else if (tileMap[j][i] instanceof Grass) {
                    g2.drawImage(imageList.get(GRASS), m, n, GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                } else if (tileMap[j][i] instanceof Brick) {
                    Brick brick = (Brick) tileMap[j][i];
                    if (brick.getExplosionStage() + 3 == 6) {
                        tileMap[j][i] = new Grass();
                        g2.drawImage(imageList.get(GRASS), m, n, GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                    } else {
                        if (brick.getExplosionStage() + 3 != BRICK) {
                        g2.drawImage(imageList.get(GRASS), m, n, GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                        } 
                        g2.drawImage(imageList.get(brick.getExplosionStage() + 3), m, n, 
                                GamePanel.TILESIZE, GamePanel.TILESIZE, null);
                    }
                }
                m += GamePanel.TILESIZE;
            }
            m = 0;
            n += GamePanel.TILESIZE;
        }
    }
    
    public Tile getTileAt(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return null;
        } else {
            return tileMap[x][y];
        }
    }
    
    private final int WIDTH = 16;
    private final int HEIGHT = 13;
    
    private final int GRASS = 0;
    private final int WALL = 1;
    private final int BRICK = 2;
    private final int BRICK_EXPLODED = 3;
    private final int BRICK_EXPLODED_1 = 4;
    private final int BRICK_EXPLODED_2 = 5;
}
