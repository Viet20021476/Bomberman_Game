package mygame;

public class Scroller {

    private GamePanel gamePanel;
    private int offsetX = 0;
    private int offsetY = 0;

    public Scroller(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void updateOffset() {
        offsetX = gamePanel.player.screenX - gamePanel.getPlayer().getSolidArea().x;
        offsetY = gamePanel.player.screenY - gamePanel.getPlayer().getSolidArea().y;
        
        if (gamePanel.player.screenX > gamePanel.player.getSolidArea().x) {
            offsetX = 0;
        }

        if (gamePanel.player.screenY > gamePanel.player.getSolidArea().y) {
            offsetY = 0;
        }

        if (gamePanel.screenWidth - gamePanel.player.screenX 
                > gamePanel.mapWidth - gamePanel.player.getSolidArea().x) {
            offsetX = gamePanel.screenWidth - gamePanel.mapWidth;
        }

        if (gamePanel.screenHeight - gamePanel.player.screenY > 
                gamePanel.mapHeight - gamePanel.player.getSolidArea().y) {
            offsetY = gamePanel.screenHeight - gamePanel.mapHeight;
        }
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
