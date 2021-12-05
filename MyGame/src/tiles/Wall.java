package tiles;

public class Wall implements Tile {
    @Override
    public boolean isCollision() {
        return true;
    }
    
    @Override
    public boolean canExplode(){
        return false;
    }
}
