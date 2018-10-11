package com.xchance.Asteroids.main.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    //size
    protected int width;
    protected int height;

    //position
    protected double x;
    protected double y;

    //velocity vectors
    protected double xVel;
    protected double yVel;

    //Angle of image rotation
    protected double theta;

    //whether or not entity has not been collided
    protected boolean isAlive;

    //entity name
    protected String name;

    //rectangle that goes around sprites, acts as collision detector using intersect method
    //simplistic, but serves the purpose of game
    protected Rectangle boundingBox;

    //bounding box size
    protected int boundingWidth, boundingHeight;
    protected double boundingX, boundingY;


    public abstract void update();
    public abstract void draw(Graphics2D g);

    //rotate image based on theta (double rotation)
    public static BufferedImage rotate(BufferedImage img, double rotation) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        Graphics2D g2 = newImage.createGraphics();
        g2.rotate(Math.toRadians(rotation), w / 2 , h / 2);
        g2.drawImage(img, null, 0, 0);
        return newImage;
    }

    //getters and setters


    public String getName() {
        return name;
    }

    public void settheta(double theta) {
        this.theta = theta;
    }

    public double gettheta() {
        return theta;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }
}
