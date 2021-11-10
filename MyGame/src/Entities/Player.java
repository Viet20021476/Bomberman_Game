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

    public Player(GamePanel gamePanel, KeyHandle keyHandle) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandle;
        solidArea = new Rectangle(8, 16, 32, 32);
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 3;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(new FileInputStream("res/player/up_1.png"));
            up2 = ImageIO.read(new FileInputStream("res/player/up_2.png"));
            up3 = ImageIO.read(new FileInputStream("res/player/up_3.png"));
            up4 = ImageIO.read(new FileInputStream("res/player/up_4.png"));
            up5 = ImageIO.read(new FileInputStream("res/player/up_5.png"));
            up6 = ImageIO.read(new FileInputStream("res/player/up_6.png"));
            up7 = ImageIO.read(new FileInputStream("res/player/up_7.png"));
            down1 = ImageIO.read(new FileInputStream("res/player/down_1.png"));
            down2 = ImageIO.read(new FileInputStream("res/player/down_2.png"));
            down3 = ImageIO.read(new FileInputStream("res/player/down_3.png"));
            down4 = ImageIO.read(new FileInputStream("res/player/down_4.png"));
            down5 = ImageIO.read(new FileInputStream("res/player/down_5.png"));
            down6 = ImageIO.read(new FileInputStream("res/player/down_6.png"));
            down7 = ImageIO.read(new FileInputStream("res/player/down_7.png"));
            left1 = ImageIO.read(new FileInputStream("res/player/left_1.png"));
            left2 = ImageIO.read(new FileInputStream("res/player/left_2.png"));
            left3 = ImageIO.read(new FileInputStream("res/player/left_3.png"));
            left4 = ImageIO.read(new FileInputStream("res/player/left_4.png"));
            left5 = ImageIO.read(new FileInputStream("res/player/left_5.png"));
            left6 = ImageIO.read(new FileInputStream("res/player/left_6.png"));
            left7 = ImageIO.read(new FileInputStream("res/player/left_7.png"));
            right1 = ImageIO.read(new FileInputStream("res/player/right_1.png"));
            right2 = ImageIO.read(new FileInputStream("res/player/right_2.png"));
            right3 = ImageIO.read(new FileInputStream("res/player/right_3.png"));
            right4 = ImageIO.read(new FileInputStream("res/player/right_4.png"));
            right5 = ImageIO.read(new FileInputStream("res/player/right_5.png"));
            right6 = ImageIO.read(new FileInputStream("res/player/right_6.png"));
            right7 = ImageIO.read(new FileInputStream("res/player/right_7.png"));

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
                    case "up":
                        y -= speed;
                        break;
                    case "down":
                        y += speed;
                        break;
                    case "left":
                        x -= speed;
                        break;
                    case "right":
                        x += speed;
                        break;

                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 5;
                } else if (spriteNum == 5) {
                    spriteNum = 6;
                } else if (spriteNum == 6) {
                    spriteNum = 7;
                } else if (spriteNum == 7) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage bufferedImage = null;
        switch (direction) {
            case "up" -> {
                switch (spriteNum) {
                    case 1 ->
                        bufferedImage = up1;
                    case 2 ->
                        bufferedImage = up2;
                    case 3 ->
                        bufferedImage = up3;
                    case 4 ->
                        bufferedImage = up4;
                    case 5 ->
                        bufferedImage = up5;
                    case 6 ->
                        bufferedImage = up6;
                    case 7 ->
                        bufferedImage = up7;
                    default -> {
                    }
                }
            }
            case "down" -> {
                switch (spriteNum) {
                    case 1 ->
                        bufferedImage = down1;
                    case 2 ->
                        bufferedImage = down2;
                    case 3 ->
                        bufferedImage = down3;
                    case 4 ->
                        bufferedImage = down4;
                    case 5 ->
                        bufferedImage = down5;
                    case 6 ->
                        bufferedImage = down6;
                    case 7 ->
                        bufferedImage = down7;
                    default -> {
                    }
                }
            }
            case "left" -> {
                switch (spriteNum) {
                    case 1 ->
                        bufferedImage = left1;
                    case 2 ->
                        bufferedImage = left2;
                    case 3 ->
                        bufferedImage = left3;
                    case 4 ->
                        bufferedImage = left4;
                    case 5 ->
                        bufferedImage = left5;
                    case 6 ->
                        bufferedImage = left6;
                    case 7 ->
                        bufferedImage = left7;
                    default -> {
                    }
                }
            }
            case "right" -> {
                switch (spriteNum) {
                    case 1 ->
                        bufferedImage = right1;
                    case 2 ->
                        bufferedImage = right2;
                    case 3 ->
                        bufferedImage = right3;
                    case 4 ->
                        bufferedImage = right4;
                    case 5 ->
                        bufferedImage = right5;
                    case 6 ->
                        bufferedImage = right6;
                    case 7 ->
                        bufferedImage = right7;
                    default -> {
                    }
                }
            }
            default -> {
            }
        }
        g2.drawImage(bufferedImage, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }

}
