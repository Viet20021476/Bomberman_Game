package Objects;

import java.awt.Graphics2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import mygame.GamePanel;

public class Bomb extends Object {

    boolean alreadyBombed = false;

    public Bomb() {
        this.name = "Bomb";
        try {
            image = ImageIO.read(new FileInputStream("res/objects_image/bomb.png"));
        } catch (IOException ex) {
            Logger.getLogger(Bomb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gamePanel) {

        if (gamePanel.keyHandle.bombed == true) {
            g2.drawImage(image, gamePanel.player.x, gamePanel.player.y, gamePanel.tileSize - 10, gamePanel.tileSize + 5, null);
            x = gamePanel.player.x;
            y = gamePanel.player.y;
            alreadyBombed = true;
        } else {
            if (alreadyBombed) {
                g2.drawImage(image, x, y, gamePanel.tileSize - 10, gamePanel.tileSize + 5, null);
            }
        }
    }

}
