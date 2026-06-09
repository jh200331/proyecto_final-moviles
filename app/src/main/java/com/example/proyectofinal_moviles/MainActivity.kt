package com.example.proyectofinal_moviles

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_final.EvaluacionesActivity
import com.example.proyecto_final.LogrosActivity
import com.example.proyecto_final.MiProgresoActivity
import com.example.proyecto_final.ModulosActivity
import com.example.proyecto_final.SimuladorActivity
import com.example.proyectofinal_moviles.ui.theme.ProyectoFinal_movilesTheme
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoFinal_movilesTheme {
                DashboardScreen()
            }
        }
    }
}

// Colores institucionales ESEIT
val EseitNavyColor = Color(0xFF0D1B3E)
val EseitRedColor = Color(0xFFD32F2F)
val EseitBackgroundColor = Color(0xFFF5F6F8)

@Composable
fun DashboardScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = EseitBackgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            HeaderSection()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                LevelCard()

                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = "¿Qué deseas aprender hoy?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = EseitNavyColor
                )

                Spacer(modifier = Modifier.height(16.dp))
                MenuGrid()
            }
        }
    }
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(EseitNavyColor)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 8.dp, vertical = 20.dp)
    ) {
        Text(
            text = "CyberLearn",
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Serif,
            fontSize = 21.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun LevelCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = EseitNavyColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(HexagonShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "4",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            fontSize = 23.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Nivel actual",
                            color = Color.White.copy(alpha = 0.7f),
                            fontFamily = FontFamily.Serif,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Defensor Digital",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            fontSize = 19.sp
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "XP Total",
                        color = Color.White.copy(alpha = 0.7f),
                        fontFamily = FontFamily.Serif,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "1.250",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        fontSize = 19.sp
                    )
                }
            }

            Column {
                LinearProgressIndicator(
                    progress = { 0.8f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    color = EseitRedColor,
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "XP para el siguiente nivel: 250",
                    color = Color.White.copy(alpha = 0.7f),
                    fontFamily = FontFamily.Serif,
                    fontSize = 13.sp
                )
            }
        }
    }
}

val HexagonShape = GenericShape { size, _ ->
    val radius = size.width / 2f
    val centerX = size.width / 2f
    val centerY = size.height / 2f
    for (i in 0 until 6) {
        val angleRad = Math.toRadians((i * 60 - 90).toDouble())
        val x = centerX + radius * cos(angleRad).toFloat()
        val y = centerY + radius * sin(angleRad).toFloat()
        if (i == 0) moveTo(x, y) else lineTo(x, y)
    }
    close()
}

@Composable
fun MenuGrid() {
    val context = LocalContext.current
    val items = listOf(
        MenuDataItem("Módulos", "6 módulos educativos", icon = Icons.Filled.MenuBook, activity = ModulosActivity::class.java),
        MenuDataItem("Evaluaciones", "Pon a prueba tus conocimientos", icon = Icons.Filled.Quiz, activity = EvaluacionesActivity::class.java),
        MenuDataItem("Simulador", "Entrena ante ataques reales", icon = Icons.Filled.VideogameAsset, activity = SimuladorActivity::class.java),
        MenuDataItem("Mi progreso", "Consulta tu avance", icon = Icons.Filled.TrendingUp, activity = MiProgresoActivity::class.java),
        MenuDataItem("Logros", "Desbloquea recompensas", icon = Icons.Filled.EmojiEvents, activity = LogrosActivity::class.java),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        items(items) { item ->
            MenuCardItem(item) {
                context.startActivity(Intent(context, item.activity))
            }
        }
    }
}

data class MenuDataItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector? = null,
    val imageRes: Int? = null,
    val activity: Class<*>
)

@Composable
fun MenuCardItem(item: MenuDataItem, onClick: () -> Unit) {
    val cardHeight = 140.dp
    val cardPadding = 12.dp
    val imageSize = 40.dp
    val imagePaddingTop = 5.dp
    val imagePaddingBottom = 2.dp
    val spaceBetweenImageAndText = 7.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(cardPadding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier.padding(top = imagePaddingTop, bottom = imagePaddingBottom),
                contentAlignment = Alignment.Center
            ) {
                if (item.imageRes != null) {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = item.title,
                        modifier = Modifier.size(imageSize).offset(x = (-1).dp)
                    )
                } else if (item.icon != null) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = EseitNavyColor,
                        modifier = Modifier.size(imageSize)
                    )
                }
            }

            Spacer(modifier = Modifier.height(spaceBetweenImageAndText))

            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 17.sp,
                    color = EseitNavyColor
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.subtitle,
                    fontFamily = FontFamily.Serif,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    lineHeight = 15.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    ProyectoFinal_movilesTheme {
        DashboardScreen()
    }
}
