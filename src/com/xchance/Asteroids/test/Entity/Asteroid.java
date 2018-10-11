package com.xchance.Asteroids.main.Entity;

import com.xchance.Asteroids.main.Game.GamePanel;
import com.xchance.Asteroids.main.Managers.CollisionManager;
import com.xchance.Asteroids.main.Managers.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@SuppressWarnings("Duplicates")
public class Asteroid extends Entity {

    //asteroid images
    private BufferedImage[] asteroids;
    private BufferedImage currentImage;

    //image size
    private int size;
    public static final int LARGE = -1;
    public static final int MEDIUM = 0;
    public static final int SMALL = 1;

    private Random rd;

    //initializing variables, default constructor (large asteroid)
    public Asteroid(Player player) {
        name = "ASTEROID";
        rd = new Random();

        size = LARGE;

        choosePos();
        chooseVel();

        asteroids = new BufferedImage[2];
        asteroids[0] = SpriteLoader.ASTEROID_MEDIUM;
        asteroids[1] = SpriteLoader.ASTEROID_SMALL;
        currentImage = asteroids[0];

        width = (int) (currentImage.getWidth() * 1.5);
        height = (int) (currentImage.getHeight() * 1.5);

        boundingWidth = width - 30;
        boundingHeight = height - 30;

        boundingX = x + 12;
        boundingY = y + 18;

        boundingBox = new Rectangle((int) boundingX, (int) boundingY, boundingWidth, boundingHeight);

        isAlive = true;
    }

    //initliaze variables, subAsteroid constructor
    public Asteroid(int size, double x, double y) {
        name = "ASTEROID";
        rd = new Random();

        this.x = x;
        this.y = y;

        this.size = size;

        chooseVel();

        asteroids = new BufferedImage[2];
        asteroids[0] = SpriteLoader.ASTEROID_MEDIUM;
        asteroids[1] = SpriteLoader.ASTEROID_SMALL;

        if (size == MEDIUM) {
            currentImage = asteroids[MEDIUM];
            width = currentImage.getWidth();
            height = currentImage.getHeight();

            boundingWidth = width - 30;
            boundingHeight = height - 30;

            boundingX = x + 12;
            boundingY = y + 18;

        } else if (size == SMALL) {
            currentImage = asteroids[SMALL];
            width = currentImage.getWidth();
            height = currentImage.getHeight();

            boundingWidth = width - 10;
            boundingHeight = height - 10;

            boundingX = x + 5;
            boundingY = y + 5;
        }

        boundingBox = new Rectangle((int) boundingX, (int) boundingY, boundingWidth, boundingHeight);

        isAlive = true;
    }

    //update movement
    @Override
    public void update() {
        if (size == MEDIUM || size == LARGE) {
            boundingX = x + 12;
            boundingY = y + 18;
        } else {
            boundingX = x + 5;
            boundingY = y + 5;
        }

        boundingBox.setBounds((int) boundingX, (int) boundingY, boundingWidth, boundingHeight);
        move();
    }

    //move asteroid
    public void move() {
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

    //draw asteroid
    @Override
    public void draw(Graphics2D g) {
        if (size == MEDIUM || size == LARGE) {
            boundingX = x + 12;
            boundingY = y + 18;
        } else {
            boundingX = x + 5;
            boundingY = y + 5;
        }

        boundingBox.setBounds((int) boundingX, (int) boundingY, boundingWidth, boundingHeight);
        g.drawImage(currentImage, (int) x, (int) y, width, height, null);
    }


    //chooses a random velocity for asteroid
    public void chooseVel() {
        int temp = rd.nextInt(4);

        if (temp == 0) {
            xVel = (rd.nextInt(3) + 1);
            yVel = (rd.nextInt(3) + 1);
        } else if (temp == 1) {
            xVel = -(rd.nextInt(3) + 1);
            yVel = -(rd.nextInt(3) + 1);
        } else if (temp == 2) {
            xVel = -(rd.nextInt(3) + 1);
            yVel = rd.nextInt(3) + 1;
        } else if (temp == 3) {
            xVel = (rd.nextInt(3) + 1);
            yVel = -(rd.nextInt(3) + 1);
        }

        if(xVel == 0 || yVel == 0){
            chooseVel();
        }
    }

    //chooses a random position for asteroid within outer screen bounds
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

    public int getSize() {
        return size;
    }

}