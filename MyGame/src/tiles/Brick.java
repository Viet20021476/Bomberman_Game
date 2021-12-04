package tiles;

public class Brick implements Tile {
    private int explosionStage = -1;
    
    public boolean isCollision() {
        return true;
    }
    
    public boolean canExplode(){
        return false;
    }
    
    public int getExplosionStage() {
        return explosionStage;
    }
    
    public void setExplosionStage(int explosionStage) {
        this.explosionStage = explosionStage;
    }
}
