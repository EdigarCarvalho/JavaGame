// TRABALHO FEITO POR:
// EDIGAR DE ALMEIDA CARVALHO
// IAN SILVA DOS SANTOS
// LUCIANO AMORIM DE SOUSA

package com.mycompany.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Game extends JPanel implements ActionListener {
    private Timer timer;
    private int score;
    private int lives;
    private int playerX;
    private int playerY;
    private int player2X;
    private int player2Y;
    private int enemyX;
    private int enemyY;
    private int enemyXSpeed;
    private int enemyYSpeed;
    private int lar; 
    private int alt; 
    private BufferedImage[] playerImage = new BufferedImage[8]; 
    private BufferedImage[] enemyImage = new BufferedImage[2]; 
    private BufferedImage gameOver;
    private int step = 1;
    private int enemyStep = 1;
    private int counter = 60;
    private BufferedImage backgroundImage;
    private BufferedImage heartImage;
    private boolean pressedLeft;
    private boolean pressedRight;
    private boolean pressedDown;
    private boolean pressedUp;
    private Image bird;

    private SwingWorker<Void, Void> birdWorker = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            while (true) {
                repaint();
                Thread.sleep(99999999); 
                
            }
        }
    };

    public Game() {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        lar = (int) tela.getWidth();
        alt = (int) tela.getHeight();
        timer = new Timer(11, this);
        score = 0;
        lives = 2;
        playerX = lar / 2 - 25;
        playerY = alt / 2 - 25; 
        player2X = playerX;
        player2Y = playerY;
        respawnEnemy();

        setFocusable(true);
        addKeyListener(new MyKeyListener());
        setPlayerSprites();
        setEnemySprites();

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/background.jpeg"));
        } catch (IOException e) {
            System.out.println(e);
        }

        try {
            heartImage = ImageIO.read(getClass().getResource("/heart.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        try {
            gameOver = ImageIO.read(getClass().getResource("/gameover.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        try {
            bird = new ImageIcon(new URL("https://static.tildacdn.com/tild3966-6339-4137-a236-623866643864/bird-flap-animation.gif")).getImage();
        } catch (IOException e) {
            System.out.println(e);
        }

        addMouseMotionListener(new MyMouseMotionAdapter());

        // Start the SwingWorker for bird movement
        birdWorker.execute();

        timer.start();
    }

    private void loadImage(String path, BufferedImage[] images, int numImages) {
        for (int i = 1; i <= numImages; i++) {
            String valor = Integer.toString(i);
            try {
                images[i - 1] = ImageIO.read(getClass().getResource(path + valor + ".png"));
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    private void setPlayerSprites() {
        loadImage("/ciclo", playerImage, 8);
    }

    private void setEnemySprites() {
        loadImage("/inimigo", enemyImage, 2);
    }

    private void respawnEnemy() {
        enemyX = (int) (Math.random() * lar);
        enemyY = (int) (Math.random() * (alt / 2)); 
        enemyXSpeed = (Math.random() < 0.5) ? -2 : 2;
        enemyYSpeed = (Math.random() < 0.5) ? -2 : 2;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, lar, alt, this);
        }

        if (bird != null) {
            g.drawImage(bird, player2X, player2Y, 100, 100, this);
        }
        
         g.setColor(Color.WHITE);
         g.setFont(new Font("Arial", Font.PLAIN, 20));
         
        String scoreText = "Score: " + score;
        FontMetrics metrics = g.getFontMetrics();
        int xScore = (lar - metrics.stringWidth(scoreText)) / 2;
        int yScore = 30; 
        g.drawString(scoreText, xScore, yScore);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(xScore - 5, yScore - metrics.getHeight(), metrics.stringWidth(scoreText) + 10, metrics.getHeight() + 5);
    
        String livesText = "Lives: ";
        int xLives = (lar - metrics.stringWidth(scoreText)) / 2 + 100;
        int yLives = 30; 
        g.drawString(livesText, xLives, yLives);
        
        int heartWidth = 20;
        int heartHeight = 20;
        int heartsGap = 5;

        int xHearts = xLives + metrics.stringWidth(livesText) + 10;
        int yHearts = 30 - heartHeight;

        for (int i = 0; i < lives; i++) {
            g.drawImage(heartImage, xHearts + (heartWidth + heartsGap) * i, yHearts, heartWidth, heartHeight, this);
        }
        
        if (lives == 0) {
            g.drawImage(gameOver, 0, 0, this.getBounds().width, this.getBounds().height, this);
            timer.stop();
            return;
        }
        
        if (enemyImage[enemyStep - 1] != null) {
            g.drawImage(enemyImage[enemyStep - 1], enemyX, enemyY, 50 * enemyStep , 50, this);
            
            if (counter == 0) {
                counter = 60;
                enemyStep += 1;
                if (enemyStep == 3) enemyStep = 1;
            } else {
                counter -= 2;
            }
        }

        if (playerImage[step - 1] != null) {
            g.drawImage(playerImage[step - 1], playerX, playerY, 50, 50, this);
        }
                
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, 10, 40);
    }

    public void actionPerformed(ActionEvent e) {
        enemyX += enemyXSpeed;
        enemyY += enemyYSpeed;

        if (checkCollision()) {
            score++;
            respawnEnemy();
        }

        checkOutOfBounds();

        repaint();
    }

    public boolean checkCollision() {
        Rectangle playerBounds1 = new Rectangle(playerX, playerY, 50, 50);
        Rectangle playerBounds2 = new Rectangle(player2X, player2Y, 100, 100); 
        Rectangle enemyBounds = new Rectangle(enemyX, enemyY, 50 * enemyStep, 50);

        return playerBounds1.intersects(enemyBounds) || playerBounds2.intersects(enemyBounds);
    }
    
    private void checkOutOfBounds() {
        if (enemyX < 0 || enemyX > lar) {
            enemyXSpeed *= -1;
        }
        if (enemyY < 0 || enemyY > alt) {
            enemyYSpeed *= -1;
        }
        if (enemyY > alt) {
            lives--;
            if (lives > 0) {
                // Game over
                respawnEnemy();
            }
        }
    }

    private void updateBirdPosition(MouseEvent e) {
        player2X = e.getX() - 50;
        player2Y = e.getY() - 50;
    }

    public void mouseDragged(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void mouseMoved(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private class MyMouseMotionAdapter extends MouseMotionAdapter {
    @Override
    public void mouseMoved(MouseEvent e) {
        updateBirdPosition(e);
        repaint();
    }
}

private class MyKeyListener extends KeyAdapter {
    private double speedPercentage = 0.05; 

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int playerWidth = 50; 
        int playerHeight = 50; 

        if (keyCode == KeyEvent.VK_LEFT && playerX > 0) {
            playerX -= (int) (lar * speedPercentage);
            step += 1;
            if (step >= 4) 
                step = 1;
        } else if (keyCode == KeyEvent.VK_RIGHT && playerX + playerWidth < lar) {
            playerX += (int) (lar * speedPercentage);
            step += 1;
            if (step == 9 || step < 5) 
                step = 5;
        }
        
        if (keyCode == KeyEvent.VK_UP && playerY > 0) {
            playerY -= (int) (alt * speedPercentage);
        } else if (keyCode == KeyEvent.VK_DOWN && playerY + playerHeight < alt) {
            playerY += (int) (alt * speedPercentage);
        } 
        
        if (keyCode == KeyEvent.VK_SPACE && lives ==0) {
            timer.start();
            lives = 3;
        }
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("O Jogo mais incrível");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                Game game = new Game();
                frame.add(game);
                frame.setVisible(true);
            }
        });
    }
}
