package tiles;

import mygame.GamePanel;

public class SpeedPowerUp extends PowerUp {
    @Override
    public void applyPower(GamePanel gamePanel) {
        gamePanel.getPlayer().increaseSpeed();
    }
}
