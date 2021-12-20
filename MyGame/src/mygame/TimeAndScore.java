package mygame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimeAndScore {

    GamePanel gamePanel;
    Font scoreFont;
    int countdown = 50;
    int score = 0;

    public TimeAndScore(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream("res/font/scoreFont.ttf"));
            scoreFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TimeAndScore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(TimeAndScore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, gamePanel.TILESIZE * gamePanel.getMaxMapCol(), gamePanel.TILESIZE);
        g2.setFont(scoreFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35));
        g2.setColor(Color.BLUE);
        g2.drawString("Time  " + countdown, 60, 35);
        g2.drawString("Score  " + score, 320, 35);
        g2.drawString("Left  " + gamePanel.numOfPlayerLives, 600, 35);
    }

    public int getCountdown() {
        return countdown;
    }

    public void resetTime() {
        countdown = 50;
    }
    
    public void resetTime1() {
        countdown = 53;
    }

    public void resetScore() {
        score = 0;
    }
    

    public void increaseScore(int num) {
        score += num;
    }

}
