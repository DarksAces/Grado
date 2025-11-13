package com.example.conexionxammp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Llamar a la tarea asincrónica para validar el usuario
        String usuario = "a";
        String contraseña = "a";
        new ValidarUsuarioTask().execute(usuario, contraseña);
    }

    private void abrirNuevaActividad(String usuarioValido) {
        // Si el usuario es válido, abrir la nueva actividad
        if (usuarioValido.equals("ok")) {
            Intent intent = new Intent(this, NuevaActividad.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Usuario o contraseña inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    private static Document convertirStringToXMLDocument(String xmlString)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private class ValidarUsuarioTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String usuario = params[0];
            String contrasena = params[1];
            //String url = "http://10.0.2.2/validacuenta.php"; // Reemplaza esto con la URL de tu archivo PHP
            String url = "http://192.168.1.15/validacuenta.php"; // Reemplaza esto con la URL de tu archivo PHP

            String resultado = null;
            try {
                // Crear la conexión HTTP
                URL direccion = new URL(url);
                HttpURLConnection conexion = (HttpURLConnection) direccion.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setDoOutput(true);

                // Crear los datos del formulario
                String datos = "usuario=" + usuario + "&contrasena=" + contrasena;

                // Escribir los datos del formulario en la solicitud HTTP
                OutputStream salida = conexion.getOutputStream();
                byte[] bytes = datos.getBytes(StandardCharsets.UTF_8);
                salida.write(bytes);
                salida.flush();
                salida.close();

                // Leer la respuesta del servidor
                InputStream entrada = conexion.getInputStream();
                BufferedReader lector = new BufferedReader(new InputStreamReader(entrada));
                StringBuilder respuesta = new StringBuilder();
                String linea;

                while ((linea = lector.readLine()) != null) {
                    respuesta.append(linea);
                }

                // Cerrar la conexión HTTP
                entrada.close();
                conexion.disconnect();

                // Procesar la respuesta del servidor
                resultado = respuesta.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return resultado;
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
            Document doc = convertirStringToXMLDocument(resultado);
            NodeList listaItem = (NodeList) doc.getElementsByTagName("respuesta");
            Element element = (Element) listaItem.item(0);
            String var_id = element.getElementsByTagName("estado").item(0).getTextContent();

            // Abrir la nueva actividad en función del resultado de la validación
            abrirNuevaActividad(var_id);
        }
    }
}
