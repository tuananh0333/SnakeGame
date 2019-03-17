/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_java_snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class Board extends JPanel implements ActionListener, Commons {

    boolean leftDirection = false;
    boolean rightDirection = true;
    boolean upDirection = false;
    boolean downDirection = false;

    Snake snake;
    Apple apple;

    private boolean inGame = true;

    private Menu menu;
    private About about;

    private Timer timer;
    private Image imgBall;
    private Image imgApple;
    private Image imgHead;
    
    private int score;
    private int highScore = 10;
    private boolean isPaused = false;

    private static JLabel statusbar;

    public static enum STATE {
        MENU,
        GAME,
        ABOUT
    };
    public static STATE state = STATE.MENU;

    public Board() {
        addKeyListener(new TAdapter());
        addMouseListener(new MouseInput());
        setBackground(Color.BLACK);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        menu = new Menu();
        about = new About();
        
        loadImages();
        initGame();

    }

    private void initGame() {
        snake = new Snake();
        apple = new Apple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon(getClass().getResource("/resources/dot.png"));
        imgBall = iid.getImage();

        ImageIcon iia = new ImageIcon(getClass().getResource("/resources/apple.png"));
        imgApple = iia.getImage();

        ImageIcon iih = new ImageIcon(getClass().getResource("/resources/head.png"));
        imgHead = iih.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (state == STATE.GAME) {
            if (inGame) {

                g.drawImage(imgApple, apple.getApple_x(), apple.getApple_y(), this);

                for (int z = 0; z < snake.getDots(); z++) {
                    if (z == 0) {
                        g.drawImage(imgHead, snake.getSnakeX()[z], snake.getSnakeY()[z], this);
                    } else {
                        g.drawImage(imgBall, snake.getSnakeX()[z], snake.getSnakeY()[z], this);
                    }
                }

                Toolkit.getDefaultToolkit().sync();
                
                g.setColor(Color.WHITE);
                g.setFont(new Font("Calibri", Font.ROMAN_BASELINE, 30));
                if(score > highScore)
                {
                    g.drawString("New High Score: " + score, 10, 30);
                }
                else
                {
                    g.drawString("Score: " + score, 10, 30);
                }
                
                if(isPaused)
                {
                    //gamePause(g);
                    timer.stop();
                }
            } else {
                gameOver(g);
            }
        } else if(state == STATE.MENU) {
            menu.render(g);
        }
        else if(state == STATE.ABOUT) {
            about.render(g);
        }
    }

    private void gamePause(Graphics g)
    {
        Font small = new Font("Arial", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);
        
        String mess = "Game paused, press P to resume game";
        
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(mess, (B_WIDTH - metr.stringWidth(mess)) / 2, (B_HEIGHT / 4)*3);
    }
    
    private void gameOver(Graphics g) {
        //dung Timer
        inGame = false;
        String msg = "Game Over";
        String scr = "";
        if(score > highScore)
        {
            scr = "New High Score: " + score;
            highScore = score;
        }
        else
        {
            scr = "Your score: "+ score;
        }
        String suc = "Press SPACE to PLAY AGAIN";
        String sup = "Press ESC to back to MENU";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        //In Thong Bao
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
        if(score == highScore)
        {
            g.drawString(scr, (B_WIDTH - metr.stringWidth(scr)) / 2, B_HEIGHT / 2 + 30);
            g.drawString(suc, (B_WIDTH - metr.stringWidth(suc)) / 2, B_HEIGHT / 2 + 60);
            g.drawString(sup, (B_WIDTH - metr.stringWidth(sup)) / 2, B_HEIGHT / 2 + 90);
        }
        else
        {
            g.drawString(scr, (B_WIDTH - metr.stringWidth(scr)) / 2, B_HEIGHT / 2 + 30);
            String hiScr = "Highest score: " + highScore;
            g.drawString(hiScr, (B_WIDTH - metr.stringWidth(hiScr)) / 2, B_HEIGHT / 2 + 60);
            g.drawString(suc, (B_WIDTH - metr.stringWidth(suc)) / 2, B_HEIGHT / 2 + 90);
            g.drawString(sup, (B_WIDTH - metr.stringWidth(sup)) / 2, B_HEIGHT / 2 + 120);
        }
        
        //set diem ve 0
        score = 0;
        //chay ve phai neu bat dau game lai
        leftDirection = false;
        rightDirection = true;
        upDirection = false;
        downDirection = false;
    }

    private void checkCollision() {
        for (int z = snake.getDots(); z > 0; z--) {
            if ((z > 4) && (snake.getSnakeX()[0] == snake.getSnakeX()[z]) && (snake.getSnakeY()[0] == snake.getSnakeY()[z]) ) {
                inGame = false;
            }
        }
        if (snake.getSnakeY()[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (snake.getSnakeY()[0] < 0) {
            inGame = false;
        }

        if (snake.getSnakeX()[0] >= B_WIDTH) {
            inGame = false;
        }

        if (snake.getSnakeX()[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void checkApple() {

        if ((snake.getSnakeX()[0] == apple.getApple_x()) && (snake.getSnakeY()[0] == apple.getApple_y())) {
            snake.setDots(snake.getDots() + 1);
            apple = new Apple();
            score++;
        }
    }

    private void move() {

        int[] x = snake.getSnakeX();
        int[] y = snake.getSnakeY();

        for (int z = snake.getDots(); z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }

        snake.setX(x);
        snake.setY(y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    public class TAdapter extends KeyAdapter implements Commons {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE && !inGame) {
                if (!inGame) {
                    inGame = true;
                    timer.stop();
                    initGame();
                }
            }
            
            if(key == KeyEvent.VK_P && inGame)
            {
                if(timer.isRunning())
                {
                    isPaused = true;
                }
                else
                {
                    isPaused= false;
                    timer.start();
                    repaint();
                }
            }
            
            if(key == KeyEvent.VK_ESCAPE)
            {
                Board.state = Board.STATE.MENU;
                timer.stop();
                repaint();
            }

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }

    public class MouseInput implements MouseListener
    {
        
        @Override
        public void mouseClicked(MouseEvent e) {
            int mx = e.getX();
            int my = e.getY();
            
            if(mx >= (Commons.B_WIDTH/2 - 20) && mx <= (Commons.B_WIDTH/2 - 20 + 50))
            {
                //Click chuot vao nut Play
                if(my >= 200 && my <= 225)
                {
                    System.out.println("play button clicked");
                    Board.state = Board.STATE.GAME;
                    snake = new Snake();
                    inGame = true;
                    timer.stop();
                    initGame();
                }
                //Click chuot vao nut Exit
                if(my >= 300 && my <= 325)
                {
                        System.out.println("exit button clicked");
                        timer.stop();
                        System.exit(1);
                }
                //Click chuot vao nut AboutUs
                if(my >= 250 && my <= 275)
                {
                        System.out.println("about button clicked");
                        timer.stop();
                        Board.state = Board.STATE.ABOUT;
                        repaint();
                }
            } 
            
            if(mx >= (Commons.B_WIDTH/4) * 3 && mx <= (Commons.B_WIDTH/4) * 3 + 90)
            {
                if(my >= 50 && my <= 90)
                {
                    System.out.println("back button clicked");
                    Board.state =  Board.STATE.MENU;
                    repaint();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
