package com.example.proyecto_final.learning.content

import com.example.proyecto_final.learning.Lesson

object ModuleLessons4to6 {

    fun module4Lessons() = listOf(
        Lesson(
            id = 401, moduleId = 4, order = 1,
            title = "Valorar la información personal",
            introduction = "En la era digital, tu información personal es uno de tus activos más valiosos. Comprender su valor es el primer paso para protegerla adecuadamente.",
            explanation = "Tu información personal incluye nombre, dirección, teléfono, correo, documentos de identidad, historial médico, datos financieros y patrones de comportamiento digital. Los cibercriminales monetizan estos datos en mercados clandestinos: un número de tarjeta de crédito puede venderse por pocos dólares, pero un perfil completo de identidad vale mucho más. Las empresas también recopilan tus datos para publicidad dirigida, creando perfiles detallados de tus preferencias y hábitos. El robo de identidad ocurre cuando alguien usa tu información para abrir cuentas, solicitar préstamos o cometer fraudes a tu nombre. Proteger tu información no es solo cuestión de privacidad, sino de seguridad financiera y reputacional. Piensa en tu información como dinero: no la dejas expuesta ni la compartes con desconocidos.",
            examples = "La filtración de Equifax en 2017 expuso datos de 147 millones de personas. Mercados dark web venden paquetes de identidad completa por $20-50. Robo de identidad en EE.UU. afectó a 15 millones de personas en 2021.",
            tips = "Minimiza datos compartidos online. Revisa informes de crédito periódicamente. Congela tu crédito si sospechas exposición.",
            summary = "Tu información personal tiene valor real para atacantes y empresas. Trátala como un activo que debes proteger activamente.",
            reflectionQuestion = "¿Qué información personal tuya podría ser más valiosa para un atacante?"
        ),
        Lesson(
            id = 402, moduleId = 4, order = 2,
            title = "Copias de seguridad",
            introduction = "Las copias de seguridad son tu red de seguridad contra pérdida de datos por ransomware, fallos de hardware, robo o accidentes. Sin backups, una pérdida de datos puede ser irreversible.",
            explanation = "La regla 3-2-1 es el estándar de oro: mantén 3 copias de tus datos, en 2 tipos de medios diferentes, con 1 copia fuera de tu ubicación (nube o almacenamiento externo en otro lugar). Realiza backups automáticos y regulares de documentos importantes, fotos y configuraciones. Las soluciones incluyen servicios en la nube (Google Drive, iCloud, OneDrive), discos duros externos y NAS. Prueba periódicamente que puedes restaurar tus datos desde el backup: un backup que no funciona no sirve. Protege tus backups con cifrado y contraseñas, especialmente si están en la nube. El ransomware moderno busca y cifra también los backups conectados, por eso la copia offline o en la nube con versionado es crucial.",
            examples = "Ransomware WannaCry dejó a miles sin acceso a sus archivos sin backups. Empresas que perdieron datos por no tener backups sufrieron pérdidas millonarias. Usuarios con backups en la nube recuperaron datos tras robo de dispositivos.",
            tips = "Automatiza backups semanales mínimo. Usa cifrado en backups. Prueba restauración cada 3 meses. Mantén una copia desconectada.",
            summary = "Los backups son esenciales contra pérdida de datos. Sigue la regla 3-2-1 y verifica que tus copias funcionan.",
            reflectionQuestion = "¿Tienes copias de seguridad de tus archivos importantes? ¿Cuándo fue la última vez que las actualizaste?"
        ),
        Lesson(
            id = 403, moduleId = 4, order = 3,
            title = "Revisar privacidad",
            introduction = "La configuración de privacidad en tus aplicaciones y servicios determina quién puede ver y usar tu información. Revisarla regularmente es una responsabilidad digital esencial.",
            explanation = "Cada red social, app y servicio tiene configuraciones de privacidad que controlan la visibilidad de tu perfil, publicaciones, ubicación y datos personales. Por defecto, muchas apps comparten más de lo necesario. Revisa y ajusta quién puede ver tus publicaciones, tu lista de contactos, tu ubicación y tu actividad. Desactiva el rastreo de ubicación en apps que no lo necesitan. Revisa los permisos de apps instaladas en tu teléfono y revoca los innecesarios. En redes sociales, limita la audiencia de publicaciones pasadas. Lee las actualizaciones de políticas de privacidad cuando las empresas las modifiquen. Usa herramientas como los paneles de privacidad de Google y Facebook para revisar qué datos tienen sobre ti.",
            examples = "Datos de Strava revelaron ubicación de bases militares. Configuraciones por defecto de Facebook exponían datos de millones. Apps de linterna que pedían acceso a contactos y ubicación.",
            tips = "Revisa privacidad cada 3 meses. Usa configuración de máxima privacidad por defecto. Desactiva etiquetado y ubicación innecesarios.",
            summary = "La privacidad se configura activamente. Revisa y ajusta regularmente quién puede acceder a tu información personal.",
            reflectionQuestion = "¿Cuándo fue la última vez que revisaste la configuración de privacidad de tus redes sociales?"
        ),
        Lesson(
            id = 404, moduleId = 4, order = 4,
            title = "Controlar permisos",
            introduction = "Las aplicaciones solicitan permisos para acceder a funciones de tu dispositivo. Otorgar permisos innecesarios expone tu información y privacidad innecesariamente.",
            explanation = "Cada app en tu teléfono puede solicitar acceso a cámara, micrófono, contactos, ubicación, almacenamiento, SMS y más. Antes de aceptar, pregúntate si la app realmente necesita ese permiso para funcionar. Una calculadora no necesita acceso a contactos; una linterna no necesita tu micrófono. En Android, puedes otorgar permisos solo mientras usas la app o denegarlos completamente. Revisa periódicamente los permisos otorgados en Configuración > Aplicaciones. En iOS, los indicadores de punto naranja (micrófono) y verde (cámara) muestran cuando están activos. Desinstala apps que soliciten permisos excesivos o que ya no uses. Los permisos de accesibilidad y administrador de dispositivo son especialmente sensibles y solo deben otorgarse a apps de total confianza.",
            examples = "Apps de belleza que accedían secretamente a la cámara. Juegos que leían SMS para fraude de facturación. Apps COVID falsas que robaban datos con permisos excesivos.",
            tips = "Revisa permisos al instalar y mensualmente. Usa 'solo al usar la app' cuando esté disponible. Desinstala apps no usadas.",
            summary = "Otorga solo los permisos estrictamente necesarios. Revisa y revoca permisos excesivos regularmente.",
            reflectionQuestion = "¿Sabes qué permisos tienen las apps instaladas en tu teléfono?"
        ),
        Lesson(
            id = 405, moduleId = 4, order = 5,
            title = "Proteger dispositivos",
            introduction = "Tus dispositivos físicos son la puerta de entrada a toda tu vida digital. Protegerlos física y digitalmente es fundamental para la seguridad de tus datos.",
            explanation = "Activa el bloqueo de pantalla con PIN de al menos 6 dígitos, patrón complejo o biometría. Habilita el cifrado completo del disco en tu dispositivo. Activa 'Buscar mi dispositivo' (Find My iPhone, Find My Device de Google) para localizar o borrar remotamente un dispositivo perdido o robado. Mantén el sistema operativo y apps actualizados. No dejes dispositivos desatendidos en público. Usa VPN en redes Wi-Fi públicas. Para dispositivos con información muy sensible, considera borrado remoto automático tras varios intentos fallidos de desbloqueo. Al vender o desechar un dispositivo, realiza un borrado de fábrica completo. Las fundas con bloqueo de RFID protegen tarjetas de contactless en tu cartera.",
            examples = "Dispositivos perdidos sin bloqueo expusieron datos corporativos. Remote wipe salvó datos de ejecutivos con laptops robadas. Teléfonos robados vendidos con datos personales accesibles.",
            tips = "Activa bloqueo automático a 30 segundos. Habilita borrado remoto. Cifra tu dispositivo. No uses PINs obvios como 1234.",
            summary = "Protege tus dispositivos con bloqueo fuerte, cifrado, actualizaciones y capacidad de borrado remoto.",
            reflectionQuestion = "¿Qué pasaría si perdieras tu teléfono hoy? ¿Está protegido adecuadamente?"
        )
    )

