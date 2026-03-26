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

        EditText etName_dgb = findViewById(R.id.etName);
        RadioGroup rgDifficulty_dgb = findViewById(R.id.rgDifficulty);
        Button btnStart_dgb = findViewById(R.id.btnStart);

        btnStart_dgb.setOnClickListener(v -> {
            String name = etName_dgb.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }

            int checkedId = rgDifficulty_dgb.getCheckedRadioButtonId();
            int level_dgb = (checkedId == R.id.rbLevel1) ? 1 : 2;

            Intent intent_dgb = new Intent(MainActivity.this, GameActivity.class);
            intent_dgb.putExtra("PLAYER_NAME", name);
            intent_dgb.putExtra("DIFFICULTY_LEVEL", level_dgb);
            startActivity(intent_dgb);
        });
    }
}