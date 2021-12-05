package Entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

    public int x;
    public int y;
    protected int speed;
    
    protected BufferedImage[] up = new BufferedImage[3];
    protected BufferedImage[] down = new BufferedImage[3];
    protected BufferedImage[] left = new BufferedImage[3];
    protected BufferedImage[] right = new BufferedImage[3];
    protected BufferedImage[] dead = new BufferedImage[3];

    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    protected Rectangle solidArea;
    public boolean collisionOn = false;

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public Rectangle getSolidArea() {
        return solidArea;
    }
    
    public int getSpeed() {
        return speed;
    }
}
