package com.example.listasyadaptadores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LlamadaAdaptadorSimpleSpinner();
        //LLamadaAdaptadorSimpleListViewConstructor1();
        //LLamadaAdaptadorSimpleListViewConstructor2();
        LlamadaAdaptadorPropioListView();
    }

    private void LLamadaAdaptadorSimpleListViewConstructor1() {
        // Este es el array en donde están los datos a visualizar
        String[] jmh_opciones = {"hola","adios","Viernes"};
        //Definimos el adaptador
        ArrayAdapter<String> jmh_adapter1 = new ArrayAdapter <String>
                (getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,jmh_opciones);
        //Creamos variable que apunte al ListView del Layout
        ListView jmh_origen = (ListView) findViewById(R.id.listviewjmh);
        //Inflamos los valores del ListView usando el adaptador
        jmh_origen.setAdapter(jmh_adapter1);
    }
    private void LLamadaAdaptadorSimpleListViewConstructor2() {
        // Este es el array en donde están los datos a visualizar
        String[] jmh_opciones = {"hola","adios"};
        //Definimos el adaptador
        ArrayAdapter<String>adapter_jmh=new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_2,android.R.id.text1, jmh_opciones);
        //Creamos variable que apunte al ListView del Layout
        ListView jmh_origen = (ListView) findViewById(R.id.listviewjmh);
        //Inflamos los valores del ListView usando el adaptador
        jmh_origen.setAdapter(adapter_jmh);
    }

    private void LlamadaAdaptadorSimpleSpinner() {
        // Este es el array en donde están los datos a visualizar
        String[] jmh_opciones = {"hola","adios"};
        //Definimos el adaptador
        ArrayAdapter<String> jmh_adapter1 = new ArrayAdapter <String> (MainActivity.this, R.layout.support_simple_spinner_dropdown_item,jmh_opciones);
        //Creamos variable que apunte al spinner del Layout
        Spinner jmh_origen = (Spinner) findViewById(R.id.spinnerjmh);
        //Inflamos los valores del spinner usando el adaptador
        jmh_origen.setAdapter(jmh_adapter1);


    }

    private void LlamadaAdaptadorPropioListView() {
        // Creamos los datos
        ArrayList<User> arrayOfUsers_jmh = new ArrayList<User>();
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        arrayOfUsers_jmh.add(new User("Nathan", "San Diego"));
        // Definimos el adaptador propio. En este caso no posee layout.
        UsersAdapter adapter_jmh = new UsersAdapter(this, arrayOfUsers_jmh);
        // Attach the adapter to a ListView
        ListView listView_jmh = (ListView) findViewById(R.id.listviewjmh);
        listView_jmh.setAdapter(adapter_jmh);
        // Limpiar el adaptador
        //adapter_jmh.clear();
    }
}









