package mygame;

import Entities.Entity;
import tiles.Grass;
import tiles.Tile;

public class CollisionDetect {

    private GamePanel gamePanel;

    public CollisionDetect(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int leftX = entity.getX();
        int rightX = entity.getX() + entity.getSolidArea().width;
        int topY = entity.getY();
        int bottomY = entity.getY() + entity.getSolidArea().height;

        int leftCol = leftX / GamePanel.TILESIZE;
        int rightCol = rightX / GamePanel.TILESIZE;
        int topRow = topY / GamePanel.TILESIZE;
        int bottomRow = bottomY / GamePanel.TILESIZE;
        
        int tileX = entity.getCenterX() / GamePanel.TILESIZE;
        int tileY = entity.getCenterY() / GamePanel.TILESIZE;
        Tile tile = gamePanel.getTileManager().getTileAt(leftCol, topRow);
        if (tile instanceof Grass) {
            Grass grass = (Grass) tile;
            if (grass.hasBomb()) {
                return;
            }
        }

        Tile tile1;
        Tile tile2;

        switch (entity.getDirection()) {
            case "up" -> {
                topRow = (topY - entity.getSpeed()) / GamePanel.TILESIZE;
                tile1 = gamePanel.getTileManager().getTileAt(leftCol, topRow);
                tile2 = gamePanel.getTileManager().getTileAt(rightCol, topRow);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.setCollision(true);
                }
            }
            case "down" -> {
                bottomRow = (bottomY + entity.getSpeed()) / GamePanel.TILESIZE;
                tile1 = gamePanel.getTileManager().getTileAt(leftCol, bottomRow);
                tile2 = gamePanel.getTileManager().getTileAt(rightCol, bottomRow);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.setCollision(true);
                }
            }
            case "left" -> {
                leftCol = (leftX - entity.getSpeed()) / GamePanel.TILESIZE;
                tile1 = gamePanel.getTileManager().getTileAt(leftCol, topRow);
                tile2 = gamePanel.getTileManager().getTileAt(leftCol, bottomRow);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.setCollision(true);
                }
            }
            case "right" -> {
                rightCol = (rightX + entity.getSpeed()) / GamePanel.TILESIZE;
                tile1 = gamePanel.getTileManager().getTileAt(rightCol, topRow);
                tile2 = gamePanel.getTileManager().getTileAt(rightCol, bottomRow);
                if (tile1.isCollision() || tile2.isCollision()) {
                    entity.setCollision(true);
                }
            }
        }
    }
}
