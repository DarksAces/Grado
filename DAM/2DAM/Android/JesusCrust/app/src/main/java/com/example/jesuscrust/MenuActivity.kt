package com.example.jesuscrust

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var scrollView: HorizontalScrollView
    private lateinit var container: LinearLayout
    private val handler = Handler(Looper.getMainLooper())
    private var scrollPos = 0
    private val scrollSpeed = 2 // píxeles por frame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        // Referencias de vistas
        scrollView = findViewById(R.id.MenuScroll)
        container = findViewById(R.id.productsContainer)

        // 👉 Botón que lleva a HomeActivity
        val imageButton1: ImageButton = findViewById(R.id.imageButton1)
        imageButton1.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // Poblar productos y activar scroll
        populateProducts()
        startAutoScroll()
    }

    // Poblamos productos y duplicamos para loop infinito
    private fun populateProducts() {
        val productNames = listOf("Producto 1", "Producto 2", "Producto 3", "Producto 4")
        val inflater = LayoutInflater.from(this)

        repeat(2) { // Duplicamos dos veces para loop suave
            for (name in productNames) {
                val item = inflater.inflate(R.layout.product_item, container, false)
                val nameView = item.findViewById<TextView>(R.id.productName)
                nameView.text = name
                container.addView(item)
            }
        }
    }

    private fun startAutoScroll() {
        val runnable = object : Runnable {
            override fun run() {
                scrollPos += scrollSpeed
                scrollView.scrollTo(scrollPos, 0)

                // Cuando llegamos a la mitad, reiniciamos el scroll
                val maxScroll = container.width / 2
                if (scrollPos >= maxScroll) scrollPos = 0

                handler.postDelayed(this, 16) // ~60fps
            }
        }
        handler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
