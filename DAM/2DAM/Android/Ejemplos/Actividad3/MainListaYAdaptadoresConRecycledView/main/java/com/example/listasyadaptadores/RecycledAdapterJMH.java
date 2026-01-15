package com.example.listasyadaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecycledAdapterJMH extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    ArrayList<User> jmh_claseAlmacenarArray;
    //Constructor en donde indicamos la instancia y el array con los datos
    public RecycledAdapterJMH(Context context, ArrayList<User> arrayOfUsers_jmh) {
        this.mContext = context;
        this.jmh_claseAlmacenarArray = arrayOfUsers_jmh;
    }

    // Define el ViewHolder explícitamente
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvHome;
        public Button button_jmh;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvHome = view.findViewById(R.id.tvHome);
            button_jmh = view.findViewById(R.id.buttonjmh);
        }
    }
    // Asocia el Layout de cada fila con el ViewHolder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Con el "inflater" pintamos los elementos de cada fila
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Buscamos el dato de esta fila en el array
        User user = jmh_claseAlmacenarArray.get(position);
        //Creamos variables asociadas a los elementos del Layout que recibimos del ViewHolder
        // Creamos las variables que apuntan a los TextView definidos en el layout "item_user.xml"
        TextView tvName = (TextView) holder.itemView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) holder.itemView.findViewById(R.id.tvHome);
        // Informamos los valores de los TextView
        tvName.setText(user.name);
        tvHome.setText(user.hometown);
        //Podemos añadir eventos dentro de los elementos
        // En este caso he añadido un botón y creo el listener para que mustre un mensage con TOAST
        Button button_jmh = (Button) holder.itemView.findViewById(R.id.buttonjmh);
        // Defino una varieble para poder saber el contexto
        //View finalConvertView_jmh = convertView;
        button_jmh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast notificacion=Toast.makeText(mContext,"hola",Toast.LENGTH_LONG);
                notificacion.show();

            }
        });
    }
    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo (por ejem, si implementamos filtros o búsquedas)
    @Override
    public int getItemCount() {return jmh_claseAlmacenarArray.size();}
}
