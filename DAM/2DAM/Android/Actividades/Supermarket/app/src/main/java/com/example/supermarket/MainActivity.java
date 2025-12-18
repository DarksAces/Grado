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
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.os.Looper;

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
        // Intentamos conectar a XAMPP. Si falla, cargamos lo local (Offline).
        attemptSyncWithServer();

        // --- CARGA DE LA LISTA ---
        // NOTA: La carga de la lista ocurrirá después del intento de sync (en
        // attemptSyncWithServer)
        // para asegurar que mostramos los datos actualizados si hubo conexión.

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

    // --- MÉTODOS DE CONEXIÓN REAL (XAMPP) ---

    private void attemptSyncWithServer() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            ArrayList<Fruta> frutasRemotas = null;
            try {
                // CAMBIAR IP SI ES NECESARIO: 10.0.2.2 es localhost para Emulador Android.
                // Si usas dispositivo real, pon la IP de tu PC (ej. 192.168.1.35)
                String jsonStr = downloadDataFromUrl("http://10.0.2.2/supermarket/get_frutas.php");
                if (jsonStr != null) {
                    frutasRemotas = parsearJsonFrutas(jsonStr);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            final ArrayList<Fruta> nuevosDatos = frutasRemotas;

            handler.post(() -> {
                if (nuevosDatos != null && !nuevosDatos.isEmpty()) {
                    // CONEXIÓN EXITOSA: Actualizamos SQLite
                    admin.sincronizarFrutas(nuevosDatos);
                    Toast.makeText(MainActivity.this, "Conectado a XAMPP: Datos actualizados", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // FALLO DE CONEXIÓN: Usamos lo que haya en SQLite
                    Toast.makeText(MainActivity.this, "Sin conexión a XAMPP. Modo Offline (SQLite)", Toast.LENGTH_LONG)
                            .show();
                }
                // SIEMPRE cargamos la lista al final (sea nueva o vieja)
                cargarListaDesdeSQLite();
            });
        });
    }

    private String downloadDataFromUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000); // 5 segundos de espera max
        conn.setReadTimeout(5000);

        if (conn.getResponseCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            return sb.toString();
        }
        return null;
    }

    private ArrayList<Fruta> parsearJsonFrutas(String jsonStr) {
        ArrayList<Fruta> lista = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int id = obj.getInt("id");
                String nombre = obj.getString("nombre");
                double precio = obj.getDouble("precio");
                // imagenResId lo dejamos en 0, se gestiona localmente con los sprites
                // id se usa para el mapeo del sprite
                lista.add(new Fruta(id, nombre, 0, precio, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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