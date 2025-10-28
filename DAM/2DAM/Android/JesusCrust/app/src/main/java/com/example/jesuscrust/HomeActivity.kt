package com.example.jesuscrust

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)  // Asegúrate que el archivo sea "home.xml"

        // Botón MenuHome -> abre MenuActivity
        val menuHomeButton: ImageButton = findViewById(R.id.MenuHome)
        menuHomeButton.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
        // Botón Cart -> abre CartActivity
        val cartButton: ImageButton = findViewById(R.id.Cart)
        cartButton.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}