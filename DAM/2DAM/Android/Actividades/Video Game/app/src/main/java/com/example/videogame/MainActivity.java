package com.example.videogame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        EditText etName = findViewById(R.id.etName);
        RadioGroup rgDifficulty = findViewById(R.id.rgDifficulty);
        Button btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }

            int checkedId = rgDifficulty.getCheckedRadioButtonId();
            int level = (checkedId == R.id.rbLevel1) ? 1 : 2;

            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("PLAYER_NAME", name);
            intent.putExtra("DIFFICULTY_LEVEL", level);
            startActivity(intent);
        });
    }
}