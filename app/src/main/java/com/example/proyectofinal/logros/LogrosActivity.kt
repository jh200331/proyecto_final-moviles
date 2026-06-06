package com.example.proyectofinal.logros

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.proyectofinal.R

class LogrosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logros)

        findViewById<android.view.View>(R.id.btnVolverLogros).setOnClickListener {
            finish()
        }
    }
}
