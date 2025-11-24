package com.example.supermarket

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvProducts: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var products: MutableList<Product>
    private lateinit var dbHelper: ProductDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = ProductDbHelper(this)
        rvProducts = findViewById(R.id.rvProducts)
        rvProducts.layoutManager = LinearLayoutManager(this)

        loadProducts()

        productAdapter = ProductAdapter(products)
        rvProducts.adapter = productAdapter

        val btnGoToCart: Button = findViewById(R.id.btnGoToCart)
        btnGoToCart.setOnClickListener { goToCart() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                goToCart()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToCart() {
        val selectedProducts = ArrayList(products.filter { it.quantity > 0 })
        if (selectedProducts.isEmpty()) {
            Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, CartActivity::class.java).apply {
                putParcelableArrayListExtra("selectedProducts", selectedProducts)
            }
            startActivity(intent)
        }
    }

    private fun loadProducts() {
        val db = dbHelper.readableDatabase
        val cursor = db.query(ProductContract.ProductEntry.TABLE_NAME, null, null, null, null, null, null)
        products = mutableListOf()

        if (cursor.count == 0) {
            insertInitialProducts()
            // Re-query after insertion
            val newCursor = db.query(ProductContract.ProductEntry.TABLE_NAME, null, null, null, null, null, null)
            addProductsFromCursor(newCursor)
            newCursor.close()
        } else {
            addProductsFromCursor(cursor)
        }
        cursor.close()
    }

    private fun addProductsFromCursor(cursor: android.database.Cursor) {
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_NAME))
                val image = getInt(getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_IMAGE))
                val price = getDouble(getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_NAME_PRICE))
                products.add(Product(name, image, price = price))
            }
        }
    }


    private fun insertInitialProducts() {
        val db = dbHelper.writableDatabase
        val initialProducts = listOf(
            Product("Manzana", android.R.drawable.sym_def_app_icon, price = 0.5),
            Product("Limón", android.R.drawable.sym_def_app_icon, price = 0.4),
            Product("Piña", android.R.drawable.sym_def_app_icon, price = 1.5),
            Product("Uvas", android.R.drawable.sym_def_app_icon, price = 2.5),
            Product("Ciruela", android.R.drawable.sym_def_app_icon, price = 0.3),
            Product("Naranja", android.R.drawable.sym_def_app_icon, price = 0.6),
            Product("Sandía", android.R.drawable.sym_def_app_icon, price = 3.0),
            Product("Pera", android.R.drawable.sym_def_app_icon, price = 0.7),
            Product("Cerezas", android.R.drawable.sym_def_app_icon, price = 4.0),
            Product("Mora", android.R.drawable.sym_def_app_icon, price = 5.0),
            Product("Fresa", android.R.drawable.sym_def_app_icon, price = 4.5)
        )

        initialProducts.forEach { product ->
            val values = ContentValues().apply {
                put(ProductContract.ProductEntry.COLUMN_NAME_NAME, product.name)
                put(ProductContract.ProductEntry.COLUMN_NAME_IMAGE, product.image)
                put(ProductContract.ProductEntry.COLUMN_NAME_PRICE, product.price)
            }
            db.insert(ProductContract.ProductEntry.TABLE_NAME, null, values)
        }
    }
}
