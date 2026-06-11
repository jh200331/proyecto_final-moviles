package com.example.proyecto_final

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView

class

InicioActivity : CyberBaseActivity() {
    override val screenTitle: String = "CyberEdu"
    override val screenSubtitle: String = "Aprende hoy, protege tu mundo mañana"
    override val showBottomMenu: Boolean = false

    override fun buildContent() {
        // Huerfano: aqui se termina el inicio segun el mockup. No poner barra azul inferior.
        hero()
        stats()
        dashboard()
        content.addView(primaryButton("Iniciar sesion") { openActivity(ModulosActivity::class.java) })
        content.addView(outlineButton("Registrarse") { openActivity(ModulosActivity::class.java) })
    }

    private fun hero() {
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(18), dp(28), dp(18), dp(28))
            background = GradientDrawable().apply {
                setColor(blueDark)
                cornerRadius = dp(18).toFloat()
            }
            layoutParams = blockParams(bottom = 14)
        }
        box.addView(
            TextView(this).apply {
                text = "ESEIT"
                setTextColor(Color.WHITE)
                textSize = 31f
                typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                gravity = Gravity.CENTER
                layoutParams = blockParams(bottom = 6)
            }
        )
        box.addView(
            TextView(this).apply {
                text = "CyberEdu"
                setTextColor(Color.WHITE)
                textSize = 29f
                typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                gravity = Gravity.CENTER
                layoutParams = blockParams(bottom = 8)
            }
        )
        box.addView(
            TextView(this).apply {
                text = "Plataforma educativa para aprender seguridad informatica por modulos."
                setTextColor(Color.rgb(205, 224, 255))
                textSize = 15f
                typeface = Typeface.SERIF
                gravity = Gravity.CENTER
            }
        )
        content.addView(box)
    }

    private fun stats() {

        val db = ProgressDatabaseHelper(this)

        val totalXp = db.getTotalXp()
        val (nivel, titulo) = db.getUserLevel()

        val progresoNivel =
            com.example.proyecto_final.learning.GamificationHelper
                .xpProgressInCurrentLevel(totalXp)

        card {
            addView(label("Nivel actual"))
            addView(title("Nivel $nivel · $titulo"))
            addView(progressLine("XP para el siguiente nivel", progresoNivel, true))
            addView(paragraph("XP total REAL: $totalXp"))
        }
    }

    private fun dashboard() {
        content.addView(
            TextView(this).apply {
                text = "Que deseas aprender hoy?"
                setTextColor(textDark)
                textSize = 18f
                typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                layoutParams = blockParams(bottom = 10)
            }
        )

        val grid = GridLayout(this).apply {
            columnCount = 2
            layoutParams = blockParams(bottom = 12)
        }
        grid.addView(tile("Modulos", "6 disponibles") { openActivity(ModulosActivity::class.java) })
        grid.addView(tile("Evaluaciones", "Por modulo") { openActivity(EvaluacionesActivity::class.java) })
        grid.addView(tile("Simulador", "Escenarios reales") { openActivity(SimuladorActivity::class.java) })
        grid.addView(tile("Mi progreso", "Consulta tu avance") { openActivity(MiProgresoActivity::class.java) })
        grid.addView(tile("Logros", "Insignias y rangos") { openActivity(LogrosActivity::class.java) })
        content.addView(grid)
    }

    private fun tile(title: String, subtitle: String, action: () -> Unit): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(12), dp(12), dp(12), dp(12))
            background = GradientDrawable().apply {
                setColor(Color.WHITE)
                cornerRadius = dp(12).toFloat()
                setStroke(dp(1), Color.rgb(221, 231, 245))
            }
            layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(dp(4), dp(4), dp(4), dp(8))
            }
            addView(label(title))
            addView(paragraph(subtitle))
            setOnClickListener { action() }
        }
    }

    override fun onResume() {
        super.onResume()

        content.removeAllViews()

        addHeader()
        buildContent()
    }
}
