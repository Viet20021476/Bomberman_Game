package tiles;

public class Wall implements Tile {
    public boolean isCollision() {
        return true;
    }
    
    public boolean canExplode(){
        return false;
    }
}
