package com.example.jesuscrust

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val LOGIN_URL = "http://10.0.2.2/validacuenta.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias del XML
        val loginButton: Button = findViewById(R.id.LoginButton)
        val usernameInput: EditText = findViewById(R.id.UsernameInput)
        val passwordInput: EditText = findViewById(R.id.PasswordInput)

        // 🚨 NUEVA REFERENCIA: Botón de Sign Up
        val signUpButton: Button = findViewById(R.id.SignUpButton)

        // Lógica del botón Login
        loginButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                validateUser(username, password)
            }
        }

        // 🚀 Lógica de navegación a RegisterActivity
        signUpButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateUser(username: String, password: String) {
        val queue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Method.POST, LOGIN_URL,
            Response.Listener<String> { response ->
                try {
                    Log.d("LoginResponse", "Respuesta: $response")
                    val jsonObject = JSONObject(response)
                    val status = jsonObject.getString("status")

                    if (status == "success") {
                        // Login correcto
                        Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                        // NOTA: Asegúrate de tener una HomeActivity o cámbialo a otra Activity
                        // startActivity(Intent(this, HomeActivity::class.java))
                        finish()

                    } else {
                        // Error de credenciales
                        val message = jsonObject.getString("message")
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(this, "Error procesando la respuesta del servidor", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                Log.e("LoginError", "Error de Volley: ${error.message}")
                Toast.makeText(this, "Error de conexión: Revisa tu IP, XAMPP y el Firewall.", Toast.LENGTH_LONG).show()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        queue.add(stringRequest)
    }
}