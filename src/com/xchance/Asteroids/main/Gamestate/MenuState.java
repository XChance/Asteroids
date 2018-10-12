package com.xchance.Asteroids.main.Gamestate;

import com.xchance.Asteroids.main.Entity.Entity;
import com.xchance.Asteroids.main.Game.GamePanel;
import com.xchance.Asteroids.main.Managers.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

@SuppressWarnings("Duplicates")
public class MenuState extends GameState implements MouseListener {

    private String title;
    private String play;
    private String quit;

    //current selected menu option
    private int currentSelection;

    //image that shows which option is currently selected
    private BufferedImage selectionImage = SpriteLoader.SHIP_DEFAULT;
    private double rotation;

    private Rectangle playBox;
    private Rectangle quitBox;
    private Rectangle mouseBox;

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

        playBox = new Rectangle(GamePanel.WIDTH/2 - 70, GamePanel.HEIGHT/2 - 30, 120, 26);
        quitBox = new Rectangle(GamePanel.WIDTH/2 - 70, GamePanel.HEIGHT/2, 120, 26);
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

    public void setMousePos(){
        double x = MouseInfo.getPointerInfo().getLocation().getX() - MouseHelper.mousePos.getX();
        double y = MouseInfo.getPointerInfo().getLocation().getY() - MouseHelper.mousePos.getY();
        mouseBox.setBounds((int)x - 2, (int)y - 2, 2, 2);
    }

    public void getMouseSelection(){
        if (mouseBox.intersects(playBox) && currentSelection != 0) {
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
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(gsm.getCurrentState() == GameStateManager.MENU) {
            if (mouseBox.intersects(playBox)) {
                gsm.setState(GameStateManager.PLAY);
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
