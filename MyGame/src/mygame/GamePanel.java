package mygame;

import Entities.Player;
import bomb.Bomb;
import bomb.BombManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable {
    
    private static final int ORIGINALTILESIZE = 16;
    private static final int SCALE = 3;
    public static final int TILESIZE = ORIGINALTILESIZE * SCALE;
    
    final int maxScreenCol = 16;
    final int maxScreenRow = 14;
    public final int screenWidth = TILESIZE * maxScreenCol;
    public final int screenHeight = TILESIZE * maxScreenRow;

    //FPS
    int FPS = 60;
    
    public KeyHandle keyHandle = new KeyHandle();
    Thread gameThread;
    public Player player = new Player(this, keyHandle);
    TileManager tileManager = new TileManager(this);
    public CollisionDetect collisionDetect = new CollisionDetect(this);
    Bomb b1 = new Bomb(this.tileManager);
    
    public void setupGame() {
        
    }
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandle);
        this.setFocusable(true);
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
            
            long currentTime = System.nanoTime();

            //1.Update: update information such as character positions
            update();

            //2.Draw : draw the screen with the updated information
            repaint();
            
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                
                Thread.sleep((long) remainingTime);
                
                nextDrawTime += drawInterval;
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void update() {
        player.update();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        b1.draw(g2, this);
        player.draw(g2);
        g2.dispose();
    }
}
