package Entities;

public abstract class Enemy extends Entity {
    protected int targetX;
    protected int targetY;
    
    protected abstract void behavior();
    
    @Override
    public void update() {
        behavior();
     
        collisionOn = false;
        gamePanel.collisionDetect.checkTile(this);

        if (!collisionOn) {
            switch (direction) {
                case "up" -> {
                    if (solidArea.y - speed < targetY) {
                        solidArea.y = targetY;
                    } else {
                        solidArea.y -= speed;
                    }
                }
                case "down" -> {
                    if (solidArea.y + speed > targetY) {
                        solidArea.y = targetY;
                    } else {
                        solidArea.y += speed;
                    }
                }
                case "left" -> {
                    if (solidArea.x - speed < targetX) {
                        solidArea.x = targetX;
                    } else {
                        solidArea.x -= speed;
                    }
                }
                case "right" -> {
                    if (solidArea.x + speed > targetX) {
                        solidArea.x = targetX;
                    } else {
                        solidArea.x += speed;
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
}
