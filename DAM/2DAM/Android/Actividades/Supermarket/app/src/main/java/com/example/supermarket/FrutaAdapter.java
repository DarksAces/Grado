package com.example.supermarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FrutaAdapter extends RecyclerView.Adapter<FrutaAdapter.MyViewHolder> {
    
    private Context mContext;
    private ArrayList<Fruta> listaFrutas;

    public FrutaAdapter(Context context, ArrayList<Fruta> listaFrutas) {
        this.mContext = context;
        this.listaFrutas = listaFrutas;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNombre, tvPrecio, tvCantidad;
        public ImageView imgFruta;
        public Button btnMas, btnMenos;

        public MyViewHolder(View view) {
            super(view);
            // Asegúrate de tener estos IDs en tu layout item_user.xml (o item_fruta.xml)
            tvNombre = view.findViewById(R.id.tvNombreFruta);
            tvPrecio = view.findViewById(R.id.tvPrecio);
            tvCantidad = view.findViewById(R.id.tvCantidad);
            imgFruta = view.findViewById(R.id.imgFruta);
            btnMas = view.findViewById(R.id.btnMas); // Botón + 
            btnMenos = view.findViewById(R.id.btnMenos); // Botón - 
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Fruta fruta = listaFrutas.get(position);

        holder.tvNombre.setText(fruta.nombre);
        holder.tvPrecio.setText(String.valueOf(fruta.precio) + "€");
        holder.tvCantidad.setText(String.valueOf(fruta.cantidad)); // Elemento cantidad
        holder.imgFruta.setImageResource(fruta.imagenResId); // Imagen del elemento

        // Lógica Botón Sumar (+)
        holder.btnMas.setOnClickListener(v -> {
            fruta.cantidad++;
            holder.tvCantidad.setText(String.valueOf(fruta.cantidad));
            updateCantidadEnDB(fruta);
        });

        // Lógica Botón Restar (-)
        holder.btnMenos.setOnClickListener(v -> {
            if (fruta.cantidad > 0) { // La cantidad no puede ser negativa 
                fruta.cantidad--;
                holder.tvCantidad.setText(String.valueOf(fruta.cantidad));
                updateCantidadEnDB(fruta);
            }
        });
    }

    @Override
    public int getItemCount() { return listaFrutas.size(); }

    private void updateCantidadEnDB(Fruta fruta) {
        AdmBaseDatosSQLite admin = new AdmBaseDatosSQLite(mContext);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cantidad", fruta.cantidad);
        db.update("frutas", values, "id = ?", new String[]{String.valueOf(fruta.id)});
        db.close();
    }
}
