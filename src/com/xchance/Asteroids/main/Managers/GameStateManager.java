package com.xchance.Asteroids.main.Managers;

import com.xchance.Asteroids.main.Gamestate.*;

import java.awt.Graphics2D;

public class GameStateManager {
    private boolean paused;
    private PauseState pauseState;

    private GameState[] gameStates;
    private int currentState;
    private int previousState;

    public static final int NUM_STATES = 3;
    public static final int MENU = 0;
    public static final int PLAY = 1;
    public static final int GAMEOVER = 2;

    public GameStateManager(){
        JukeBox.init();
        JukeBox.load("sounds/BULLET_SHOOT.wav", "BULLET_SHOOT");
        JukeBox.setVolume("BULLET_SHOOT", -10);
        JukeBox.load("sounds/ASTEROID_SHOT.wav", "ASTEROID_SHOT");
        JukeBox.setVolume("ASTEROID_SHOT", -10);
        JukeBox.load("sounds/EXPLOSION.wav", "EXPLOSION");
        JukeBox.setVolume("EXPLOSION", -10);
        JukeBox.load("sounds/PLAYER_THRUST.wav", "THRUST");
        JukeBox.setVolume("THRUST", -15);

        paused = false;
        pauseState = new PauseState(this);

        gameStates = new GameState[NUM_STATES];
        setState(MENU);
    }

    public void setState(int i){
        previousState = currentState;
        unloadState(previousState);
        currentState = i;
        if(i == MENU){
            gameStates[i] = new MenuState(this);
            gameStates[i].init();
        }else if(i == PLAY){
            gameStates[i] = new PlayState(this);
            gameStates[i].init();
        }else if(i == GAMEOVER){
            gameStates[i] = new GameoverState(this);
            gameStates[i].init();
        }
    }

    public void update(){
        if(paused){
            pauseState.update();
        }else{
            gameStates[currentState].update();
        }
    }

    public void draw(Graphics2D g){
        if(paused){
            pauseState.draw(g);
        }else{
            gameStates[currentState].draw(g);
        }
    }

    public void unloadState(int i) {
        gameStates[i] = null;
    }

    public void setPaused(boolean b) {
        paused = b;
    }

}
