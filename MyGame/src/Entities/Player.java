package Entities;

import java.awt.Rectangle;
import mygame.GamePanel;
import mygame.KeyHandle;

public class Player extends Entity {

    private KeyHandle keyHandler;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, int x, int y) {
        this.gamePanel = gamePanel;
        this.keyHandler = gamePanel.getKeyHandle();
        screenX = (gamePanel.getScreenWidth() - 36) / 2;
        screenY = (gamePanel.getScreenHeight() - 48) / 2;
        solidArea = new Rectangle(x, y, 36, 40);
        setDefaultValues();
    }

    public void setDefaultValues() {
        speed = 2;
        direction = "down";
    }

    @Override
    protected void setDirection() {
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
        } 
    }
    
    @Override
    public void setXY() {
        if (keyHandler.upPressed == true || keyHandler.downPressed == true
                || keyHandler.leftPressed == true || keyHandler.rightPressed == true) {
            collisionOn = false;
            gamePanel.getCollisionDetect().checkTile(this);

            if (!collisionOn) {
                switch (direction) {
                    case "up" ->
                        setY(getY() - speed);
                    case "down" ->
                        setY(getY() + speed);
                    case "left" ->
                        setX(getX() - speed);
                    case "right" ->
                        setX(getX() + speed);
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
