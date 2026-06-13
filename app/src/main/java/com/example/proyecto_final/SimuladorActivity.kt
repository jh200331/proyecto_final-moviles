package com.example.proyecto_final

import android.content.Intent
import android.graphics.Typeface
import android.widget.TextView
import com.example.proyecto_final.learning.EvaluationContent
import com.example.proyecto_final.learning.GamificationHelper
import com.example.proyecto_final.learning.LearningContent

class SimuladorActivity : CyberBaseActivity() {
    override val screenTitle: String = "Simulador"
    override val screenSubtitle: String = "30 escenarios practicos conectados con progreso y XP"

    override fun onResume() {
        super.onResume()
        content.removeAllViews()
        addHeader()
        buildContent()
    }

    override fun buildContent() {
        val database = ProgressDatabaseHelper(this)
        gradientCard {
            addView(whiteTitle("Entrenamiento de decisiones"))
            addView(whiteParagraph("Cada modulo tiene 5 simulaciones. La primera solucion correcta otorga ${GamificationHelper.XP_PER_SIMULATION} XP."))
        }

        LearningContent.allModules().forEach { module ->
            val percent = database.getModuleSimulationPercent(module.id)
            card {
                addView(label("Modulo ${module.id}"))
                addView(title(module.title))
                addView(progressLine("Simulaciones completadas", percent, animate = true))
            }

            EvaluationContent.scenariosForModule(module.id).forEachIndexed { index, scenario ->
                val lessonCompleted = database.isLessonCompleted(scenario.lessonId)
                val result = database.getSimulationResult(scenario.id)
                card {
                    addView(label("Simulacion ${index + 1} de 5"))
                    addView(title(scenario.title))
                    addView(
                        TextView(this@SimuladorActivity).apply {
                            text = when {
                                result?.passed == true -> "Completada - ${result.attempts} intento(s)"
                                lessonCompleted -> "Disponible"
                                else -> "Bloqueada hasta completar la leccion ${index + 1}"
                            }
                            setTextColor(if (result?.passed == true) greenAccent else textMuted)
                            textSize = 16f
                            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                            layoutParams = blockParams(bottom = 8)
                        }
                    )
                    if (lessonCompleted) {
                        addView(primaryButton(if (result == null) "Iniciar simulacion" else "Repetir simulacion") {
                            startActivity(
                                Intent(this@SimuladorActivity, SimulationScenarioActivity::class.java)
                                    .putExtra(SimulationScenarioActivity.EXTRA_SCENARIO_ID, scenario.id)
                            )
                        })
                    }
                }
            }
        }
    }
}
