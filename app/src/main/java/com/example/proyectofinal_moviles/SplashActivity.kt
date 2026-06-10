package com.example.proyectofinal_moviles

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofinal_moviles.ui.theme.ProyectoFinal_movilesTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoFinal_movilesTheme {
                SplashScreen {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val eseitNavy = Color(0xFF0D1B3E)
    val eseitRed = Color(0xFFD32F2F)

    LaunchedEffect(Unit) {
        delay(3000) // 3 segundos de carga
        onTimeout()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.icon_cyberlearn),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "CyberLearn",
            fontFamily = FontFamily.Serif,
            fontSize = 29.sp,
            fontWeight = FontWeight.Bold,
            color = eseitNavy
        )

        Spacer(modifier = Modifier.height(40.dp))

        LinearProgressIndicator(
            modifier = Modifier
                .width(200.dp)
                .height(8.dp),
            color = eseitRed,
            trackColor = eseitNavy.copy(alpha = 0.15f)
        )
    }
}
