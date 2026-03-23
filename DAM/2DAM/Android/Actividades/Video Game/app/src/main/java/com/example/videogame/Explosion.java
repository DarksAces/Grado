package com.example.videogame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Explosion {
    private float x, y;
    private List<Particle> particles;
    private boolean finished;
    private Random random = new Random();

    public Explosion(float x, float y) {
        this.x = x;
        this.y = y;
        this.particles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            particles.add(new Particle(x, y));
        }
    }

    public void update() {
        boolean anyVisible = false;
        for (Particle p : particles) {
            p.update();
            if (p.alpha > 0) anyVisible = true;
        }
        if (!anyVisible) finished = true;
    }

    public void draw(Canvas canvas) {
        for (Particle p : particles) {
            p.draw(canvas);
        }
    }

    public boolean isFinished() { return finished; }

    private class Particle {
        float px, py, vx, vy;
        int alpha = 255;
        int color;

        Particle(float x, float y) {
            this.px = x;
            this.py = y;
            this.vx = (random.nextFloat() - 0.5f) * 10;
            this.vy = (random.nextFloat() - 0.5f) * 10;
            this.color = Color.rgb(255, random.nextInt(128) + 127, 0); // Yellow/Orange
        }

        void update() {
            px += vx;
            py += vy;
            alpha -= 10;
            if (alpha < 0) alpha = 0;
        }

        void draw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setColor(color);
            paint.setAlpha(alpha);
            canvas.drawCircle(px, py, 5, paint);
        }
    }
}
