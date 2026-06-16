package com.example.proyecto_final

import android.content.Intent
import android.graphics.Typeface
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.example.proyecto_final.learning.GamificationHelper
import com.example.proyecto_final.learning.LearningContent
import com.example.proyecto_final.learning.LearningRepository
import com.example.proyecto_final.learning.Lesson
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LessonActivity : CyberBaseActivity() {

    companion object {
        const val EXTRA_LESSON_ID = "lesson_id"
    }

    private enum class LessonSection(
        val tabLabel: String,
        val title: String,
        val icon: String
    ) {
        INTRODUCTION("Introducción", "Introducción", "📖"),
        EXPLANATION("Explicación", "Explicación detallada", "📋"),
        EXAMPLES("Ejemplos", "Ejemplos reales", "🌍"),
        TIPS("Consejos", "Consejos prácticos", "💡"),
        SUMMARY("Resumen", "Resumen", "✅"),
        REFLECTION("Reflexión", "Pregunta de reflexión", "💭")
    }

    private lateinit var repository: LearningRepository
    private var lessonId: Int = 0
    private var currentSection: LessonSection = LessonSection.INTRODUCTION
    private lateinit var sectionContentContainer: LinearLayout
    private lateinit var sectionIndicator: TextView
    private val sectionTabButtons = mutableMapOf<LessonSection, MaterialButton>()
    private lateinit var prevButton: MaterialButton
    private lateinit var nextButton: MaterialButton

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

        addView(
            TextView(this).apply {
                text = "Selecciona un apartado para estudiar o repasar"
                setTextColor(textMuted)
                textSize = 14f
                typeface = Typeface.SERIF
                layoutParams = blockParams(bottom = 10)
            }
        )

        addView(buildSectionTabBar())

        sectionIndicator = TextView(this).apply {
            textSize = 13f
            setTextColor(accent)
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            gravity = Gravity.END
            layoutParams = blockParams(bottom = 6)
        }
        addView(sectionIndicator)

        sectionContentContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = blockParams(bottom = 12)
        }
        addView(sectionContentContainer)
        renderSectionContent(lesson)

        addView(buildSectionNavigation())

        card {
            addView(label("Acciones de la lección"))
            if (completed) {
                addView(
                    TextView(this@LessonActivity).apply {
                        text = "✅ Lección completada  •  +${GamificationHelper.XP_PER_LESSON} XP"
                        setTextColor(greenAccent)
                        textSize = 15f
                        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
                        layoutParams = blockParams(bottom = 8)
                    }
                )
                val evaluationResult = ProgressDatabaseHelper(this@LessonActivity)
                    .getLessonEvaluationResult(lessonId)
                addView(primaryButton(
                    if (evaluationResult?.passed == true) "Repetir evaluación de la lección"
                    else "Realizar evaluación de la lección"
                ) {
                    startActivity(
                        Intent(this@LessonActivity, LessonEvaluationActivity::class.java)
                            .putExtra(LessonEvaluationActivity.EXTRA_LESSON_ID, lessonId)
                    )
                })
            } else {
                addView(paragraph("Cuando hayas revisado los apartados, marca la lección como completada."))
                addView(primaryButton("Marcar como completada") {
                    markLessonComplete(lesson)
                })
            }
            addView(outlineButton("Volver al módulo") {
                startActivity(
                    Intent(this@LessonActivity, ModuleDetailActivity::class.java)
                        .putExtra(ModuleDetailActivity.EXTRA_MODULE_ID, lesson.moduleId)
                )
                finish()
            })
        }
    }

    private fun buildSectionTabBar(): LinearLayout {
        val wrapper = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = blockParams(bottom = 4)
        }
        val scroll = sectionTabBar {
            LessonSection.entries.forEach { section ->
                val button = sectionTabButton(
                    text = "${section.icon} ${section.tabLabel}",
                    selected = section == currentSection
                ) {
                    selectSection(section)
                }
                sectionTabButtons[section] = button
                addView(button)
            }
        }
        wrapper.addView(scroll)
        return wrapper
    }

    private fun buildSectionNavigation(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            layoutParams = blockParams(bottom = 8)

            prevButton = outlineButton("← Anterior") {
                navigateSection(-1)
            }.apply {
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                    marginEnd = dp(6)
                }
            }
            nextButton = primaryButton("Siguiente →") {
                navigateSection(1)
            }.apply {
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                    marginStart = dp(6)
                }
            }
            addView(prevButton)
            addView(nextButton)
            updateNavigationButtons()
        }
    }

    private fun selectSection(section: LessonSection) {
        currentSection = section
        val lesson = LearningContent.getLesson(lessonId) ?: return
        updateTabSelection()
        renderSectionContent(lesson)
        updateNavigationButtons()
    }

    private fun navigateSection(direction: Int) {
        val sections = LessonSection.entries
        val newIndex = (sections.indexOf(currentSection) + direction).coerceIn(0, sections.lastIndex)
        selectSection(sections[newIndex])
    }

    private fun updateTabSelection() {
        sectionTabButtons.forEach { (section, button) ->
            val selected = section == currentSection
            button.setTextColor(if (selected) android.graphics.Color.WHITE else blue)
            button.setBackgroundColor(if (selected) accent else cardBg)
            button.strokeWidth = if (selected) 0 else dp(1)
            button.typeface = Typeface.create(
                Typeface.SERIF,
                if (selected) Typeface.BOLD else Typeface.NORMAL
            )
        }
        val index = LessonSection.entries.indexOf(currentSection) + 1
        val total = LessonSection.entries.size
        sectionIndicator.text = "Apartado $index de $total  •  ${currentSection.title}"
    }

    private fun updateNavigationButtons() {
        val index = LessonSection.entries.indexOf(currentSection)
        prevButton.isEnabled = index > 0
        prevButton.alpha = if (index > 0) 1f else 0.4f
        nextButton.isEnabled = index < LessonSection.entries.lastIndex
        nextButton.alpha = if (index < LessonSection.entries.lastIndex) 1f else 0.4f
        nextButton.text = if (index < LessonSection.entries.lastIndex) "Siguiente →" else "Último apartado"
    }

    private fun renderSectionContent(lesson: Lesson) {
        sectionContentContainer.removeAllViews()
        val section = currentSection
        updateTabSelection()

        val contentCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(14), dp(16), dp(14))
            background = android.graphics.drawable.GradientDrawable().apply {
                setColor(cardBg)
                cornerRadius = dp(14).toFloat()
                setStroke(dp(1), cardStroke)
            }
            elevation = dp(3).toFloat()
        }

        contentCard.addView(sectionLabel("${section.icon} ${section.title}"))

        when (section) {
            LessonSection.INTRODUCTION -> contentCard.addView(paragraph(lesson.introduction))
            LessonSection.EXPLANATION -> contentCard.addView(paragraph(lesson.explanation))
            LessonSection.EXAMPLES -> contentCard.addView(paragraph(lesson.examples))
            LessonSection.TIPS -> contentCard.addView(paragraph(lesson.tips))
            LessonSection.SUMMARY -> contentCard.addView(paragraph(lesson.summary))
            LessonSection.REFLECTION -> contentCard.addView(
                TextView(this).apply {
                    text = lesson.reflectionQuestion
                    setTextColor(textDark)
                    textSize = 17f
                    typeface = Typeface.create(Typeface.SERIF, Typeface.ITALIC)
                    setLineSpacing(4f, 1.12f)
                    layoutParams = blockParams(bottom = 8)
                }
            )
        }

        sectionContentContainer.addView(contentCard)
    }

    private fun markLessonComplete(lesson: Lesson) {
        val wasNew = repository.completeLesson(lessonId, lesson.moduleId)
        if (!wasNew) return

        val progress = repository.getModuleProgress(lesson.moduleId)
        val message = if (progress.lessonPercent >= 100) {
            GamificationHelper.motivationalMessage(GamificationHelper.MotivationContext.MODULE_COMPLETE)
        } else {
            GamificationHelper.motivationalMessage(GamificationHelper.MotivationContext.LESSON_COMPLETE)
        }
        MaterialAlertDialogBuilder(this)
            .setTitle("¡Lección completada!")
            .setMessage("$message\n\n+${GamificationHelper.XP_PER_LESSON} XP ganados")
            .setPositiveButton("Ir a la evaluación") { _, _ ->
                startActivity(
                    Intent(this, LessonEvaluationActivity::class.java)
                        .putExtra(LessonEvaluationActivity.EXTRA_LESSON_ID, lessonId)
                )
                finish()
            }
            .setNegativeButton("Seguir repasando", null)
            .show()
    }
}
