package com.xchance.Asteroids.main.Gamestate;

import com.xchance.Asteroids.main.Game.GamePanel;
import com.xchance.Asteroids.main.Managers.GameStateManager;
import com.xchance.Asteroids.main.Managers.Keys;
import com.xchance.Asteroids.main.Managers.SpriteLoader;

import java.awt.*;

public class PauseState extends GameState {

    public PauseState(GameStateManager gsm){
        super(gsm);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
        SpriteLoader.drawString(g, "Paused", GamePanel.WIDTH/2 - 80,
                GamePanel.HEIGHT/2 - 140, 24, 24);
    }

    @Override
    public void handleInput() {
        if(Keys.isPressed(Keys.ESCAPE)){
            gsm.setPaused(false);
        }
    }

}
