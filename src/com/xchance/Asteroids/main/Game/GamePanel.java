package com.xchance.Asteroids.main.Game;

import com.xchance.Asteroids.main.Managers.GameStateManager;
import com.xchance.Asteroids.main.Managers.Keys;
import com.xchance.Asteroids.main.Managers.MouseHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

    private boolean running = false;
    private Thread thread;
    private final int FPS = 30;
    private final int TARGET_TIME = 1000 / FPS;

    private boolean isMenu;
    private boolean isGameOver;

    private GameStateManager gsm;

    public GamePanel(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(this);

        thread = new Thread(this, "Display");
        thread.start();
    }

    public void init(){
        running = true;
        gsm = new GameStateManager();
        isMenu = false;
        isGameOver = false;
    }

    @Override
    public void run() {
        init();

        long start;
        long elapsed;
        long wait;

        while(running){
            start = System.nanoTime();

            update();
            repaint();

            elapsed = System.nanoTime() - start;

            wait = TARGET_TIME - elapsed / 1000000;

           if(wait < 0){
               wait = TARGET_TIME;
           }

            try {
                Thread.sleep(wait);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void update(){
        MouseHelper.getPos(getLocationOnScreen());
        gsm.update();
        Keys.update();
        checkMouseListener();
    }

    public void checkMouseListener(){
        if(gsm.getCurrentState() == GameStateManager.MENU && !isMenu) {
            addMouseListener((MouseListener) gsm.currentGameState(GameStateManager.MENU));
            isMenu = true;
        }
        if(gsm.getCurrentState() == GameStateManager.GAMEOVER && !isGameOver){
            addMouseListener((MouseListener) gsm.currentGameState(GameStateManager.GAMEOVER));
            System.out.println("  ff");
            isGameOver = true;
        }

        if(gsm.getCurrentState() != GameStateManager.MENU){
            isMenu = false;
        }
        if(gsm.getCurrentState() != GameStateManager.GAMEOVER){
            isGameOver = false;
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        Map<RenderingHints.Key, Object> renderingHints = new HashMap<>();
        renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(renderingHints);

        gsm.draw(g2d);
        g2d.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent key) {
        Keys.keySet(key.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent key) {
        Keys.keySet(key.getKeyCode(), false);
    }

}