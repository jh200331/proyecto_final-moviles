package com.example.proyecto_final

import android.content.Intent
import android.graphics.Typeface
import android.widget.TextView
import com.example.proyecto_final.learning.EvaluationContent
import com.example.proyecto_final.learning.LearningContent
import com.example.proyecto_final.learning.GamificationHelper

class EvaluacionesActivity : CyberBaseActivity() {
    override val screenTitle: String = "Evaluaciones"
    override val screenSubtitle: String = "5 preguntas por leccion, progreso y XP guardados"

    override fun onResume() {
        super.onResume()
        content.removeAllViews()
        addHeader()
        buildContent()
    }

    override fun buildContent() {
        val database = ProgressDatabaseHelper(this)
        val totalXp = database.getTotalXp()
        val (level, levelTitle) = database.getUserLevel()

        gradientCard {
            addView(whiteTitle("Centro de evaluaciones"))
            addView(whiteParagraph("Nivel $level - $levelTitle"))
            addView(whiteParagraph("XP acumulada: $totalXp"))
            addView(whiteParagraph("Aprueba con 4 de 5 respuestas correctas. Cada primera aprobacion suma ${GamificationHelper.XP_PER_LESSON_EVALUATION} XP."))
        }

        LearningContent.allModules().forEach { module ->
            val evaluationPercent = database.getModuleEvaluationPercent(module.id)
            card {
                addView(label("Modulo ${module.id}"))
                addView(title(module.title))
                addView(progressLine("Evaluaciones aprobadas", evaluationPercent, animate = true))
            }

            EvaluationContent.evaluationsForModule(module.id).forEach { evaluation ->
                val lessonCompleted = database.isLessonCompleted(evaluation.lessonId)
                val result = database.getLessonEvaluationResult(evaluation.lessonId)
                card {
                    addView(label("Evaluacion ${evaluation.lessonOrder} de 5"))
                    addView(title(evaluation.title))
                    addView(
                        TextView(this@EvaluacionesActivity).apply {
                            text = when {
                                result?.passed == true -> "Aprobada: ${result.score}/${result.totalQuestions} - ${result.attempts} intento(s)"
                                result != null -> "Pendiente de aprobacion: ${result.score}/${result.totalQuestions}"
                                lessonCompleted -> "Disponible"
                                else -> "Bloqueada: completa primero la leccion ${evaluation.lessonOrder}"
                            }
                            setTextColor(if (result?.passed == true) greenAccent else textMuted)
                            textSize = 16f
                            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                            layoutParams = blockParams(bottom = 8)
                        }
                    )

                    if (lessonCompleted) {
                        addView(primaryButton(if (result == null) "Comenzar evaluacion" else "Repetir evaluacion") {
                            startActivity(
                                Intent(this@EvaluacionesActivity, LessonEvaluationActivity::class.java)
                                    .putExtra(LessonEvaluationActivity.EXTRA_LESSON_ID, evaluation.lessonId)
                            )
                        })
                    } else {
                        addView(outlineButton("Ir a la leccion") {
                            startActivity(
                                Intent(this@EvaluacionesActivity, LessonActivity::class.java)
                                    .putExtra(LessonActivity.EXTRA_LESSON_ID, evaluation.lessonId)
                            )
                        })
                    }
                }
            }
        }
    }
}
