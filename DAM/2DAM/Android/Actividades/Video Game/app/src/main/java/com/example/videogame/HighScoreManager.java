package com.example.videogame;

import android.content.Context;
import android.content.SharedPreferences;

public class HighScoreManager {
    private static final String PREFS_NAME_dgb = "SpaceShooterPrefs";
    private static final String KEY_HIGH_SCORE_dgb = "high_score";

    public static int getHighScore(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME_dgb, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_HIGH_SCORE_dgb, 0);
    }

    public static void setHighScore(Context context, int score) {
        int currentHigh_dgb = getHighScore(context);
        if (score > currentHigh_dgb) {
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME_dgb, Context.MODE_PRIVATE);
            prefs.edit().putInt(KEY_HIGH_SCORE_dgb, score).apply();
        }
    }
}
