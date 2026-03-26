package com.example.videogame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private GameView gameView_dgb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String playerName_dgb = getIntent().getStringExtra("PLAYER_NAME");
        int difficultyLevel_dgb = getIntent().getIntExtra("DIFFICULTY_LEVEL", 1);

        FrameLayout container_dgb = findViewById(R.id.gameContainer);
        gameView_dgb = new GameView(this, playerName_dgb, difficultyLevel_dgb);
        container_dgb.addView(gameView_dgb);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView_dgb.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView_dgb.resume();
    }
}
