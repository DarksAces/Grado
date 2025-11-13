package com.example.basesdedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static EditText mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    public void borrarDatos(View view) {
        AdmBaseDatosSQLite admin = new AdmBaseDatosSQLite(MainActivity.this, "Administration", null, 1);
        //Abrimos la base de datos
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();
        baseDeDatos = admin.getReadableDatabase();


        baseDeDatos.execSQL("delete from monedas");

        mensaje = findViewById(R.id.editTextTextPersonName);
        mensaje.setText("Datos borrados");
        admin.close();

    }

    public void insertarDatos(View view) {
        AdmBaseDatosSQLite admin = new AdmBaseDatosSQLite(MainActivity.this, "Administration", null, 1);
        //Abrimos la base de datos
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();
        baseDeDatos = admin.getReadableDatabase();


        // Insertar en base de datos
        ContentValues registro = new ContentValues();
        registro.put("id", 1);
        registro.put("currency","currency1");
        registro.put("ratio","1.1");
        baseDeDatos.insert("monedas",null, registro);

        registro.put("id", 2);
        registro.put("currency","currency2");
        registro.put("ratio","2.2");
        baseDeDatos.insert("monedas",null, registro);

        registro.put("id", 3);
        registro.put("currency","currency3");
        registro.put("ratio","3.3");
        baseDeDatos.insert("monedas",null, registro);

        mensaje = findViewById(R.id.editTextTextPersonName);
        mensaje.setText("Datos insertados");
        admin.close();

    }

    public void consultarDatos(View view) {
        AdmBaseDatosSQLite admin = new AdmBaseDatosSQLite(MainActivity.this, "Administration", null, 1);
        //Abrimos la base de datos
        SQLiteDatabase baseDeDatos = admin.getReadableDatabase();
        baseDeDatos = admin.getReadableDatabase();


        String query ="select * from monedas";
        Cursor c = baseDeDatos.rawQuery(query,null);
        Integer j=0;
        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndex("currency"));
            String rate_jmh = c.getString(c.getColumnIndex("ratio"));
            System.out.println("Name: " + name );
            j=j+1;
        }

        mensaje = findViewById(R.id.editTextTextPersonName);
        mensaje.setText("Hay "+j.toString()+ " registros");
        admin.close();
    }
}