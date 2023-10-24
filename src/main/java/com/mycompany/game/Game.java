package com.mycompany.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

    public Game() {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        lar = (int) tela.getWidth();
        alt = (int) tela.getHeight();
        timer = new Timer(4, this);
        score = 0;
        lives = 3;
        playerX = lar / 2 - 25;
        playerY = alt / 2 - 25; 
        respawnEnemy();

        setFocusable(true);
        addKeyListener(new MyKeyListener());
        timer.start();
    }
    
    private void respawnEnemy() {
        enemyX = (int) (Math.random() * lar);
        enemyY = (int) (Math.random() * alt);
        enemyXSpeed = (Math.random() < 0.5) ? -1 : 1;
        enemyYSpeed = (Math.random() < 0.5) ? -1 : 1;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(playerX, playerY, 50, 50);

        g.setColor(Color.RED);
        g.fillRect(enemyX, enemyY, 50, 50);

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
            if (lives == 0) {
                // Game over
                timer.stop();
            }
            respawnEnemy();
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
        } else if (keyCode == KeyEvent.VK_RIGHT && playerX + playerWidth < lar) {
            playerX += (int) (lar * speedPercentage);
        } else if (keyCode == KeyEvent.VK_UP && playerY > 0) {
            playerY -= (int) (alt * speedPercentage);
        } else if (keyCode == KeyEvent.VK_DOWN && playerY + playerHeight < alt) {
            playerY += (int) (alt * speedPercentage);
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
