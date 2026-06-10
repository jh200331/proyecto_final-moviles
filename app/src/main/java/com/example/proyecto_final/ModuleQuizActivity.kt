package com.example.proyecto_final

import android.content.Intent
import android.graphics.Typeface
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.proyecto_final.learning.GamificationHelper
import com.example.proyecto_final.learning.LearningContent
import com.example.proyecto_final.learning.LearningRepository
import com.example.proyecto_final.learning.QuizQuestion
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ModuleQuizActivity : CyberBaseActivity() {

    companion object {
        const val EXTRA_MODULE_ID = "module_id"
    }

    private lateinit var repository: LearningRepository
    private var moduleId: Int = 1
    private var currentIndex: Int = 0
    private lateinit var questions: List<QuizQuestion>
    private val answers = mutableMapOf<Int, Int>()
    private lateinit var quizContainer: LinearLayout

    override val screenTitle: String get() {
        val module = LearningContent.getModule(moduleId)
        return "Cuestionario: ${module?.title ?: ""}"
    }

    override val screenSubtitle: String = "Responde las 10 preguntas del módulo"

    override val showBottomMenu: Boolean = false

    override fun buildContent() {
        moduleId = intent.getIntExtra(EXTRA_MODULE_ID, 1)
        repository = LearningRepository(this)
        questions = LearningContent.getQuiz(moduleId)

        if (questions.isEmpty()) {
            card { addView(paragraph("No hay preguntas disponibles para este módulo.")) }
            return
        }

        quizContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = blockParams()
        }
        content.addView(quizContainer)
        showQuestion()
    }

    private fun showQuestion() {
        quizContainer.removeAllViews()
        val question = questions[currentIndex]

        card {
            addView(
                TextView(this@ModuleQuizActivity).apply {
                    text = "Pregunta ${currentIndex + 1} de ${questions.size}"
                    setTextColor(accent)
                    textSize = 15f
                    typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                    layoutParams = blockParams(bottom = 8)
                }
            )
            addView(title(question.question))

            val radioGroup = RadioGroup(this@ModuleQuizActivity).apply {
                orientation = RadioGroup.VERTICAL
                layoutParams = blockParams(top = 8, bottom = 8)
            }

            question.options.forEachIndexed { index, option ->
                radioGroup.addView(
                    RadioButton(this@ModuleQuizActivity).apply {
                        id = View.generateViewId()
                        text = option
                        textSize = 16f
                        typeface = Typeface.SERIF
                        setTextColor(textDark)
                        setPadding(dp(4), dp(8), dp(4), dp(8))
                        tag = index
                        isChecked = answers[currentIndex] == index
                    }
                )
            }
            addView(radioGroup)

            val isLast = currentIndex == questions.size - 1
            addView(primaryButton(if (isLast) "Finalizar cuestionario" else "Siguiente pregunta") {
                val selectedId = radioGroup.checkedRadioButtonId
                if (selectedId == -1) {
                    MaterialAlertDialogBuilder(this@ModuleQuizActivity)
                        .setTitle("Selecciona una respuesta")
                        .setMessage("Debes elegir una opción antes de continuar.")
                        .setPositiveButton("OK", null)
                        .show()
                    return@primaryButton
                }
                val selectedIndex = radioGroup.findViewById<RadioButton>(selectedId).tag as Int
                answers[currentIndex] = selectedIndex

                if (isLast) {
                    finishQuiz()
                } else {
                    currentIndex++
                    showQuestion()
                }
            })

            if (currentIndex > 0) {
                addView(outlineButton("Pregunta anterior") {
                    currentIndex--
                    showQuestion()
                })
            }
        }
    }

    private fun finishQuiz() {
        quizContainer.removeAllViews()
        val score = questions.indices.count { index ->
            answers[index] == questions[index].correctIndex
        }
        val passed = repository.saveQuizResult(moduleId, score, questions.size)
        val percent = score * 100 / questions.size

        card {
            addView(title(if (passed) "¡Cuestionario aprobado!" else "Cuestionario no aprobado"))
            addView(
                TextView(this@ModuleQuizActivity).apply {
                    text = "Resultado: $score/${questions.size} ($percent%)"
                    setTextColor(if (passed) greenAccent else textMuted)
                    textSize = 22f
                    typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                    layoutParams = blockParams(bottom = 8)
                }
            )
            addView(paragraph(
                if (passed) GamificationHelper.motivationalMessage(GamificationHelper.MotivationContext.QUIZ_PASS)
                else "Necesitas al menos ${GamificationHelper.QUIZ_PASS_THRESHOLD} respuestas correctas. ¡Repasa las lecciones e inténtalo de nuevo!"
            ))
            if (passed) {
                addView(paragraph("+${GamificationHelper.XP_PER_QUIZ_PASS} XP ganados"))
            }
        }

        card {
            addView(label("Revisión de respuestas"))
            questions.forEachIndexed { index, q ->
                val userAnswer = answers[index]
                val correct = userAnswer == q.correctIndex
                addView(
                    TextView(this@ModuleQuizActivity).apply {
                        text = "${if (correct) "✅" else "❌"} P${index + 1}: ${q.explanation}"
                        setTextColor(if (correct) greenAccent else textMuted)
                        textSize = 14f
                        typeface = Typeface.SERIF
                        layoutParams = blockParams(bottom = 6)
                    }
                )
            }
        }

        val profile = repository.getUserProfile()
        if (profile.certificateIssued) {
            card {
                addView(label("🎓 ¡Certificado desbloqueado!"))
                addView(paragraph(GamificationHelper.motivationalMessage(
                    GamificationHelper.MotivationContext.ALL_MODULES_COMPLETE
                )))
                addView(primaryButton("Ver certificado") {
                    startActivity(Intent(this@ModuleQuizActivity, CertificateActivity::class.java))
                })
            }
        }

        quizContainer.addView(primaryButton("Volver al módulo") {
            startActivity(
                Intent(this, ModuleDetailActivity::class.java)
                    .putExtra(ModuleDetailActivity.EXTRA_MODULE_ID, moduleId)
            )
            finish()
        })
    }
}
