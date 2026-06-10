package com.example.proyecto_final

import android.app.AlertDialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.proyecto_final.learning.GamificationHelper
import com.example.proyecto_final.learning.LearningContent
import com.example.proyecto_final.learning.LearningRepository
import com.example.proyectofinal_moviles.R

class LogrosActivity : ComponentActivity() {

    private lateinit var repository: LearningRepository
    private lateinit var database: ProgressDatabaseHelper
    private lateinit var allRewardsContainer: LinearLayout
    private lateinit var showAllButton: TextView
    private var allRewardsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logros)

        repository = LearningRepository(this)
        database = ProgressDatabaseHelper(this)
        allRewardsContainer = findViewById(R.id.layoutTodosLogros)
        showAllButton = findViewById(R.id.btnVerTodosLogros)

        findViewById<View>(R.id.btnVolverLogros).setOnClickListener { finish() }

        bindHeader()
        bindMainBadges()
        bindShowAll()
        renderAllRewards()
    }

    private fun bindHeader() {
        val profile = repository.getUserProfile()
        val nextXp = profile.xpForNextLevel
        val missingXp = (nextXp - profile.totalXp).coerceAtLeast(0)

        findViewById<TextView>(R.id.txtNivelNumero).text = profile.level.toString()
        findViewById<TextView>(R.id.txtRangoActual).text = profile.levelTitle
        findViewById<TextView>(R.id.txtProgresoNivel).text = "${profile.totalXp} XP acumulados"
        findViewById<ProgressBar>(R.id.progressNivel).progress = profile.xpProgressInLevel
        findViewById<TextView>(R.id.txtProximoRango).text = if (missingXp == 0) {
            "Maestro CyberLearn"
        } else {
            "Siguiente nivel"
        }
        findViewById<TextView>(R.id.txtMetaRango).text = if (missingXp == 0) {
            "Ya alcanzaste el rango más alto. Sigue completando retos para mantener tu progreso."
        } else {
            "Te faltan $missingXp XP para subir de nivel."
        }
    }

    private fun bindMainBadges() {
        findViewById<View>(R.id.badgePrincipiante).setOnClickListener {
            showRewardDialog(findAchievement("first_lesson"))
        }
        findViewById<View>(R.id.badgeAprendiz).setOnClickListener {
            showRewardDialog(findAchievement("first_quiz"))
        }
        findViewById<View>(R.id.badgeDefensor).setOnClickListener {
            showRewardDialog(findAchievement("three_modules"))
        }
        findViewById<View>(R.id.badgeAntiPhishing).setOnClickListener {
            showRewardDialog(findAchievement("phishing_detector"))
        }
        findViewById<View>(R.id.badgeRedes).setOnClickListener {
            showRewardDialog(findAchievement("wifi_defender"))
        }
        findViewById<View>(R.id.badgeMaestro).setOnClickListener {
            showRewardDialog(findAchievement("all_modules"))
        }
    }

    private fun bindShowAll() {
        showAllButton.setOnClickListener {
            allRewardsVisible = !allRewardsVisible
            allRewardsContainer.visibility = if (allRewardsVisible) View.VISIBLE else View.GONE
            showAllButton.text = if (allRewardsVisible) "Ocultar ˄" else "Ver todo ›"
            if (allRewardsVisible) renderAllRewards()
        }
    }

    private fun renderAllRewards() {
        allRewardsContainer.removeAllViews()

        val rewards = database.getBadgeDetails() + database.getAchievementDetails()
        rewards.forEach { reward ->
            allRewardsContainer.addView(createRewardRow(reward))
        }
    }

    private fun createRewardRow(reward: RewardRecord): View {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(14), dp(12), dp(14), dp(12))
            background = getDrawable(R.drawable.bg_logros_goal)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = dp(10)
            }
            isClickable = true
            isFocusable = true
            setOnClickListener { showRewardDialog(reward) }

            addView(TextView(this@LogrosActivity).apply {
                text = "${if (reward.unlocked) "Obtenida" else "Bloqueada"} · ${reward.name}"
                setTextColor(if (reward.unlocked) getColor(R.color.logros_azul_principal) else getColor(R.color.logros_texto_suave))
                textSize = 16f
                typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            })
            addView(TextView(this@LogrosActivity).apply {
                text = if (reward.unlocked) {
                    "Lograda el ${reward.unlockedAt ?: "día de tu avance"}"
                } else {
                    progressTextFor(reward)
                }
                setTextColor(getColor(R.color.logros_texto_suave))
                textSize = 14f
                setPadding(0, dp(4), 0, 0)
            })
        }
    }

    private fun showRewardDialog(reward: RewardRecord?) {
        if (reward == null) return
        registerBadgeTap()

        val message = if (reward.unlocked) {
            "${reward.description}\n\nFecha obtenida: ${reward.unlockedAt ?: "Sin fecha registrada"}"
        } else {
            "${reward.description}\n\nProgreso: ${progressTextFor(reward)}"
        }

        AlertDialog.Builder(this)
            .setTitle(reward.name)
            .setMessage(message)
            .setPositiveButton("Entendido", null)
            .show()

        renderAllRewards()
    }

    private fun registerBadgeTap() {
        val prefs = getSharedPreferences("logros_interactions", MODE_PRIVATE)
        val taps = prefs.getInt("badge_taps", 0) + 1
        prefs.edit().putInt("badge_taps", taps).apply()
        if (taps >= 5) {
            database.unlockAchievement("curious_tap")
        }
    }

    private fun findAchievement(id: String): RewardRecord? =
        database.getAchievementDetails().find { it.id == id }

    private fun progressTextFor(reward: RewardRecord): String {
        reward.moduleId?.let { moduleId ->
            val module = LearningContent.getModule(moduleId)
            val progress = repository.getModuleProgress(moduleId)
            return "${progress.lessonsCompleted}/${progress.totalLessons} lecciones del módulo ${module?.title ?: moduleId}"
        }

        val modulesCompleted = repository.getCompletedModulesCount()
        val lessonsCompleted = repository.getAllModuleProgress().sumOf { it.lessonsCompleted }
        val taps = getSharedPreferences("logros_interactions", MODE_PRIVATE).getInt("badge_taps", 0)

        return when (reward.id) {
            "first_lesson" -> "$lessonsCompleted/1 lecciones completadas"
            "first_quiz" -> "Aprueba tu primera evaluación"
            "three_modules" -> "$modulesCompleted/3 módulos completados"
            "all_modules" -> "$modulesCompleted/6 módulos completados"
            "perfect_quiz" -> "Obtén 10/10 en una evaluación"
            "password_guardian" -> moduleProgressText(1)
            "phishing_detector" -> moduleProgressText(2)
            "safe_browser" -> moduleProgressText(3)
            "data_keeper" -> moduleProgressText(4)
            "wifi_defender" -> moduleProgressText(5)
            "social_shield" -> moduleProgressText(6)
            "quiz_streak" -> "Aprueba 3 evaluaciones de módulos diferentes"
            "curious_tap" -> "$taps/5 insignias presionadas"
            "three_day_streak" -> "Completa actividades en 3 días diferentes"
            else -> "Sigue avanzando en módulos y evaluaciones"
        }
    }

    private fun moduleProgressText(moduleId: Int): String {
        val module = LearningContent.getModule(moduleId)
        val progress = repository.getModuleProgress(moduleId)
        return "${progress.lessonsCompleted}/${progress.totalLessons} lecciones de ${module?.title ?: "módulo $moduleId"}"
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
