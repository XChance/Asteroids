package com.xchance.Asteroids.main.Game;

import javax.swing.*;

public class Game {

    public static void main(String[] args){
        JFrame frame = new JFrame("Asteroids");

        frame.add(new GamePanel());

        frame.setResizable(false);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
