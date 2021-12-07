package tiles;

import mygame.GamePanel;

public abstract class PowerUp implements Tile {
    @Override
    public boolean isCollision() {
        return false;
    }
    
    @Override
    public boolean canExplode(){
        return false;
    }
    
    public void detectPlayer(GamePanel gamePanel) {
        applyPower(gamePanel);
    }
    
    public abstract void applyPower(GamePanel gamePanel);
}
