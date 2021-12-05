package mygame;

import Entities.Entity;
import tiles.Tile;

public class CollisionDetect {

    GamePanel gamePanel;

    public CollisionDetect(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int leftX = entity.getX() + entity.getSolidArea().x - 5;
        int rightX = entity.getX() + entity.getSolidArea().x + entity.getSolidArea().width - 10;
        int topY = entity.getY() + entity.getSolidArea().y;
        int bottomY = entity.getY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int leftCol = leftX / gamePanel.TILESIZE;
        int rightCol = rightX / gamePanel.TILESIZE;
        int topRow = (topY - 10) / gamePanel.TILESIZE;
        int bottomRow = (bottomY - 20) / gamePanel.TILESIZE;

        Tile tile1;
        Tile tile2;

        switch (entity.direction) {
            case "up":
                topRow = (topY - entity.speed) / gamePanel.TILESIZE;
                tile1 = gamePanel.tileManager.getTileAt(leftCol, topRow - 1);
                tile2 = gamePanel.tileManager.getTileAt(rightCol, topRow - 1);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                bottomRow = (bottomY + entity.speed - 10) / gamePanel.TILESIZE;
                tile1 = gamePanel.tileManager.getTileAt(leftCol, bottomRow - 1);
                tile2 = gamePanel.tileManager.getTileAt(rightCol, bottomRow - 1);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                leftCol = (leftX - entity.speed) / gamePanel.TILESIZE;
                tile1 = gamePanel.tileManager.getTileAt(leftCol, topRow - 1);
                tile2 = gamePanel.tileManager.getTileAt(leftCol, bottomRow - 1);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                rightCol = (rightX + entity.speed) / gamePanel.TILESIZE;
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
