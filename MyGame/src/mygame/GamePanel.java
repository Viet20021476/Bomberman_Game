package mygame;

import Entities.Player;
import Objects.Bomb;
import Objects.ObjectManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable {
    
    final int originalTileSize = 16;
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 14;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //FPS
    int FPS = 60;
    
    public KeyHandle keyHandle = new KeyHandle();
    Thread gameThread;
    public Player player = new Player(this, keyHandle);
    public Object[] obj = new Object[10];
    public ObjectManager objectManager = new ObjectManager(this);
    Bomb b1 = new Bomb();
    Bomb b2 = new Bomb();
    
 
    TileManager tileManager = new TileManager(this);
    public CollisionDetect collisionDetect = new CollisionDetect(this);
    
    public void setupGame() {
        objectManager.setObjects();
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
        b2.draw(g2, this);
        player.draw(g2);
        g2.dispose();
    }
    
}
