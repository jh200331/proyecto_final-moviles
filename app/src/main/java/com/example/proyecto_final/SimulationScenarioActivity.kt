package com.example.proyecto_final

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.proyecto_final.learning.EvaluationContent
import com.example.proyecto_final.learning.GamificationHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SimulationScenarioActivity : CyberBaseActivity() {

    companion object {
        const val EXTRA_SCENARIO_ID = "scenario_id"
    }

    private var scenarioId: Int = 0

    override val screenTitle: String
        get() = EvaluationContent.scenarioById(scenarioId)?.title ?: "Simulacion"
    override val screenSubtitle: String = "Analiza el caso y toma la decision mas segura"
    override val showBottomMenu: Boolean = false

    override fun buildContent() {
        scenarioId = intent.getIntExtra(EXTRA_SCENARIO_ID, 0)
        val scenario = EvaluationContent.scenarioById(scenarioId) ?: return
        val database = ProgressDatabaseHelper(this)
        val previous = database.getSimulationResult(scenarioId)

        gradientCard {
            addView(whiteTitle("Escenario realista"))
            addView(whiteParagraph(scenario.situation))
        }

        card {
            addView(title("Que decision tomarias?"))
            val group = RadioGroup(this@SimulationScenarioActivity).apply {
                orientation = RadioGroup.VERTICAL
                layoutParams = blockParams(bottom = 10)
            }
            scenario.options.forEachIndexed { index, option ->
                group.addView(
                    RadioButton(this@SimulationScenarioActivity).apply {
                        id = View.generateViewId()
                        text = option
                        tag = index
                        textSize = 16f
                        typeface = Typeface.SERIF
                        setTextColor(textDark)
                        setPadding(dp(8), dp(10), dp(8), dp(10))
                    }
                )
            }
            addView(group)

            val feedback = TextView(this@SimulationScenarioActivity).apply {
                visibility = View.GONE
                textSize = 16f
                typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                layoutParams = blockParams(bottom = 8)
            }
            addView(feedback)

            addView(primaryButton("Confirmar decision") {
                val selectedId = group.checkedRadioButtonId
                if (selectedId == -1) {
                    MaterialAlertDialogBuilder(this@SimulationScenarioActivity)
                        .setTitle("Selecciona una decision")
                        .setMessage("Analiza el escenario y elige una opcion.")
                        .setPositiveButton("Entendido", null)
                        .show()
                    return@primaryButton
                }
                val selected = group.findViewById<RadioButton>(selectedId).tag as Int
                val correct = selected == scenario.correctIndex
                feedback.visibility = View.VISIBLE
                feedback.setTextColor(if (correct) greenAccent else Color.rgb(183, 28, 28))
                feedback.text = if (correct) "Decision segura. ${scenario.explanation}" else "La mejor decision era: ${scenario.options[scenario.correctIndex]}\n${scenario.explanation}"
                if (correct) {
                    database.saveSimulationResult(scenario.id, scenario.moduleId, 1)
                    if (previous?.passed != true) {
                        addView(label("+${GamificationHelper.XP_PER_SIMULATION} XP obtenidos"))
                    }
                    addView(primaryButton("Volver al simulador") {
                        startActivity(Intent(this@SimulationScenarioActivity, SimuladorActivity::class.java))
                        finish()
                    })
                }
            })
        }
    }
}
