package com.example.proyecto_final

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.example.proyecto_final.learning.LearningContent
import com.example.proyecto_final.learning.LearningRepository
import com.example.proyectofinal_moviles.R
import com.example.proyecto_final.learning.GamificationHelper

class LogrosActivity : ComponentActivity() {

    private lateinit var repository: LearningRepository
    private lateinit var database: ProgressDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logros)

        repository = LearningRepository(this)
        database = ProgressDatabaseHelper(this)

        findViewById<View>(R.id.btnVolverLogros).setOnClickListener { finish() }

        bindHeader()
        bindMainBadges()
        bindShowAllButton()
    }

    // ──────────────────────────────────────────────
    // HEADER: nivel, rango, XP y barra de progreso
    // ──────────────────────────────────────────────
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
        refreshProgress()
    }

    override fun onResume() {
        super.onResume()
        refreshProgress()
    }

    private fun refreshProgress() {
        val database = ProgressDatabaseHelper(this)
        val totalXp = database.getTotalXp()
        val (level, title) = database.getUserLevel()
        val completedModules = database.getProgress().count {
            it.modulePercent >= 100 && it.evaluationPercent >= 100 && it.simulationPercent >= 100
        }

        findViewById<TextView>(R.id.txtNivelNumero)?.text = level.toString()
        findViewById<TextView>(R.id.txtRangoActual)?.text = title
        findViewById<TextView>(R.id.txtModulosCompletados)?.text = "$completedModules de 6 modulos dominados"
        findViewById<ProgressBar>(R.id.progressNivel)?.progress =
            GamificationHelper.xpProgressInCurrentLevel(totalXp)
    }

    // ──────────────────────────────────────────────
    // INSIGNIAS PRINCIPALES (las 6 del XML)
    // Se actualizan visualmente según estado real
    // ──────────────────────────────────────────────
    private fun bindMainBadges() {
        // Mapa badgeView → achievementId
        val badgeMap = mapOf(
            R.id.badgePrincipiante to "first_lesson",
            R.id.badgeAprendiz     to "first_quiz",
            R.id.badgeDefensor     to "three_modules",
            R.id.badgeAntiPhishing to "phishing_detector",
            R.id.badgeRedes        to "wifi_defender",
            R.id.badgeMaestro      to "all_modules"
        )

        val allAchievements = database.getAchievementDetails()

        badgeMap.forEach { (viewId, achievementId) ->
            val badgeView = findViewById<View>(viewId)
            val reward = allAchievements.find { it.id == achievementId }

            // Si está desbloqueada, aplicar apariencia "completada"
            if (reward?.unlocked == true) {
                badgeView.alpha = 1f
                // Busca el candado dentro del badge y ocúltalo si existe
                badgeView.findViewWithTag<View>("lock_icon")?.visibility = View.GONE
            } else {
                badgeView.alpha = 0.45f
            }

            badgeView.setOnClickListener {
                registerBadgeTap()
                showRewardDialog(reward)
            }
        }
    }

    // ──────────────────────────────────────────────
    // BOTÓN "Ver todo" → abre pantalla emergente
    // ──────────────────────────────────────────────
    private fun bindShowAllButton() {
        findViewById<TextView>(R.id.btnVerTodosLogros).setOnClickListener {
            showAllBadgesDialog()
        }
    }

    // ──────────────────────────────────────────────
    // DIÁLOGO CON TODAS LAS INSIGNIAS EN GRID
    // Desbloqueadas primero (con color), luego bloqueadas (gris + candado)
    // ──────────────────────────────────────────────
    private fun showAllBadgesDialog() {
        val dialog = Dialog(this, android.R.style.Theme_Material_Light_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(createBadgesDialogView(dialog))
        dialog.window?.apply {
            setLayout(
                (resources.displayMetrics.widthPixels * 0.95).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawableResource(android.R.color.transparent)
        }
        dialog.show()
    }

    private fun createBadgesDialogView(dialog: Dialog): View {
        val allBadges   = database.getBadgeDetails()
        val allAchieve  = database.getAchievementDetails()
        val allRewards  = (allBadges + allAchieve)
            .sortedWith(compareByDescending<RewardRecord> { it.unlocked }.thenBy { it.name })

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
            setPadding(dp(16), dp(16), dp(16), dp(16))
            // Esquinas redondeadas via background programático
        }

        // ── Cabecera del dialog ──
        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dp(12) }
        }
        header.addView(TextView(this).apply {
            text = "Todas las insignias"
            textSize = 18f
            setTextColor(ContextCompat.getColor(this@LogrosActivity, R.color.logros_texto))
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        })
        header.addView(TextView(this).apply {
            text = "✕"
            textSize = 18f
            setTextColor(ContextCompat.getColor(this@LogrosActivity, R.color.logros_texto_suave))
            setPadding(dp(8), 0, 0, 0)
            setOnClickListener { dialog.dismiss() }
        })
        root.addView(header)

        // Separador
        root.addView(View(this).apply {
            setBackgroundColor(ContextCompat.getColor(this@LogrosActivity, R.color.logros_borde))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(1)
            ).apply { bottomMargin = dp(12) }
        })

        // ── Sección obtenidas ──
        val unlockedList = allRewards.filter { it.unlocked }
        if (unlockedList.isNotEmpty()) {
            root.addView(sectionLabel("Insignias obtenidas ✓"))
            root.addView(buildBadgeGrid(unlockedList, unlocked = true, dialog = dialog))
            root.addView(View(this).apply {
                setBackgroundColor(ContextCompat.getColor(this@LogrosActivity, R.color.logros_borde))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, dp(1)
                ).apply { topMargin = dp(8); bottomMargin = dp(8) }
            })
        }

        // ── Sección bloqueadas ──
        val lockedList = allRewards.filter { !it.unlocked }
        if (lockedList.isNotEmpty()) {
            root.addView(sectionLabel("Insignias bloqueadas 🔒"))
            root.addView(buildBadgeGrid(lockedList, unlocked = false, dialog = dialog))
        }

        // Envuelve en ScrollView
        val scrollView = ScrollView(this).apply {
            addView(root)
        }
        val wrapper = FrameLayout(this).apply {
            // Fondo blanco con bordes redondeados simulado
            setBackgroundResource(R.drawable.bg_logros_goal)
            addView(scrollView)
        }
        return wrapper
    }

    private fun sectionLabel(text: String): TextView = TextView(this).apply {
        this.text = text
        textSize = 14f
        setTextColor(ContextCompat.getColor(this@LogrosActivity, R.color.logros_texto_suave))
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { bottomMargin = dp(8) }
    }

    /**
     * Construye una cuadrícula de 3 columnas con las insignias.
     * Desbloqueadas → fondo azul translúcido + ícono a color.
     * Bloqueadas    → fondo gris translúcido + ícono en gris + candado encima.
     */
    private fun buildBadgeGrid(rewards: List<RewardRecord>, unlocked: Boolean, dialog: Dialog): LinearLayout {
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dp(4) }
        }

        // Dividir en filas de 3
        rewards.chunked(3).forEach { row ->
            val rowLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = dp(10) }
            }
            row.forEach { reward ->
                rowLayout.addView(createBadgeCell(reward, unlocked, dialog))
            }
            // Rellenar celdas vacías para mantener el grid
            repeat(3 - row.size) {
                rowLayout.addView(View(this).apply {
                    layoutParams = LinearLayout.LayoutParams(0, 1, 1f)
                })
            }
            container.addView(rowLayout)
        }
        return container
    }

    private fun createBadgeCell(reward: RewardRecord, unlocked: Boolean, dialog: Dialog): LinearLayout {
        val iconRes = badgeIconFor(reward.id)
        val bgColorRes = if (unlocked) R.color.logros_azul_principal else R.color.logros_borde

        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(6), dp(10), dp(6), dp(8))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            isClickable = true
            isFocusable = true

            setOnClickListener {
                registerBadgeTap()
                showRewardDialog(reward)
            }

            // Hexágono con ícono
            val hexFrame = FrameLayout(this@LogrosActivity).apply {
                layoutParams = LinearLayout.LayoutParams(dp(60), dp(60))
            }

            // Fondo hexagonal
            val hexBg = ImageView(this@LogrosActivity).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                setImageResource(
                    if (unlocked) R.drawable.badge_hex_gold else R.drawable.badge_hex_locked
                )
                if (!unlocked) {
                    setColorFilter(
                        ContextCompat.getColor(this@LogrosActivity, android.R.color.darker_gray)
                    )
                }
            }
            hexFrame.addView(hexBg)

            // Ícono de la insignia
            val icon = ImageView(this@LogrosActivity).apply {
                layoutParams = FrameLayout.LayoutParams(dp(28), dp(28)).apply {
                    gravity = Gravity.CENTER
                }
                setImageResource(iconRes)
                if (!unlocked) {
                    setColorFilter(Color.GRAY)
                } else {
                    colorFilter = null
                }
            }
            hexFrame.addView(icon)

            // Candado encima si está bloqueada
            if (!unlocked) {
                val lock = ImageView(this@LogrosActivity).apply {
                    layoutParams = FrameLayout.LayoutParams(dp(18), dp(18)).apply {
                        gravity = Gravity.BOTTOM or Gravity.END
                        bottomMargin = dp(2)
                        rightMargin = dp(2)
                    }
                    setImageResource(R.drawable.ic_badge_lock)
                    setColorFilter(Color.DKGRAY)
                }
                hexFrame.addView(lock)
            }

            addView(hexFrame)

            // Nombre de la insignia
            addView(TextView(this@LogrosActivity).apply {
                text = reward.name
                textSize = 11f
                gravity = Gravity.CENTER
                setTextColor(
                    if (unlocked)
                        ContextCompat.getColor(this@LogrosActivity, R.color.logros_azul_principal)
                    else
                        ContextCompat.getColor(this@LogrosActivity, R.color.logros_texto_suave)
                )
                typeface = if (unlocked) Typeface.create(Typeface.SERIF, Typeface.BOLD)
                else Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { topMargin = dp(6) }
            })

            // Tick de completado si está desbloqueada
            if (unlocked) {
                addView(TextView(this@LogrosActivity).apply {
                    text = "✓ Obtenida"
                    textSize = 10f
                    gravity = Gravity.CENTER
                    setTextColor(ContextCompat.getColor(this@LogrosActivity, R.color.logros_azul_principal))
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply { topMargin = dp(2) }
                })
            }
        }
    }

    /** Devuelve el drawable del ícono según el id del logro */
    private fun badgeIconFor(id: String): Int = when (id) {
        "password_guardian"  -> R.drawable.ic_badge_shield
        "phishing_detector"  -> R.drawable.ic_badge_shield
        "safe_browser"       -> R.drawable.ic_badge_shield
        "data_keeper"        -> R.drawable.ic_badge_shield
        "wifi_defender"      -> R.drawable.ic_badge_shield
        "social_shield"      -> R.drawable.ic_badge_shield
        "first_lesson"       -> R.drawable.ic_badge_person
        "first_quiz"         -> R.drawable.ic_badge_cap
        "three_modules"      -> R.drawable.ic_badge_shield
        "all_modules"        -> R.drawable.ic_badge_cap
        "perfect_quiz"       -> R.drawable.ic_badge_cap
        "quiz_streak"        -> R.drawable.ic_badge_cap
        "curious_tap"        -> R.drawable.ic_badge_person
        "three_day_streak"   -> R.drawable.ic_badge_person
        else                 -> R.drawable.ic_badge_shield
    }

    // ──────────────────────────────────────────────
    // DIÁLOGO DE DETALLE DE UNA INSIGNIA
    // ──────────────────────────────────────────────
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
    }

    // ──────────────────────────────────────────────
    // REGISTRO DE TAPS (para logro "curious_tap")
    // ──────────────────────────────────────────────
    private fun registerBadgeTap() {
        val prefs = getSharedPreferences("logros_interactions", MODE_PRIVATE)
        val taps = prefs.getInt("badge_taps", 0) + 1
        prefs.edit().putInt("badge_taps", taps).apply()
        if (taps >= 5) {
            database.unlockAchievement("curious_tap")
        }
    }

    // ──────────────────────────────────────────────
    // TEXTO DE PROGRESO PARA INSIGNIAS BLOQUEADAS
    // ──────────────────────────────────────────────
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
            "first_lesson"      -> "$lessonsCompleted/1 lecciones completadas"
            "first_quiz"        -> "Aprueba tu primera evaluación"
            "three_modules"     -> "$modulesCompleted/3 módulos completados"
            "all_modules"       -> "$modulesCompleted/6 módulos completados"
            "perfect_quiz"      -> "Obtén 10/10 en una evaluación"
            "password_guardian" -> moduleProgressText(1)
            "phishing_detector" -> moduleProgressText(2)
            "safe_browser"      -> moduleProgressText(3)
            "data_keeper"       -> moduleProgressText(4)
            "wifi_defender"     -> moduleProgressText(5)
            "social_shield"     -> moduleProgressText(6)
            "quiz_streak"       -> "Aprueba 3 evaluaciones de módulos diferentes"
            "curious_tap"       -> "$taps/5 insignias presionadas"
            "three_day_streak"  -> "Completa actividades en 3 días diferentes"
            else                -> "Sigue avanzando en módulos y evaluaciones"
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