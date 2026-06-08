package com.example.proyecto_final

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto_final.learning.ModuleInfo
import com.example.proyecto_final.learning.ModuleProgress
import com.example.proyecto_final.learning.ModuleStatus
import com.google.android.material.button.MaterialButton
import com.example.proyectofinal_moviles.MainActivity
import com.example.proyectofinal_moviles.R

abstract class CyberBaseActivity : AppCompatActivity() {
    protected lateinit var content: LinearLayout

    protected val blue by lazy { ContextCompat.getColor(this, R.color.primary) }
    protected val blueDark by lazy { ContextCompat.getColor(this, R.color.primary_dark) }
    protected val accent by lazy { ContextCompat.getColor(this, R.color.accent) }
    protected val pageBg by lazy { ContextCompat.getColor(this, R.color.background) }
    protected val cardBg by lazy { ContextCompat.getColor(this, R.color.card_background) }
    protected val cardStroke by lazy { ContextCompat.getColor(this, R.color.card_stroke) }
    protected val textDark by lazy { ContextCompat.getColor(this, R.color.text_primary) }
    protected val textMuted by lazy { ContextCompat.getColor(this, R.color.text_muted) }
    protected val purpleAccent by lazy { ContextCompat.getColor(this, R.color.purple_accent) }
    protected val greenAccent by lazy { ContextCompat.getColor(this, R.color.green_accent) }

