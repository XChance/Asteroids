package com.xchance.Asteroids.main.Managers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

//This class loads sprites from res folder
//Static sprites that can be called anywhere
public class SpriteLoader {

    public static BufferedImage BACKGROUND = load("res/icons/BACKGROUND.png");

    public static BufferedImage SHIP_DEFAULT = load("res/icons/SPACESHIP_DEFAULT.png");
    public static BufferedImage SHIP_THRUST = load("res/icons/SPACESHIP_THRUST.png");

    public static BufferedImage ENEMY_SAUCER = load("res/icons/ENEMY_SAUCER.png");

    public static BufferedImage PLAYER_BULLET = load("res/icons/PLAYER_BULLET.png");
    public static BufferedImage ENEMY_BULLET = load("res/icons/ENEMY_BULLET.png");

    public static BufferedImage ASTEROID_MEDIUM = load("res/icons/ASTEROID_MEDIUM.png");
    public static BufferedImage ASTEROID_SMALL = load("res/icons/ASTEROID_SMALL.png");

    public static BufferedImage[][] font = load("res/fonts/font.gif", 8, 8);

    //class for simple loading of images
    public static BufferedImage load(String s) {
        try {
            BufferedImage temp = ImageIO.read(new FileInputStream(s));
            return temp;
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error loading graphics.");
            System.exit(0);
        }
        return null;
    }

    public static BufferedImage[][] load(String s, int w, int h) {
        BufferedImage[][] ret;
        try {
            BufferedImage spritesheet = ImageIO.read(new FileInputStream(s));
            int width = spritesheet.getWidth() / w;
            int height = spritesheet.getHeight() / h;
            ret = new BufferedImage[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
                }
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading graphics.");
            System.exit(0);
        }
        return null;
    }

    public static void drawString (Graphics2D g, String s, int x, int y){
        s = s.toUpperCase();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 47) c = 36; // slash
            if (c == 58) c = 37; // colon
            if (c == 32) c = 38; // space
            if (c >= 65 && c <= 90) c -= 65; // letters
            if (c >= 48 && c <= 57) c -= 22; // numbers
            int row = c / font[0].length;
            int col = c % font[0].length;
            g.drawImage(font[row][col], x + 8 * i, y, null);
        }
    }

    public static void drawString (Graphics2D g, String s, int x, int y, int width, int height){
        s = s.toUpperCase();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 47) c = 36; // slash
            if (c == 58) c = 37; // colon
            if (c == 32) c = 38; // space
            if (c >= 65 && c <= 90) c -= 65; // letters
            if (c >= 48 && c <= 57) c -= 22; // numbers
            int row = c / font[0].length;
            int col = c % font[0].length;
            g.drawImage(font[row][col], x + width * i, y, width, height, null);
        }
    }

}
