package tiles;

public class Grass implements Tile {
    private boolean hasBomb = false;
    private boolean hasFlame = false;
    
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
    
    public void setHasFlame(boolean hasFlame) {
        this.hasFlame = hasFlame;
    }
    
    public boolean hasFlame() {
        return hasFlame;
    }
}
