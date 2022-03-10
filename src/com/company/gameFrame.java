package com.company;

import javax.swing.*;

public class gameFrame extends JFrame {
    gameFrame(){
        this.add(new gamePanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);//Sets the location of the Window to center
    }

}
