package com.example.proyecto_final.learning

object GamificationHelper {

    const val XP_PER_LESSON = 100
    const val XP_PER_QUIZ_PASS = 250
    const val XP_PER_MODULE_COMPLETE = 500
    const val QUIZ_PASS_THRESHOLD = 7
    const val LESSONS_PER_MODULE = 5

    private val levelThresholds = listOf(
        0 to "Novato Digital",
        500 to "Aprendiz Seguro",
        1200 to "Explorador Cibernético",
        2200 to "Defensor Digital",
        3500 to "Guardián de Datos",
        5000 to "Experto en Ciberseguridad",
        7000 to "Maestro CyberLearn"
    )

    fun calculateLevel(totalXp: Int): Pair<Int, String> {
        var level = 1
        var title = levelThresholds.first().second
        for ((index, entry) in levelThresholds.withIndex()) {
            if (totalXp >= entry.first) {
                level = index + 1
                title = entry.second
            }
        }
        return level to title
    }

    fun xpForNextLevel(totalXp: Int): Int {
        val next = levelThresholds.firstOrNull { it.first > totalXp }
        return next?.first ?: (totalXp + 1000)
    }

    fun xpProgressInCurrentLevel(totalXp: Int): Int {
        val currentThreshold = levelThresholds.lastOrNull { it.first <= totalXp }?.first ?: 0
        val nextThreshold = xpForNextLevel(totalXp)
        val range = nextThreshold - currentThreshold
        if (range <= 0) return 100
        return ((totalXp - currentThreshold) * 100 / range).coerceIn(0, 100)
    }

    fun lessonPercent(completedLessons: Int): Int =
        (completedLessons * 100 / LESSONS_PER_MODULE).coerceIn(0, 100)

    fun motivationalMessage(context: MotivationContext): String = when (context) {
        MotivationContext.LESSON_COMPLETE -> listOf(
            "¡Excelente! Cada lección te hace más seguro.",
            "¡Bien hecho! Tu conocimiento crece paso a paso.",
            "¡Sigue así! La ciberseguridad es un hábito diario."
        ).random()
        MotivationContext.MODULE_COMPLETE -> listOf(
            "¡Módulo completado! Has demostrado gran compromiso.",
            "¡Felicidades! Eres un paso más cerca de ser un experto.",
            "¡Increíble! Tu dedicación marca la diferencia."
        ).random()
        MotivationContext.QUIZ_PASS -> listOf(
            "¡Cuestionario aprobado! Dominas estos conceptos.",
            "¡Excelente resultado! Tu preparación rinde frutos.",
            "¡Aprobado! Sigue reforzando lo aprendido."
        ).random()
        MotivationContext.ALL_MODULES_COMPLETE -> listOf(
            "¡Has completado todos los módulos! Eres un verdadero defensor digital.",
            "¡Certificado desbloqueado! Tu esfuerzo ha valido la pena.",
            "¡Logro máximo! CyberLearn está orgulloso de ti."
        ).random()
        MotivationContext.FIRST_LESSON -> "¡Buen comienzo! El primer paso es el más importante."
        MotivationContext.LOCKED_MODULE -> "Completa el módulo anterior al 100% para desbloquear este."
    }

    enum class MotivationContext {
        LESSON_COMPLETE, MODULE_COMPLETE, QUIZ_PASS, ALL_MODULES_COMPLETE, FIRST_LESSON, LOCKED_MODULE
    }
}
