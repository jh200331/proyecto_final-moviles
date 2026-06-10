package com.example.proyecto_final

import android.content.Intent
import android.graphics.Typeface
import android.widget.TextView
import com.example.proyecto_final.learning.GamificationHelper
import com.example.proyecto_final.learning.LearningContent
import com.example.proyecto_final.learning.LearningRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LessonActivity : CyberBaseActivity() {

    companion object {
        const val EXTRA_LESSON_ID = "lesson_id"
    }

    private lateinit var repository: LearningRepository
    private var lessonId: Int = 0

    override val screenTitle: String get() =
        LearningContent.getLesson(lessonId)?.title ?: "Lección"

    override val screenSubtitle: String get() {
        val lesson = LearningContent.getLesson(lessonId) ?: return ""
        val module = LearningContent.getModule(lesson.moduleId)
        return "${module?.title ?: ""}  •  Lección ${lesson.order}"
    }

    override val showBottomMenu: Boolean = false

    override fun buildContent() {
        lessonId = intent.getIntExtra(EXTRA_LESSON_ID, 0)
        repository = LearningRepository(this)
        val lesson = LearningContent.getLesson(lessonId) ?: return
        val completed = repository.isLessonCompleted(lessonId)

        card {
            addView(sectionLabel("Introducción"))
            addView(paragraph(lesson.introduction))
        }

        card {
            addView(sectionLabel("Explicación detallada"))
            addView(paragraph(lesson.explanation))
        }

        card {
            addView(sectionLabel("Ejemplos reales"))
            addView(paragraph(lesson.examples))
        }

        card {
            addView(sectionLabel("Consejos prácticos"))
            addView(paragraph(lesson.tips))
        }

        card {
            addView(sectionLabel("Resumen"))
            addView(paragraph(lesson.summary))
        }

        card {
            addView(sectionLabel("Pregunta de reflexión"))
            addView(
                TextView(this@LessonActivity).apply {
                    text = "💭 ${lesson.reflectionQuestion}"
                    setTextColor(textDark)
                    textSize = 17f
                    typeface = Typeface.create(Typeface.SERIF, Typeface.ITALIC)
                    setLineSpacing(4f, 1.1f)
                    layoutParams = blockParams(bottom = 8)
                }
            )
        }

        if (completed) {
            addView(
                TextView(this).apply {
                    text = "✅ Lección completada  •  +${GamificationHelper.XP_PER_LESSON} XP"
                    setTextColor(greenAccent)
                    textSize = 16f
                    typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                    layoutParams = blockParams(bottom = 8)
                }
            )
        } else {
            addView(primaryButton("Marcar como completada") {
                val wasNew = repository.completeLesson(lessonId, lesson.moduleId)
                if (wasNew) {
                    val progress = repository.getModuleProgress(lesson.moduleId)
                    val message = if (progress.lessonPercent >= 100) {
                        GamificationHelper.motivationalMessage(
                            GamificationHelper.MotivationContext.MODULE_COMPLETE
                        )
                    } else {
                        GamificationHelper.motivationalMessage(
                            GamificationHelper.MotivationContext.LESSON_COMPLETE
                        )
                    }
                    MaterialAlertDialogBuilder(this)
                        .setTitle("¡Lección completada!")
                        .setMessage("$message\n\n+${GamificationHelper.XP_PER_LESSON} XP ganados")
                        .setPositiveButton("Continuar") { _, _ ->
                            if (progress.lessonPercent >= 100) {
                                startActivity(
                                    Intent(this, ModuleDetailActivity::class.java)
                                        .putExtra(ModuleDetailActivity.EXTRA_MODULE_ID, lesson.moduleId)
                                )
                            } else {
                                finish()
                            }
                        }
                        .show()
                }
            })
        }

        addView(outlineButton("Volver al módulo") {
            startActivity(
                Intent(this, ModuleDetailActivity::class.java)
                    .putExtra(ModuleDetailActivity.EXTRA_MODULE_ID, lesson.moduleId)
            )
            finish()
        })
    }
}
