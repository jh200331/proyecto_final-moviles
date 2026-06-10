package com.example.proyecto_final

class SimuladorActivity : CyberBaseActivity() {
    override val screenTitle: String = "Simulador"
    override val screenSubtitle: String = "Escenarios practicos por modulo"

    override fun buildContent() {
        // Iver: aqui van las simulaciones. Son 6 modulos con minimo 5 simulaciones cada uno.
        AppData.modules.forEach { module ->
            moduleSpace(
                module = module,
                owner = "Iver",
                percent = if (module.id == 1) 20 else 0,
                body = "Espacio para crear minimo 5 simulaciones del modulo ${module.id}.\n" +
                    "- Simulacion 1: escenario pendiente\n" +
                    "- Simulacion 2: escenario pendiente\n" +
                    "- Simulacion 3: escenario pendiente\n" +
                    "- Simulacion 4: escenario pendiente\n" +
                    "- Simulacion 5: escenario pendiente"
            )
        }
    }
}
