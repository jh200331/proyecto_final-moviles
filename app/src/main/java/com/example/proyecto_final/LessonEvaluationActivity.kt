package com.example.proyecto_final

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.proyecto_final.learning.EvaluationContent
import com.example.proyecto_final.learning.GamificationHelper
import com.example.proyecto_final.learning.LearningContent
import com.example.proyecto_final.learning.LessonEvaluationQuestion
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LessonEvaluationActivity : CyberBaseActivity() {

    companion object {
        const val EXTRA_LESSON_ID = "lesson_id"
    }

    private var lessonId: Int = 0
    private var currentQuestionIndex: Int = 0
    private var score: Int = 0
    private var answered: Boolean = false
    private lateinit var database: ProgressDatabaseHelper
    private lateinit var questions: List<LessonEvaluationQuestion>
    private lateinit var questionContainer: LinearLayout

    override val screenTitle: String
        get() = EvaluationContent.evaluationForLesson(lessonId)?.let {
            "Evaluacion ${it.lessonOrder}"
        } ?: "Evaluacion"

    override val screenSubtitle: String
        get() = EvaluationContent.evaluationForLesson(lessonId)?.title ?: ""

    override val showBottomMenu: Boolean = false

    override fun buildContent() {
        lessonId = intent.getIntExtra(EXTRA_LESSON_ID, 0)
        database = ProgressDatabaseHelper(this)
        val evaluation = EvaluationContent.evaluationForLesson(lessonId) ?: return

        if (!database.isLessonCompleted(lessonId)) {
            card {
                addView(title("Evaluacion bloqueada"))
                addView(paragraph("Completa primero la leccion para habilitar sus preguntas."))
                addView(primaryButton("Abrir leccion") {
                    startActivity(
                        Intent(this@LessonEvaluationActivity, LessonActivity::class.java)
                            .putExtra(LessonActivity.EXTRA_LESSON_ID, lessonId)
                    )
                    finish()
                })
            }
            return
        }

        questions = evaluation.questions
        gradientCard {
            addView(whiteTitle(evaluation.title))
            addView(whiteParagraph("5 preguntas - Minimo 4 correctas - +${GamificationHelper.XP_PER_LESSON_EVALUATION} XP la primera vez"))
        }

        questionContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = blockParams()
        }
        content.addView(questionContainer)
        showQuestion()
    }

    private fun showQuestion() {
        answered = false
        questionContainer.removeAllViews()
        val question = questions[currentQuestionIndex]

        val questionCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(16), dp(16), dp(16))
            layoutParams = blockParams(bottom = 12)
        }

        questionCard.addView(progressLine("Pregunta ${currentQuestionIndex + 1} de ${questions.size}", ((currentQuestionIndex + 1) * 100) / questions.size, animate = true))
        questionCard.addView(title(question.prompt))

        val radioGroup = RadioGroup(this).apply {
            orientation = RadioGroup.VERTICAL
            layoutParams = blockParams(top = 8, bottom = 10)
        }
        question.options.forEachIndexed { index, option ->
            radioGroup.addView(
                RadioButton(this).apply {
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
        questionCard.addView(radioGroup)

        val feedback = TextView(this).apply {
            textSize = 16f
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            visibility = View.GONE
            layoutParams = blockParams(bottom = 8)
        }
        questionCard.addView(feedback)

        lateinit var actionButton: MaterialButton
        actionButton = primaryButton("Comprobar respuesta") {
            if (!answered) {
                val selectedId = radioGroup.checkedRadioButtonId
                if (selectedId == -1) {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Selecciona una respuesta")
                        .setMessage("Debes elegir una opcion antes de continuar.")
                        .setPositiveButton("Entendido", null)
                        .show()
                    return@primaryButton
                }

                val selected = radioGroup.findViewById<RadioButton>(selectedId).tag as Int
                val correct = selected == question.correctIndex
                if (correct) score++
                answered = true
                radioGroup.isEnabled = false
                for (index in 0 until radioGroup.childCount) {
                    radioGroup.getChildAt(index).isEnabled = false
                }
                feedback.visibility = View.VISIBLE
                feedback.setTextColor(if (correct) greenAccent else Color.rgb(183, 28, 28))
                feedback.text = if (correct) {
                    "Correcto. ${question.explanation}"
                } else {
                    "Respuesta correcta: ${question.options[question.correctIndex]}\n${question.explanation}"
                }
                actionButton.text = if (currentQuestionIndex == questions.lastIndex) {
                    "Ver resultado"
                } else {
                    "Siguiente pregunta"
                }
            } else if (currentQuestionIndex < questions.lastIndex) {
                currentQuestionIndex++
                showQuestion()
            } else {
                finishEvaluation()
            }
        }
        questionCard.addView(actionButton)
        questionContainer.addView(questionCard)
    }

    private fun finishEvaluation() {
        questionContainer.removeAllViews()
        val evaluation = EvaluationContent.evaluationForLesson(lessonId) ?: return
        val previous = database.getLessonEvaluationResult(lessonId)
        val passed = database.saveLessonEvaluationResult(lessonId, evaluation.moduleId, score, questions.size)
        val earnedXp = passed && previous?.passed != true

        card {
            addView(title(if (passed) "Evaluacion aprobada" else "Necesitas reforzar la leccion"))
            addView(progressLine("Resultado", (score * 100) / questions.size, animate = true))
            addView(paragraph("Puntaje: $score/${questions.size}"))
            addView(paragraph(if (passed) "Dominaste los conceptos esenciales." else "Repasa la leccion y vuelve a intentarlo. Debes obtener 4/5."))
            if (earnedXp) addView(label("+${GamificationHelper.XP_PER_LESSON_EVALUATION} XP obtenidos"))
        }

        if (!passed) {
            addView(primaryButton("Reintentar evaluacion") {
                currentQuestionIndex = 0
                score = 0
                showQuestion()
            })
            addView(outlineButton("Repasar leccion") {
                startActivity(Intent(this, LessonActivity::class.java).putExtra(LessonActivity.EXTRA_LESSON_ID, lessonId))
                finish()
            })
            return
        }

        val lessons = LearningContent.getLessons(evaluation.moduleId)
        val nextLesson = lessons.firstOrNull { it.order == evaluation.lessonOrder + 1 }
        if (nextLesson != null) {
            addView(primaryButton("Continuar con la leccion ${nextLesson.order}") {
                startActivity(Intent(this, LessonActivity::class.java).putExtra(LessonActivity.EXTRA_LESSON_ID, nextLesson.id))
                finish()
            })
        } else {
            addView(primaryButton("Ver progreso del modulo") {
                startActivity(Intent(this, MiProgresoActivity::class.java))
                finish()
            })
        }
        addView(outlineButton("Volver a evaluaciones") {
            startActivity(Intent(this, EvaluacionesActivity::class.java))
            finish()
        })
    }
}
