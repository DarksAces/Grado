package com.example.jesuscrust

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Tu pantalla de login

        // Botón de login (cambia el ID según tu XML)
        val loginButton: Button = findViewById(R.id.LoginButton)
        loginButton.setOnClickListener {
            // Aquí validas usuario/contraseña
            // Si es correcto:
            startActivity(Intent(this, HomeActivity::class.java))
            finish()  // Cierra el login para que no pueda volver con Back
        }
    }
}