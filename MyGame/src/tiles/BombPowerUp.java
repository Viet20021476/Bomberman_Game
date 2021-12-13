package tiles;

import mygame.GamePanel;

public class BombPowerUp extends PowerUp {
    @Override
    public void applyPower(GamePanel gamePanel) {
        gamePanel.getBombManager().increaseBombCount();
    }
}
