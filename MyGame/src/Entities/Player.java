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

    KeyHandle keyHandler;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandle keyHandle) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandle;
        screenX = (gamePanel.screenWidth - 36) / 2;
        screenY = (gamePanel.screenHeight - 48) / 2;
        solidArea = new Rectangle(GamePanel.TILESIZE, GamePanel.TILESIZE, 36, 40);
        setDefaultValues();
    }

    public void setDefaultValues() {
        speed = 2;
        direction = "down";
    }

    @Override
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
                    case "up" ->
                        solidArea.y -= speed;
                    case "down" ->
                        solidArea.y += speed;
                    case "left" ->
                        solidArea.x -= speed;
                    case "right" ->
                        solidArea.x += speed;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                switch (spriteNum) {
                    case 0 ->
                        spriteNum = 1;
                    case 1 ->
                        spriteNum = 2;
                    case 2 ->
                        spriteNum = 0;
                    default -> {
                    }
                }
                spriteCounter = 0;
            }
        }

    }

    public void increaseSpeed() {
        speed++;
    }
}
