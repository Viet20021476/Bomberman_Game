package bomb;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import mygame.GamePanel;
import tiles.TileManager;

public class BombManager {
    private final int BOMB_CAP = 3;
    
    GamePanel gamePanel;
    LinkedList<Bomb> bomb = new LinkedList<Bomb>();
    private int numberOfBombs = 3;

    public BombManager(GamePanel gamePanel, TileManager tileManager) {
        this.gamePanel = gamePanel;
    }
    
    public void draw(Graphics2D g2, GamePanel gamePanel) {
        Iterator bombIterator = bomb.iterator();
        while (bombIterator.hasNext()) {
            
        }
        if (gamePanel.keyHandle.bombed == true) {
            for (int i = 0; i < numberOfBombs; i++) {
                if (!bomb[i].isAlreadyBombed()) {
                    bomb[i].draw(g2, gamePanel);
                    break;
                }
            }
        }
    }
}
