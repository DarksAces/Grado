package com.example.jesuscrust

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// 🚨 ¡IMPORTACIÓN FALTANTE!
import android.util.Log
// Importa Volley y otras clases para la conexión al servidor
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    // ⚠️ Define la URL para tu script de registro (ej. registra.php)
    private val REGISTER_URL = "http://10.0.2.2/registra.php"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        // 1. Obtener referencias de los elementos del layout (register.xml)
        val signUpButton: Button = findViewById(R.id.SignUpButton)
        val usernameInput: EditText = findViewById(R.id.RegisterUsernameInput)
        val emailInput: EditText = findViewById(R.id.RegisterEmailInput)
        val passwordInput: EditText = findViewById(R.id.RegisterPasswordInput)
        val goToLoginButton: Button = findViewById(R.id.GoToLoginButton)

        // 2. Lógica para volver a la pantalla de Login (cerrar esta Activity)
        goToLoginButton.setOnClickListener {
            finish()
        }

        // 3. Lógica principal del botón de registro
        signUpButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos para registrarte.", Toast.LENGTH_SHORT).show()
            } else {
                // Validación básica OK. Llamar a la función de conexión al servidor
                registerUser(username, email, password)
            }
        }
    }

    // 4. Función de conexión al servidor (usando Volley, similar a la de MainActivity)
    private fun registerUser(username: String, email: String, password: String) {
        val queue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Method.POST, REGISTER_URL,
            Response.Listener<String> { response ->
                try {
                    // ¡Log ya no dará error aquí!
                    android.util.Log.d("RegisterResponse", "Respuesta: $response")

                    val jsonObject = JSONObject(response)
                    val status = jsonObject.getString("status")

                    if (status == "success") {
                        // Registro correcto: Muestra mensaje y vuelve a Login
                        Toast.makeText(this, "Registro exitoso. ¡Inicia sesión!", Toast.LENGTH_LONG).show()
                        finish() // Cierra RegisterActivity y regresa a MainActivity (Login)

                    } else {
                        // Error (ej. usuario/email ya existe)
                        val message = jsonObject.getString("message")
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(this, "Error al procesar la respuesta del servidor", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                // ¡Log ya no dará error aquí!
                android.util.Log.e("RegisterError", "Error de Volley: ${error.message}")
                Toast.makeText(this, "Error de conexión: Verifica tu servidor web y URL.", Toast.LENGTH_LONG).show()
            }) {

            // Envía los datos al script PHP
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["email"] = email
                params["password"] = password
                return params
            }
        }
        queue.add(stringRequest)
    }
}