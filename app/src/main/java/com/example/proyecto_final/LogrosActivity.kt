package com.example.proyecto_final

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.proyectofinal_moviles.R
import com.example.proyecto_final.learning.GamificationHelper

class LogrosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logros)

        findViewById<android.view.View>(R.id.btnVolverLogros).setOnClickListener {
            finish()
        }
        refreshProgress()
    }

    override fun onResume() {
        super.onResume()
        refreshProgress()
    }

    private fun refreshProgress() {
        val database = ProgressDatabaseHelper(this)
        val totalXp = database.getTotalXp()
        val (level, title) = database.getUserLevel()
        val completedModules = database.getProgress().count {
            it.modulePercent >= 100 && it.evaluationPercent >= 100 && it.simulationPercent >= 100
        }

        findViewById<TextView>(R.id.txtNivelNumero)?.text = level.toString()
        findViewById<TextView>(R.id.txtRangoActual)?.text = title
        findViewById<TextView>(R.id.txtModulosCompletados)?.text = "$completedModules de 6 modulos dominados"
        findViewById<ProgressBar>(R.id.progressNivelLogros)?.progress =
            GamificationHelper.xpProgressInCurrentLevel(totalXp)
    }
}
