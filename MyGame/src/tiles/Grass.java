package tiles;

public class Grass implements Tile {
    private boolean hasBomb = false;
    
    @Override
    public boolean isCollision() {
        return hasBomb;
    }
    
    @Override
    public boolean canExplode(){
        return true;
    }
    
    public void setHasBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
    }
    
    public boolean hasBomb() {
        return hasBomb;
    }
}