    fun module5Lessons() = listOf(
        Lesson(
            id = 501, moduleId = 5, order = 1,
            title = "Elegir una buena contraseña Wi-Fi",
            introduction = "La contraseña de tu red Wi-Fi doméstica protege toda tu red doméstica. Una contraseña débil permite que vecinos o atacantes accedan a tus dispositivos y datos.",
            explanation = "Tu contraseña Wi-Fi debe ser diferente de la contraseña de administrador del router y de cualquier otra contraseña que uses. Usa WPA2 o WPA3 con una clave de al menos 12 caracteres que combine letras, números y símbolos. Evita nombres de red (SSID) que revelen información personal como tu nombre o dirección. Desactiva WPS (Wi-Fi Protected Setup) ya que tiene vulnerabilidades conocidas que permiten ataques de fuerza bruta. No compartas tu contraseña Wi-Fi con todos los visitantes: crea una red de invitados separada si tu router lo permite. Cambia la contraseña Wi-Fi si sospechas que ha sido comprometida o si compartiste con personas que ya no necesitan acceso.",
            examples = "Vecinos que consumen ancho de banda con Wi-Fi sin proteger. Ataques a dispositivos IoT accediendo por Wi-Fi débil. Redes con contraseñas por defecto del fabricante hackeadas en minutos.",
            tips = "Usa WPA3 si tu router lo soporta. Crea red de invitados separada. Cambia contraseña por defecto del fabricante.",
            summary = "Una contraseña Wi-Fi fuerte con WPA2/WPA3 protege toda tu red doméstica. Desactiva WPS y usa redes de invitados.",
            reflectionQuestion = "¿Sabes qué tipo de seguridad Wi-Fi usa tu red doméstica y si la contraseña es segura?"
        ),
        Lesson(
            id = 502, moduleId = 5, order = 2,
            title = "Cambiar credenciales del router",
            introduction = "Los routers vienen con credenciales de administrador por defecto que son públicamente conocidas. Cambiarlas es uno de los pasos de seguridad más importantes y frecuentemente ignorados.",
            explanation = "Accede a la configuración de tu router (generalmente 192.168.1.1 o 192.168.0.1) y cambia el usuario y contraseña de administrador por defecto. Las credenciales por defecto como 'admin/admin' están en listas públicas que los atacantes usan automáticamente. También cambia la contraseña de la red Wi-Fi que viene impresa en la etiqueta del router. Usa credenciales únicas y fuertes para la administración del router. Desactiva la administración remota del router a menos que sea absolutamente necesaria. Si necesitas acceso remoto, usa contraseñas muy fuertes y considera cambiar el puerto predeterminado. Guarda las nuevas credenciales en tu gestor de contraseñas.",
            examples = "Botnets como Mirai comprometieron millones de routers con credenciales por defecto. Ataques a routers permitieron interceptar tráfico bancario. ISPs con routers mal configurados expusieron redes de clientes.",
            tips = "Cambia credenciales al instalar el router. Desactiva administración remota. Guarda credenciales en gestor de contraseñas.",
            summary = "Las credenciales por defecto del router son públicamente conocidas. Cámbialas inmediatamente al configurar tu red.",
            reflectionQuestion = "¿Has cambiado alguna vez las credenciales de administrador de tu router?"
        ),
        Lesson(
            id = 503, moduleId = 5, order = 3,
            title = "Actualizar el router",
            introduction = "El firmware de tu router, como cualquier software, contiene vulnerabilidades que los fabricantes corrigen con actualizaciones. Un router desactualizado es una puerta abierta para atacantes.",
            explanation = "Revisa periódicamente si hay actualizaciones de firmware para tu router en el sitio web del fabricante o en el panel de administración. Las actualizaciones corrigen vulnerabilidades de seguridad críticas que pueden permitir a atacantes tomar control de tu router, interceptar tráfico o usar tu red para ataques. Configura actualizaciones automáticas si tu router lo soporta. Si tu router ya no recibe actualizaciones del fabricante, considera reemplazarlo por uno moderno que sí las reciba. Un router comprometido puede redirigir tu tráfico a sitios maliciosos (DNS hijacking), interceptar comunicaciones y usar tu red para actividades ilegales sin tu conocimiento.",
            examples = "Vulnerabilidades en routers Netgear y TP-Link explotadas activamente. Botnets de routers infectados usados para ataques DDoS. Routers sin parches desde 2018 con vulnerabilidades conocidas.",
            tips = "Revisa actualizaciones cada 3 meses. Activa auto-actualización si está disponible. Reemplaza routers sin soporte.",
            summary = "Mantén el firmware de tu router actualizado para cerrar vulnerabilidades. Un router comprometido pone en riesgo toda tu red.",
            reflectionQuestion = "¿Sabes cuándo fue la última vez que se actualizó el firmware de tu router?"
        ),
        Lesson(
            id = 504, moduleId = 5, order = 4,
            title = "Usar WPA2 o WPA3",
            introduction = "El protocolo de seguridad Wi-Fi que uses determina qué tan protegida está tu red. Los protocolos antiguos como WEP y WPA son fácilmente vulnerables.",
            explanation = "WPA3 es el estándar más reciente y seguro, con protección contra ataques de diccionario y mayor privacidad en redes públicas. WPA2 sigue siendo aceptable si WPA3 no está disponible, pero nunca uses WEP o WPA original que son fácilmente crackeables. En el panel de tu router, selecciona WPA2-PSK (AES) como mínimo o WPA3 si está disponible. Evita WPA2-TKIP que es menos seguro. Para máxima seguridad en WPA2, usa una contraseña larga y compleja ya que las contraseñas cortas pueden ser atacadas con fuerza bruta incluso con WPA2. En redes públicas, asume que tu tráfico puede ser interceptado y usa VPN para datos sensibles.",
            examples = "WEP crackeado en menos de 5 minutos con herramientas gratuitas. KRACK attack explotó vulnerabilidades en WPA2 en 2017. WPA3 resuelve muchas debilidades de WPA2.",
            tips = "Usa WPA3 o WPA2-AES mínimo. Nunca uses WEP. En Wi-Fi público, siempre usa VPN.",
            summary = "Usa WPA2-AES como mínimo o WPA3 para máxima seguridad. Los protocolos antiguos dejan tu red vulnerable.",
            reflectionQuestion = "¿Sabes qué protocolo de seguridad Wi-Fi está configurado en tu red?"
        ),
        Lesson(
            id = 505, moduleId = 5, order = 5,
            title = "Revisar dispositivos conectados",
            introduction = "Conocer qué dispositivos están conectados a tu red Wi-Fi te permite detectar accesos no autorizados y mantener el control de tu red doméstica.",
            explanation = "Accede regularmente al panel de administración de tu router y revisa la lista de dispositivos conectados. Identifica cada dispositivo por su nombre o dirección MAC. Si ves dispositivos desconocidos, cambia inmediatamente tu contraseña Wi-Fi y revisa la seguridad del router. Algunos routers y apps permiten bloquear dispositivos específicos. Considera segmentar tu red: dispositivos IoT (cámaras, asistentes virtuales) en una red separada de tus computadoras y teléfonos, ya que los IoT frecuentemente tienen seguridad débil. Desactiva la transmisión del SSID solo como medida complementaria, no como única protección. Monitorea el ancho de banda inusual que podría indicar uso no autorizado.",
            examples = "Vecinos conectados a Wi-Fi sin proteger consumiendo ancho de banda. Dispositivos IoT comprometidos usados como punto de entrada a la red. Redes domésticas con 20+ dispositivos sin que el dueño lo sepa.",
            tips = "Revisa dispositivos conectados mensualmente. Usa red separada para IoT. Bloquea dispositivos desconocidos inmediatamente.",
            summary = "Monitorea regularmente los dispositivos en tu red. Accesos no autorizados deben detectarse y bloquearse rápidamente.",
            reflectionQuestion = "¿Sabes cuántos dispositivos están conectados actualmente a tu Wi-Fi?"
        )
    )

