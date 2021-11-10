package Entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

    public int x;
    public int y;
    public int speed;

    public BufferedImage up1;
    public BufferedImage up2;
    public BufferedImage up3;
    public BufferedImage up4;
    public BufferedImage up5;
    public BufferedImage up6;
    public BufferedImage up7;

    public BufferedImage down1;
    public BufferedImage down2;
    public BufferedImage down3;
    public BufferedImage down4;
    public BufferedImage down5;
    public BufferedImage down6;
    public BufferedImage down7;

    public BufferedImage left1;
    public BufferedImage left2;
    public BufferedImage left3;
    public BufferedImage left4;
    public BufferedImage left5;
    public BufferedImage left6;
    public BufferedImage left7;

    public BufferedImage right1;
    public BufferedImage right2;
    public BufferedImage right3;
    public BufferedImage right4;
    public BufferedImage right5;
    public BufferedImage right6;
    public BufferedImage right7;

    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public boolean collisionOn = false;

}
