package bomb;

import mygame.GamePanel;

public class Bomb {
    private GamePanel gamePanel;
    private int x;
    private int y;
    private boolean expired = false;
    private long explosionTime;

    public Bomb(GamePanel gamePanel, int x, int y) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.explosionTime = System.nanoTime()
                + gamePanel.getBombManager().TIME_UNTIL_EXPLOSION_ENDS;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public boolean isExpired() {
        return expired;
    }
    
    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    
    public long getExplosionTime() {
        return explosionTime;
    }
}
