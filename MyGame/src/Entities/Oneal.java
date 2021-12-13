package Entities;

import java.awt.Rectangle;
import mygame.GamePanel;

public class Oneal extends Enemy {
    
    public Oneal(GamePanel gamePanel, int x, int y) {
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
            int deltaX = gamePanel.getPlayer().getX() - getX();
            int deltaY = gamePanel.getPlayer().getY() - getY();
            if (Math.abs(deltaY) > Math.abs(deltaX)) {
                if (deltaX > 0) {
                    direction = "right";
                    targetX = getX() + GamePanel.TILESIZE;
                } else if (deltaX < 0) {
                    direction = "left";
                    targetX = getX() - GamePanel.TILESIZE;
                }
            } else {
                if (deltaY > 0) {
                    direction = "down";
                    targetY = getY() + GamePanel.TILESIZE;
                } else {
                    direction = "up";
                    targetY = getY() - GamePanel.TILESIZE;
                }
            }
        }
    }
}
