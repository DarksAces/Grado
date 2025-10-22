package com.example.jesuscrust

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cart)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bottom navigation
        findViewById<ImageButton>(R.id.nav_home).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        findViewById<ImageButton>(R.id.nav_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
        findViewById<ImageButton>(R.id.nav_cart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

    }
}
