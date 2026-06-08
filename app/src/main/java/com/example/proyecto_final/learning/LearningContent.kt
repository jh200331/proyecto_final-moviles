package com.example.proyecto_final.learning

import com.example.proyecto_final.learning.content.ModuleLessons1to3
import com.example.proyecto_final.learning.content.ModuleLessons4to6
import com.example.proyecto_final.learning.content.ModuleQuizzes
import com.example.proyectofinal_moviles.R

object LearningContent {

    fun allModules(): List<ModuleInfo> = listOf(
        ModuleInfo(1, "Contraseñas Seguras",
            "Aprende a proteger tus cuentas mediante contraseñas robustas y métodos modernos de autenticación.",
            "Principiante", 45, R.drawable.ic_module_password,
            0xFF1A2F5C.toInt(), 0xFFD32F2F.toInt()),
        ModuleInfo(2, "Phishing",
            "Aprende a identificar correos, mensajes y sitios web fraudulentos.",
            "Principiante", 50, R.drawable.ic_module_phishing,
            0xFF0D1B3E.toInt(), 0xFFB71C1C.toInt()),
        ModuleInfo(3, "Navegación Segura",
            "Aprende a navegar de forma segura por internet evitando riesgos y amenazas.",
            "Intermedio", 45, R.drawable.ic_module_browser,
            0xFF1A2F5C.toInt(), 0xFF0D1B3E.toInt()),
        ModuleInfo(4, "Protección de Datos",
            "Aprende a proteger la información personal y evitar pérdidas de datos.",
            "Intermedio", 50, R.drawable.ic_module_data,
            0xFF0D1B3E.toInt(), 0xFFD32F2F.toInt()),
        ModuleInfo(5, "Redes Wi-Fi Seguras",
            "Aprende a proteger tu conexión doméstica y evitar accesos no autorizados.",
            "Intermedio", 40, R.drawable.ic_module_wifi,
            0xFF1A2F5C.toInt(), 0xFFB71C1C.toInt()),
        ModuleInfo(6, "Ingeniería Social",
            "Aprende cómo los atacantes manipulan psicológicamente a las personas para obtener información.",
            "Avanzado", 55, R.drawable.ic_module_social,
            0xFF0D1B3E.toInt(), 0xFFD32F2F.toInt())
    )

    fun allLessons(): List<Lesson> =
        ModuleLessons1to3.module1Lessons() +
            ModuleLessons1to3.module2Lessons() +
            ModuleLessons1to3.module3Lessons() +
            ModuleLessons4to6.module4Lessons() +
            ModuleLessons4to6.module5Lessons() +
            ModuleLessons4to6.module6Lessons()

    fun getLessons(moduleId: Int): List<Lesson> =
        allLessons().filter { it.moduleId == moduleId }.sortedBy { it.order }

    fun getLesson(lessonId: Int): Lesson? =
        allLessons().find { it.id == lessonId }

    fun getModule(moduleId: Int): ModuleInfo? =
        allModules().find { it.id == moduleId }

    fun getQuiz(moduleId: Int): List<QuizQuestion> =
        ModuleQuizzes.allQuizzes().filter { it.moduleId == moduleId }
}
