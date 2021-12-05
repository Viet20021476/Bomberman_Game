package tiles;

import mygame.GamePanel;

public class BombPowerUp extends PowerUp {
    public void applyPower(GamePanel gamePanel) {
        gamePanel.getBombManager().increaseBombCount();
    }
}
