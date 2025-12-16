package com.example.supermarket;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        RecyclerView recycler = findViewById(R.id.recyclerCheckout);
        TextView tvTotal = findViewById(R.id.tvTotalPagar);
        Button btnVolver = findViewById(R.id.btnVolver);

        // 1. Cargar datos de la BD donde cantidad > 0
        ArrayList<Fruta> listaCompra = new ArrayList<>();
        AdmBaseDatosSQLite admin = new AdmBaseDatosSQLite(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        // Query solo para productos con cantidad > 0
        Cursor cursor = db.rawQuery("SELECT id, nombre, imagen, precio, cantidad FROM frutas WHERE cantidad > 0", null);

        double totalPrecio = 0;

        if (cursor.moveToFirst()) {
            do {
                Fruta fruta = new Fruta(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getDouble(3),
                        cursor.getInt(4));
                listaCompra.add(fruta);
                totalPrecio += (fruta.precio * fruta.cantidad);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // REASIGNAR BITMAPS PARA CHECKOUT (Igual que en MainActivity)
        android.graphics.Bitmap bitmapCompleto = android.graphics.BitmapFactory.decodeResource(getResources(),
                R.drawable.imagen);

        // AJUSTE DE GRILLA
        int NUM_COLUMNAS = 6;
        int NUM_FILAS = 6;

        int anchoTotal = bitmapCompleto.getWidth();
        int altoTotal = bitmapCompleto.getHeight();
        int anchoSprite = anchoTotal / NUM_COLUMNAS;
        int altoSprite = altoTotal / NUM_FILAS;

        // Mismos ajustes que en MainActivity
        int correccionX = 0;
        int correccionY = 0;

        for (Fruta f : listaCompra) {
            int col = -1, row = 0;
            switch (f.id) {
                case 1:
                    col = 1;
                    row = 5;
                    break; // Manzana
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
                int x = (col * anchoSprite) + correccionX;
                int y = (row * altoSprite) + correccionY;
                if (x < 0)
                    x = 0;
                if (y < 0)
                    y = 0;
                f.imagenBitmap = cortarBitmap(bitmapCompleto, x, y, anchoSprite, altoSprite);
            }
        }

        // 2. Configurar RecyclerView (Reusamos FrutaAdapter)
        recycler.setLayoutManager(new LinearLayoutManager(this));
        FrutaAdapter adapter = new FrutaAdapter(this, listaCompra);
        recycler.setAdapter(adapter);

        // 3. Mostrar Total
        tvTotal.setText("Total a Pagar: " + String.format("%.2f", totalPrecio) + "€");

        btnVolver.setOnClickListener(v -> finish());
    }

    private android.graphics.Bitmap cortarBitmap(android.graphics.Bitmap original, int x, int y, int width,
            int height) {
        if (x + width > original.getWidth())
            width = original.getWidth() - x;
        if (y + height > original.getHeight())
            height = original.getHeight() - y;
        return android.graphics.Bitmap.createBitmap(original, x, y, width, height);
    }
}