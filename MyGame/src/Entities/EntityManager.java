package Entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import mygame.GamePanel;
import tiles.Grass;

public class EntityManager {
    private ArrayList<Entity> entityList = new ArrayList<>();
    private GamePanel gamePanel;
    
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
            genericEnemyDead[0] = ImageIO.read(new FileInputStream("res/enemies/mob_dead1.png"));
            genericEnemyDead[1] = ImageIO.read(new FileInputStream("res/enemies/mob_dead2.png"));
            genericEnemyDead[2] = ImageIO.read(new FileInputStream("res/enemies/mob_dead3.png"));
            specificEnemyDead[BALLOOM] = ImageIO.read(new FileInputStream("res/enemies/balloom_dead.png"));
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
            e.update();
            int TileX = e.getCenterX() / GamePanel.TILESIZE;
            int TileY = e.getCenterY() / GamePanel.TILESIZE;
            try {
                Grass grass = (Grass) gamePanel.getTileManager().getTileAt(TileX, TileY);
                if (grass.hasFlame()) {
                    entityList.remove(i);
                    i--;
                }
            } catch (NullPointerException ex) {
                
            }
        }
    }
    
    public void draw (Graphics2D g2) {
        for (Entity e: entityList) {
            e.draw(g2);
        }
    }
    
    protected BufferedImage[][] enemyLeft = new BufferedImage[2][3];
    protected BufferedImage[][] enemyRight = new BufferedImage[2][3];
    protected BufferedImage[] genericEnemyDead = new BufferedImage[3];
    protected BufferedImage[] specificEnemyDead = new BufferedImage[2];
    
    private final int BALLOOM = 0;
    private final int ONEAL = 1;
}
