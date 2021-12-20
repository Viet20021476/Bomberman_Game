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

public class ScreenState {

    private GamePanel gamePanel;
    Graphics2D g2;
    Font titleFont;
    Font menuFont;
    Font loadingFont;
    int num = 0;
    int spriteNum = 0;
    int spriteCounter = 0;

    public ScreenState(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        InputStream inputStream1 = null;
        InputStream inputStream2 = null;
        InputStream inputStream3 = null;
        try {
            inputStream1 = new BufferedInputStream(new FileInputStream("res/font/titleFont.ttf"));
            inputStream2 = new BufferedInputStream(new FileInputStream("res/font/menuFont.ttf"));
            inputStream3 = new BufferedInputStream(new FileInputStream("res/font/loadingFont.ttf"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ScreenState.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            titleFont = Font.createFont(Font.TRUETYPE_FONT, inputStream1);
            menuFont = Font.createFont(Font.TRUETYPE_FONT, inputStream2);
            loadingFont = Font.createFont(Font.TRUETYPE_FONT, inputStream3);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(ScreenState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(titleFont);
        g2.setColor(Color.YELLOW);

        if (gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen();
        } else if (gamePanel.gameState == gamePanel.loadingState) {
            drawLoadingScreen();
        } else if (gamePanel.gameState == gamePanel.loseState) {
            drawGameoverScreen();
        }

    }

    public void drawTitleScreen() {
        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 150));
        String title1 = "BOMBER";
        String title2 = "MAN";
        int x1 = 230;
        int y1 = 180;
        int x2 = 270;
        int y2 = 310;
        g2.setColor(Color.YELLOW);
        g2.drawString(title1, x1, y1);
        g2.drawString(title2, x2, y2);

        // BOMBER RUNNING
        int x5 = 350;
        int y5 = 350;
        g2.drawImage(gamePanel.getEntityManager().getBufferedImagePlayerRight()[spriteNum], x5, y5, 48, 48, null);
        spriteCounter++;
        if (spriteCounter > 12) {
            switch (spriteNum) {
                case 0 ->
                    spriteNum = 1;
                case 1 ->
                    spriteNum = 2;
                case 2 ->
                    spriteNum = 0;
                default -> {
                }
            }
            spriteCounter = 0;
        }

        // MENU
        g2.setFont(menuFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40));
        String choice_1 = "New Game";
        String choice_2 = "Quit";
        int x3 = 280;
        int y3 = 450;
        int x4 = 280;
        int y4 = 500;
        g2.setColor(Color.WHITE);
        g2.drawString(choice_1, x3, y3);
        if (num == 0) {
            g2.drawString(">", x3 - 40, y3);
        }
        g2.drawString(choice_2, x4, y4);
        if (num == 1) {
            g2.drawString(">", x4 - 40, y4);
        }
    }

    public void drawLoadingScreen() {
        g2.setFont(loadingFont);
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30));
        String loadingText = "Stage " + gamePanel.getLevel();
        int x = 310;
        int y = 300;
        g2.drawString(loadingText, x, y);
    }

    public void drawGameoverScreen() {
        g2.setFont(loadingFont);
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
        String gameoverText = "GAME OVER";
        int x = 120;
        int y = 280;
        g2.drawString(gameoverText, x, y);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30));
        String score = "Score " + gamePanel.getTimeAndScore().score;
        int x2 = 300;
        int y2 = 400;
        g2.drawString(score, x2, y2);
        String playAgainText = ">Press Enter to play again";
        int x1 = 150;
        int y1 = 500;

        g2.setColor(Color.WHITE);
        g2.drawString(playAgainText, x1, y1);

    }

}
