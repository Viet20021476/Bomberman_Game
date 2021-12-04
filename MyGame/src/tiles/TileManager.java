package tiles;

import java.awt.Graphics2D;
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

    GamePanel gamePanel;
    Tile[] tile_list;
    public char mapTile[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile_list = new Tile[10];
        mapTile = new char[13][16];
        getTileImage();
    }

    public void getTileImage() {
        try {
            tile_list[0] = new Tile();
            tile_list[0].image = ImageIO.read(new FileInputStream("res/tiles/grass.png"));

            tile_list[1] = new Tile();
            tile_list[1].image = ImageIO.read(new FileInputStream("res/tiles/brick.png"));
            tile_list[1].collision = true;

            tile_list[2] = new Tile();
            tile_list[2].image = ImageIO.read(new FileInputStream("res/tiles/wall.png"));
            tile_list[2].collision = true;
        } catch (IOException ex) {
            Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void draw(Graphics2D g2) {

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
                    mapTile[col][row] = s.charAt(i);
                    row++;
                }
                row = 0;
                col++;
            }

            int m = 0;
            int n = 48;

            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 16; j++) {
                    if (mapTile[i][j] == '#') {
                        g2.drawImage(tile_list[2].image, m, n, gamePanel.TILESIZE, gamePanel.TILESIZE, null);
                    } else if (mapTile[i][j] == ' ') {
                        g2.drawImage(tile_list[0].image, m, n, gamePanel.TILESIZE, gamePanel.TILESIZE, null);
                    } else if (mapTile[i][j] == '*') {
                        g2.drawImage(tile_list[1].image, m, n, gamePanel.TILESIZE, gamePanel.TILESIZE, null);
                    }
                    m += 48;
                }
                m = 0;
                n += 48;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