    abstract val screenTitle: String
    abstract val screenSubtitle: String
    open val showBottomMenu: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(pageBg)
        }

        val scroll = ScrollView(this).apply {
            isFillViewport = true
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f
            )
        }

        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(16), dp(18), dp(18))
        }

        scroll.addView(content)
        root.addView(scroll)

        if (showBottomMenu) {
            root.addView(bottomMenu())
        }

        setContentView(root)
        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        addHeader()
        buildContent()
    }

    abstract fun buildContent()

    protected fun addHeader() {
        content.addView(
            TextView(this).apply {
                text = screenTitle
                setTextColor(blueDark)
                textSize = 24f
                typeface = Typeface.DEFAULT_BOLD
                layoutParams = blockParams(bottom = 4)
            }
        )
        content.addView(
            TextView(this).apply {
                text = screenSubtitle
                setTextColor(textMuted)
                textSize = 14f
                layoutParams = blockParams(bottom = 16)
            }
        )
    }

    protected fun card(block: LinearLayout.() -> Unit) {
        val view = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(14), dp(16), dp(14))
            background = GradientDrawable().apply {
                setColor(cardBg)
                cornerRadius = dp(14).toFloat()
                setStroke(dp(1), cardStroke)
            }
            elevation = dp(3).toFloat()
            layoutParams = blockParams(bottom = 12)
        }
        view.block()
        content.addView(view)
    }

    protected fun gradientCard(block: LinearLayout.() -> Unit) {
        val view = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(16), dp(18), dp(16))
            background = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(
                    ContextCompat.getColor(this@CyberBaseActivity, R.color.gradient_start),
                    ContextCompat.getColor(this@CyberBaseActivity, R.color.gradient_end)
                )
            ).apply { cornerRadius = dp(16).toFloat() }
            elevation = dp(4).toFloat()
            layoutParams = blockParams(bottom = 14)
        }
        view.block()
        content.addView(view)
    }

    protected fun title(text: String): TextView = TextView(this).apply {
        this.text = text
        setTextColor(textDark)
        textSize = 18f
        typeface = Typeface.DEFAULT_BOLD
        layoutParams = blockParams(bottom = 6)
    }

    protected fun whiteTitle(text: String): TextView = TextView(this).apply {
        this.text = text
        setTextColor(Color.WHITE)
        textSize = 18f
        typeface = Typeface.DEFAULT_BOLD
        layoutParams = blockParams(bottom = 6)
    }

    protected fun whiteParagraph(text: String): TextView = TextView(this).apply {
        this.text = text
        setTextColor(Color.argb(200, 255, 255, 255))
        textSize = 14f
        setLineSpacing(2f, 1.08f)
        layoutParams = blockParams(bottom = 6)
    }

    protected fun paragraph(text: String): TextView = TextView(this).apply {
        this.text = text
        setTextColor(textMuted)
        textSize = 14f
        setLineSpacing(2f, 1.08f)
        layoutParams = blockParams(bottom = 8)
    }

    protected fun label(text: String): TextView = TextView(this).apply {
        this.text = text
        setTextColor(accent)
        textSize = 13f
        typeface = Typeface.DEFAULT_BOLD
        layoutParams = blockParams(bottom = 6)
    }

    protected fun sectionLabel(text: String): TextView = TextView(this).apply {
        this.text = text
        setTextColor(purpleAccent)
        textSize = 15f
        typeface = Typeface.DEFAULT_BOLD
        layoutParams = blockParams(top = 8, bottom = 4)
    }

    protected fun bullet(text: String): TextView = paragraph("• $text")

    protected fun statusChip(status: ModuleStatus): TextView {
        val (bg, fg, text) = when (status) {
            ModuleStatus.LOCKED -> Triple(Color.rgb(216, 222, 232), textMuted, "Bloqueado")
            ModuleStatus.IN_PROGRESS -> Triple(Color.argb(30, 11, 99, 246), accent, "En progreso")
            ModuleStatus.COMPLETED -> Triple(Color.argb(30, 0, 168, 107), greenAccent, "Completado")
        }
        return TextView(this).apply {
            this.text = text
            setTextColor(fg)
            textSize = 11f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(10), dp(4), dp(10), dp(4))
            background = GradientDrawable().apply {
                setColor(bg)
                cornerRadius = dp(20).toFloat()
            }
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

    protected fun primaryButton(text: String, action: () -> Unit): MaterialButton {
        return MaterialButton(this).apply {
            this.text = text
            isAllCaps = false
            cornerRadius = dp(12)
            setTextColor(Color.WHITE)
            setBackgroundColor(accent)
            layoutParams = blockParams(top = 6)
            setOnClickListener { action() }
        }
    }

    protected fun outlineButton(text: String, action: () -> Unit): MaterialButton {
        return MaterialButton(this).apply {
            this.text = text
            isAllCaps = false
            cornerRadius = dp(12)
            setTextColor(blue)
            setBackgroundColor(cardBg)
            strokeWidth = dp(1)
            strokeColor = android.content.res.ColorStateList.valueOf(blue)
            layoutParams = blockParams(top = 6)
            setOnClickListener { action() }
        }
    }

    protected fun progressLine(label: String, percent: Int, animate: Boolean = false): LinearLayout {
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = blockParams(bottom = 10)
        }
        container.addView(
            TextView(this).apply {
                text = "$label  $percent%"
                setTextColor(textDark)
                textSize = 13f
                typeface = Typeface.DEFAULT_BOLD
                layoutParams = blockParams(bottom = 4)
            }
        )
        val bar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
            max = 100
            progress = if (animate) 0 else percent
            progressDrawable?.setTint(accent)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(10)
            )
        }
        container.addView(bar)
        if (animate && percent > 0) {
            ObjectAnimator.ofInt(bar, "progress", 0, percent).apply {
                duration = 800
                start()
            }
        }
        return container
    }

    protected fun moduleCard(
        module: ModuleInfo,
        progress: ModuleProgress,
        onContinue: () -> Unit,
        onOpen: () -> Unit
    ) {
        card {
            val header = LinearLayout(this@CyberBaseActivity).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                layoutParams = blockParams(bottom = 8)
            }
            header.addView(
                ImageView(this@CyberBaseActivity).apply {
                    setImageResource(module.iconRes)
                    layoutParams = LinearLayout.LayoutParams(dp(44), dp(44)).apply {
                        marginEnd = dp(12)
                    }
                }
            )
            val info = LinearLayout(this@CyberBaseActivity).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }
            info.addView(title(module.title))
            info.addView(
                TextView(this@CyberBaseActivity).apply {
                    text = module.difficulty
                    setTextColor(purpleAccent)
                    textSize = 11f
                    typeface = Typeface.DEFAULT_BOLD
                }
            )
            header.addView(info)
            header.addView(statusChip(progress.status))
            addView(header)

            addView(paragraph(module.description))
            addView(
                TextView(this@CyberBaseActivity).apply {
                    text = "⏱ ${module.estimatedMinutes} min  •  ${progress.lessonsCompleted}/${progress.totalLessons} lecciones"
                    setTextColor(textMuted)
                    textSize = 12f
                    layoutParams = blockParams(bottom = 6)
                }
            )
            addView(progressLine("Progreso", progress.lessonPercent, animate = true))

            if (!progress.isUnlocked) {
                addView(
                    TextView(this@CyberBaseActivity).apply {
                        text = "🔒 Completa el módulo anterior al 100% para desbloquear"
                        setTextColor(textMuted)
                        textSize = 12f
                        layoutParams = blockParams(bottom = 4)
                    }
                )
            } else {
                val btnText = when {
                    progress.lessonPercent >= 100 -> "Repasar módulo"
                    progress.lessonsCompleted > 0 -> "Continuar aprendiendo"
                    else -> "Comenzar módulo"
                }
                addView(primaryButton(btnText) { onContinue() })
                addView(outlineButton("Ver detalles") { onOpen() })
            }
            setOnClickListener { if (progress.isUnlocked) onOpen() }
        }
    }

    protected fun addView(view: View) {
        content.addView(view)
    }

    protected fun moduleSpace(module: ModuleBase, owner: String, percent: Int, body: String) {
        card {
            addView(label("Módulo ${module.id} - Responsable: $owner"))
            addView(title(module.title))
            addView(paragraph(module.description))
            addView(progressLine("Avance", percent))
            addView(paragraph(body))
        }
    }

    protected fun openActivity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }

    protected fun openActivityWithExtra(clazz: Class<*>, key: String, value: Int) {
        startActivity(Intent(this, clazz).putExtra(key, value))
    }

    protected fun blockParams(top: Int = 0, bottom: Int = 0): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = dp(top)
            bottomMargin = dp(bottom)
        }
    }

    protected fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

    private fun bottomMenu(): View {
        val scroll = HorizontalScrollView(this).apply {
            setBackgroundColor(cardBg)
            isHorizontalScrollBarEnabled = false
            elevation = dp(8).toFloat()
        }
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(dp(8), dp(8), dp(8), dp(8))
        }

        val items = listOf(
            "🏠 Inicio" to MainActivity::class.java,
            "📚 Módulos" to ModulosActivity::class.java,
            "📝 Evaluaciones" to EvaluacionesActivity::class.java,
            "🎮 Simulador" to SimuladorActivity::class.java,
            "📈 Progreso" to MiProgresoActivity::class.java,
            "🏆 Logros" to LogrosActivity::class.java
        )

        items.forEach { (text, clazz) ->
            row.addView(
                MaterialButton(this).apply {
                    this.text = text
                    isAllCaps = false
                    textSize = 11f
                    cornerRadius = dp(10)
                    setTextColor(if (clazz == ModulosActivity::class.java) accent else blue)
                    setBackgroundColor(cardBg)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply { marginEnd = dp(4) }
                    setOnClickListener {
                        if (clazz != this@CyberBaseActivity::class.java) {
                            startActivity(Intent(this@CyberBaseActivity, clazz))
                        }
                    }
                }
            )
        }
        scroll.addView(row)
        return scroll
    }
}
