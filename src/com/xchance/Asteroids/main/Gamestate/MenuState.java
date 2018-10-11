package com.xchance.Asteroids.main.Gamestate;

import com.xchance.Asteroids.main.Entity.Entity;
import com.xchance.Asteroids.main.Game.GamePanel;
import com.xchance.Asteroids.main.Managers.GameStateManager;
import com.xchance.Asteroids.main.Managers.Keys;
import com.xchance.Asteroids.main.Managers.SpriteLoader;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class MenuState extends GameState implements MouseListener {

    private String title;
    private String play;
    private String quit;

    private int currentSelection;


    private BufferedImage selectionImage = SpriteLoader.SHIP_DEFAULT;
    private double rotation;

    public MenuState(GameStateManager gsm){
        super(gsm);
    }

    @Override
    public void init() {
        title = "Asteroids";
        play = "Play";
        quit = "Quit";

        currentSelection = 0;

        rotation = 0;

    }

    @Override
    public void update() {
        rotation += 2;
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
        drawMenu(g);
        drawSelection(g);
    }

    public void drawMenu(Graphics2D g){
        BufferedImage bufferedImage = new BufferedImage(GamePanel.WIDTH, GamePanel.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bufferedGraphics = bufferedImage.createGraphics();

        bufferedGraphics.drawImage(SpriteLoader.BACKGROUND,0, 0, null);

        SpriteLoader.drawString(bufferedGraphics, title, GamePanel.WIDTH/2 - 140, GamePanel.HEIGHT/2 - 140,
                28, 28);

        SpriteLoader.drawString(bufferedGraphics, play, GamePanel.WIDTH/2 - 60, GamePanel.HEIGHT/2 - 30,
                24, 24);

        SpriteLoader.drawString(bufferedGraphics, quit, GamePanel.WIDTH/2 - 60, GamePanel.HEIGHT/2,
                24, 24);

        g.drawImage(bufferedImage, 0, 0, null);
    }

    public void drawSelection(Graphics2D g){
        if(currentSelection == 0){
            g.drawImage(Entity.rotate(selectionImage, rotation), GamePanel.WIDTH/2 - 100,
                    GamePanel.HEIGHT/2 - 40, 40, 40, null);
        }else{
            g.drawImage(Entity.rotate(selectionImage, rotation), GamePanel.WIDTH/2 - 100,
                    GamePanel.HEIGHT/2 - 10, 40, 40, null);
        }
    }

    @Override
    public void handleInput() {
        if(Keys.isPressed(Keys.ENTER)){
            if(currentSelection == 0) {
                gsm.setState(GameStateManager.PLAY);
            }else{
                System.exit(0);
            }
        }
        if(Keys.isPressed(Keys.UP)){
            if(currentSelection == 0){
                currentSelection = 1;
            }else if(currentSelection == 1){
                currentSelection = 0;
            }
        }
        if(Keys.isPressed(Keys.DOWN)){
            if(currentSelection == 0){
                currentSelection = 1;
            }else if(currentSelection == 1){
                currentSelection = 0;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
