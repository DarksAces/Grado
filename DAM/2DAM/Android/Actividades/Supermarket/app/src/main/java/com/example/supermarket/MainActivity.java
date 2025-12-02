package com.example.supermarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AdmBaseDatosSQLite admin;
    ArrayList<Fruta> misFrutas;
    FrutaAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar componentes
        admin = new AdmBaseDatosSQLite(this);
        recyclerView = findViewById(R.id.miRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button btnPagar = findViewById(R.id.btnPagar);

        // --- LÓGICA DE CONEXIÓN Y SINCRONIZACIÓN ---
        // Aquí cumplimos el requisito de usar SQLite como caché del backend [cite: 20, 21]

        if (hayConexionInternet()) {
            // 1. Si hay internet, "descargamos" los datos y actualizamos la BD local
            ArrayList<Fruta> datosNuevos = simularDescargaBackend();
            admin.sincronizarFrutas(datosNuevos);
            Toast.makeText(this, "Catálogo actualizado desde el servidor", Toast.LENGTH_SHORT).show();
        } else {
            // 2. Si NO hay internet, no hacemos nada aquí.
            // La app cargará los datos que existan en SQLite en el siguiente paso.
            Toast.makeText(this, "Modo Offline: Usando datos locales", Toast.LENGTH_LONG).show();
        }

        // --- CARGA DE LA LISTA ---
        cargarListaDesdeSQLite();

        // --- BOTÓN PAGAR ---
        btnPagar.setOnClickListener(v -> {
            boolean carritoVacio = true;

            // Verificamos si hay algún ítem con cantidad > 0
            for(Fruta f : misFrutas) {
                if(f.cantidad > 0) {
                    carritoVacio = false;
                    break;
                }
            }

            if (carritoVacio) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }

    // Se ejecuta cada vez que volvemos a esta pantalla (ej. al volver de Checkout)
    @Override
    protected void onResume() {
        super.onResume();
        // Recargamos la lista por si cambiaron cantidades o datos
        cargarListaDesdeSQLite();
    }

    private void cargarListaDesdeSQLite() {
        // Pedimos al admin de BD que nos de la lista actual
        misFrutas = admin.obtenerFrutas();

        // Validamos si la lista está vacía (Requisito: indicar si está vacía [cite: 19])
        if (misFrutas.isEmpty()) {
            Toast.makeText(this, "No hay productos disponibles.", Toast.LENGTH_LONG).show();
        }

        // Configuramos el adaptador
        adapter = new FrutaAdapter(this, misFrutas);
        recyclerView.setAdapter(adapter);
    }

    // --- MÉTODOS SIMULADOS (BACKEND) ---

    private boolean hayConexionInternet() {
        // Retornamos true para simular que SIEMPRE hay conexión y actualiza los datos al abrir.
        // Si pones false, probarás el modo offline (los datos persisten).
        return true;
    }

    private ArrayList<Fruta> simularDescargaBackend() {
        // Estos datos vendrían de un JSON o API real.
        ArrayList<Fruta> lista = new ArrayList<>();
        // NOTA: Usa tus propios R.drawable.manzana si los tienes, aquí uso launcher por defecto.
        lista.add(new Fruta(1, "Manzana", R.mipmap.ic_launcher, 1.50, 0));
        lista.add(new Fruta(2, "Banana", R.mipmap.ic_launcher, 0.50, 0));
        lista.add(new Fruta(3, "Naranja", R.mipmap.ic_launcher, 0.80, 0));
        lista.add(new Fruta(4, "Uva", R.mipmap.ic_launcher, 2.00, 0));
        lista.add(new Fruta(5, "Pera", R.mipmap.ic_launcher, 1.20, 0));
        lista.add(new Fruta(6, "Sandía", R.mipmap.ic_launcher, 3.50, 0));
        return lista;
    }

    // --- MENÚ SUPERIOR ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            admin.resetearCantidades(); // Método limpio en la clase Admin
            cargarListaDesdeSQLite();   // Refrescar UI
            Toast.makeText(this, "Carrito vaciado", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_exit) {
            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}