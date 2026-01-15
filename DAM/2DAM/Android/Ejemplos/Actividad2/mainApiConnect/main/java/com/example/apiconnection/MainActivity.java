package com.example.apiconnection;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class MainActivity extends AppCompatActivity {
    String apiUrl = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    TextView titleTextView;
    ProgressDialog progressDialog;
    Button displayData;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setMovementMethod(new ScrollingMovementMethod());

        displayData = (Button) findViewById(R.id.mostrarDatos);
        displayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create object of MyAsyncTasks class and execute it
                MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                myAsyncTasks.execute(apiUrl);
            }
        });
    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... direccion) {
            String resultado = new String();
            try {
                URL url = new URL(direccion[0]);
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("GET");
                conexion.connect();

                StringBuilder respuesta;
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream(), "utf-8"))) {
                    respuesta = new StringBuilder();
                    String respuestaLinea;
                    while ((respuestaLinea = br.readLine()) != null) {
                        respuesta.append(respuestaLinea.trim());
                    }
                    resultado = respuesta.toString();
                }
            } catch (Exception e) {
                resultado = "error";
            } finally {
                return resultado; // se lo pasa a postExecute()
            }
        }

        @Override
        protected void onPostExecute(String mensaje) {
            titleTextView.setText(mensaje.toString());
            progressDialog.dismiss();
        }
    }
}