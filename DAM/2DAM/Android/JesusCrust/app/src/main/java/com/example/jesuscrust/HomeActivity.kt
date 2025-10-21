package com.example.jesuscrust

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Aquí se vincula el layout home.xml, no activity_main
        setContentView(R.layout.home)
    }
}
