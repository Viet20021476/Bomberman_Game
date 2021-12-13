package Entities;

public abstract class Enemy extends Entity {
    protected int targetX;
    protected int targetY;
    
    @Override
    public void setXY() {
        collisionOn = false;
        gamePanel.getCollisionDetect().checkTile(this);

        if (!collisionOn) {
            switch (direction) {
                case "up" -> {
                    if (getY() - speed < targetY) {
                        setY(targetY);
                    } else {
                        setY(getY() - speed);
                    }
                }
                case "down" -> {
                    if (getY() + speed > targetY) {
                        setY(targetY);
                    } else {
                        setY(getY() + speed);
                    }
                }
                case "left" -> {
                    if (getX() - speed < targetX) {
                        setX(targetX);
                    } else {
                        setX(getX() - speed);
                    }
                }
                case "right" -> {
                    if (getX() + speed > targetX) {
                        setX(targetX);
                    } else {
                        setX(getX() + speed);
                    }
                }
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
    
    public int getTargetX() {
        return targetX;
    }
    
    public int getTargetY() {
        return targetY;
    }
    
    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }
    
    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }
}
