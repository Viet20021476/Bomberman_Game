package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import mygame.GamePanel;

public class Balloom extends Entity {

    GamePanel gamePanel;
    private int movingCounter = 0;

    public Balloom(GamePanel gamePanel, int x, int y) {
        this.gamePanel = gamePanel;
        solidArea = new Rectangle(x, y, 40, 40);
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        speed = 2;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            left[0] = ImageIO.read(new FileInputStream("res/enemies/balloom_left1.png"));
            left[1] = ImageIO.read(new FileInputStream("res/enemies/balloom_left2.png"));
            left[2] = ImageIO.read(new FileInputStream("res/enemies/balloom_left3.png"));
            right[0] = ImageIO.read(new FileInputStream("res/enemies/balloom_right1.png"));
            right[1] = ImageIO.read(new FileInputStream("res/enemies/balloom_right2.png"));
            right[2] = ImageIO.read(new FileInputStream("res/enemies/balloom_right3.png"));
            dead[0] = ImageIO.read(new FileInputStream("res/enemies/mob_dead1.png"));
            dead[1] = ImageIO.read(new FileInputStream("res/enemies/mob_dead2.png"));
            dead[2] = ImageIO.read(new FileInputStream("res/enemies/mob_dead3.png"));
            dead[3] = ImageIO.read(new FileInputStream("res/enemies/balloom_dead.png"));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update() {
        movingCounter++;
        if (movingCounter == 50) {
            Random random = new Random();
            int randomInt = random.nextInt(4);
            switch (randomInt) {
                case 1:
                    direction = "up";
                    break;
                case 2:
                    direction = "down";
                    break;
                case 3:
                    direction = "left";
                    break;
                case 4:
                    direction = "right";
                    break;
                default:
                    break;
            }
            movingCounter = 0;
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

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage bufferedImage = null;
        switch (direction) {
            case "up" -> {
                bufferedImage = left[spriteNum];
            }
            case "down" -> {
                bufferedImage = right[spriteNum];
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
        gamePanel.draw(getX(), getY(), bufferedImage, g2);
    }
}
