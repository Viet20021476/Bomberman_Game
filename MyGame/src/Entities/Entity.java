package Entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import mygame.GamePanel;

public class Entity {

    protected String direction;
    protected int speed;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    protected Rectangle solidArea;
    public boolean collisionOn = false;

    public String getDirection() {
        return direction;
    }
    
    public int getX() {
        return solidArea.x;
    }
    
    public void setX(int x) {
        this.solidArea.x = x;
    }
    
    public void setY(int y) {
        this.solidArea.y = y;
    }
    
    public int getY() {
        return solidArea.y;
    }
    
    public int getCenterX() {
        return solidArea.x + GamePanel.TILESIZE / 2;
    }
    
    public int getCenterY() {
        return solidArea.y + GamePanel.TILESIZE / 2;
    }
    
    public Rectangle getSolidArea() {
        return solidArea;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    protected BufferedImage[] up = new BufferedImage[3];
    protected BufferedImage[] down = new BufferedImage[3];
    protected BufferedImage[] left = new BufferedImage[3];
    protected BufferedImage[] right = new BufferedImage[3];
    protected BufferedImage[] dead = new BufferedImage[4];
}
