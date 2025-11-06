package com.example.jesuscrust

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    // ⚠️ IMPORTANTE: '10.0.2.2' es la IP especial para referirse a 'localhost'
    // desde el emulador de Android. Si usas un dispositivo físico,
    // reemplázalo con la IP de tu PC en tu red local (ej. http://192.168.1.5/registra.php).
    private val REGISTER_URL = "http://10.0.2.2/registra.php"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 🚨 Asegúrate de que el nombre del layout coincida con el nombre del archivo XML
        setContentView(R.layout.register)

        // 1. Obtener referencias de los elementos del layout
        val signUpButton: Button = findViewById(R.id.SignUpButton)
        val usernameInput: EditText = findViewById(R.id.RegisterUsernameInput)
        val emailInput: EditText = findViewById(R.id.RegisterEmailInput)
        val passwordInput: EditText = findViewById(R.id.RegisterPasswordInput)
        val goToLoginButton: Button = findViewById(R.id.GoToLoginButton)

        // 2. Lógica para volver a la pantalla de Login
        goToLoginButton.setOnClickListener {
            finish() // Cierra esta Activity
        }

        // 3. Lógica principal del botón de registro
        signUpButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos para registrarte.", Toast.LENGTH_SHORT).show()
            } else {
                // Llamar a la función de conexión al servidor
                registerUser(username, email, password)
            }
        }
    }

    // Función de conexión al servidor (usando Volley)
    private fun registerUser(username: String, email: String, password: String) {
        val queue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Method.POST, REGISTER_URL,
            Response.Listener<String> { response ->
                try {
                    Log.d("RegisterResponse", "Respuesta del Servidor: $response")

                    val jsonObject = JSONObject(response)
                    val status = jsonObject.getString("status")
                    val message = jsonObject.getString("message")

                    if (status == "success") {
                        // Registro correcto: Muestra mensaje y vuelve a Login
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                        finish() // Cierra RegisterActivity y regresa a MainActivity (Login)
                    } else {
                        // Error (ej. usuario/email ya existe)
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    Log.e("RegisterError", "Error al procesar la respuesta JSON: ${e.message}")
                    Toast.makeText(this, "Error al procesar la respuesta del servidor", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                Log.e("RegisterError", "Error de Volley: ${error.message}", error)
                Toast.makeText(this, "Error de conexión: Revisa tu URL de XAMPP.", Toast.LENGTH_LONG).show()
            }) {

            // Envía los datos al script PHP
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["email"] = email
                params["password"] = password // Esta contraseña se hashea en el PHP
                return params
            }
        }
        queue.add(stringRequest)
    }
}