package com.example.proyecto_final

data class ModuleBase(
    val id: Int,
    val title: String,
    val description: String
)

object AppData {
    val modules = listOf(
        ModuleBase(1, "Contrasenas seguras", "Proteccion de cuentas, claves robustas y doble factor."),
        ModuleBase(2, "Phishing", "Deteccion de correos, enlaces y mensajes falsos."),
        ModuleBase(3, "Navegacion segura", "Habitos para navegar, descargar y comprar en linea."),
        ModuleBase(4, "Proteccion de datos", "Cuidado de datos personales, copias y privacidad."),
        ModuleBase(5, "Redes Wi-Fi seguras", "Buenas practicas en redes domesticas y publicas."),
        ModuleBase(6, "Ingenieria social", "Como evitar manipulacion y robo de informacion.")
    )
}
