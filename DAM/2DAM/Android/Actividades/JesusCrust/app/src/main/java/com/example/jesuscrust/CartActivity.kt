package com.example.jesuscrust

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)

    }
        private fun setupBottomNavigation() {
            findViewById<ImageView>(R.id.menu).setOnClickListener {
                startActivity(Intent(this, MenuActivity::class.java))
            }

            findViewById<ImageView>(R.id.home4).setOnClickListener {
                startActivity(Intent(this, HomeActivity::class.java))
            }

            // TAREA PENDIENTE: Puedes añadir aquí la lógica para R.id.home, R.id.orders, R.id.profile


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cart_icon)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




    }
}
