package Entities;

import java.awt.Rectangle;
import java.util.Random;
import mygame.GamePanel;
import tiles.Tile;

public class Balloom extends Enemy {

    public Balloom(GamePanel gamePanel, int x, int y) {
        this.gamePanel = gamePanel;
        solidArea = new Rectangle(x, y, 40, 40);
        setDefaultValues();
    }

    public void setDefaultValues() {
        speed = 2;
        direction = "down";
    }

    @Override
    protected void behavior() {
        if (isInTileCenter()) {
            int tileX = getX() / GamePanel.TILESIZE;
            int tileY = getY() / GamePanel.TILESIZE;
            boolean availble[] = new boolean[4];
            int amountAvailble = 0;
            for (int i = 0; i < 4; i++) {
                availble[i] = false;
            }
            Tile tileUp = gamePanel.getTileManager().getTileAt(tileX, tileY - 1);
            if (!tileUp.isCollision()) {
                availble[0] = true;
                amountAvailble++;
            }
            Tile tileDown = gamePanel.getTileManager().getTileAt(tileX, tileY + 1);
            if (!tileDown.isCollision()) {
                availble[1] = true;
                amountAvailble++;
            }
            Tile tileLeft = gamePanel.getTileManager().getTileAt(tileX - 1, tileY);
            if (!tileLeft.isCollision()) {
                availble[2] = true;
                amountAvailble++;
            }
            Tile tileRight = gamePanel.getTileManager().getTileAt(tileX + 1, tileY);
            if (!tileRight.isCollision()) {
                availble[3] = true;
                amountAvailble++;
            }
            Random random = new Random();
            int randomInt = random.nextInt(amountAvailble);
            int i = 0;
            int j = 1;
            for (; j <= 4; j++) {
                if (availble[j - 1]) {
                    i++;
                }
                if (i == randomInt) {
                    break;
                }
            }
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
    
    private boolean isInTileCenter() {
        return getX() % 48 == 0
                && getY() % 48 == 0;
    }
}