    fun module6Lessons() = listOf(
        Lesson(
            id = 601, moduleId = 6, order = 1,
            title = "No creer todo de inmediato",
            introduction = "La ingeniería social explota la confianza humana, no vulnerabilidades técnicas. La capacidad de cuestionar y verificar antes de actuar es tu mejor defensa.",
            explanation = "La ingeniería social es el arte de manipular personas para que revelen información confidencial o realicen acciones que comprometan la seguridad. Los atacantes explotan emociones como miedo, urgencia, curiosidad, codicia y deseo de ayudar. Ante cualquier solicitud inusual, pausa y aplica el escepticismo saludable: ¿es esto demasiado bueno para ser verdad? ¿por qué me contactan de esta forma? ¿puedo verificar esta información por otro canal? Los ataques pueden venir por teléfono, correo, mensaje, redes sociales o en persona. Los pretextos comunes incluyen: técnico de soporte, empleado de banco, familiar en apuros, oferta de empleo, premio ganado o autoridad gubernamental. La regla de oro: si algo genera presión para actuar rápido sin pensar, probablemente es ingeniería social.",
            examples = "Estafas del 'príncipe nigeriano' que siguen funcionando décadas después. Llamadas de 'Microsoft Support' pidiendo acceso remoto. Estafas románticas (catfishing) que duran meses para ganar confianza.",
            tips = "Pausa ante solicitudes urgentes. Verifica por canal alternativo. Desconfía de ofertas demasiado buenas.",
            summary = "La ingeniería social explota emociones y confianza. El escepticismo y la verificación son tus mejores defensas.",
            reflectionQuestion = "¿Alguna vez actuaste por impulso ante una solicitud que resultó sospechosa?"
        ),
        Lesson(
            id = 602, moduleId = 6, order = 2,
            title = "Confirmar identidades",
            introduction = "Verificar la identidad de quien te contacta antes de compartir información o realizar acciones es fundamental para prevenir la ingeniería social.",
            explanation = "Si alguien dice ser de tu banco, empresa de tecnología o institución gubernamental, no uses los datos de contacto que te proporcionan. Busca el número oficial en el sitio web de la organización y llama tú mismo. Para correos internos de trabajo, verifica solicitudes inusuales de dinero o información sensible directamente con la persona por teléfono o en persona. Los atacantes investigan a sus víctimas en LinkedIn y redes sociales para personalizar ataques convincentes. El vishing (phishing por voz) usa técnicas de manipulación psicológica sofisticadas. Las videollamadas deepfake emergen como nueva amenaza donde la imagen y voz de alguien conocido son falsificadas. Establece protocolos de verificación con familia y trabajo para solicitudes sensibles.",
            examples = "CEO fraud: atacantes impersonan ejecutivos pidiendo transferencias urgentes. Llamadas de 'banco' usando spoofing de número. Deepfake de voz usado para transferir $35 millones en Hong Kong.",
            tips = "Verifica identidad por canal independiente. Establece palabras clave con familia. Desconfía de llamadas no solicitadas.",
            summary = "Nunca confíes en la identidad presentada sin verificarla por un canal independiente y confiable.",
            reflectionQuestion = "¿Cómo verificarías la identidad de alguien que dice llamar de tu banco?"
        ),
        Lesson(
            id = 603, moduleId = 6, order = 3,
            title = "Proteger información privada",
            introduction = "La información que compartes voluntariamente es el combustible de la ingeniería social. Controlar qué revelas y a quién reduce significativamente tu vulnerabilidad.",
            explanation = "Los atacantes recopilan información de múltiples fuentes: redes sociales, bases de datos filtradas, sitios web públicos y conversaciones casuales. Cada dato que compartes puede parecer inofensivo, pero combinado con otros forma un perfil completo para ataques dirigidos. Evita publicar planes de viaje en tiempo real, ubicación habitual, información financiera, relaciones familiares detalladas o datos de empleo sensibles. En el trabajo, sigue las políticas de seguridad de la información y no discutas temas confidenciales en espacios públicos. Los dumpster diving (buscar en basura) y shoulder surfing siguen siendo técnicas efectivas. La limpieza de escritorio y pantalla son prácticas básicas pero importantes. Piensa antes de publicar: ¿esta información podría usarse en mi contra?",
            examples = "Publicaciones de vacaciones que alertan a ladrones. LinkedIn usado para spear phishing corporativo. Información de redes sociales usada para responder preguntas de seguridad.",
            tips = "Minimiza información pública en redes. No publiques planes de viaje en tiempo real. Usa bloqueo de pantalla siempre.",
            summary = "Limita la información que compartes públicamente. Los atacantes construyen perfiles detallados con datos aparentemente inofensivos.",
            reflectionQuestion = "¿Qué información personal compartes en redes sociales que un atacante podría usar?"
        ),
        Lesson(
            id = 604, moduleId = 6, order = 4,
            title = "Evitar actuar bajo presión",
            introduction = "La urgencia artificial es la herramienta más poderosa de la ingeniería social. Los atacantes crean presión para que actúes antes de pensar o verificar.",
            explanation = "Frases como 'actúa ahora o perderás acceso', 'oferta válida solo hoy', 'tu familiar está en peligro' o 'transferencia urgente antes de que cierre el banco' están diseñadas para desactivar tu pensamiento crítico. Cuando sientas presión para actuar inmediatamente, esa es la señal para detenerte. Las decisiones financieras, de seguridad o que involucren información sensible nunca deben tomarse bajo presión. Establece la regla personal de esperar al menos 24 horas antes de actuar en solicitudes inusuales. Si alguien dice que es una emergencia, verifica independientemente antes de enviar dinero o información. En el entorno laboral, las solicitudes urgentes de transferencias o cambios de datos deben verificarse con un segundo empleado según protocolos establecidos.",
            examples = "Estafas de 'secuestro virtual' pidiendo rescate inmediato. Urgencia en correos de phishing bancario. CEO fraud con plazos de 'antes del cierre del día'.",
            tips = "Nunca actúes bajo presión inmediata. Espera 24 horas en decisiones inusuales. Verifica emergencias llamando directamente.",
            summary = "La urgencia artificial es una señal de alerta. Detente, verifica y nunca actúes impulsivamente ante presión.",
            reflectionQuestion = "¿Has tomado alguna decisión apresurada online que luego lamentaste?"
        ),
        Lesson(
            id = 605, moduleId = 6, order = 5,
            title = "Reportar situaciones sospechosas",
            introduction = "Reportar intentos de ingeniería social protege no solo a ti, sino a tu comunidad y organización. Tu reporte puede prevenir que otros sean víctimas.",
            explanation = "Si recibes un intento de estafa, phishing o ingeniería social, repórtalo a las autoridades y plataformas correspondientes. En correo, usa la función de reportar phishing de tu proveedor. Reporta números de teléfono sospechosos a tu operador y autoridades locales. En el trabajo, sigue el protocolo de reporte de incidentes de seguridad de tu organización. Compartir experiencias con familiares y amigos los hace más conscientes. Plataformas como el FBI IC3, Action Fraud (UK) o la policía cibernética local aceptan reportes. Si fuiste víctima, reporta inmediatamente para intentar recuperar fondos y prevenir más daño. Documenta el incidente: capturas de pantalla, números de teléfono, direcciones de correo y conversaciones. No te avergüences de haber caído en un intento: los atacantes son profesionales y reportar ayuda a combatirlos.",
            examples = "Reportes colectivos que cerraron operaciones de call centers fraudulentos. Phishing reportado a Google que bloqueó dominios maliciosos. Comunidades que alertan sobre estafas locales en tiempo real.",
            tips = "Reporta phishing a tu proveedor de correo. Documenta incidentes con capturas. Comparte alertas con familia y trabajo.",
            summary = "Reportar intentos de ingeniería social protege a otros y ayuda a autoridades a combatir el cibercrimen.",
            reflectionQuestion = "¿Sabes a quién reportar un intento de estafa o ingeniería social en tu país?"
        )
    )
}
