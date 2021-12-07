package bomb;

import java.awt.Graphics2D;
import java.util.ArrayDeque;
import java.util.Queue;
import mygame.GamePanel;

public class BombManager {
    private final int BOMB_CAP = 5;
    
    private GamePanel gamePanel;
    private Queue<Bomb> bombArray = new ArrayDeque<>();
    private int bombCount = 1;
    private int range = 1;

    public BombManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    public void increaseBombCount() {
        if (bombCount < BOMB_CAP) {
            bombCount++;
        }
    }
    
    public void increaseRange() {
        range++;
    }
    
    private int fitTilePosition(int coord) {
        return coord / GamePanel.TILESIZE;
    }
    
    public void addBomb() {
        if (bombArray.size() < bombCount) {
            int x = fitTilePosition(gamePanel.getPlayer().screenX);
            int y = fitTilePosition(gamePanel.getPlayer().screenY);
            bombArray.add(new Bomb(gamePanel.getTileManager(), this, x, y));
        }
    }
    
    public void draw(Graphics2D g2, GamePanel gamePanel) {
        for (Bomb bomb: bombArray) {
            bomb.draw(g2, gamePanel);
        }
        try {
            if (bombArray.peek().isExpired()) {
                bombArray.poll();
            }
        } catch (NullPointerException ex) {

        }
    }
    
    public int getRange() {
        return range;
    }
}
