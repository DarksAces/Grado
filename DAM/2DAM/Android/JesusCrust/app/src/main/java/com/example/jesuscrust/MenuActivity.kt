package com.example.jesuscrust

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var scrollView: HorizontalScrollView
    private lateinit var container: LinearLayout
    private val handler = Handler(Looper.getMainLooper())
    private var scrollPos = 0
    private val scrollSpeed = 2 // pixels por frame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        scrollView = findViewById(R.id.MenuScroll)
        container = findViewById(R.id.productsContainer)

        populateProducts()  // Pobla productos y duplica automáticamente
        startAutoScroll()
    }

    // Poblamos productos y duplicamos para loop infinito
    private fun populateProducts() {
        val productNames = listOf("Producto 1", "Producto 2", "Producto 3", "Producto 4")
        val inflater = LayoutInflater.from(this)

        // Duplicamos dos veces para loop suave
        repeat(2) {
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

                // La mitad del contenedor es donde termina la primera serie de productos
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
