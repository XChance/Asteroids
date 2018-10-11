package com.xchance.Asteroids.main.Entity;

import com.xchance.Asteroids.main.Game.GamePanel;
import com.xchance.Asteroids.main.Managers.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends Entity {

    //initilize bullet image
    private BufferedImage bulletImage;

    //set up limited tick amount that bullet can exist
    private int tickCount;
    private final int TICK_MAX = 26;

    //initialize variables, pos and velocity vectors based on player entity
    public Bullet(Player player){
        name = "BULLET";
        bulletImage = SpriteLoader.PLAYER_BULLET;

        isAlive = true;
        tickCount = 0;

        x = player.getX() + 12;
        y = player.getY() + 12;

        theta = player.theta;

        width = bulletImage.getWidth();
        height = bulletImage.getHeight();

        boundingBox = new Rectangle((int)x, (int)y, width, height);

        xVel = -10 * Math.cos((Math.PI / 2) + Math.toRadians(theta));
        yVel = -10 * Math.sin((Math.PI / 2) + Math.toRadians(theta));
    }

    public Bullet(Saucer saucer, double xVel, double yVel){
        name = "BULLET";
        bulletImage = SpriteLoader.ENEMY_BULLET;

        isAlive = true;
        tickCount = 0;

        x = saucer.getX() + 12;
        y = saucer.getY() + 12;

        this.xVel = xVel;
        this.yVel = yVel;

        width = bulletImage.getWidth();
        height = bulletImage.getHeight();

        boundingBox = new Rectangle((int)x, (int)y, width, height);
    }

    //update pos, handle tick limit
    @Override
    public void update() {
        boundingBox.setBounds((int)x, (int)y, width, height);
        move();
        getTicks();
    }

    //draw asteroids
    @Override
    public void draw(Graphics2D g) {
        boundingBox.setBounds((int) x, (int) y, width, height);
        g.drawImage(bulletImage, (int) x, (int) y, null);
    }

    //move bullet
    //if bullets exceeds screen bounds, put it on opposite side of screen
    public void move(){
        x += xVel;
        y += yVel;

        if(x >= GamePanel.WIDTH + 20){
            x = -15;
        }else if(x <= -20){
            x = GamePanel.WIDTH + 15;
        }

        if(y >= GamePanel.HEIGHT + 20){
            y = -15;
        }else if(y <= -20){
            y = GamePanel.HEIGHT + 15;
        }
    }

    //check if bullet has been around for too long
    public void getTicks(){
        if(tickCount >= TICK_MAX){
            isAlive = false;
        }
        tickCount++;
    }

}
