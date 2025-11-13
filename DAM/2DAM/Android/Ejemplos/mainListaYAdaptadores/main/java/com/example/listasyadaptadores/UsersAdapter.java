package com.example.listasyadaptadores;

import android.content.Context;
import android.text.method.BaseKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UsersAdapter extends ArrayAdapter<User> {
    // El constructor solo recibe dos parámentro, pero le pasa al padre/madre 3
    // El layout que se le pasa es el 0 debido a que se le pasa uno propio más abajo al realizar el inflate
    public UsersAdapter(Context context, ArrayList<User> users) {

        super(context, 0, users);
    }

    private static class ViewHolder {
        TextView name;
        TextView home;
    }
    // El método getView se llamará tantas veces como registros tengan los datos a visualizar.
    // Si el array usado posee 10 valores el getView se llamará 10 veces
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // En a variable position tenemos la posición del array que estoy pintando.
        // El getItem es un método propio del ArrayAdapter, en este caso el tipo de adaptador usado es el de la clase "User"
        // por lo tanto el getItem nos devolverá un objeto de tipo "User" que está en la posición "position"
        // En los usos básicos de adaptadores en los spinner se usa un ArrayAdapter<string>
        // //, por lo tanto el getItem nos devolvería un String
        User user = getItem(position);
        // Validamos si nos pasan por parámetro la vista a visualizar
        // en caso que esté vacía usaremos la vista (el layout) que hemos creado para visualizar los elementes
        // el inflater se encarga de pintarlo.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }
        // Creamos las variables que apuntan a los TextView definidos en el layout "item_user.xml"
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        // Informamos los valores de los TextView
        tvName.setText(user.name);
        tvHome.setText(user.hometown);
        //Podemos añadir eventos dentro de los elementos
        // En este caso he añadido un botón y creo el listener para que mustre un mensage con TOAST
        Button button_jmh = (Button)  convertView.findViewById(R.id.buttonjmh);
        // Defino una varieble para poder saber el contexto
        View finalConvertView_jmh = convertView;
        button_jmh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast notificacion=Toast.makeText(finalConvertView_jmh.getContext(),"hola",Toast.LENGTH_LONG);
                notificacion.show();

            }
        });
        // Devolvemos la vista para que se pinte (render) por la pantalla
        return convertView;
    }
}
