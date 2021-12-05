package bomb;

import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayDeque;
import java.util.Queue;
import mygame.GamePanel;

public class BombManager {
    private final int BOMB_CAP = 5;
    
    private GamePanel gamePanel;
    private Queue<Bomb> bombArray = new ArrayDeque<>();
    private int bombCount = 2;
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
        return coord / GamePanel.TILESIZE * GamePanel.TILESIZE;
    }
    
    public void draw(Graphics2D g2, GamePanel gamePanel) {
        for (Bomb bomb: bombArray) {
            bomb.draw(g2, gamePanel);
        }
        if (gamePanel.keyHandle.bombed && bombArray.size() < bombCount) {
            int x = fitTilePosition(gamePanel.getPlayer().getX() + 5);
            int y = fitTilePosition(gamePanel.getPlayer().getY() + 5);
            bombArray.add(new Bomb(gamePanel.getTileManager(), this, x, y));
            System.out.println("created");
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
