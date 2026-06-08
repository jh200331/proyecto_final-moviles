package com.example.proyecto_final

import android.content.Intent
import android.graphics.Typeface
import android.widget.LinearLayout
import android.widget.TextView
import com.example.proyecto_final.learning.GamificationHelper
import com.example.proyecto_final.learning.LearningContent
import com.example.proyecto_final.learning.LearningRepository
import com.example.proyecto_final.learning.ModuleStatus
class ModuleDetailActivity : CyberBaseActivity() {

    companion object {
        const val EXTRA_MODULE_ID = "module_id"
    }

    private lateinit var repository: LearningRepository
    private var moduleId: Int = 1

    override val screenTitle: String get() = LearningContent.getModule(moduleId)?.title ?: "Módulo"
    override val screenSubtitle: String get() = LearningContent.getModule(moduleId)?.description ?: ""

    override fun onResume() {
        super.onResume()
        content.removeAllViews()
        addHeader()
        buildContent()
    }

    override fun buildContent() {
        moduleId = intent.getIntExtra(EXTRA_MODULE_ID, 1)
        repository = LearningRepository(this)
        val module = LearningContent.getModule(moduleId) ?: return
        val progress = repository.getModuleProgress(moduleId)
        val lessons = LearningContent.getLessons(moduleId)
        val quiz = repository.getModuleProgress(moduleId)

        if (!progress.isUnlocked) {
            card {
                addView(title("Módulo bloqueado"))
                addView(paragraph(GamificationHelper.motivationalMessage(
                    GamificationHelper.MotivationContext.LOCKED_MODULE
                )))
            }
            return
        }

        card {
            addView(label("${module.difficulty}  •  ⏱ ${module.estimatedMinutes} min"))
            addView(progressLine("Progreso del módulo", progress.lessonPercent, animate = true))
            addView(
                TextView(this@ModuleDetailActivity).apply {
                    text = when (progress.status) {
                        ModuleStatus.COMPLETED -> "Estado: Completado ✅"
                        ModuleStatus.IN_PROGRESS -> "Estado: En progreso 📖"
                        ModuleStatus.LOCKED -> "Estado: Bloqueado 🔒"
                    }
                    setTextColor(greenAccent)
                    textSize = 13f
                    typeface = Typeface.DEFAULT_BOLD
                    layoutParams = blockParams(bottom = 6)
                }
            )
            addView(paragraph("XP ganada en este módulo: ${progress.xp}"))
        }

        addView(
            TextView(this).apply {
                text = "Lecciones"
                setTextColor(textDark)
                textSize = 18f
                typeface = Typeface.DEFAULT_BOLD
                layoutParams = blockParams(top = 4, bottom = 8)
            }
        )

        lessons.forEach { lesson ->
            val completed = repository.isLessonCompleted(lesson.id)
            card {
                addView(label("Lección ${lesson.order}"))
                addView(title(lesson.title))
                addView(
                    TextView(this@ModuleDetailActivity).apply {
                        text = if (completed) "✅ Completada" else "Pendiente"
                        setTextColor(if (completed) greenAccent else textMuted)
                        textSize = 12f
                        layoutParams = blockParams(bottom = 6)
                    }
                )
                addView(
                    primaryButton(if (completed) "Repasar lección" else "Iniciar lección") {
                        startActivity(
                            Intent(this@ModuleDetailActivity, LessonActivity::class.java)
                                .putExtra(LessonActivity.EXTRA_LESSON_ID, lesson.id)
                        )
                    }
                )
            }
        }

        card {
            addView(label("Cuestionario final"))
            addView(title("Evaluación del módulo"))
            addView(paragraph("10 preguntas  •  Mínimo 7 correctas para aprobar  •  +${GamificationHelper.XP_PER_QUIZ_PASS} XP"))
            if (quiz.quizPassed) {
                addView(
                    TextView(this@ModuleDetailActivity).apply {
                        text = "✅ Aprobado: ${quiz.quizScore}/10"
                        setTextColor(greenAccent)
                        textSize = 14f
                        typeface = Typeface.DEFAULT_BOLD
                        layoutParams = blockParams(bottom = 6)
                    }
                )
            }
            val canTakeQuiz = progress.lessonPercent >= 100
            if (canTakeQuiz) {
                addView(primaryButton(if (quiz.quizPassed) "Repetir cuestionario" else "Iniciar cuestionario") {
                    startActivity(
                        Intent(this@ModuleDetailActivity, ModuleQuizActivity::class.java)
                            .putExtra(ModuleQuizActivity.EXTRA_MODULE_ID, moduleId)
                    )
                })
            } else {
                addView(paragraph("Completa las 5 lecciones para desbloquear el cuestionario."))
            }
        }

        if (progress.lessonPercent >= 100) {
            card {
                addView(label("¡Módulo completado!"))
                addView(paragraph(GamificationHelper.motivationalMessage(
                    GamificationHelper.MotivationContext.MODULE_COMPLETE
                )))
                if (moduleId < 6) {
                    addView(paragraph("El siguiente módulo ha sido desbloqueado."))
                }
            }
        }
    }
}
