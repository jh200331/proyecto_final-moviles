package com.example.proyecto_final

import android.graphics.Color
import android.graphics.Typeface
import android.widget.TextView
import com.example.proyecto_final.learning.LearningRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CertificateActivity : CyberBaseActivity() {

    override val screenTitle: String = "Certificado Digital"
    override val screenSubtitle: String = "CyberLearn - Programa de Ciberseguridad"

    override val showBottomMenu: Boolean = false

    override fun buildContent() {
        val repository = LearningRepository(this)
        val profile = repository.getUserProfile()
        val date = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es", "ES")).format(Date())

        if (!profile.certificateIssued) {
            card {
                addView(title("Certificado no disponible"))
                addView(paragraph("Completa los 6 módulos de aprendizaje para obtener tu certificado digital."))
                addView(paragraph("Progreso: ${repository.getCompletedModulesCount()}/6 módulos completados"))
            }
            addView(outlineButton("Volver a módulos") { finish() })
            return
        }

        gradientCard {
            addView(
                TextView(this@CertificateActivity).apply {
                    text = "🎓"
                    textSize = 48f
                    gravity = android.view.Gravity.CENTER
                    layoutParams = blockParams(bottom = 8)
                }
            )
            addView(whiteTitle("Certificado de Finalización"))
            addView(whiteParagraph("CyberLearn - Educación en Ciberseguridad"))
            addView(
                TextView(this@CertificateActivity).apply {
                    text = "Se certifica que el estudiante ha completado exitosamente los 6 módulos del programa de ciberseguridad CyberLearn, demostrando conocimientos en:"
                    setTextColor(Color.argb(220, 255, 255, 255))
                    textSize = 14f
                    setLineSpacing(4f, 1.1f)
                    layoutParams = blockParams(top = 12, bottom = 8)
                }
            )
            listOf(
                "Contraseñas Seguras", "Phishing", "Navegación Segura",
                "Protección de Datos", "Redes Wi-Fi Seguras", "Ingeniería Social"
            ).forEach { mod ->
                addView(whiteParagraph("✓ $mod"))
            }
        }

        card {
            addView(title("Detalles del certificado"))
            addView(paragraph("Nivel alcanzado: ${profile.level} - ${profile.levelTitle}"))
            addView(paragraph("Experiencia total: ${profile.totalXp} XP"))
            addView(paragraph("Insignias obtenidas: ${repository.getUnlockedBadgesCount()}/6"))
            addView(paragraph("Fecha de emisión: $date"))
            addView(
                TextView(this@CertificateActivity).apply {
                    text = "ID: CYBER-${profile.totalXp}-${System.currentTimeMillis().toString().takeLast(6)}"
                    setTextColor(accent)
                    textSize = 12f
                    typeface = Typeface.MONOSPACE
                    layoutParams = blockParams(top = 8)
                }
            )
        }

        addView(primaryButton("Volver a módulos") { finish() })
    }
}
