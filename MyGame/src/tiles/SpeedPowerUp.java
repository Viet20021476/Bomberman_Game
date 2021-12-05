package tiles;

import mygame.GamePanel;
import Entities.Player;

public class SpeedPowerUp extends PowerUp {
    public void applyPower(GamePanel gamePanel) {
        gamePanel.getPlayer().increaseSpeed();
    }
}
