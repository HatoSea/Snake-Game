package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class gamePanel extends JPanel implements ActionListener {

    static final int SCREEN_HEIGHT = 800;//Screen Height
    static final int SCREEN_WIDTH = 600;//Screen Width
    static final int UNIT_SIZE = 25;//Size of Apple
    static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;//Number of Coordinate
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];//Number of x coordinate
    final int y[] = new int[GAME_UNITS];//Number of y coordinate
    int bodyParts = 6;//Star with 6 body part
    int appleEaten;
    int appleX;//the x-coordinate of where the Apple is located, it's going to appear randomly each time that the snake eats an apple
    int appleY;//the y-coordinate of where the Apple is located, it's going to appear randomly each time that the snake eats an apple
    char direction = 'D';//The snake begin going right when we star the game
    boolean running;
    Timer timer;
    Random random;

    gamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if (running){
//            for (int i=0; i < SCREEN_HEIGHT/UNIT_SIZE;i++) {
//                //Draw lines across the y-axis
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//                //Draw lines across the x-axis
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//            }
            //Sets the Apple
            g.setColor(new Color(255,0,0));
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

            for (int i=0;i<bodyParts;i++){
                //Draws the Head of the snake
                if (i==0) {
                    g.setColor(new Color(0, 102, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                //Draws the body of the snake
                else{
                    g.setColor(new Color(0, 255, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(new Color(255,0,0));
            g.setFont(new Font("Georgia",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+appleEaten,(SCREEN_WIDTH-metrics.stringWidth("Score "+appleEaten))/2,30);
        }
        else if (!running){
            gameOver(g);
            timer.stop();
        }
    }

    public void newApple(){
        //The x-coordinate of our Apple equals random, Apple appear someplace along the x-axis
        appleX = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        //The y-coordinate of our Apple equals random, Apple appear someplace along the y-axis
        appleY = random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move(){
         for (int i=bodyParts;i>0;i--){
             x[i]=x[i-1];
             y[i]=y[i-1];
         }
         switch(direction){
                //Move to up
             case 'W':
                 y[0]-=UNIT_SIZE;
                 break;
                 //Move to down
             case 'S':
                 y[0]+=UNIT_SIZE;
                 break;
                 //Move to right
             case 'D':
                x[0]+=UNIT_SIZE;
                break;
                //Move to left
             case 'A':
                 x[0]-=UNIT_SIZE;
                break;
         }
    }

    public void checkApple(){
        if (x[0]==appleX && y[0]==appleY){
            appleEaten++;
            bodyParts++;
            newApple();
        }
    }

    public void checkCollisions(){
        for (int i=1;i<bodyParts;i++){
            //check if head collides with body
            if (x[0] == x[i] && y[0] == y[i])
                running = false;
            //check if head collides with right border
            if (x[0]>SCREEN_WIDTH){
                running = false;
            }
            //check if head collides with left border
            if (x[0]<0){
                running = false;
            }
            //check if head collides with top border
            if (y[0]<0){
                running = false;
            }
            //check if head collides with bottom border
            if (y[0]>SCREEN_HEIGHT){
                running = false;
            }
        }
    }

    public void gameOver(Graphics g){

        g.setColor(new Color(255,0,0));
        g.setFont(new Font("Georgia",Font.BOLD,60));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (running){
           move();
           checkApple();
           checkCollisions();
       }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_UP:
                    if (direction!='S'){
                        direction='W';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction!='W'){
                        direction='S';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction!='A'){
                        direction='D';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction!='D'){
                        direction='A';
                    }
                    break;
            }
        }
    }
}
