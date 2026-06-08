package com.example.proyecto_final

class ModulosActivity : CyberBaseActivity() {
    override val screenTitle: String = "Modulos"
    override val screenSubtitle: String = "Base para 6 modulos educativos"

    override fun buildContent() {
        // Leal: aqui van los 6 modulos. Cada modulo debe tener minimo 5 lecciones y porcentaje.
        AppData.modules.forEach { module ->
            moduleSpace(
                module = module,
                owner = "Leal",
                percent = if (module.id == 1) 20 else 0,
                body = "Espacio para desarrollar minimo 5 lecciones del modulo ${module.id}.\n" +
                    "- Leccion 1: pendiente\n" +
                    "- Leccion 2: pendiente\n" +
                    "- Leccion 3: pendiente\n" +
                    "- Leccion 4: pendiente\n" +
                    "- Leccion 5: pendiente"
            )
        }
    }
}
