package Objects;

import mygame.GamePanel;

public class ObjectManager {

    GamePanel gamePanel;

    public ObjectManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    public void setObjects() {
        gamePanel.obj[0] = new Bomb();
    }

}
