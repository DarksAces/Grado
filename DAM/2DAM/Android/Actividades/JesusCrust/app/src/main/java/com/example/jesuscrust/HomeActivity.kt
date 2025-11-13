package com.example.jesuscrust
import android.widget.ImageView
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        // 1. Configuración de la Barra de Navegación Inferior (Bottom Nav)
        setupBottomNavigation()

        // 2. Configuración de las Categorías (Reutilización de Código)
        setupCategoryListeners()
    }

    /**
     * Configura los listeners para los botones de la barra de navegación inferior.
     * Hemos mantenido la lógica original de Intent para las pantallas Menu y Cart.
     */
    private fun setupBottomNavigation() {
        // Los IDs 'menu' y 'cart' ya están corregidos y en minúsculas.

        findViewById<ImageView>(R.id.menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        findViewById<ImageView>(R.id.cart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        // TAREA PENDIENTE: Puedes añadir aquí la lógica para R.id.home, R.id.orders, R.id.profile
    }

    /**
     * Asigna un único listener a todas las tarjetas de categoría y determina
     * qué categoría fue pulsada usando el ID de la vista.
     */
    private fun setupCategoryListeners() {
        // Obtenemos las referencias a los contenedores de categoría (LinearLayouts)
        val categoryBreads = findViewById<LinearLayout>(R.id.category_breads)
        val categoryPastries = findViewById<LinearLayout>(R.id.category_pastries)
        val categoryCakes = findViewById<LinearLayout>(R.id.category_cakes)
        val categoryOrganic = findViewById<LinearLayout>(R.id.category_organic)

        // Creamos un único listener y se lo asignamos a las cuatro categorías
        val categoryClickListener = View.OnClickListener { view ->
            handleCategoryClick(view.id)
        }

        categoryBreads.setOnClickListener(categoryClickListener)
        categoryPastries.setOnClickListener(categoryClickListener)
        categoryCakes.setOnClickListener(categoryClickListener)
        categoryOrganic.setOnClickListener(categoryClickListener)
    }

    /**
     * Maneja la lógica de la navegación una vez que una categoría es pulsada.
     * Esta es la función central donde aplicar la lógica de negocio.
     * @param categoryId El ID del recurso (R.id.category_xxx) que fue pulsado.
     */
    private fun handleCategoryClick(categoryId: Int) {
        val categoryName = when (categoryId) {
            R.id.category_breads -> "Panes"
            R.id.category_pastries -> "Repostería"
            R.id.category_cakes -> "Pasteles"
            R.id.category_organic -> "Orgánico"
            else -> "Desconocida"
        }

        // Simulación de navegación (se podría lanzar un Intent aquí)
        Toast.makeText(this, "Navegando a categoría: $categoryName", Toast.LENGTH_SHORT).show()

        // Ejemplo de INTENT eficiente:
        // val intent = Intent(this, CategoryDetailActivity::class.java)
        // intent.putExtra("CATEGORY_NAME", categoryName)
        // startActivity(intent)
    }
}