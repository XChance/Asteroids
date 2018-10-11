package com.xchance.Asteroids.main.Entity;

import com.xchance.Asteroids.main.Game.GamePanel;
import com.xchance.Asteroids.main.Managers.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("Duplicates")
public class Saucer extends Entity {

    private ArrayList<Bullet> bullets;

    private BufferedImage saucerImage;

    private int tickCount;

    private Random rd;

    public Saucer(){
        bullets = new ArrayList<>();
        rd = new Random();

        choosePos();
        chooseVel();

        saucerImage = SpriteLoader.ENEMY_SAUCER;

        tickCount = 0;
        isAlive = true;

        width = 40;
        height = 40;

        boundingWidth = width - 10;
        boundingHeight = height - 14;

        boundingX = x + 5;
        boundingY = y + 7;

        boundingBox = new Rectangle((int)boundingX, (int)boundingY, boundingWidth, boundingHeight);
    }

    @Override
    public void update() {
        updateBoundingBox();
        tickCount++;
        move();
        updateBullets();
        changeVel();
    }

    @Override
    public void draw(Graphics2D g) {
        updateBoundingBox();
        g.drawImage(saucerImage, (int)x, (int)y, 40, 40, null);
        drawBullets(g);
        g.setColor(Color.GREEN);
        g.draw(boundingBox);
    }

    public void move(){
        x += xVel;
        y += yVel;

        if (x >= GamePanel.WIDTH) {
            x = -width;
        } else if (x <= -width) {
            x = GamePanel.WIDTH;
        }

        if (y >= GamePanel.HEIGHT) {
            y = -height;
        } else if (y <= -height) {
            y = GamePanel.HEIGHT;
        }
    }

    public void shoot(double angle, int dir){
        double xVel, yVel;
        if(tickCount % 100 == 0) {
            if (dir == 0) {
                xVel = 12 * Math.cos(angle);
                yVel = 12 * Math.sin(angle);
            } else {
                xVel = -12 * Math.cos(angle);
                yVel = 12 * Math.sin(angle);
            }
            chooseVel();
            bullets.add(new Bullet(this, xVel, yVel));
        }
    }

    public void updateBullets(){
        for(int i = 0; i < bullets.size(); i++){
            if(bullets.get(i).isAlive) {
                bullets.get(i).update();
            }else{
                bullets.remove(i);
            }
        }
    }

    public void drawBullets(Graphics2D g){
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).draw(g);
        }
    }

    public void choosePos(){
        int temp = rd.nextInt(4);

        if (temp == 0) {
            x = GamePanel.WIDTH + width/2;
            y = GamePanel.HEIGHT + height/2;
        } else if (temp == 1) {
            x = -width + width/2;
            y = -height + height/2;
        } else if (temp == 2) {
            x = -width + width/2;
            y = height + height/2;
        } else if (temp == 3) {
            x = width + width/2;
            y = -height + height/2;
        }
    }

    //chooses random velocity for saucer
    public void chooseVel(){
        int temp = rd.nextInt(4);

        if (temp == 0) {
            xVel = (rd.nextInt(3) + 2);
            yVel = (rd.nextInt(3) + 2);
        } else if (temp == 1) {
            xVel = -(rd.nextInt(3) + 2);
            yVel = -(rd.nextInt(3) + 2);
        } else if (temp == 2) {
            xVel = -(rd.nextInt(3) + 2);
            yVel = rd.nextInt(3) + 2;
        } else if (temp == 3) {
            xVel = (rd.nextInt(3) + 2);
            yVel = -(rd.nextInt(3) + 2);
        }

        if(xVel == 0 || yVel == 0){
            chooseVel();
        }
    }

    public void changeVel(){
        if(tickCount >= 180){
            chooseVel();
            tickCount = 0;
        }
    }

    public void updateBoundingBox(){
        boundingX = x + 5;
        boundingY = y + 7;
        boundingBox.setBounds((int)boundingX, (int)boundingY, boundingWidth, boundingHeight);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

}
