package com.example.proyecto_final

class EvaluacionesActivity : CyberBaseActivity() {
    override val screenTitle: String = "Evaluaciones"
    override val screenSubtitle: String = "Preguntas y porcentaje por modulo"

    override fun buildContent() {
        // Jhonatan: aqui van las evaluaciones. Minimo 4 evaluaciones por cada modulo.
        AppData.modules.forEach { module ->
            moduleSpace(
                module = module,
                owner = "Jhonatan",
                percent = if (module.id == 1) 25 else 0,
                body = "Espacio para crear minimo 4 preguntas/evaluaciones del modulo ${module.id}.\n" +
                    "- Evaluacion 1: pregunta pendiente\n" +
                    "- Evaluacion 2: pregunta pendiente\n" +
                    "- Evaluacion 3: pregunta pendiente\n" +
                    "- Evaluacion 4: pregunta pendiente"
            )
        }
    }
}
