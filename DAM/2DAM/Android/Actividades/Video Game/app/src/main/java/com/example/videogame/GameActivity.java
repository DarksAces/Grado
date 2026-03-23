package com.example.videogame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String playerName = getIntent().getStringExtra("PLAYER_NAME");
        int difficultyLevel = getIntent().getIntExtra("DIFFICULTY_LEVEL", 1);

        FrameLayout container = findViewById(R.id.gameContainer);
        gameView = new GameView(this, playerName, difficultyLevel);
        container.addView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
