package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import mygame.GamePanel;
import mygame.KeyHandle;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandle keyHandler;
    
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandle keyHandle) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandle;
        screenX = gamePanel.screenWidth / 2 - 35;
        screenY = gamePanel.screenHeight / 2 - (GamePanel.TILESIZE);
        solidArea = new Rectangle(8, 12, 32, 32);
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = screenX;
        y = screenY;
        speed = 3;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up[0] = ImageIO.read(new FileInputStream("res/player/player_up.png"));
            up[1] = ImageIO.read(new FileInputStream("res/player/player_up1.png"));
            up[2] = ImageIO.read(new FileInputStream("res/player/player_up2.png"));
            down[0] = ImageIO.read(new FileInputStream("res/player/player_down.png"));
            down[1] = ImageIO.read(new FileInputStream("res/player/player_down1.png"));
            down[2] = ImageIO.read(new FileInputStream("res/player/player_down2.png"));
            left[0] = ImageIO.read(new FileInputStream("res/player/player_left.png"));
            left[1] = ImageIO.read(new FileInputStream("res/player/player_left1.png"));
            left[2] = ImageIO.read(new FileInputStream("res/player/player_left2.png"));
            right[0] = ImageIO.read(new FileInputStream("res/player/player_right.png"));
            right[1] = ImageIO.read(new FileInputStream("res/player/player_right1.png"));
            right[2] = ImageIO.read(new FileInputStream("res/player/player_right2.png"));
            dead[0] = ImageIO.read(new FileInputStream("res/player/player_dead.png"));
            dead[1] = ImageIO.read(new FileInputStream("res/player/player_dead.png"));
            dead[2] = ImageIO.read(new FileInputStream("res/player/player_dead.png"));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update() {
        if (keyHandler.upPressed == true || keyHandler.downPressed == true
                || keyHandler.leftPressed == true || keyHandler.rightPressed == true) {
            if (keyHandler.upPressed == true) {
                direction = "up";
            } else if (keyHandler.downPressed == true) {
                direction = "down";
            } else if (keyHandler.leftPressed == true) {
                direction = "left";
            } else if (keyHandler.rightPressed == true) {
                direction = "right";

            }

            collisionOn = false;
            gamePanel.collisionDetect.checkTile(this);

            if (!collisionOn) {
                switch (direction) {
                    case "up" -> y -= speed;
                    case "down" -> y += speed;
                    case "left" -> x -= speed;
                    case "right" -> x += speed;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                switch (spriteNum) {
                    case 0 -> spriteNum = 1;
                    case 1 -> spriteNum = 2;
                    case 2 -> spriteNum = 0;
                    default -> {
                    }
                }
                spriteCounter = 0;
            }
        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage bufferedImage = null;
        switch (direction) {
            case "up" -> {
                bufferedImage = up[spriteNum];
            }
            case "down" -> {
                bufferedImage = down[spriteNum];
            }
            case "left" -> {
                bufferedImage = left[spriteNum];
            }
            case "right" -> {
                bufferedImage = right[spriteNum];
            }
            default -> {
            }
        }
        g2.drawImage(bufferedImage, screenX, screenY, GamePanel.TILESIZE, GamePanel.TILESIZE, null);
    }
    
    public void increaseSpeed() {
        speed++;
    }
}
