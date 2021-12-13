package mygame;

public class Scroller {

    private GamePanel gamePanel;
    private int offsetX = 0;
    private int offsetY = 0;

    public Scroller(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void updateOffset() {
        offsetX = gamePanel.getPlayer().screenX - gamePanel.getPlayer().getX();
        offsetY = gamePanel.getPlayer().screenY - gamePanel.getPlayer().getY();
        
        if (gamePanel.getPlayer().screenX > gamePanel.getPlayer().getX()) {
            offsetX = 0;
        }

        if (gamePanel.getPlayer().screenY > gamePanel.getPlayer().getY()) {
            offsetY = 0;
        }

        if (gamePanel.getScreenWidth() - gamePanel.getPlayer().screenX 
                > gamePanel.getMapWidth() - gamePanel.getPlayer().getX()) {
            offsetX = gamePanel.getScreenWidth() - gamePanel.getMapWidth();
        }

        if (gamePanel.getScreenHeight() - gamePanel.getPlayer().screenY > 
                gamePanel.getMapHeight() - gamePanel.getPlayer().getY()) {
            offsetY = gamePanel.getScreenHeight() - gamePanel.getMapHeight();
        }
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
