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

    private Thread gameThread_dgb;
    private boolean isRunning_dgb;
    private SurfaceHolder holder;
    private Canvas canvas;

    private Player player;
    private List<Enemy> enemies_dgb;
    private List<Bullet> playerBullets_dgb;
    private List<Bullet> enemyBullets_dgb;
    private List<Explosion> explosions_dgb;

    private Bitmap playerBitmap, enemyBitmap, bulletBitmap, backgroundBitmap;
    private String playerName_dgb;
    private int difficultyLevel_dgb;
    private int score_dgb = 0;
    private float enemySpeedBase = 5f;
    private long startTime_dgb;

    private Random random = new Random();
    private ToneGenerator toneGenerator;

    // Smooth movement flags
    private boolean isMovingUp = false;
    private boolean isMovingDown = false;

    public GameView(Context context, String playerName_dgb, int difficultyLevel_dgb) {
        super(context);
        this.playerName_dgb = playerName_dgb;
        this.difficultyLevel_dgb = difficultyLevel_dgb;
        this.holder = getHolder();
        this.holder.addCallback(this);

        this.playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_ship);
        this.playerBitmap = Bitmap.createScaledBitmap(playerBitmap, 100, 100, true);

        this.enemyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_ship);
        this.enemyBitmap = Bitmap.createScaledBitmap(enemyBitmap, 180, 180, true);

        this.bulletBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.laser_bullet);
        this.bulletBitmap = Bitmap.createScaledBitmap(bulletBitmap, 100, 50, true);

        this.backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.space_background);

        this.enemies_dgb = new ArrayList<>();
        this.playerBullets_dgb = new ArrayList<>();
        this.enemyBullets_dgb = new ArrayList<>();
        this.explosions_dgb = new ArrayList<>();

        this.toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        this.startTime_dgb = System.currentTimeMillis();

        setFocusable(true);
        requestFocus();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (getWidth() > 0) {
            player = new Player(playerBitmap, getWidth() - playerBitmap.getWidth() - 100, getHeight() / 2f);
            resume();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        pause();
    }

    public void pause() {
        isRunning_dgb = false;
        try {
            if (gameThread_dgb != null) gameThread_dgb.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isRunning_dgb = true;
        gameThread_dgb = new Thread(this);
        gameThread_dgb.start();
    }

    @Override
    public void run() {
        while (isRunning_dgb) {
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

        // Increase speed over time - MUCH FASTER for video demo
        long elapsed = (System.currentTimeMillis() - startTime_dgb) / 1000;
        float currentEnemySpeed = enemySpeedBase + (elapsed * 1.5f); // Increased from 0.3f

        // Spawn enemies - increases over time
        int spawnChance = (int) (2 + difficultyLevel_dgb * 2 + (elapsed / 2));
        if (random.nextInt(100) < Math.min(25, spawnChance)) {
            float y = random.nextInt(Math.max(1, getHeight() - enemyBitmap.getHeight()));
            synchronized (enemies_dgb) {
                enemies_dgb.add(new Enemy(enemyBitmap, -enemyBitmap.getWidth(), y, currentEnemySpeed, difficultyLevel_dgb == 2));
            }
        }

        // Update enemies
        synchronized (enemies_dgb) {
            Iterator<Enemy> enemyIterator = enemies_dgb.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                enemy.update();

                if (enemy.canShoot() && random.nextInt(100) < 1) {
                    synchronized (enemyBullets_dgb) {
                        enemyBullets_dgb.add(new Bullet(bulletBitmap, enemy.getX() + enemy.width, enemy.getY() + enemy.height / 2f, 15f));
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
        synchronized (playerBullets_dgb) {
            Iterator<Bullet> bulletIterator = playerBullets_dgb.iterator();
            while (bulletIterator.hasNext()) {
                Bullet b = bulletIterator.next();
                b.x -= 25; // Move LEFT
                if (b.x < -b.width) {
                    bulletIterator.remove();
                    continue;
                }

                synchronized (enemies_dgb) {
                    Iterator<Enemy> eIter = enemies_dgb.iterator();
                    while (eIter.hasNext()) {
                        Enemy e = eIter.next();
                        if (Rect.intersects(b.getBounds(), e.getBounds())) {
                            synchronized (explosions_dgb) {
                                explosions_dgb.add(new Explosion(e.getX() + e.width / 2, e.getY() + e.height / 2));
                            }
                            eIter.remove();
                            bulletIterator.remove();
                            score_dgb += 10;
                            break;
                        }
                    }
                }
            }
        }

        // Update enemy bullets
        synchronized (enemyBullets_dgb) {
            Iterator<Bullet> ebIterator = enemyBullets_dgb.iterator();
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
        synchronized (explosions_dgb) {
            Iterator<Explosion> exIter = explosions_dgb.iterator();
            while (exIter.hasNext()) {
                Explosion ex = exIter.next();
                ex.update();
                if (ex.isFinished()) exIter.remove();
            }
        }
    }

    private void draw() {
        canvas = holder.lockCanvas();
        if (canvas != null) {
            canvas.drawBitmap(backgroundBitmap, null, new Rect(0, 0, getWidth(), getHeight()), null);

            player.draw(canvas);
            synchronized (enemies_dgb) { for (Enemy e : enemies_dgb) e.draw(canvas); }
            synchronized (playerBullets_dgb) { for (Bullet b : playerBullets_dgb) b.draw(canvas); }
            synchronized (enemyBullets_dgb) { for (Bullet b : enemyBullets_dgb) b.draw(canvas); }
            synchronized (explosions_dgb) { for (Explosion ex : explosions_dgb) ex.draw(canvas); }

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            paint.setFakeBoldText(true);
            canvas.drawText("Player: " + playerName_dgb, 50, 70, paint);
            canvas.drawText("Score: " + score_dgb, 50, 130, paint);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void gameOver() {
        isRunning_dgb = false;
        toneGenerator.startTone(ToneGenerator.TONE_SUP_ERROR, 500);
        Intent intent = new Intent(getContext(), GameOverActivity.class);
        intent.putExtra("PLAYER_NAME", playerName_dgb);
        intent.putExtra("SCORE", score_dgb);
        intent.putExtra("DIFFICULTY_LEVEL", difficultyLevel_dgb);
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
        synchronized (playerBullets_dgb) {
            playerBullets_dgb.add(new Bullet(bulletBitmap, player.getX() - bulletBitmap.getWidth(), player.getY() + player.height / 2f, -25f));
        }
        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 100);
    }
}
