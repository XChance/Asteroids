package com.xchance.Asteroids.main.Gamestate;

import com.xchance.Asteroids.main.Entity.Entity;
import com.xchance.Asteroids.main.Game.GamePanel;
import com.xchance.Asteroids.main.Managers.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

@SuppressWarnings("Duplicates")
public class GameoverState extends GameState implements MouseListener {

    private int finalScore;

    private BufferedImage background;

    //current selected menu option
    private int currentSelection;

    //image that shows which option is currently selected
    private BufferedImage selectionImage = SpriteLoader.SHIP_DEFAULT;
    private double rotation;

    private Rectangle menuBox;
    private Rectangle quitBox;
    private Rectangle mouseBox;

    public GameoverState(GameStateManager gsm, int score){
        super(gsm);
        this.finalScore = score;
    }

    @Override
    public void init() {
        background = SpriteLoader.BACKGROUND;

        currentSelection = 0;
        rotation = 0;

        menuBox = new Rectangle(GamePanel.WIDTH/2 - 270,
                GamePanel.HEIGHT/2 + 30, 240, 26);
        quitBox = new Rectangle(GamePanel.WIDTH/2 + 30,
                GamePanel.HEIGHT/2 + 30, 240, 26);
        mouseBox = new Rectangle(0,0,0,0);
    }

    @Override
    public void update() {
        rotation += 2;
        handleInput();
        setMousePos();
        getMouseSelection();
    }

    @Override
    public void draw(Graphics2D g) {
       drawScreen(g);
    }

    public void drawScreen(Graphics2D g){
        BufferedImage bufferedImage = new BufferedImage(GamePanel.WIDTH, GamePanel.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bufferedGraphics = bufferedImage.createGraphics();

        bufferedGraphics.drawImage(background, 0, 0, null);

        SpriteLoader.drawString(bufferedGraphics, "GameOver", GamePanel.WIDTH/2 - 120,
                GamePanel.HEIGHT/2 - 160, 28, 28);

        SpriteLoader.drawString(bufferedGraphics, "Score:" + finalScore, GamePanel.WIDTH/2 - 130,
                GamePanel.HEIGHT/2 - 75, 24, 24);

        SpriteLoader.drawString(bufferedGraphics, "Main Menu", GamePanel.WIDTH/2 - 260,
                GamePanel.HEIGHT/2 + 30, 24, 24);

        SpriteLoader.drawString(bufferedGraphics, "Quit Game", GamePanel.WIDTH/2 + 40,
                GamePanel.HEIGHT/2 + 30, 24, 24);

        SpriteLoader.drawString(bufferedGraphics, "Created by", GamePanel.WIDTH/2 - 80,
                GamePanel.HEIGHT/2 + 140, 16, 16);

        SpriteLoader.drawString(bufferedGraphics, "Xian Chance", GamePanel.WIDTH/2 - 90,
                GamePanel.HEIGHT/2 + 160, 16, 16);

        drawSelection(bufferedGraphics);

        g.drawImage(bufferedImage, 0, 0, null);
    }

    public void drawSelection(Graphics2D g){
        if(currentSelection == 0){
            g.drawImage(Entity.rotate(selectionImage, rotation), GamePanel.WIDTH/2 - 288,
                    GamePanel.HEIGHT/2 + 28, null);
        }else{
            g.drawImage(Entity.rotate(selectionImage, rotation), GamePanel.WIDTH/2 + 12,
                    GamePanel.HEIGHT/2 + 28, null);
        }
    }

    public void setMousePos(){
        double x = MouseInfo.getPointerInfo().getLocation().getX() - MouseHelper.mousePos.getX();
        double y = MouseInfo.getPointerInfo().getLocation().getY() - MouseHelper.mousePos.getY();
        mouseBox.setBounds((int)x - 2, (int)y - 2, 2, 2);
    }

    public void getMouseSelection(){
        if (mouseBox.intersects(menuBox) && currentSelection != 0) {
            currentSelection = 0;
            JukeBox.play("SELECT");
        } else if (mouseBox.intersects(quitBox) && currentSelection != 1) {
            currentSelection = 1;
            JukeBox.play("SELECT");
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
            JukeBox.play("SELECT");
            if(currentSelection == 0){
                currentSelection = 1;
            }else if(currentSelection == 1){
                currentSelection = 0;
            }
        }
        if(Keys.isPressed(Keys.DOWN)){
            JukeBox.play("SELECT");
            if(currentSelection == 0){
                currentSelection = 1;
            }else if(currentSelection == 1){
                currentSelection = 0;
            }
        }
        if(Keys.isPressed(Keys.LEFT)){
            JukeBox.play("SELECT");
            if(currentSelection == 0){
                currentSelection = 1;
            }else if(currentSelection == 1){
                currentSelection = 0;
            }
        }
        if(Keys.isPressed(Keys.RIGHT)){
            JukeBox.play("SELECT");
            if(currentSelection == 0){
                currentSelection = 1;
            }else if(currentSelection == 1){
                currentSelection = 0;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(gsm.getCurrentState() == GameStateManager.GAMEOVER) {
            if (mouseBox.intersects(menuBox)) {
                gsm.setState(GameStateManager.MENU);
            } else if (mouseBox.intersects(quitBox)) {
                System.exit(0);
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
