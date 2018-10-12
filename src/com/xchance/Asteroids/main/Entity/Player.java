package com.xchance.Asteroids.main.Entity;

import com.xchance.Asteroids.main.Game.GamePanel;
import com.xchance.Asteroids.main.Managers.JukeBox;
import com.xchance.Asteroids.main.Managers.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class Player extends Entity {

    //initialize image variables
    private BufferedImage[] playerSprites = new BufferedImage[2];
    private BufferedImage currentImage;

    //initialize arraylist of bullets
    private ArrayList<Bullet> bullets = new ArrayList<>();

    //initialize movement boolean
    private boolean isMoving;

    //for number of lives and respawning
    private int lives;
    private int tickCounter;
    private int invulnerableMediator;
    private boolean invulnerable;

    //initialize variables
    public Player() {
        name = "PLAYER";

        lives = 3;
        invulnerableMediator = 0;
        invulnerable = false;
        tickCounter = 0;

        playerSprites[0] = SpriteLoader.SHIP_DEFAULT;
        playerSprites[1] = SpriteLoader.SHIP_THRUST;
        currentImage = playerSprites[0];

        x = GamePanel.WIDTH/2 - width/2;
        y = GamePanel.HEIGHT/2 - height;

        width = 40;
        height = 40;

        boundingWidth = width - 10;
        boundingHeight = height - 10;

        boundingX = x + 5;
        boundingY = y + 5;

        boundingBox = new Rectangle((int)boundingX, (int)boundingY, boundingWidth, boundingHeight);

        isMoving = false;
        isAlive = true;
    }

    //update position
    //update any/all bullets
    public void update() {
        boundingX = x + 5;
        boundingY = y + 5;
        boundingBox.setBounds((int)boundingX, (int)boundingY, boundingWidth, boundingHeight);
        move();
        updateBullets();
        checkInvulnerable();
    }

    //move player
    public void move() {
        //if is moving, get vector based on theta (rotation value)
        //also set currentimage based on whether or not player is moving
        if(isMoving) {
            currentImage = playerSprites[1];
            xVel = -5 * Math.cos((Math.PI / 2) + Math.toRadians(theta));
            yVel = -5 * Math.sin((Math.PI / 2) + Math.toRadians(theta));
        }else {
            //if player is not moving, player drifts for a few moments rather than stopping completely
            currentImage = playerSprites[0];
            xVel *= 0.95;
            yVel *= 0.95;
        }

        //move player
        x += xVel;
        y += yVel;

        //if player exits bounds of screen, player is placed on opposite side
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

    //draw player, and any/all bullets
    @Override
    public void draw(Graphics2D g) {
        drawBullets(g);
        if(!invulnerable) {
            boundingX = x + 5;
            boundingY = y + 5;
            boundingBox.setBounds((int) boundingX, (int) boundingY, boundingWidth, boundingHeight);
            g.setColor(Color.GREEN);
            g.drawImage(rotate(currentImage, theta), (int) x, (int) y, width, height, null);
        }else {
            if(invulnerableMediator % 4 == 0) {
                boundingX = x + 5;
                boundingY = y + 5;
                boundingBox.setBounds((int) boundingX, (int) boundingY, boundingWidth, boundingHeight);
                g.setColor(Color.GREEN);
                g.drawImage(rotate(currentImage, theta), (int) x, (int) y, width, height, null);
            }
        }
    }

    //player shoots bullet
    //does so by adding new bullet to bullet arraylist
    public void shoot(){
        if(bullets.size() <= 3) {
            bullets.add(new Bullet(this));
            if(!JukeBox.isPlaying("BULLET_SHOOT")) {
                JukeBox.play("BULLET_SHOOT");
            }
        }
    }

    public void respawn(){
        JukeBox.play("EXPLOSION");
        if(lives != 0) {
            x = GamePanel.WIDTH / 2 - width / 2;
            y = GamePanel.HEIGHT / 2 - height;
            lives--;
            invulnerable = true;
        }else{
            isAlive = false;
        }
    }

    //iterate through bullets, update any/all
    public void updateBullets(){
        for(int i = 0; i < bullets.size(); i++){
            if(!bullets.get(i).isAlive()){
                bullets.remove(i);
            }else {
                bullets.get(i).update();
            }
        }
    }

    //iterate through bullets, draw any/all
    public void drawBullets(Graphics2D g){
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).draw(g);
        }
    }

    public void checkInvulnerable(){
        if(invulnerable && tickCounter >= 40){
            invulnerable = false;
        }else if(invulnerable){
            tickCounter++;
            invulnerableMediator++;
        }else{
            tickCounter = 0;
            invulnerableMediator = 0;
        }
    }

    //Getters & Setters

    public BufferedImage getCurrentImage(){
        return currentImage;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }


}