package mygame;

import Entities.Entity;
import tiles.Tile;

public class CollisionDetect {

    GamePanel gamePanel;

    public CollisionDetect(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int leftX = entity.getX() + entity.getSolidArea().x;
        int rightX = entity.getX() + entity.getSolidArea().x + entity.getSolidArea().width - 20;
        int topY = entity.getY() + entity.getSolidArea().y + 20;
        int bottomY = entity.getY() + entity.getSolidArea().y + entity.getSolidArea().height + 10;

        int leftCol = leftX / GamePanel.TILESIZE;
        int rightCol = rightX / GamePanel.TILESIZE;
        int topRow = (topY - 10) / GamePanel.TILESIZE;
        int bottomRow = (bottomY - 20) / GamePanel.TILESIZE;

        Tile tile1;
        Tile tile2;

        switch (entity.direction) {
            case "up":
                topRow = (topY - entity.speed) / GamePanel.TILESIZE;
                tile1 = gamePanel.tileManager.getTileAt(leftCol, topRow - 1);
                tile2 = gamePanel.tileManager.getTileAt(rightCol, topRow - 1);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                bottomRow = (bottomY + entity.speed - 10) / GamePanel.TILESIZE;
                tile1 = gamePanel.tileManager.getTileAt(leftCol, bottomRow - 1);
                tile2 = gamePanel.tileManager.getTileAt(rightCol, bottomRow - 1);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                leftCol = (leftX - entity.speed) / GamePanel.TILESIZE;
                tile1 = gamePanel.tileManager.getTileAt(leftCol, topRow - 1);
                tile2 = gamePanel.tileManager.getTileAt(leftCol, bottomRow - 1);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                rightCol = (rightX + entity.speed) / GamePanel.TILESIZE;
                tile1 = gamePanel.tileManager.getTileAt(rightCol, topRow - 1);
                tile2 = gamePanel.tileManager.getTileAt(rightCol, bottomRow - 1);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.collisionOn = true;
                }
                if (entity.getX() + entity.getSolidArea().width + entity.speed == gamePanel.screenWidth) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
}
