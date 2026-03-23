package com.example.videogame;

import android.graphics.Bitmap;

public class Enemy extends Entity {
    private float speed;
    private boolean canShoot;

    public Enemy(Bitmap bitmap, float x, float y, float speed, boolean canShoot) {
        super(bitmap, x, y);
        this.speed = speed;
        this.canShoot = canShoot;
    }

    public void update() {
        x += speed;
    }

    public boolean canShoot() {
        return canShoot;
    }
}
