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

        String name_dgb = getIntent().getStringExtra("PLAYER_NAME");
        int score_dgb = getIntent().getIntExtra("SCORE", 0);
        int difficulty_dgb = getIntent().getIntExtra("DIFFICULTY_LEVEL", 1);

        TextView tvPlayerName_dgb = findViewById(R.id.tvPlayerName);
        TextView tvScore_dgb = findViewById(R.id.tvScore);
        Button btnRestart_dgb = findViewById(R.id.btnRestart);
        Button btnExit_dgb = findViewById(R.id.btnExit);

        HighScoreManager.setHighScore(this, score_dgb);
        int highScore_dgb = HighScoreManager.getHighScore(this);

        tvPlayerName_dgb.setText("Player: " + name_dgb);
        tvScore_dgb.setText("Score: " + score_dgb + "\nHigh Score: " + highScore_dgb);
        tvScore_dgb.setTextColor(Color.WHITE);
        tvScore_dgb.setTextSize(24);

        btnRestart_dgb.setOnClickListener(v -> {
            Intent intent_dgb = new Intent(GameOverActivity.this, GameActivity.class);
            intent_dgb.putExtra("PLAYER_NAME", name_dgb);
            intent_dgb.putExtra("DIFFICULTY_LEVEL", difficulty_dgb);
            startActivity(intent_dgb);
            finish();
        });

        btnExit_dgb.setOnClickListener(v -> {
            Intent intent_dgb = new Intent(GameOverActivity.this, MainActivity.class);
            intent_dgb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent_dgb);
            finish();
        });
    }
}
