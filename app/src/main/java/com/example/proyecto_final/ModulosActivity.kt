package com.example.proyecto_final

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.proyecto_final.learning.GamificationHelper
import com.example.proyecto_final.learning.LearningContent
import com.example.proyecto_final.learning.LearningRepository

class ModulosActivity : CyberBaseActivity() {

    private lateinit var repository: LearningRepository

    override val screenTitle: String = "Módulos"
    override val screenSubtitle: String = "Tu camino hacia la ciberseguridad"

    override fun onResume() {
        super.onResume()
        content.removeAllViews()
        addHeader()
        buildContent()
    }

    override fun buildContent() {
        repository = LearningRepository(this)
        val profile = repository.getUserProfile()
        val allProgress = repository.getAllModuleProgress()
        val badges = repository.getBadges()
        val unlockedBadges = badges.count { it.unlocked }
        val completedModules = repository.getCompletedModulesCount()
        val overallPercent = repository.getOverallProgress()

        gradientCard {
            addView(whiteTitle("Resumen de aprendizaje"))
            addView(whiteParagraph("Progreso general: $overallPercent%  •  $completedModules/6 módulos completados"))

            val statsRow = LinearLayout(this@ModulosActivity).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = blockParams(top = 10, bottom = 8)
            }
            listOf(
                "Nivel ${profile.level}" to profile.levelTitle,
                "${profile.totalXp} XP" to "Experiencia total",
                "$unlockedBadges/6" to "Insignias"
            ).forEach { (value, label) ->
                val col = LinearLayout(this@ModulosActivity).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                }
                col.addView(TextView(this@ModulosActivity).apply {
                    text = value
                    setTextColor(Color.WHITE)
                    textSize = 16f
                    typeface = Typeface.DEFAULT_BOLD
                })
                col.addView(TextView(this@ModulosActivity).apply {
                    text = label
                    setTextColor(Color.argb(180, 255, 255, 255))
                    textSize = 11f
                })
                statsRow.addView(col)
            }
            addView(statsRow)

            addView(
                ProgressBar(this@ModulosActivity, null, android.R.attr.progressBarStyleHorizontal).apply {
                    max = 100
                    progress = profile.xpProgressInLevel
                    progressDrawable?.setTint(Color.WHITE)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, dp(8)
                    )
                }
            )
            addView(whiteParagraph("XP para siguiente nivel: ${profile.xpForNextLevel - profile.totalXp}"))
        }

        card {
            addView(title("Insignias y certificados"))
            badges.forEach { badge ->
                addView(
                    TextView(this@ModulosActivity).apply {
                        text = if (badge.unlocked) "🏅 ${badge.name}" else "🔒 ${badge.name}"
                        setTextColor(if (badge.unlocked) greenAccent else textMuted)
                        textSize = 13f
                        layoutParams = blockParams(bottom = 4)
                    }
                )
            }
            if (profile.certificateIssued) {
                addView(label("✅ Certificado CyberLearn desbloqueado"))
                addView(primaryButton("Ver certificado") {
                    startActivity(Intent(this@ModulosActivity, CertificateActivity::class.java))
                })
            } else {
                addView(paragraph("Completa los 6 módulos para obtener tu certificado digital."))
            }
        }

        card {
            addView(title("Logros"))
            repository.getAchievements().forEach { achievement ->
                addView(
                    TextView(this@ModulosActivity).apply {
                        text = if (achievement.unlocked) "🎖 ${achievement.name}: ${achievement.description}"
                        else "🔒 ${achievement.name}: ${achievement.description}"
                        setTextColor(if (achievement.unlocked) textDark else textMuted)
                        textSize = 12f
                        layoutParams = blockParams(bottom = 4)
                    }
                )
            }
        }

        addView(
            TextView(this).apply {
                text = GamificationHelper.motivationalMessage(
                    if (completedModules == 0) GamificationHelper.MotivationContext.FIRST_LESSON
                    else GamificationHelper.MotivationContext.LESSON_COMPLETE
                )
                setTextColor(purpleAccent)
                textSize = 13f
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
                layoutParams = blockParams(bottom = 12)
            }
        )

        addView(
            TextView(this).apply {
                text = "Módulos de aprendizaje"
                setTextColor(textDark)
                textSize = 20f
                typeface = Typeface.DEFAULT_BOLD
                layoutParams = blockParams(bottom = 10)
            }
        )

        LearningContent.allModules().forEach { module ->
            val progress = allProgress.find { it.moduleId == module.id }
                ?: return@forEach
            moduleCard(
                module = module,
                progress = progress,
                onContinue = {
                    val nextLesson = LearningContent.getLessons(module.id)
                        .firstOrNull { !repository.isLessonCompleted(it.id) }
                    if (nextLesson != null) {
                        openLesson(nextLesson.id)
                    } else {
                        openModuleDetail(module.id)
                    }
                },
                onOpen = { openModuleDetail(module.id) }
            )
        }
    }

    private fun openModuleDetail(moduleId: Int) {
        startActivity(
            Intent(this, ModuleDetailActivity::class.java)
                .putExtra(ModuleDetailActivity.EXTRA_MODULE_ID, moduleId)
        )
    }

    private fun openLesson(lessonId: Int) {
        startActivity(
            Intent(this, LessonActivity::class.java)
                .putExtra(LessonActivity.EXTRA_LESSON_ID, lessonId)
        )
    }
}
