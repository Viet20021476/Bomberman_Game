package tiles;

import mygame.GamePanel;

public abstract class PowerUp implements Tile {
    private boolean used = false;
    
    @Override
    public boolean isCollision() {
        return false;
    }
    
    @Override
    public boolean canExplode(){
        return false;
    }
    
    public void usePower(GamePanel gamePanel) {
        if (!used) {
            applyPower(gamePanel);
        }
        used = true;
    }
    
    public boolean isUsed() {
        return used;
    }
    
    public abstract void applyPower(GamePanel gamePanel);
}
