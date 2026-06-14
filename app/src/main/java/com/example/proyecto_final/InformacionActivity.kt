package com.example.proyecto_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class InformacionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InformacionScreen(
                onBackPressed = {
                    finish()
                }
            )
        }
    }
}

private val NavyBlue = Color(0xFF0D1B3E)
private val CyberRed = Color(0xFFD32F2F)
private val BackgroundColor = Color(0xFFF5F6F8)

@Composable
fun InformacionScreen(
    onBackPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(NavyBlue)
                .padding(top = 50.dp, bottom = 20.dp)
        ) {

            IconButton(
                onClick = onBackPressed
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Text(
                text = "Información",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // SOBRE CYBERLEARN

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Sobre CyberLearn",
                        color = NavyBlue,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Divider(
                        color = CyberRed,
                        thickness = 3.dp,
                        modifier = Modifier.width(60.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "CyberLearn es una aplicación educativa diseñada para fortalecer los conocimientos en ciberseguridad mediante módulos interactivos, evaluaciones y simulaciones prácticas. Su objetivo es promover el aprendizaje de buenas prácticas digitales de forma sencilla, accesible y basada en información confiable.",
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExpandableSection(
                title = "Fuentes y referencias"
            ) {
                Text(
                    text = """
• Instituto Nacional de Ciberseguridad (INCIBE)

https://www.incibe.es

Todo el contenido educativo de CyberLearn ha sido elaborado tomando como referencia la documentación oficial, guías y materiales educativos publicados por INCIBE.
                    """.trimIndent()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ExpandableSection(
                title = "Preguntas frecuentes"
            ) {
                Text(
                    text =
                        """
¿Qué es CyberLearn?

Es una aplicación educativa enfocada en el aprendizaje de la ciberseguridad.

¿Cómo funciona el progreso?

El progreso se calcula mediante la experiencia (XP) obtenida al completar actividades.

¿Cómo desbloqueo logros?

Los logros se desbloquean al completar módulos, evaluaciones y simulaciones.

¿Necesito conexión a internet?

No, la aplicacion funciona correctamente sin la nesesidad de conexion a internet ya que los datos se guardan de manera local.
                    """.trimIndent()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ExpandableSection(
                title = "Créditos"
            ) {
                Text(
                    text =
                        """
CyberLearn fue desarrollado como proyecto académico para la asignatura de Seguridad Informática.

    Equipo de desarrollo

    • Josep Steven Huerfano ocampo
    • Jhonatan Steven Perez Ramos
    • Lukas Pardo Gomez
    • Jhonatan David Leal Cardona
    • Iver Andres Mosquera Contreras
    • Cesar Julian Pinto Daza

    Funciones:
    - Desarrollo de la aplicación móvil
    - Diseño de interfaz de usuario (UI/UX)
    - Implementación de módulos educativos
    - Desarrollo del sistema de evaluaciones
    - Implementación del simulador interactivo
    
     ─────────────────────

    Equipo de documentacion

    • Lauren Juliana Perez Pedraza
    • Isis Andrea Cohis Perez
    
    

    Funciones:
    - Elaboración de documentación técnica
    - Redacción del anteproyecto
    - Desarrollo de informes y evidencias
    - Validación de contenidos educativos
    
    ─────────────────────


    Institución:
    Escuela Superior de Empresa, Ingeniería y Tecnología (ESEIT)

Aplicación desarrollada con fines educativos.
                    """.trimIndent()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Versión de la aplicación",
                        color = NavyBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Versión actual: 1.0.0")

                    Text("Compilación: Release")

                    Text("Última actualización: Junio 2026")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun ExpandableSection(
    title: String,
    content: @Composable () -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = title,
                    color = NavyBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif
                )

                IconButton(
                    onClick = {
                        expanded = !expanded
                    }
                ) {

                    Icon(
                        imageVector =
                            if (expanded)
                                Icons.Default.ExpandLess
                            else
                                Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = CyberRed
                    )
                }
            }

            AnimatedVisibility(expanded) {

                Column(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                ) {
                    content()
                }
            }
        }
    }
}