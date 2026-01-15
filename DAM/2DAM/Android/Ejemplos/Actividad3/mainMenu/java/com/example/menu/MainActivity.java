package com.example.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView txtMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referenciamos la Toolbar del layout y la configuramos como ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtMensaje = findViewById(R.id.txtMensaje);
    }

    // 1. Crear / inflar el menú en la Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // "R.menu.main_menu" es el XML que creamos en res/menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true; // true -> el menú se mostrará
    }

    // 2. Manejar los clics en las opciones del menú
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_nuevo:
                txtMensaje.setText("Has pulsado: Nuevo");
                Toast.makeText(this, "Opción Nuevo", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_guardar:
                txtMensaje.setText("Has pulsado: Guardar");
                Toast.makeText(this, "Opción Guardar", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_salir:
                txtMensaje.setText("Has pulsado: Salir");
                Toast.makeText(this, "Cerrando actividad...", Toast.LENGTH_SHORT).show();
                // Ejemplo simple: finalizar la actividad
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
