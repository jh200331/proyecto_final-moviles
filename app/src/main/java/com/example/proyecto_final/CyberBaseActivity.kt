package com.example.proyecto_final

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.example.proyectofinal_moviles.R

abstract class CyberBaseActivity : AppCompatActivity() {
    protected lateinit var content: LinearLayout

    protected val blue by lazy { ContextCompat.getColor(this, R.color.primary) }
    protected val blueDark by lazy { ContextCompat.getColor(this, R.color.primary_dark) }
    protected val accent by lazy { ContextCompat.getColor(this, R.color.accent) }
    protected val pageBg by lazy { ContextCompat.getColor(this, R.color.background) }
    protected val textDark: Int = Color.rgb(21, 38, 64)
    protected val textMuted: Int = Color.rgb(90, 105, 128)

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
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
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
                setColor(Color.WHITE)
                cornerRadius = dp(12).toFloat()
                setStroke(dp(1), Color.rgb(221, 231, 245))
            }
            layoutParams = blockParams(bottom = 12)
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

    protected fun bullet(text: String): TextView = paragraph("- $text")

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
            setBackgroundColor(Color.WHITE)
            strokeWidth = dp(1)
            strokeColor = android.content.res.ColorStateList.valueOf(blue)
            layoutParams = blockParams(top = 6)
            setOnClickListener { action() }
        }
    }

    protected fun progressLine(label: String, percent: Int): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = blockParams(bottom = 10)
            addView(
                TextView(this@CyberBaseActivity).apply {
                    text = "$label  $percent%"
                    setTextColor(textDark)
                    textSize = 13f
                    typeface = Typeface.DEFAULT_BOLD
                    layoutParams = blockParams(bottom = 4)
                }
            )
            addView(
                ProgressBar(
                    this@CyberBaseActivity,
                    null,
                    android.R.attr.progressBarStyleHorizontal
                ).apply {
                    max = 100
                    progress = percent
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(8)
                    )
                }
            )
        }
    }

    protected fun moduleSpace(module: ModuleBase, owner: String, percent: Int, body: String) {
        card {
            addView(label("Modulo ${module.id} - Responsable: $owner"))
            addView(title(module.title))
            addView(paragraph(module.description))
            addView(progressLine("Avance", percent))
            addView(paragraph(body))
        }
    }

    protected fun openActivity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
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
            setBackgroundColor(Color.WHITE)
            isHorizontalScrollBarEnabled = false
        }
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(dp(8), dp(8), dp(8), dp(8))
        }

        val items = listOf(
            "Inicio" to InicioActivity::class.java,
            "Aprender" to ModulosActivity::class.java,
            "Evaluaciones" to EvaluacionesActivity::class.java,
            "Simulador" to SimuladorActivity::class.java,
            "Progreso" to MiProgresoActivity::class.java,
            "Logros" to LogrosActivity::class.java
        )

        items.forEach { (text, clazz) ->
            row.addView(
                MaterialButton(this).apply {
                    this.text = text
                    isAllCaps = false
                    textSize = 12f
                    cornerRadius = dp(10)
                    setTextColor(blue)
                    setBackgroundColor(Color.WHITE)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply { marginEnd = dp(6) }
                    setOnClickListener { openActivity(clazz) }
                }
            )
        }
        scroll.addView(row)
        return scroll
    }
}
