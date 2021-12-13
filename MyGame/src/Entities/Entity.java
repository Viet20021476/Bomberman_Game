package Entities;

import java.awt.Rectangle;
import mygame.GamePanel;
import tiles.Tile;

public abstract class Entity {

    protected GamePanel gamePanel;
    protected String direction;
    protected int speed;
    protected int spriteCounter = 0;
    protected int spriteNum = 1;
    protected Rectangle solidArea;
    protected boolean collisionOn = false;
    private boolean isDead = false;
    protected long deadTime;
    
    protected abstract void setDirection();
    
    protected abstract void setXY();

    public String getDirection() {
        return direction;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getX() {
        return solidArea.x;
    }

    public void setX(int x) {
        this.solidArea.x = x;
    }

    public void setY(int y) {
        this.solidArea.y = y;
    }

    public int getY() {
        return solidArea.y;
    }
    
    public Tile getCurrentTile() {
        int TileX = getCenterX() / GamePanel.TILESIZE;
        int TileY = getCenterY() / GamePanel.TILESIZE;
        return gamePanel.getTileManager().getTileAt(TileX, TileY);
    }

    public int getCenterX() {
        return solidArea.x + GamePanel.TILESIZE / 2;
    }

    public int getCenterY() {
        return solidArea.y + GamePanel.TILESIZE / 2;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean getCollisionOn() {
        return collisionOn;
    }

    public void setCollision(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    protected int getSpriteNum() {
        return spriteNum;
    }
    
    protected boolean isInTileCenter() {
        return getX() % 48 == 0
                && getY() % 48 == 0;
    }
    
    protected void die(long timeUntilDead) {
        isDead = true;
        deadTime = System.nanoTime() + timeUntilDead;
    }
    
    protected boolean isDead() {
        return isDead;
    }
    
    protected long getDeadTime() {
        return deadTime;
    }
}
