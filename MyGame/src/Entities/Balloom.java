package Entities;

import java.awt.Rectangle;
import java.util.Random;
import mygame.GamePanel;

public class Balloom extends Enemy {

    public Balloom(GamePanel gamePanel, int x, int y) {
        this.gamePanel = gamePanel;
        solidArea = new Rectangle(x, y, 47, 47);
        setDefaultValues();
    }

    public void setDefaultValues() {
        speed = 2;
        direction = "down";
    }

    @Override
    protected void setDirection() {
        if (isInTileCenter()) {
            Random random = new Random();
            int j = random.nextInt(4) + 1;
            switch (j) {
                case 1 -> {
                    direction = "up";
                    targetY = getY() - GamePanel.TILESIZE;
                }
                case 2 -> {
                    direction = "down";
                    targetY = getY() + GamePanel.TILESIZE;
                }
                case 3 -> {
                    direction = "left";
                    targetX = getX() - GamePanel.TILESIZE;
                }
                case 4 -> {
                    direction = "right";
                    targetX = getX() + GamePanel.TILESIZE;
                }
            }
        }
    }
}
