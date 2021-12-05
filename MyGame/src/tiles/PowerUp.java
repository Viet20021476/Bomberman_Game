package tiles;

import Entities.Player;
import mygame.GamePanel;

public abstract class PowerUp implements Tile {
    @Override
    public boolean isCollision() {
        return true;
    }
    
    @Override
    public boolean canExplode(){
        return false;
    }
    
    public void detectPlayer(Player player) {
        
    }
    
    public abstract void applyPower(GamePanel gamePanel);
}
