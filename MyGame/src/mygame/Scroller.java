package mygame;

public class Scroller {

    private final int BASE_X = 0;
    private final int BASE_Y = GamePanel.TILESIZE;

    private GamePanel gamePanel;
    private int offsetX = 0;
    private int offsetY = 0;

    public Scroller(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void updateOffset() {
        offsetX = gamePanel.player.screenX - gamePanel.getPlayer().getSolidArea().x;
        offsetY = gamePanel.player.screenY - gamePanel.getPlayer().getSolidArea().y;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
