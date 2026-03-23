package com.example.videogame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Entity {
    protected float x, y;
    protected Bitmap bitmap;
    protected int width, height;

    public Entity(Bitmap bitmap, float x, float y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public Rect getBounds() {
        return new Rect((int) x, (int) y, (int) (x + width), (int) (y + height));
    }

    public float getX() { return x; }
    public float getY() { return y; }
}
