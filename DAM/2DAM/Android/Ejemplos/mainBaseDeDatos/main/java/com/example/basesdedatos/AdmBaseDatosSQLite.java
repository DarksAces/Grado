package com.example.basesdedatos;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

public class AdmBaseDatosSQLite extends SQLiteOpenHelper{

    public AdmBaseDatosSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory,
                              int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table monedas(id int primary key, currency text, ratio text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
