package com.example.videogame;

import android.content.Context;
import android.content.SharedPreferences;

public class HighScoreManager {
    private static final String PREFS_NAME = "SpaceShooterPrefs";
    private static final String KEY_HIGH_SCORE = "high_score";

    public static int getHighScore(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_HIGH_SCORE, 0);
    }

    public static void setHighScore(Context context, int score) {
        int currentHigh = getHighScore(context);
        if (score > currentHigh) {
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            prefs.edit().putInt(KEY_HIGH_SCORE, score).apply();
        }
    }
}
