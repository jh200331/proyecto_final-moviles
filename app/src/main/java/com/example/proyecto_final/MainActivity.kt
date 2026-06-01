package com.example.proyecto_final

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var navigationBar: LinearLayout
    private lateinit var contentContainer: LinearLayout
    private var selectedSection = Section.INICIO
    private var quizScore = 0
    private var answeredQuestions = 0

    private val modules = listOf(
        LearningModule(
            "1. Diagnóstico y gestión del riesgo",
            "Métrica: identificar activos, amenazas, vulnerabilidades e impacto.",
            "El usuario aprende a reconocer qué información vale más, qué amenazas la pueden afectar y cómo priorizar controles.",
            listOf(
                "Inventario de activos: datos personales, contraseñas, equipos, redes y copias.",
                "Riesgo = probabilidad + impacto. Primero se atiende lo que más afecta al usuario.",
                "Controles: prevención, detección, respuesta y recuperación."
            ),
            "Actividad: clasifica tres activos de tu vida digital como riesgo bajo, medio o alto."
        ),
        LearningModule(
            "2. Protección de identidad",
            "Métrica: autenticación fuerte y buenas prácticas de acceso.",
            "Se explica el uso de contraseñas robustas, gestores de claves y doble factor de autenticación.",
            listOf(
                "Usa frases largas y únicas para cada servicio.",
                "Activa MFA/2FA en correo, bancos, redes sociales y plataformas educativas.",
                "No compartas códigos de verificación ni contraseñas por mensajería."
            ),
            "Actividad: crea una frase contraseña segura y revisa si tus cuentas críticas tienen 2FA."
        ),
        LearningModule(
            "3. Phishing e ingeniería social",
            "Métrica: concienciación, verificación y reporte de incidentes.",
            "El módulo muestra señales de correos, enlaces y mensajes fraudulentos para reducir errores humanos.",
            listOf(
                "Revisa remitente, urgencia exagerada, faltas de ortografía y enlaces acortados.",
                "No descargues adjuntos inesperados.",
                "Verifica por un canal oficial antes de entregar datos."
            ),
            "Actividad: analiza un mensaje sospechoso y marca sus señales de alerta."
        ),
        LearningModule(
            "4. Seguridad en dispositivos y redes",
            "Métrica: actualización, configuración segura y protección de comunicaciones.",
            "Incluye hábitos para móvil, computador, Wi-Fi, copias de seguridad y navegación segura.",
            listOf(
                "Actualiza sistema, apps y antivirus.",
                "Evita Wi-Fi público para trámites sensibles o usa VPN confiable.",
                "Realiza copias de seguridad periódicas y prueba que puedas restaurarlas."
            ),
            "Actividad: revisa permisos de tres apps instaladas y elimina los que no sean necesarios."
        )
    )

    private val schedule = listOf(
        ScheduleItem("20 mayo", "Investigación INCIBE y referentes de seguridad", "Todo el grupo"),
        ScheduleItem("21 mayo", "Diseño de anteproyecto, objetivo general y alcance", "Líder de documentación"),
        ScheduleItem("22 mayo", "Prototipo de pantallas y navegación", "Diseño UI/UX"),
        ScheduleItem("23-24 mayo", "Desarrollo de módulos educativos y quiz", "Desarrollo móvil"),
        ScheduleItem("25 mayo", "Pruebas funcionales y corrección de textos", "QA y contenidos"),
        ScheduleItem("26 mayo", "Diapositivas y piezas publicitarias", "Marketing del producto"),
        ScheduleItem("27 mayo", "Ensayo de sustentación grupal", "Todo el grupo"),
        ScheduleItem("28 mayo", "Entrega final y lanzamiento simulado", "Todo el grupo")
    )

    private val roles = listOf(
        RoleItem("Integrante 1", "Coordinación y anteproyecto", "Define problema, objetivos, justificación y alcance."),
        RoleItem("Integrante 2", "Desarrollo móvil", "Implementa pantallas, navegación, contenido y quiz."),
        RoleItem("Integrante 3", "Investigación y contenidos", "Resume métricas, buenas prácticas y referencias INCIBE."),
        RoleItem("Integrante 4", "Presentación y publicidad", "Crea diapositivas, afiche, pitch y material de lanzamiento.")
    )

    private val artifacts = listOf(
        "Documento tipo investigación: portada, resumen, problema, objetivos, marco teórico, metodología, cronograma, roles, conclusiones y referencias.",
        "Presentación: problema, solución, público objetivo, demo de la app, valor educativo y cierre comercial.",
        "Aplicación funcional: inicio, módulos educativos, quiz, cronograma, roles y materiales.",
        "Material publicitario: nombre del producto, eslogan, afiche, publicación para redes y guion de lanzamiento."
    )

    private val questions = listOf(
        Question(
            "¿Cuál es una práctica recomendada para proteger cuentas importantes?",
            listOf("Activar doble factor de autenticación", "Usar la misma clave en todo", "Compartir códigos por chat"),
            0
        ),
        Question(
            "¿Qué señal puede indicar phishing?",
            listOf("Mensaje urgente que pide datos personales", "URL oficial verificada", "Comunicación esperada por canal institucional"),
            0
        ),
        Question(
            "¿Para qué sirve una copia de seguridad?",
            listOf("Para recuperar información ante fallos o incidentes", "Para publicar contraseñas", "Para evitar actualizar el sistema"),
            0
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        navigationBar = findViewById(R.id.navigationBar)
        contentContainer = findViewById(R.id.contentContainer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        buildNavigation()
        showSection(Section.INICIO)
    }

    private fun buildNavigation() {
        navigationBar.removeAllViews()
        Section.entries.forEach { section ->
            navigationBar.addView(navButton(section))
        }
    }

    private fun navButton(section: Section): MaterialButton {
        return MaterialButton(this).apply {
            text = section.label
            isAllCaps = false
            cornerRadius = dp(18)
            setTextColor(if (section == selectedSection) Color.WHITE else tealDark)
            setBackgroundColor(if (section == selectedSection) teal else Color.WHITE)
            strokeWidth = dp(1)
            strokeColor = android.content.res.ColorStateList.valueOf(teal)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { marginEnd = dp(10) }
            setOnClickListener { showSection(section) }
        }
    }

    private fun showSection(section: Section) {
        selectedSection = section
        buildNavigation()
        contentContainer.removeAllViews()
        when (section) {
            Section.INICIO -> showHome()
            Section.MODULOS -> showModules()
            Section.QUIZ -> showQuiz()
            Section.PROYECTO -> showProject()
            Section.LANZAMIENTO -> showLaunch()
        }
    }

    private fun showHome() {
        addCard {
            addView(label("Producto educativo", amber, 14, true))
            addView(title("CiberAprende: Seguridad informática para todos"))
            addView(paragraph("Aplicación móvil informativa y educativa para aprender hábitos de ciberseguridad, tomar decisiones seguras y practicar con preguntas tipo quiz. La base está orientada a buenas prácticas promovidas por INCIBE y a los temas trabajados en seguridad informática."))
        }
        addCard {
            addView(title("Objetivo general"))
            addView(paragraph("Desarrollar una aplicación Android funcional que enseñe conceptos de seguridad informática mediante módulos cortos, ejemplos cotidianos, actividades prácticas y evaluación básica del aprendizaje."))
        }
        addCard {
            addView(title("Público objetivo"))
            addView(bullet("Estudiantes que inician en seguridad informática."))
            addView(bullet("Usuarios de móvil que desean proteger sus cuentas, datos y dispositivos."))
            addView(bullet("Docentes que requieren una herramienta sencilla para sensibilización."))
        }
        addPrimaryAction("Comenzar módulos") { showSection(Section.MODULOS) }
    }

    private fun showModules() {
        modules.forEach { module ->
            addCard {
                addView(label(module.metric, teal, 13, true))
                addView(title(module.title))
                addView(paragraph(module.description))
                module.lessons.forEach { addView(bullet(it)) }
                addView(label(module.activity, amberDark, 14, false))
            }
        }
    }

    private fun showQuiz() {
        quizScore = 0
        answeredQuestions = 0
        addCard {
            addView(title("Quiz de validación"))
            addView(paragraph("Responde estas preguntas para comprobar los conceptos básicos."))
        }
        questions.forEachIndexed { index, question ->
            addQuestionCard(index + 1, question)
        }
    }

    private fun addQuestionCard(number: Int, question: Question) {
        addCard {
            val feedback = paragraph("")
            addView(title("$number. ${question.prompt}"))
            question.options.forEachIndexed { optionIndex, option ->
                addView(MaterialButton(this@MainActivity).apply {
                    text = option
                    isAllCaps = false
                    cornerRadius = dp(12)
                    setTextColor(tealDark)
                    setBackgroundColor(Color.WHITE)
                    strokeWidth = dp(1)
                    strokeColor = android.content.res.ColorStateList.valueOf(teal)
                    layoutParams = blockParams(top = 8)
                    setOnClickListener {
                        if (feedback.text.isNotBlank()) return@setOnClickListener
                        answeredQuestions++
                        val correct = optionIndex == question.correctIndex
                        if (correct) quizScore++
                        feedback.text = if (correct) {
                            "Correcto. Puntaje: $quizScore/$answeredQuestions"
                        } else {
                            "Repasa el módulo. Respuesta correcta: ${question.options[question.correctIndex]}"
                        }
                        feedback.setTextColor(if (correct) teal else Color.rgb(185, 28, 28))
                    }
                })
            }
            addView(feedback)
        }
    }

    private fun showProject() {
        addCard {
            addView(title("Base del anteproyecto"))
            addView(bullet("Título: CiberAprende, aplicación móvil educativa sobre seguridad informática."))
            addView(bullet("Problema: muchos usuarios desconocen hábitos mínimos para proteger identidad, datos, dispositivos y redes."))
            addView(bullet("Justificación: la app apoya la sensibilización con contenidos breves, actividades y evaluación."))
            addView(bullet("Metodología: investigación aplicada, diseño de prototipo, desarrollo Android, pruebas y sustentación."))
        }
        addCard {
            addView(title("Cronograma de entrega"))
            schedule.forEach {
                addView(label("${it.date} - ${it.task}", tealDark, 15, true))
                addView(paragraph("Responsable: ${it.owner}"))
            }
        }
        addCard {
            addView(title("Integrantes y labores"))
            roles.forEach {
                addView(label("${it.name}: ${it.role}", amberDark, 15, true))
                addView(paragraph(it.description))
            }
        }
    }

    private fun showLaunch() {
        addCard {
            addView(title("Lanzamiento del producto"))
            addView(paragraph("Eslogan: Aprende, protege y comparte seguridad digital."))
            addView(bullet("Pitch: CiberAprende convierte la seguridad informática en hábitos claros para cualquier usuario."))
            addView(bullet("Diferencial: módulos cortos, actividades prácticas, enfoque preventivo y evaluación rápida."))
            addView(bullet("Demo sugerida: mostrar inicio, abrir un módulo, responder el quiz y cerrar con cronograma/roles."))
        }
        addCard {
            addView(title("Artefactos publicitarios"))
            artifacts.forEach { addView(bullet(it)) }
        }
        addCard {
            addView(title("Texto para afiche"))
            addView(paragraph("CiberAprende: tu guía móvil para reconocer riesgos, evitar fraudes y proteger tus datos. Ideal para estudiantes y usuarios que quieren navegar con más confianza."))
        }
    }

    private fun addPrimaryAction(text: String, action: () -> Unit) {
        contentContainer.addView(MaterialButton(this).apply {
            this.text = text
            isAllCaps = false
            cornerRadius = dp(16)
            textSize = 16f
            setTextColor(Color.WHITE)
            setBackgroundColor(teal)
            layoutParams = blockParams(top = 6)
            setOnClickListener { action() }
        })
    }

    private fun addCard(block: LinearLayout.() -> Unit) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(16), dp(16), dp(16))
            background = GradientDrawable().apply {
                color = android.content.res.ColorStateList.valueOf(Color.WHITE)
                cornerRadius = dp(8).toFloat()
                setStroke(dp(1), Color.rgb(226, 232, 240))
            }
            layoutParams = blockParams(bottom = 14)
        }
        card.block()
        contentContainer.addView(card)
    }

    private fun title(text: String) = TextView(this).apply {
        this.text = text
        setTextColor(tealDark)
        textSize = 20f
        typeface = Typeface.DEFAULT_BOLD
        layoutParams = blockParams(bottom = 8)
    }

    private fun paragraph(text: String) = TextView(this).apply {
        this.text = text
        setTextColor(Color.rgb(51, 65, 85))
        textSize = 15f
        setLineSpacing(2f, 1.08f)
        layoutParams = blockParams(bottom = 8)
    }

    private fun bullet(text: String) = paragraph("• $text")

    private fun label(text: String, color: Int, size: Int, bold: Boolean) = TextView(this).apply {
        this.text = text
        setTextColor(color)
        textSize = size.toFloat()
        if (bold) typeface = Typeface.DEFAULT_BOLD
        layoutParams = blockParams(bottom = 8)
    }

    private fun blockParams(top: Int = 0, bottom: Int = 0): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = dp(top)
            bottomMargin = dp(bottom)
        }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

    private enum class Section(val label: String) {
        INICIO("Inicio"),
        MODULOS("Módulos"),
        QUIZ("Quiz"),
        PROYECTO("Proyecto"),
        LANZAMIENTO("Lanzamiento")
    }

    private data class LearningModule(
        val title: String,
        val metric: String,
        val description: String,
        val lessons: List<String>,
        val activity: String
    )

    private data class ScheduleItem(val date: String, val task: String, val owner: String)
    private data class RoleItem(val name: String, val role: String, val description: String)
    private data class Question(val prompt: String, val options: List<String>, val correctIndex: Int)

    private companion object {
        val teal: Int = Color.rgb(17, 94, 89)
        val tealDark: Int = Color.rgb(15, 63, 59)
        val amber: Int = Color.rgb(245, 158, 11)
        val amberDark: Int = Color.rgb(180, 83, 9)
    }
}
