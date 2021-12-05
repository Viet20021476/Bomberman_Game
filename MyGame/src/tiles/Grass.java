package tiles;

public class Grass implements Tile{
    @Override
    public boolean isCollision() {
        return false;
    }
    
    @Override
    public boolean canExplode(){
        return true;
    }
}
