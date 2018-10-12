package com.xchance.Asteroids.main.Gamestate;

import com.xchance.Asteroids.main.Entity.Asteroid;
import com.xchance.Asteroids.main.Entity.Player;
import com.xchance.Asteroids.main.Entity.Saucer;
import com.xchance.Asteroids.main.Game.GamePanel;
import com.xchance.Asteroids.main.Managers.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PlayState extends GameState {

    //Initialize game variables
    private Player player;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Saucer> saucers;

    private final BufferedImage BACKGROUND = SpriteLoader.BACKGROUND;

    private int score;
    private int lifeCounter;

    //every time the score hits a certain value, saucer is spawned
    //this value is for regulating that
    private int saucerCounter;

    //Instantiate playstate with game state manager
    public PlayState(GameStateManager gsm){
        super(gsm);
    }

    //initialize variables, called in constructor superclass
    @Override
    public void init() {
        player = new Player();
        asteroids = new ArrayList<>();
        saucers = new ArrayList<>();

        asteroids.add(new Asteroid(player));
        asteroids.add(new Asteroid(player));
        asteroids.add(new Asteroid(player));

        score = 0;
    }

    //update all objects in play state
    @Override
    public void update() {
        checkGameOver();

        checkCollision();

        player.update();
        updateAsteroids();
        updateSaucers();

        shootSaucers();

        managePlayer();
        manageAsteroids();
        manageSaucers();

        handleInput();
    }

    //draw background
    //draw all objects in play state
    @Override
    public void draw(Graphics2D g) {
        g.drawImage(BACKGROUND, 0, 0, null);
        drawAsteroids(g);
        player.draw(g);
        drawSaucers(g);

        SpriteLoader.drawString(g, "Score:" + score, 10, 10, 20, 20);
        for(int i = 0; i < player.getLives(); i++){
            g.drawImage(SpriteLoader.SHIP_DEFAULT, 5 + (i * SpriteLoader.SHIP_DEFAULT.getWidth()),
                    GamePanel.HEIGHT - SpriteLoader.SHIP_DEFAULT.getHeight(), null);
        }
    }

    public void managePlayer(){
        if(lifeCounter >= 5000){
            player.setLives(player.getLives() + 1);
            lifeCounter = 0;
        }
    }

    //iterate through all asteroids, draw them
    public void drawAsteroids(Graphics2D g){
        for(int i = 0; i < asteroids.size(); i++){
            asteroids.get(i).draw(g);
        }
    }

    //iterate through all asteroids, update them
    public void updateAsteroids(){
        for(int i = 0; i < asteroids.size(); i++){
            if(asteroids.get(i).isAlive()) {
                asteroids.get(i).update();
            }else{
                asteroids.remove(i);
            }
        }
    }

    //ensures enough asteroids are on screen at once
    public void manageAsteroids() {
        int counter = 0;
        for(int i = 0; i < asteroids.size(); i++){
            if(asteroids.get(i).getSize() == Asteroid.LARGE){
                counter++;
            }
        }

        if (counter < 1) {
            asteroids.add(new Asteroid(player));
        }
    }

    //runs all collision methods
    public void checkCollision(){
        checkPlayerBulletCollision();
        checkEnemyBulletCollision();
        if(!player.isInvulnerable()) {
            checkPlayerCollision();
        }
    }

    //iterate through all asteroids, get collision with any player bullets
    //iterate through all saucers, get collision with any player bullets
    public void checkPlayerBulletCollision() {

        for(int i = 0; i < asteroids.size(); i++){
            for(int j = 0; j < player.getBullets().size(); j++){

                if(CollisionManager.collision(asteroids.get(i), player.getBullets().get(j))) {
                    JukeBox.play("ASTEROID_SHOT");
                    player.getBullets().remove(j);
                    scoreCheck(asteroids.get(i));
                    asteroids.get(i).setAlive(false);

                    if(asteroids.get(i).getSize() != Asteroid.SMALL) {

                        asteroids.add(new Asteroid(asteroids.get(i).getSize() + 1,
                                asteroids.get(i).getX(), asteroids.get(i).getY()));
                        asteroids.add(new Asteroid(asteroids.get(i).getSize() + 1,
                                asteroids.get(i).getX(), asteroids.get(i).getY()));
                    }

                }
            }
        }

        for(int i = 0; i < saucers.size(); i++){
            for(int j = 0; j < player.getBullets().size(); j++){

                if(CollisionManager.collision(saucers.get(i), player.getBullets().get(j))){
                    JukeBox.play("EXPLOSION");
                    saucers.get(i).setAlive(false);
                    player.getBullets().remove(j);
                    addScore(500);
                }

            }
        }
    }

    //iterates through all asteroids and saucers, checks for collision with
    //any saucer bullets and any asteroid
    public void checkEnemyBulletCollision(){
        for(int i = 0; i < asteroids.size(); i++){
            for(int j = 0; j < saucers.size(); j++){
                for(int k = 0; k < saucers.get(j).getBullets().size(); k++) {

                    if (CollisionManager.collision(asteroids.get(i), saucers.get(j).getBullets().get(k))){
                        JukeBox.play("ASTEROID_SHOT");
                        asteroids.get(i).setAlive(false);
                        saucers.get(j).getBullets().remove(k);

                        if(asteroids.get(i).getSize() != Asteroid.SMALL) {

                            asteroids.add(new Asteroid(asteroids.get(i).getSize() + 1,
                                    asteroids.get(i).getX(), asteroids.get(i).getY()));
                            asteroids.add(new Asteroid(asteroids.get(i).getSize() + 1,
                                    asteroids.get(i).getX(), asteroids.get(i).getY()));
                        }
                    }

                }

            }
        }
    }

    //iterate through all asteroids, get collision with player
    //iterate through all saucer bullets, get collision with player
    //iterate through all saucers, get collision with player;
    public void checkPlayerCollision(){

        for (int i = 0; i < asteroids.size(); i++) {
            if(CollisionManager.collision(player, asteroids.get(i))){
                if(player.getLives() == 0){
                    player.setAlive(false);
                }else{
                    player.respawn();
                }
            }

            for(int j = 0; j < saucers.size(); j++) {
                for (int k = 0; k < saucers.get(j).getBullets().size(); k++) {
                    if (CollisionManager.collision(player, saucers.get(j).getBullets().get(k))) {
                        saucers.get(j).getBullets().remove(k);
                        player.respawn();
                    }
                }
            }
        }

        for(int i = 0; i < saucers.size(); i++){
            if(CollisionManager.collision(player, saucers.get(i))){
                if(player.getLives() == 0){
                    player.respawn();
                    saucers.get(i).setAlive(false);
                }else{
                    player.respawn();
                    saucers.get(i).setAlive(false);
                }
            }
        }

    }

    //every 2000 score, new saucer spawns
    public void manageSaucers(){
        if(saucerCounter >= 1400){
            saucers.add(new Saucer());
            saucerCounter = 0;
        }

    }

    //update all saucers
    //if saucer is dead, remove from arraylist
    public void updateSaucers(){
        for (int i = 0; i < saucers.size(); i++) {
            if (saucers.get(i).isAlive()) {
                saucers.get(i).update();
            }else{
                saucers.remove(i);
            }
        }
    }

    //draw all saucers
    public void drawSaucers(Graphics2D g){
        if(saucers.size() > 0) {
            for (int i = 0; i < saucers.size(); i++) {
                saucers.get(i).draw(g);
            }
        }
    }

    //if player is within a certain distance of saucer, saucer shoots at player
    //saucer also gets angle between player and saucer, which can give vector for shot direction
    public void shootSaucers() {
        double distance;
        double opposite;
        double angle;
        int dir;

        for(int i = 0; i < saucers.size(); i++) {
            if (saucers.get(i).getX() >= player.getX()) {
                dir = 1;
            } else {
                dir = 0;
            }

            distance = Math.sqrt(Math.pow(player.getX() - saucers.get(i).getX(), 2) +
                    Math.pow(player.getY() - saucers.get(i).getY(), 2));
            opposite = player.getY() - saucers.get(i).getY();
            angle = Math.asin(opposite / distance);

            if (distance <= 220 && distance >= 50) {
                saucers.get(i).shoot(angle, dir);
            }
        }

    }

    //checks if player has died, indicating gameover
    public void checkGameOver(){
        if(!player.isAlive()){
            if(JukeBox.isPlaying("THRUST")){
                JukeBox.stop("THRUST");
            }
            gsm.setState(GameStateManager.GAMEOVER, score);
        }
    }

    //checks and updates score
    public void scoreCheck(Asteroid asteroid){
        switch (asteroid.getSize()){
            case Asteroid.LARGE:
                addScore(50);
                break;

            case Asteroid.MEDIUM:
                addScore(100);
                break;

            case Asteroid.SMALL:
                addScore(200);
                break;
        }
    }

    //handle keyboard input and resulting effects on player
    @Override
    public void handleInput() {
        if(Keys.isDown(Keys.UP)){
            player.setMoving(true);
            if(!JukeBox.isPlaying("THRUST") && player.isAlive()) {
                JukeBox.play("THRUST");
            }
        }else{
            player.setMoving(false);
            JukeBox.stop("THRUST");
        }

        if(Keys.isDown(Keys.LEFT)){
            player.settheta(player.gettheta() -10.5);
        }
        if(Keys.isDown(Keys.RIGHT)){
            player.settheta(player.gettheta() + 10.5);
        }
        if(Keys.isPressed(Keys.SPACE)){
            player.shoot();
        }
        if(Keys.isPressed(Keys.ESCAPE)){
            gsm.setPaused(true);
        }
    }

    public void addScore(int score){
        this.score += score;
        saucerCounter += score;
        lifeCounter += score;
    }

}