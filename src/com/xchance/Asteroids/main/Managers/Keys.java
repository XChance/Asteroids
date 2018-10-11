package com.xchance.Asteroids.main.Managers;

import java.awt.event.KeyEvent;

public class Keys {

    public static final int NUM_KEYS = 7;

    public static boolean[] keyState = new boolean[NUM_KEYS];
    public static boolean[] prevKeyState = new boolean[NUM_KEYS];

    public static int UP = 0;
    public static int DOWN = 1;
    public static int LEFT = 2;
    public static int RIGHT = 3;
    public static int SPACE = 4;
    public static int ENTER = 5;
    public static int ESCAPE = 6;

    public static void keySet(int i, boolean b){
        if(i == KeyEvent.VK_UP) keyState[UP] = b;
        if(i == KeyEvent.VK_DOWN) keyState[DOWN] = b;
        if(i == KeyEvent.VK_LEFT) keyState[LEFT] = b;
        if(i == KeyEvent.VK_RIGHT) keyState[RIGHT] = b;
        if(i == KeyEvent.VK_SPACE) keyState[SPACE] = b;
        if(i == KeyEvent.VK_ENTER) keyState[ENTER] = b;
        if(i == KeyEvent.VK_ESCAPE) keyState[ESCAPE] = b;
    }

    public static void update(){
        for(int i = 0; i < NUM_KEYS; i++){
            prevKeyState[i] = keyState[i];
        }
    }

    public static boolean isPressed(int i) {
        return keyState[i] && !prevKeyState[i];
    }

    public static boolean isDown(int i) {
        return keyState[i];
    }

}
