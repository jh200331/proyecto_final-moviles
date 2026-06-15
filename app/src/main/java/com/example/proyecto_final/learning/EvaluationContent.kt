package com.example.proyecto_final.learning

data class LessonEvaluationQuestion(
    val id: Int,
    val lessonId: Int,
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class LessonEvaluation(
    val lessonId: Int,
    val moduleId: Int,
    val lessonOrder: Int,
    val title: String,
    val questions: List<LessonEvaluationQuestion>
)

data class SimulationScenario(
    val id: Int,
    val moduleId: Int,
    val lessonId: Int,
    val title: String,
    val situation: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

object EvaluationContent {

    fun allEvaluations(): List<LessonEvaluation> =
        LearningContent.allLessons().map(::evaluationFor)

    fun evaluationsForModule(moduleId: Int): List<LessonEvaluation> =
        allEvaluations().filter { it.moduleId == moduleId }.sortedBy { it.lessonOrder }

    fun evaluationForLesson(lessonId: Int): LessonEvaluation? =
        LearningContent.getLesson(lessonId)?.let(::evaluationFor)

    fun scenariosForModule(moduleId: Int): List<SimulationScenario> =
        LearningContent.getLessons(moduleId).map(::scenarioFor)

    fun scenarioById(scenarioId: Int): SimulationScenario? =
        LearningContent.allLessons()
            .map(::scenarioFor)
            .find { it.id == scenarioId }

    private fun evaluationFor(lesson: Lesson): LessonEvaluation {
        val otherLessons = LearningContent.getLessons(lesson.moduleId).filter { it.id != lesson.id }
        val summaryDistractors = otherLessons.map { short(it.summary) }.take(3)
        val introDistractors = otherLessons.map { short(it.introduction) }.take(3)

        val questions = listOf(
            question(
                lesson,
                1,
                "Cual afirmacion resume mejor la leccion ${lesson.order}: ${lesson.title}?",
                short(lesson.summary),
                summaryDistractors,
                "El resumen de la leccion concentra el concepto principal que debes recordar."
            ),
            question(
                lesson,
                2,
                "Que practica deberias aplicar despues de estudiar esta leccion?",
                short(lesson.tips),
                listOf(
                    "Ignorar la recomendacion hasta que ocurra un incidente.",
                    "Compartir informacion sensible para comprobar si existe riesgo.",
                    "Desactivar los controles de seguridad para trabajar mas rapido."
                ),
                "Los consejos practicos convierten el contenido de la leccion en una accion concreta."
            ),
            question(
                lesson,
                3,
                "Cual concepto corresponde a la explicacion presentada?",
                short(lesson.explanation),
                introDistractors,
                "Esta opcion coincide con la explicacion tecnica de la leccion."
            ),
            // Punto editable 1: reemplaza esta pregunta si el contenido de la leccion cambia.
            question(
                lesson,
                4,
                "En un caso real relacionado con ${lesson.title}, que deberias analizar primero?",
                short(lesson.examples),
                listOf(
                    "Actuar de inmediato sin verificar el contexto.",
                    "Confiar solo en la apariencia del mensaje o servicio.",
                    "Entregar datos personales antes de confirmar la identidad."
                ),
                "Los ejemplos reales ayudan a reconocer el riesgo antes de tomar una decision."
            ),
            // Punto editable 2: puede personalizarse con una pregunta creada por el equipo.
            question(
                lesson,
                5,
                lesson.reflectionQuestion,
                "Aplicar lo aprendido, verificar la situacion y elegir la alternativa mas segura.",
                listOf(
                    "Responder por impulso y revisar despues.",
                    "Omitir las medidas de seguridad si la solicitud parece urgente.",
                    "Delegar la decision sin compartir el contexto ni comprobar la fuente."
                ),
                "La mejor respuesta combina analisis, verificacion y una accion preventiva."
            )
        )

        return LessonEvaluation(
            lessonId = lesson.id,
            moduleId = lesson.moduleId,
            lessonOrder = lesson.order,
            title = lesson.title,
            questions = questions
        )
    }

    private fun question(
        lesson: Lesson,
        order: Int,
        prompt: String,
        correct: String,
        distractors: List<String>,
        explanation: String
    ): LessonEvaluationQuestion {
        val normalizedDistractors = distractors
            .filter { it.isNotBlank() && it != correct }
            .distinct()
            .toMutableList()

        val fallbackDistractors = listOf(
            "Esta opcion contradice las recomendaciones de la leccion.",
            "Esta accion aumenta el riesgo y evita la verificacion.",
            "Esta respuesta no aplica el concepto de seguridad estudiado."
        )
        fallbackDistractors.forEach { fallback ->
            if (normalizedDistractors.size < 3 && fallback != correct && fallback !in normalizedDistractors) {
                normalizedDistractors += fallback
            }
        }

        val indexed = (listOf(correct) + normalizedDistractors.take(3))
            .mapIndexed { index, option -> index to option }
            .shuffled(java.util.Random((lesson.id * 10L) + order))

        return LessonEvaluationQuestion(
            id = lesson.id * 10 + order,
            lessonId = lesson.id,
            prompt = prompt,
            options = indexed.map { it.second },
            correctIndex = indexed.indexOfFirst { it.first == 0 },
            explanation = explanation
        )
    }

    private fun scenarioFor(lesson: Lesson): SimulationScenario {
        val options = listOf(
            "Detenerse, verificar la fuente y aplicar la recomendacion de seguridad.",
            "Continuar sin verificar porque la situacion parece normal.",
            "Compartir datos para resolver el problema mas rapido."
        )
        return SimulationScenario(
            id = 10_000 + lesson.id,
            moduleId = lesson.moduleId,
            lessonId = lesson.id,
            title = "Escenario ${lesson.order}: ${lesson.title}",
            situation = short(lesson.examples, 260),
            options = options,
            correctIndex = 0,
            explanation = "La respuesta segura es verificar primero y aplicar los controles estudiados en la leccion."
        )
    }

    private fun short(text: String, maxLength: Int = 150): String {
        val clean = text.replace("\n", " ").replace(Regex("\\s+"), " ").trim()
        val sentence = clean.substringBefore(". ").trim().ifBlank { clean }
        return if (sentence.length <= maxLength) sentence else sentence.take(maxLength - 3).trimEnd() + "..."
    }
}
