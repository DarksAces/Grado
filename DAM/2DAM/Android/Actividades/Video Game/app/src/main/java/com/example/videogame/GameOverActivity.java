package com.example.videogame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        String name = getIntent().getStringExtra("PLAYER_NAME");
        int score = getIntent().getIntExtra("SCORE", 0);
        int difficulty = getIntent().getIntExtra("DIFFICULTY_LEVEL", 1);

        TextView tvPlayerName = findViewById(R.id.tvPlayerName);
        TextView tvScore = findViewById(R.id.tvScore);
        Button btnRestart = findViewById(R.id.btnRestart);
        Button btnExit = findViewById(R.id.btnExit);

        HighScoreManager.setHighScore(this, score);
        int highScore = HighScoreManager.getHighScore(this);

        tvPlayerName.setText("Player: " + name);
        tvScore.setText("Score: " + score + "\nHigh Score: " + highScore);
        tvScore.setTextColor(Color.WHITE);
        tvScore.setTextSize(24);

        btnRestart.setOnClickListener(v -> {
            Intent intent = new Intent(GameOverActivity.this, GameActivity.class);
            intent.putExtra("PLAYER_NAME", name);
            intent.putExtra("DIFFICULTY_LEVEL", difficulty);
            startActivity(intent);
            finish();
        });

        btnExit.setOnClickListener(v -> {
            Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
