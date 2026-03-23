package com.example.videogame;

import android.graphics.Bitmap;

public class Bullet extends Entity {
    private float speed;

    public Bullet(Bitmap bitmap, float x, float y, float speed) {
        super(bitmap, x, y);
        this.speed = speed;
    }

    public void update() {
        x += speed;
    }
}
