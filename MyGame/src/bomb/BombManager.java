package bomb;

import java.awt.Graphics2D;
import mygame.GamePanel;

public class BombManager {
    private final int BOMB_CAP = 5;
    
    GamePanel gamePanel;
    private Bomb[] bomb = new Bomb[BOMB_CAP];

    public BombManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    public void draw(Graphics2D g2, GamePanel gamePanel) {
        for (int i = 0; i < BOMB_CAP; i++) {
            //if (bomb[i]
        }
    }
}
