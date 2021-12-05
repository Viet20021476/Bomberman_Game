package tiles;

public class Brick implements Tile {
    private int explosionStage = -1;
    private String powerUp;
    
    public Brick() {
        
    }
    
    public Brick(String powerUp) {
        this.powerUp = powerUp;
    }
    
    @Override
    public boolean isCollision() {
        return true;
    }
    
    @Override
    public boolean canExplode(){
        return false;
    }
    
    public int getExplosionStage() {
        return explosionStage;
    }
    
    public void setExplosionStage(int explosionStage) {
        this.explosionStage = explosionStage;
    }
    
    public String getPowerUp() {
        return powerUp;
    }
}
