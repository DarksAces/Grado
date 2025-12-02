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
                        cursor.getInt(4)
                );
                listaCompra.add(fruta);
                totalPrecio += (fruta.precio * fruta.cantidad);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // 2. Configurar RecyclerView (Reusamos FrutaAdapter)
        recycler.setLayoutManager(new LinearLayoutManager(this));
        FrutaAdapter adapter = new FrutaAdapter(this, listaCompra);
        recycler.setAdapter(adapter);

        // 3. Mostrar Total
        tvTotal.setText("Total a Pagar: " + String.format("%.2f", totalPrecio) + "€");

        btnVolver.setOnClickListener(v -> finish());
    }
}