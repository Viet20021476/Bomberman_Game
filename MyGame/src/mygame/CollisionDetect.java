package mygame;

import Entities.Enemy;
import Entities.Entity;
import Entities.Player;
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

        try {
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
        } catch (NullPointerException e) {
            
        }
    }
    
    public boolean checkEntity(Entity e1, Entity e2) {
        Entity e[] = new Entity[2];
        e[0] = e1;
        e[1] = e2;
        int leftX[] = new int[2];
        int rightX[] = new int[2];
        int topY[] = new int[2];
        int bottomY[] = new int[2];
        
        leftX[0] = e1.getX();
        topY[0] = e1.getY();
         
        leftX[1] = e2.getX();
        topY[1] = e2.getY();
        
        for (int i = 0; i < 2; i++) {
            if (e[i] instanceof Player) {
                Player p = (Player) e[i];
                if (p.isMoving()) {
                    switch (p.getDirection()) {
                        case "up" -> {
                            topY[i] -= p.getSpeed();
                        }
                        case "down" -> {
                            topY[i] += p.getSpeed();
                        }
                        case "left" -> {
                            leftX[i] -= p.getSpeed();
                        }
                        case "right" -> {
                            leftX[i] += p.getSpeed();
                        }
                    }
                }
            } else if (e[i] instanceof Enemy) {
                Enemy enemy = (Enemy) e[i];
                switch (e[i].getDirection()) {
                    case "up" -> {
                        if (topY[i] - enemy.getSpeed() < enemy.getTargetY()) {
                            topY[i] = enemy.getTargetY();
                        } else {
                            topY[i] -= enemy.getSpeed();
                        }
                    }
                    case "down" -> {
                        if (topY[i] + enemy.getSpeed() < enemy.getTargetY()) {
                            topY[i] = enemy.getTargetY();
                        } else {
                            topY[i] += enemy.getSpeed();
                        }
                    }
                    case "left" -> {
                        if (leftX[i] - enemy.getSpeed() < enemy.getTargetX()) {
                            leftX[i] = enemy.getTargetX();
                        } else {
                            leftX[i] -= enemy.getSpeed();
                        }
                    }
                    case "right" -> {
                        if (leftX[i] + enemy.getSpeed() > enemy.getTargetX()) {
                            leftX[i] = enemy.getTargetX();
                        } else {
                            leftX[i] += enemy.getSpeed();
                        }
                    }
                }
            }
        }
        
        rightX[0] = leftX[0] + e1.getSolidArea().width;
        bottomY[0] = topY[0] + e1.getSolidArea().height;
        
        rightX[1] = leftX[1] + e2.getSolidArea().width;
        bottomY[1] = topY[1] + e2.getSolidArea().height;
        
        return leftX[0] < rightX[1]
            && leftX[1] < rightX[0]
            && topY[1] < bottomY[0]
            && topY[0] < bottomY[1];
    }
}
