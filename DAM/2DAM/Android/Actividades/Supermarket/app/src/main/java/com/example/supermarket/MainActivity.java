package com.example.supermarket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        // Aquí cumplimos el requisito de usar SQLite como caché del backend [cite: 20,
        // 21]

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
            for (Fruta f : misFrutas) {
                if (f.cantidad > 0) {
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

        // Validamos si la lista está vacía (Requisito: indicar si está vacía [cite:
        // 19])
        if (misFrutas.isEmpty()) {
            Toast.makeText(this, "No hay productos disponibles.", Toast.LENGTH_LONG).show();
        } else {
            // REASIGNAR BITMAPS (ya que SQLite no los guarda)
            // Esto asegura que los iconos se vean
            Bitmap bitmapCompleto = BitmapFactory.decodeResource(getResources(), R.drawable.imagen);
            int anchoTotal = bitmapCompleto.getWidth();
            int altoTotal = bitmapCompleto.getHeight();
            int anchoSprite = anchoTotal / 6;
            int altoSprite = altoTotal / 6;

            // AJUSTES: Cambia estos números si el icono se ve cortado
            int correccionX = 0; // Mueve el corte a la derecha
            int correccionY = 0; // Mueve el corte abajo

            for (Fruta f : misFrutas) {
                // Mapeamos ID -> Posición en grilla
                int col = -1, row = 0;
                switch (f.id) {
                    case 1:
                        col = 1;
                        row = 5;
                        break; // Manzana (según tu ajuste manual)
                    case 2:
                        col = 1;
                        break; // Banana
                    case 3:
                        col = 2;
                        break; // Naranja
                    case 4:
                        col = 3;
                        break; // Uva
                    case 5:
                        col = 4;
                        break; // Pera
                    case 6:
                        col = 5;
                        break; // Sandía
                }

                if (col != -1) {
                    // Aplicamos corrección con seguridad de no salirnos
                    int x = (col * anchoSprite) + correccionX;
                    int y = (row * altoSprite) + correccionY;
                    if (x < 0)
                        x = 0;
                    if (y < 0)
                        y = 0;

                    f.imagenBitmap = cortarBitmap(bitmapCompleto, x, y, anchoSprite, altoSprite);
                }
            }
        }
        // Configuramos el adaptador
        adapter = new FrutaAdapter(this, misFrutas);
        recyclerView.setAdapter(adapter);
    }

    // --- MÉTODOS SIMULADOS (BACKEND) ---

    private boolean hayConexionInternet() {
        // Retornamos true para simular que SIEMPRE hay conexión y actualiza los datos
        // al abrir.
        // Si pones false, probarás el modo offline (los datos persisten).
        return true;
    }

    private ArrayList<Fruta> simularDescargaBackend() {
        // Obtenemos el bitmap completo de la hoja de sprites
        Bitmap bitmapCompleto = BitmapFactory.decodeResource(getResources(), R.drawable.imagen);

        // AJUSTE DE GRILLA: Define cuántas frutas hay por fila y columna en la imagen
        // original
        int NUM_COLUMNAS = 7; // <-- CUENTA LAS COLUMNAS DE TU FOTO Y PON EL NÚMERO AQUÍ
        int NUM_FILAS = 6; // <-- CUENTA LAS FILAS DE TU FOTO Y PON EL NÚMERO AQUÍ

        int anchoTotal = bitmapCompleto.getWidth();
        int altoTotal = bitmapCompleto.getHeight();
        int anchoSprite = anchoTotal / NUM_COLUMNAS;
        int altoSprite = altoTotal / NUM_FILAS;

        // AJUSTES FINOS: Si se sigue viendo mal, mueve esto pixel a pixel
        int correccionX = 0;
        int correccionY = 0;

        ArrayList<Fruta> lista = new ArrayList<>();

        // Asignamos los primeros 6 iconos de la fila 0 (o variados)
        // Fruta 1: Manzana
        Fruta manzana = new Fruta(1, "Manzana", 0, 1.50, 0);
        // NOTA: Si cambias el número de columnas, tendrás que ajustar 'col' y 'row'
        // abajo
        // para encontrar tu fruta.
        // Ejemplo: Si Manzana está en la 3º columna, pon col=2.
        lista.add(manzana);

        // Fruta 2: Banana
        Fruta banana = new Fruta(2, "Banana", 0, 0.50, 0);
        banana.imagenBitmap = cortarBitmap(bitmapCompleto, anchoSprite, 0, anchoSprite, altoSprite);
        lista.add(banana);

        // Fruta 3: Naranja
        Fruta naranja = new Fruta(3, "Naranja", 0, 0.80, 0);
        naranja.imagenBitmap = cortarBitmap(bitmapCompleto, anchoSprite * 2, 0, anchoSprite, altoSprite);
        lista.add(naranja);

        // Fruta 4: Uva
        Fruta uva = new Fruta(4, "Uva", 0, 2.00, 0);
        uva.imagenBitmap = cortarBitmap(bitmapCompleto, anchoSprite * 3, 0, anchoSprite, altoSprite);
        lista.add(uva);

        // Fruta 5: Pera
        Fruta pera = new Fruta(5, "Pera", 0, 1.20, 0);
        pera.imagenBitmap = cortarBitmap(bitmapCompleto, anchoSprite * 4, 0, anchoSprite, altoSprite);
        lista.add(pera);

        // Fruta 6: Sandía
        Fruta sandia = new Fruta(6, "Sandía", 0, 3.50, 0);
        sandia.imagenBitmap = cortarBitmap(bitmapCompleto, anchoSprite * 5, 0, anchoSprite, altoSprite);
        lista.add(sandia);

        return lista;
    }

    private Bitmap cortarBitmap(Bitmap original, int x, int y, int width, int height) {
        // Asegurar que no nos salimos de los límites
        if (x + width > original.getWidth())
            width = original.getWidth() - x;
        if (y + height > original.getHeight())
            height = original.getHeight() - y;

        return Bitmap.createBitmap(original, x, y, width, height);
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
            cargarListaDesdeSQLite(); // Refrescar UI
            Toast.makeText(this, "Carrito vaciado", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_exit) {
            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}