package com.xchance.Asteroids.main.Managers;

import com.xchance.Asteroids.main.Entity.Entity;

public class CollisionManager {

    public static boolean collision(Entity e1, Entity e2){
        if (e1.getBoundingBox().intersects(e2.getBoundingBox())) {
            return true;
        }else {
            return false;
        }
    }

}
