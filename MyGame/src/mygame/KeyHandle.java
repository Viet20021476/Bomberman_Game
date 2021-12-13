package mygame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class KeyHandle implements KeyListener {

    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;

    private GamePanel gamePanel;

    public KeyHandle(GamePanel gamePanel) {
        super();
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gamePanel.gameState == gamePanel.titleState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gamePanel.getScreenState().num--;
                if (gamePanel.getScreenState().num < 0) {
                    gamePanel.getScreenState().num = 1;
                }
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gamePanel.getScreenState().num++;
                if (gamePanel.getScreenState().num > 1) {
                    gamePanel.getScreenState().num = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gamePanel.getScreenState().num == 0) {
                    Timer timer
                            = new Timer(3000, event -> {
                                gamePanel.gameState = gamePanel.playState;
                                gamePanel.getSound().stop();
                                gamePanel.playMusic(2);
                            });
                    timer.setRepeats(false);
                    timer.start();
                    gamePanel.gameState = gamePanel.loadingState;
                    gamePanel.getSound().stop();
                    gamePanel.playMusic(3);

                } else if (gamePanel.getScreenState().num == 1) {
                    System.exit(0);
                }
            }
        }

        if (gamePanel.gameState == gamePanel.playState) {

            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_J) {
                gamePanel.getBombManager().addBomb();
            }
        }

        if (gamePanel.gameState == gamePanel.loseState) {
//            if (code == KeyEvent.VK_ENTER) {
//                gamePanel.getSound().stop();
//                gamePanel.playMusic(0);
//                gamePanel.gameState = gamePanel.titleState; // DANG BI LOI
//            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

}
