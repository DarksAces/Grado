package com.example.videogame;

import android.graphics.Bitmap;

public class Player extends Entity {
    public Player(Bitmap bitmap, float x, float y) {
        super(bitmap, x, y);
    }

    public void move(float dy, int screenHeight) {
        y += dy;
        if (y < 0) y = 0;
        if (y > screenHeight - height) y = screenHeight - height;
    }

    public void setCenterY(float centerY, int screenHeight) {
        this.y = centerY - (height / 2f);
        if (y < 0) y = 0;
        if (y > screenHeight - height) y = screenHeight - height;
    }
}
