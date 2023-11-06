package com.mycompany.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game extends JPanel implements ActionListener {
    private Timer timer;
    private int score;
    private int lives;
    private int playerX;
    private int playerY;
    private int enemyX;
    private int enemyY;
    private int enemyXSpeed;
    private int enemyYSpeed;
    private int lar; // Largura da tela
    private int alt; // Altura da tela
    private BufferedImage [] playerImage = new BufferedImage[8]; 
    private BufferedImage [] enemyImage = new BufferedImage[2]; 
    private BufferedImage gameOver;
    private int step = 1;
    private int enemyStep = 1;
    private int counter = 60;
    
    public Game() {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        lar = (int) tela.getWidth();
        alt = (int) tela.getHeight();
        timer = new Timer(10, this);
        score = 0;
        lives = 2;
        playerX = lar / 2 - 25;
        playerY = alt / 2 - 25; 
        respawnEnemy();

        setFocusable(true);
        addKeyListener(new MyKeyListener());
        setPlayerSprites();
        setEnemySprites();
        
        try {
        gameOver = ImageIO.read(getClass().getResource("/gameover.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
        
        timer.start();
    }
    
    private void setPlayerSprites() {
        for (int i = 1; i <= 8; i++) {
            String valor = Integer.toString(i);
            try {
                playerImage[i - 1] = ImageIO.read(getClass().getResource("/ciclo" + valor + ".png"));
            } catch (IOException e) {
                System.out.print(e);
            }
        }
    }
    
    private void setEnemySprites() {
        for (int i = 1; i <= 2; i++) {
            String valor = Integer.toString(i);
            try {
                enemyImage[i - 1] = ImageIO.read(getClass().getResource("/inimigo" + valor + ".png"));
            } catch (IOException e) {
                System.out.print(e);
            }
        }
    }
    
    private void respawnEnemy() {
        enemyX = (int) (Math.random() * lar);
        enemyY = (int) (Math.random() * (alt / 2)); 
        enemyXSpeed = (Math.random() < 0.5) ? -2 : 2;
        enemyYSpeed = (Math.random() < 0.5) ? -2 : 2;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

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
                counter -= 1;
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
        playerMovement();

        enemyX += enemyXSpeed;
        enemyY += enemyYSpeed;

        if (checkCollision()) {
            score++;
            respawnEnemy();
        }

        checkOutOfBounds();

        repaint();
    }

    private void playerMovement() {
        // Implement player movement as desired (e.g., using arrow keys)
    }

    public boolean checkCollision() {
        Rectangle playerBounds = new Rectangle(playerX, playerY, 50, 50);
        Rectangle enemyBounds = new Rectangle(enemyX, enemyY, 50, 50);
        return playerBounds.intersects(enemyBounds);
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

private class MyKeyListener extends KeyAdapter {
    private double speedPercentage = 0.05; // Ajuste o valor conforme necessário

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int playerWidth = 50; // Largura do jogador
        int playerHeight = 50; // Altura do jogador

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
        } else if (keyCode == KeyEvent.VK_UP && playerY > 0) {
            playerY -= (int) (alt * speedPercentage);
        } else if (keyCode == KeyEvent.VK_DOWN && playerY + playerHeight < alt) {
            playerY += (int) (alt * speedPercentage);
        } else if (keyCode == KeyEvent.VK_SPACE && lives ==0) {
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
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza a janela
                Game game = new Game();
                frame.add(game);
                frame.setVisible(true);
            }
        });
    }
}
