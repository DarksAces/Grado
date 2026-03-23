package com.example.videogame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Thread gameThread;
    private boolean isRunning;
    private SurfaceHolder holder;
    private Canvas canvas;

    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> playerBullets;
    private List<Bullet> enemyBullets;
    private List<Explosion> explosions;

    private Bitmap playerBitmap, enemyBitmap, bulletBitmap, backgroundBitmap;
    private String playerName;
    private int difficultyLevel;
    private int score = 0;
    private float enemySpeedBase = 5f;
    private long startTime;

    private Random random = new Random();
    private ToneGenerator toneGenerator;

    // Smooth movement flags
    private boolean isMovingUp = false;
    private boolean isMovingDown = false;

    public GameView(Context context, String playerName, int difficultyLevel) {
        super(context);
        this.playerName = playerName;
        this.difficultyLevel = difficultyLevel;
        this.holder = getHolder();
        this.holder.addCallback(this);

        this.playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_ship);
        this.enemyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_ship);
        this.bulletBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.laser_bullet);
        this.backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.space_background);

        this.enemies = new ArrayList<>();
        this.playerBullets = new ArrayList<>();
        this.enemyBullets = new ArrayList<>();
        this.explosions = new ArrayList<>();

        this.toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        this.startTime = System.currentTimeMillis();

        setFocusable(true);
        requestFocus();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player = new Player(playerBitmap, getWidth() - playerBitmap.getWidth() - 50, getHeight() / 2f);
        resume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        pause();
    }

    public void pause() {
        isRunning = false;
        try {
            if (gameThread != null) gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!holder.getSurface().isValid()) continue;

            update();
            draw();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        // Smooth player movement
        if (isMovingUp) player.move(-15, getHeight());
        if (isMovingDown) player.move(15, getHeight());

        // Increase speed over time
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        float currentEnemySpeed = enemySpeedBase + (elapsed * 0.3f);

        // Spawn enemies
        if (random.nextInt(100) < (2 + difficultyLevel * 2)) {
            float y = random.nextInt(getHeight() - enemyBitmap.getHeight());
            enemies.add(new Enemy(enemyBitmap, -enemyBitmap.getWidth(), y, currentEnemySpeed, difficultyLevel == 2));
        }

        // Update enemies
        synchronized (enemies) {
            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                enemy.update();

                if (enemy.canShoot() && random.nextInt(100) < 1) {
                    synchronized (enemyBullets) {
                        enemyBullets.add(new Bullet(bulletBitmap, enemy.getX() + enemy.width, enemy.getY() + enemy.height / 2f, 15f));
                    }
                }

                if (enemy.getX() > getWidth()) {
                    enemyIterator.remove();
                } else if (Rect.intersects(enemy.getBounds(), player.getBounds())) {
                    gameOver();
                    return;
                }
            }
        }

        // Update player bullets
        synchronized (playerBullets) {
            Iterator<Bullet> bulletIterator = playerBullets.iterator();
            while (bulletIterator.hasNext()) {
                Bullet b = bulletIterator.next();
                b.x -= 25;
                if (b.x < -b.width) {
                    bulletIterator.remove();
                    continue;
                }

                synchronized (enemies) {
                    Iterator<Enemy> eIter = enemies.iterator();
                    while (eIter.hasNext()) {
                        Enemy e = eIter.next();
                        if (Rect.intersects(b.getBounds(), e.getBounds())) {
                            explosions.add(new Explosion(e.getX() + e.width/2, e.getY() + e.height/2));
                            eIter.remove();
                            bulletIterator.remove();
                            score += 10;
                            break;
                        }
                    }
                }
            }
        }

        // Update enemy bullets
        synchronized (enemyBullets) {
            Iterator<Bullet> ebIterator = enemyBullets.iterator();
            while (ebIterator.hasNext()) {
                Bullet b = ebIterator.next();
                b.update();
                if (b.x > getWidth()) {
                    ebIterator.remove();
                } else if (Rect.intersects(b.getBounds(), player.getBounds())) {
                    gameOver();
                    return;
                }
            }
        }

        // Update explosions
        Iterator<Explosion> exIter = explosions.iterator();
        while (exIter.hasNext()) {
            Explosion ex = exIter.next();
            ex.update();
            if (ex.isFinished()) exIter.remove();
        }
    }

    private void draw() {
        canvas = holder.lockCanvas();
        if (canvas != null) {
            canvas.drawBitmap(backgroundBitmap, null, new Rect(0, 0, getWidth(), getHeight()), null);

            player.draw(canvas);
            synchronized (enemies) { for (Enemy e : enemies) e.draw(canvas); }
            synchronized (playerBullets) { for (Bullet b : playerBullets) b.draw(canvas); }
            synchronized (enemyBullets) { for (Bullet b : enemyBullets) b.draw(canvas); }
            for (Explosion ex : explosions) ex.draw(canvas);

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            paint.setFakeBoldText(true);
            canvas.drawText("Player: " + playerName, 50, 70, paint);
            canvas.drawText("Score: " + score, 50, 130, paint);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void gameOver() {
        isRunning = false;
        toneGenerator.startTone(ToneGenerator.TONE_SUP_ERROR, 500);
        Intent intent = new Intent(getContext(), GameOverActivity.class);
        intent.putExtra("PLAYER_NAME", playerName);
        intent.putExtra("SCORE", score);
        intent.putExtra("DIFFICULTY_LEVEL", difficultyLevel);
        getContext().startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            player.setCenterY(event.getY(), getHeight());
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            shoot();
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_W) {
            isMovingUp = true;
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_S) {
            isMovingDown = true;
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_SPACE || keyCode == KeyEvent.KEYCODE_ENTER) {
            shoot();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_W) {
            isMovingUp = false;
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_S) {
            isMovingDown = false;
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void shoot() {
        synchronized (playerBullets) {
            playerBullets.add(new Bullet(bulletBitmap, player.getX() - bulletBitmap.getWidth(), player.getY() + player.height / 2f, -25f));
        }
        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 100);
    }
}
