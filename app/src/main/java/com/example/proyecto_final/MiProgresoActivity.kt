package com.example.proyecto_final

class MiProgresoActivity : CyberBaseActivity() {
    override val screenTitle: String = "Mi progreso"
    override val screenSubtitle: String = "Datos conectados a SQLite local"

    override fun buildContent() {
        // Lukas: aqui se conecta la base de datos con modulo, evaluacion, simulacion y XP.
        val database = ProgressDatabaseHelper(this)
        val records = database.getProgress()
        val totalXp = records.sumOf { it.xp }
        val completedModules = records.count { it.modulePercent >= 100 }

        card {
            addView(title("Resumen general"))
            addView(paragraph("Modulos completados: $completedModules/6"))
            addView(paragraph("XP total: $totalXp"))
            addView(paragraph("La clase ProgressDatabaseHelper.kt tiene la tabla progress para guardar avance."))
        }

        records.forEach { record ->
            card {
                addView(label("Modulo ${record.moduleId}"))
                addView(title(record.moduleName))
                addView(progressLine("Modulo", record.modulePercent))
                addView(progressLine("Evaluacion", record.evaluationPercent))
                addView(progressLine("Simulador", record.simulationPercent))
                addView(paragraph("XP: ${record.xp}"))
            }
        }
    }
}
