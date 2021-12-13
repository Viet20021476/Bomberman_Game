package tiles;

import mygame.GamePanel;

public class FlamePowerUp extends PowerUp {
    @Override
    public void applyPower(GamePanel gamePanel) {
        gamePanel.getBombManager().increaseRange();
    }
}
