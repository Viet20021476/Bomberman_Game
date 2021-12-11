package tiles;

import mygame.GamePanel;

public class Portal extends PowerUp {
    @Override
    public void usePower(GamePanel gamePanel) {
        applyPower(gamePanel);
    }
    
    @Override
    public void applyPower(GamePanel gamePanel) {
        if (gamePanel.getEntityManager().hasNoEnemy()) {
            gamePanel.goToNextLevel();
        }
    }
}
