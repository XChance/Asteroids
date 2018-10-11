package com.xchance.Asteroids.main.Gamestate;

import com.xchance.Asteroids.main.Game.GamePanel;
import com.xchance.Asteroids.main.Managers.GameStateManager;

import java.awt.*;

public class GameoverState extends GameState {

    public GameoverState(GameStateManager gsm){
        super(gsm);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }

    @Override
    public void handleInput() {

    }
}
