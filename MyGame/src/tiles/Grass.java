package tiles;

public class Grass implements Tile{
    public boolean isCollision() {
        return false;
    }
    
    public boolean canExplode(){
        return true;
    }
}
