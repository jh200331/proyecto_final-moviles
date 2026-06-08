package com.example.proyecto_final.learning.content

import com.example.proyecto_final.learning.Lesson

object ModuleLessons1to3 {

    fun module1Lessons() = listOf(
        Lesson(
            id = 101, moduleId = 1, order = 1,
            title = "Crear buenas contraseñas",
            introduction = "Las contraseñas son la primera línea de defensa de tus cuentas digitales. Una contraseña débil puede ser adivinada en segundos por programas automatizados, mientras que una contraseña robusta puede tardar años en ser descifrada.",
            explanation = "Una contraseña segura debe tener al menos 12 caracteres e incluir una combinación de letras mayúsculas, minúsculas, números y símbolos especiales. Evita usar información personal como tu nombre, fecha de nacimiento o nombres de mascotas, ya que los atacantes pueden obtener estos datos de redes sociales. La entropía —la aleatoriedad de tu contraseña— es clave: cuanto más impredecible sea, más difícil será de crackear. Los gestores de contraseñas como Bitwarden, 1Password o el gestor integrado de Google pueden generar y almacenar contraseñas únicas y complejas de forma segura. Nunca uses secuencias obvias como '123456', 'password' o 'qwerty', que aparecen en las listas de contraseñas más hackeadas del mundo. Considera usar frases de contraseña (passphrases): combina varias palabras aleatorias con números y símbolos, por ejemplo 'Café#Montaña\$Río42', que son fáciles de recordar pero difíciles de adivinar.",
            examples = "En 2023, millones de cuentas fueron comprometidas porque los usuarios usaban '123456' como contraseña. En contraste, una contraseña como 'K9#mR\$vL2@pX7' requeriría miles de años para ser descifrada con métodos actuales. El caso de LinkedIn en 2012 expuso 6.5 millones de contraseñas hasheadas débiles que fueron descifradas rápidamente.",
            tips = "Usa un gestor de contraseñas para generar claves únicas. Activa alertas de filtraciones de datos en tu navegador. Cambia contraseñas por defecto de dispositivos IoT. Nunca compartas contraseñas por mensaje de texto o correo.",
            summary = "Una contraseña segura combina longitud, complejidad y aleatoriedad. Los gestores de contraseñas son tu mejor aliado para mantener claves únicas sin memorizarlas todas.",
            reflectionQuestion = "¿Cuántas de tus cuentas actuales usan la misma contraseña o una variación de ella?"
        ),
        Lesson(
            id = 102, moduleId = 1, order = 2,
            title = "No usar la misma contraseña en todo",
            introduction = "Reutilizar contraseñas es uno de los errores más comunes y peligrosos en ciberseguridad. Si un servicio es hackeado, todos tus demás servicios quedan expuestos.",
            explanation = "El ataque conocido como 'credential stuffing' consiste en usar credenciales filtradas de un sitio para intentar acceder a otros servicios. Los atacantes automatizan millones de intentos de inicio de sesión con bases de datos de contraseñas robadas. Si usas la misma contraseña en tu correo, banco y redes sociales, un solo hackeo compromete todo tu ecosistema digital. Cada cuenta debe tener una contraseña única e independiente. Prioriza la unicidad en cuentas críticas: correo electrónico (es la llave maestra para recuperar otras cuentas), banca en línea, almacenamiento en la nube y redes sociales principales. Los gestores de contraseñas resuelven el problema de la memoria: solo necesitas recordar una contraseña maestra fuerte para acceder a todas las demás.",
            examples = "En el hackeo de Dropbox de 2012, se filtraron 68 millones de credenciales. Usuarios que reutilizaban esas contraseñas en otros servicios sufrieron accesos no autorizados a múltiples plataformas. El caso de Canva en 2019 afectó a 137 millones de usuarios con el mismo patrón de reutilización.",
            tips = "Audita tus contraseñas con herramientas como 'Have I Been Pwned'. Prioriza cambiar contraseñas duplicadas en cuentas críticas primero. Usa un gestor de contraseñas con función de detección de duplicados.",
            summary = "Cada cuenta merece su propia contraseña única. La reutilización multiplica el daño de cualquier filtración de datos.",
            reflectionQuestion = "¿Sabes cuántas cuentas diferentes tienes y si comparten contraseñas?"
        ),
        Lesson(
            id = 103, moduleId = 1, order = 3,
            title = "Mantenerlas en privado",
            introduction = "Una contraseña compartida deja de ser secreta. Proteger la confidencialidad de tus credenciales es tan importante como crear contraseñas fuertes.",
            explanation = "Nunca compartas tus contraseñas con nadie, ni siquiera con amigos cercanos, familiares o compañeros de trabajo. Las empresas legítimas nunca te pedirán tu contraseña por teléfono, correo o mensaje. Si alguien te la solicita, es casi seguro un intento de fraude. Evita escribir contraseñas en papel pegado al monitor, en notas del teléfono sin cifrar o en archivos de texto sin protección. Cuando uses computadoras públicas o compartidas, nunca guardes contraseñas en el navegador y siempre cierra sesión al terminar. Ten cuidado con las personas que miran tu pantalla o teclado ('shoulder surfing') al escribir contraseñas en espacios públicos. Si necesitas compartir acceso a un servicio con alguien, usa funciones de cuentas compartidas o invitaciones que no requieran revelar tu contraseña personal.",
            examples = "Estafas telefónicas donde falsos técnicos de Microsoft piden contraseñas para 'reparar' computadoras. Empleados que comparten credenciales de empresa y luego uno de ellos es despedido con acceso activo. Familiares que comparten contraseñas de streaming y uno de ellos cae en un phishing.",
            tips = "Usa privacidad de pantalla en dispositivos móviles. Activa el bloqueo automático del dispositivo. Nunca fotografíes tus contraseñas. Reporta inmediatamente si sospechas que alguien conoce tu contraseña.",
            summary = "Tu contraseña es personal e intransferible. Mantenerla privada previene accesos no autorizados incluso con contraseñas fuertes.",
            reflectionQuestion = "¿Alguna vez has compartido una contraseña o la has escrito en un lugar visible?"
        ),
        Lesson(
            id = 104, moduleId = 1, order = 4,
            title = "Cambiarlas si hay dudas",
            introduction = "Saber cuándo cambiar una contraseña es una habilidad esencial. No siempre es necesario cambiarlas periódicamente, pero hay situaciones donde es urgente.",
            explanation = "Debes cambiar tu contraseña inmediatamente si: recibes una alerta de filtración de datos, sospechas que alguien accedió a tu cuenta, usaste una computadora pública o compartida, o si la contraseña es débil y antigua. Las recomendaciones modernas ya no exigen cambios periódicos forzados cada 90 días si la contraseña es fuerte y única, ya que esto puede llevar a crear contraseñas más débiles. Sin embargo, ante cualquier incidente de seguridad, el cambio es obligatorio. Al cambiar una contraseña, asegúrate de que la nueva sea completamente diferente, no una variación menor de la anterior. Revisa también las sesiones activas en tus cuentas después de un cambio y cierra las que no reconozcas. Si tu correo fue comprometido, cambia primero esa contraseña y luego las de todos los servicios vinculados.",
            examples = "Tras la filtración masiva de Yahoo en 2013-2014 que afectó 3 mil millones de cuentas, usuarios que no cambiaron contraseñas sufrieron accesos años después. Alertas de Google sobre contraseñas filtradas han ayudado a millones a cambiar credenciales a tiempo.",
            tips = "Suscríbete a alertas de seguridad de tus servicios principales. Usa haveibeenpwned.com para verificar filtraciones. Cambia también las preguntas de seguridad si fueron expuestas.",
            summary = "Cambia contraseñas ante filtraciones, accesos sospechosos o uso en dispositivos no confiables. Una contraseña comprometida nunca debe reutilizarse.",
            reflectionQuestion = "¿Cuándo fue la última vez que cambiaste las contraseñas de tus cuentas más importantes?"
        ),
        Lesson(
            id = 105, moduleId = 1, order = 5,
            title = "Activar la verificación en dos pasos",
            introduction = "La autenticación de dos factores (2FA) añade una capa extra de seguridad más allá de tu contraseña. Incluso si alguien obtiene tu contraseña, no podrá acceder sin el segundo factor.",
            explanation = "La verificación en dos pasos requiere dos elementos de autenticación: algo que sabes (contraseña) y algo que tienes (teléfono, llave de seguridad) o algo que eres (huella dactilar). Los métodos más comunes son códigos SMS, aplicaciones autenticadoras (Google Authenticator, Microsoft Authenticator, Authy) y llaves de seguridad físicas (YubiKey). Las apps autenticadoras son más seguras que SMS porque los códigos SMS pueden ser interceptados mediante SIM swapping. Activa 2FA en todas tus cuentas críticas: correo, banca, redes sociales y almacenamiento en la nube. Guarda los códigos de recuperación en un lugar seguro por si pierdes acceso a tu dispositivo de autenticación. La autenticación biométrica (huella, rostro) en tu dispositivo añade otra capa de protección local.",
            examples = "Google reportó que la 2FA bloquea el 99.9% de los ataques automatizados de credential stuffing. Usuarios de Twitter sin 2FA sufrieron secuestros de cuentas de alto perfil. La NSA recomienda llaves de seguridad hardware como el método más robusto.",
            tips = "Prioriza apps autenticadoras sobre SMS. Guarda códigos de respaldo offline. Activa 2FA en el correo primero, ya que protege la recuperación de otras cuentas.",
            summary = "La verificación en dos pasos es la medida más efectiva para proteger cuentas más allá de las contraseñas. Actívala hoy en tus servicios críticos.",
            reflectionQuestion = "¿En cuáles de tus cuentas importantes tienes activada la verificación en dos pasos?"
        )
    )

    fun module2Lessons() = listOf(
        Lesson(
            id = 201, moduleId = 2, order = 1,
            title = "Cuidado con mensajes sospechosos",
            introduction = "El phishing es el ciberataque más común del mundo. Los atacantes envían millones de mensajes falsos diariamente buscando engañar a las víctimas para robar información o dinero.",
            explanation = "Los mensajes de phishing imitan comunicaciones legítimas de bancos, empresas de tecnología, servicios de entrega o instituciones gubernamentales. Suelen crear urgencia con frases como 'Tu cuenta será bloqueada en 24 horas' o 'Has ganado un premio'. Errores ortográficos, saludos genéricos ('Estimado cliente'), remitentes extraños y solicitudes inusuales son señales de alerta. El phishing por SMS (smishing) y por llamada (vishing) son variantes igualmente peligrosas. Los atacantes investigan a sus víctimas en redes sociales para personalizar ataques (spear phishing). Nunca actúes impulsivamente ante un mensaje alarmante: tómate un momento para verificar su autenticidad contactando directamente a la empresa por canales oficiales.",
            examples = "En 2020, un ataque de phishing a empleados de Twitter comprometió cuentas de Barack Obama y Elon Musk. Correos falsos de 'Netflix' piden actualizar datos de pago y roban tarjetas. Mensajes de WhatsApp de 'familiares' pidiendo transferencias urgentes son estafas comunes en Latinoamérica.",
            tips = "Desconfía de la urgencia artificial. Verifica remitentes con atención. No hagas clic en enlaces de mensajes sospechosos. Reporta phishing a tu proveedor de correo.",
            summary = "Los mensajes de phishing explotan la urgencia y la confianza. Siempre verifica antes de actuar ante comunicaciones inesperadas.",
            reflectionQuestion = "¿Has recibido algún mensaje que te pareció sospechoso? ¿Cómo reaccionaste?"
        ),
        Lesson(
            id = 202, moduleId = 2, order = 2,
            title = "Revisar los enlaces",
            introduction = "Los enlaces maliciosos son la puerta de entrada principal del phishing. Saber inspeccionar URLs antes de hacer clic puede salvarte de innumerables ataques.",
            explanation = "Antes de hacer clic en cualquier enlace, pasa el cursor sobre él (en computadora) o mantén presionado (en móvil) para ver la URL real. Los atacantes usan dominios similares: 'g00gle.com', 'paypa1.com', 'arnazon.com'. Verifica que el dominio sea exactamente el correcto. Los enlaces acortados (bit.ly, tinyurl) ocultan el destino real: desconfía de ellos en mensajes no solicitados. En correos, un texto visible puede decir 'www.banco.com' pero el enlace real apunta a otro sitio: siempre verifica la URL real. Usa herramientas como VirusTotal para analizar enlaces sospechosos. Los sitios de phishing a menudo usan HTTP en lugar de HTTPS, aunque HTTPS ya no garantiza legitimidad por sí solo.",
            examples = "Enlaces en correos de 'COVID-19' en 2020 distribuyeron ransomware. Dominios como 'microsft.com' o 'app1e.com' engañaron a miles. El ataque a DNC en 2016 comenzó con un enlace de phishing en un correo de Gmail falso.",
            tips = "Escribe URLs manualmente en el navegador para sitios importantes. Usa marcadores para sitios frecuentes. Instala extensiones anti-phishing en tu navegador.",
            summary = "Inspeccionar enlaces antes de hacer clic es una habilidad fundamental. La URL real siempre revela la verdadera intención del enlace.",
            reflectionQuestion = "¿Sabes cómo ver la URL real de un enlace antes de abrirlo en tu dispositivo?"
        ),
        Lesson(
            id = 203, moduleId = 2, order = 3,
            title = "No entregar información personal",
            introduction = "Tu información personal es valiosa para los atacantes. Nunca debes proporcionarla en respuesta a solicitudes no solicitadas, sin importar qué tan legítimas parezcan.",
            explanation = "Los atacantes buscan nombres completos, números de identificación, datos bancarios, contraseñas, códigos de verificación y respuestas a preguntas de seguridad. Las empresas legítimas nunca solicitan contraseñas ni códigos 2FA por correo o teléfono. Los códigos de verificación que recibes en tu teléfono son solo para ti: si alguien te los pide, es una estafa. El pretexting consiste en inventar escenarios creíbles para extraer información. Formularios web falsos imitan perfectamente páginas de login de bancos o servicios populares. Antes de ingresar datos personales en cualquier sitio, verifica la URL y busca el candado HTTPS. Limita la información que compartes en redes sociales, ya que los atacantes la usan para personalizar ataques.",
            examples = "Estafas de 'soporte técnico' que piden acceso remoto y datos bancarios. Formularios falsos de Hacienda/SAT en época de declaraciones. Mensajes pidiendo códigos de WhatsApp para 'verificar identidad'.",
            tips = "Nunca compartas códigos OTP/2FA. Verifica solicitudes llamando al número oficial de la empresa. Usa datos mínimos en formularios web.",
            summary = "Tu información personal no se comparte por solicitud. Las empresas reales nunca piden contraseñas ni códigos de verificación por mensaje.",
            reflectionQuestion = "¿Qué información personal has compartido en internet que podría ser usada en tu contra?"
        ),
        Lesson(
            id = 204, moduleId = 2, order = 4,
            title = "Verificar remitentes",
            introduction = "Identificar remitentes falsos es crucial para detectar phishing. Los atacantes son expertos en falsificar identidades de personas y organizaciones de confianza.",
            explanation = "Revisa cuidadosamente la dirección de correo del remitente, no solo el nombre mostrado. 'Banco Nacional <seguridad@banco-falso.com>' muestra un nombre legítimo pero un dominio falso. Busca errores sutiles en dominios: guiones extra, extensiones inusuales (.xyz, .tk), o subdominios engañosos ('banco.com.falso.net'). En mensajes de texto, verifica números desconocidos y desconfía de mensajes de números cortos no reconocidos. Para correos internos de trabajo, confirma solicitudes inusuales directamente con el supuesto remitente por otro canal. Los atacantes comprometen cuentas reales para enviar phishing desde direcciones legítimas, por lo que mensajes inusuales de contactos conocidos también deben verificarse.",
            examples = "Correos de 'ceo@empresa.com' que en realidad vienen de 'ceo@empresa-secure.com'. Phishing del IRS/US IRS usando dominios similares a hacienda. Cuentas de Gmail comprometidas enviando phishing a contactos.",
            tips = "Configura filtros SPF/DKIM si administras correo. Marca como spam correos sospechosos. Contacta directamente a la empresa ante dudas.",
            summary = "El nombre mostrado del remitente puede ser falso. Siempre verifica la dirección real del correo o número del mensaje.",
            reflectionQuestion = "¿Sabes cómo verificar si un correo realmente viene de la empresa que dice representar?"
        ),
        Lesson(
            id = 205, moduleId = 2, order = 5,
            title = "Mantener dispositivos protegidos",
            introduction = "Incluso con conocimiento sobre phishing, un dispositivo sin protección puede ser vulnerable. La seguridad del dispositivo es tu última línea de defensa.",
            explanation = "Mantén tu sistema operativo, navegador y aplicaciones actualizados, ya que las actualizaciones corrigen vulnerabilidades explotadas por malware. Instala un antivirus o solución de seguridad confiable en tus dispositivos. Activa el filtro anti-spam y anti-phishing de tu correo electrónico. En móviles, descarga apps solo de tiendas oficiales (Google Play, App Store) y revisa permisos solicitados. Configura el bloqueo de pantalla con PIN, patrón o biometría. Si accidentalmente haces clic en un enlace sospechoso, no ingreses datos y escanea tu dispositivo. Las extensiones de navegador como uBlock Origin y bloqueadores de phishing añaden protección adicional. Realiza copias de seguridad regulares para recuperarte de posibles infecciones de ransomware.",
            examples = "Malware que se instala al hacer clic en enlaces de phishing y roba credenciales bancarias. Ransomware WannaCry explotó sistemas sin actualizar. Apps falsas en tiendas no oficiales que roban datos bancarios.",
            tips = "Activa actualizaciones automáticas. Usa bloqueadores de phishing en el navegador. Escanea dispositivos regularmente. Mantén copias de seguridad offline.",
            summary = "Un dispositivo actualizado y protegido reduce el impacto si caes en un intento de phishing. La prevención técnica complementa tu conocimiento.",
            reflectionQuestion = "¿Cuándo fue la última vez que actualizaste tu sistema operativo y aplicaciones?"
        )
    )

    fun module3Lessons() = listOf(
        Lesson(
            id = 301, moduleId = 3, order = 1,
            title = "Entrar a sitios confiables",
            introduction = "La internet contiene millones de sitios web, pero no todos son seguros. Saber identificar sitios confiables protege tu información y tu dispositivo.",
            explanation = "Accede a sitios importantes escribiendo la URL directamente en el navegador o usando marcadores guardados, nunca a través de enlaces en correos o mensajes. Verifica que el sitio tenga una política de privacidad clara y datos de contacto reales. Desconfía de sitios con diseño deficiente, muchos anuncios emergentes o que piden información excesiva. Para compras en línea, usa sitios conocidos y verifica reseñas en fuentes independientes. Los sitios de descarga de software deben ser las páginas oficiales del desarrollador. En redes Wi-Fi públicas, evita acceder a sitios sensibles sin VPN. Los motores de búsqueda pueden mostrar resultados patrocinados maliciosos: verifica siempre la URL del sitio al que llegas.",
            examples = "Sitios falsos de venta de entradas para conciertos que cobran y no entregan. Páginas de descarga de software crackeado que instalan malware. Tiendas online falsas en redes sociales que desaparecen tras recibir pagos.",
            tips = "Usa marcadores para sitios frecuentes. Verifica reseñas en Trustpilot o Google. Compra solo en sitios con HTTPS y políticas claras.",
            summary = "Accede a sitios importantes directamente o por marcadores. La confianza se verifica, no se asume por la apariencia del sitio.",
            reflectionQuestion = "¿Cómo verificas que un sitio de compras online es legítimo antes de pagar?"
        ),
        Lesson(
            id = 302, moduleId = 3, order = 2,
            title = "Verificar HTTPS",
            introduction = "HTTPS cifra la comunicación entre tu navegador y el sitio web, protegiendo tus datos de ser interceptados. Es un indicador esencial de seguridad en la navegación.",
            explanation = "HTTPS (HyperText Transfer Protocol Secure) usa cifrado TLS para proteger los datos transmitidos. Identifícalo por el candado en la barra de direcciones y la URL que comienza con 'https://'. Sin HTTPS, tus contraseñas, datos bancarios y mensajes viajan en texto plano que cualquiera en la red puede interceptar. Sin embargo, HTTPS no garantiza que el sitio sea legítimo: los atacantes también pueden obtener certificados HTTPS para sitios de phishing. Por eso HTTPS es necesario pero no suficiente: combínalo con verificación del dominio. Nunca ingreses información sensible en sitios que muestren advertencias de certificado del navegador. En HTTP, cualquier persona en la misma red Wi-Fi puede ver tu tráfico (ataque man-in-the-middle).",
            examples = "En cafeterías con Wi-Fi público, atacantes interceptan datos en sitios HTTP. Sitios de phishing con HTTPS engañan a usuarios que solo miran el candado. La migración global a HTTPS mejoró la privacidad general en internet.",
            tips = "Instala HTTPS Everywhere o usa navegadores que fuerzan HTTPS. Nunca ignores advertencias de certificado. Verifica el dominio además del candado.",
            summary = "HTTPS cifra tu conexión pero no valida la legitimidad del sitio. Siempre busca el candado y verifica el dominio correcto.",
            reflectionQuestion = "¿Sabías que un sitio con HTTPS puede ser falso? ¿Cómo lo verificarías?"
        ),
        Lesson(
            id = 303, moduleId = 3, order = 3,
            title = "Descargar con responsabilidad",
            introduction = "Las descargas de archivos son una de las principales vías de infección de malware. Cada descarga debe ser evaluada cuidadosamente antes de ejecutarse.",
            explanation = "Descarga software únicamente de sitios oficiales o tiendas de aplicaciones legítimas. Evita sitios de descargas de terceros, cracks, keygens y torrents de software comercial, que frecuentemente contienen malware. Antes de abrir un archivo descargado, escanéalo con tu antivirus. Presta atención a las extensiones de archivo: .exe, .bat, .scr, .msi pueden ejecutar código malicioso. Los archivos aparentemente inofensivos como PDFs o documentos Office también pueden contener macros maliciosas. En correos, nunca descargues adjuntos de remitentes desconocidos o inesperados. Configura tu sistema para mostrar extensiones de archivo completas y detectar archivos con doble extensión como 'documento.pdf.exe'.",
            examples = "El ransomware WannaCry se distribuyó mediante descargas de archivos infectados. Apps falsas de WhatsApp Gold distribuían spyware. Keygens de software popular contenían troyanos bancarios.",
            tips = "Descarga solo de fuentes oficiales. Escanea archivos antes de abrir. Desactiva macros en documentos Office. Muestra extensiones de archivo en tu sistema.",
            summary = "Cada descarga es un riesgo potencial. Usa fuentes oficiales, escanea archivos y desconfía de software pirata.",
            reflectionQuestion = "¿De dónde descargas habitualmente software y archivos? ¿Son fuentes confiables?"
        ),
        Lesson(
            id = 304, moduleId = 3, order = 4,
            title = "Mantener actualizado el navegador",
            introduction = "Tu navegador es tu ventana a internet y un objetivo frecuente de atacantes. Mantenerlo actualizado es fundamental para una navegación segura.",
            explanation = "Los navegadores reciben actualizaciones regulares que corrigen vulnerabilidades de seguridad descubiertas. Un navegador desactualizado puede ser explotado simplemente visitando una página web maliciosa (drive-by download). Activa las actualizaciones automáticas en Chrome, Firefox, Edge o Safari. Las extensiones del navegador también necesitan actualizarse y deben instalarse solo de fuentes confiables: extensiones maliciosas pueden robar datos, mostrar anuncios o cambiar configuraciones. Revisa periódicamente las extensiones instaladas y elimina las que no uses. Usa la navegación privada para sesiones en computadoras compartidas, pero recuerda que no te hace anónimo. Configura tu navegador para bloquear cookies de terceros y pop-ups. El modo de bloqueo de rastreadores integrado en navegadores modernos mejora tu privacidad.",
            examples = "Vulnerabilidades zero-day en Chrome explotadas antes de parches. Extensiones maliciosas en Chrome Web Store robando datos de criptomonedas. Internet Explorer desactualizado fue vector del ransomware WannaCry.",
            tips = "Activa actualizaciones automáticas. Revisa extensiones mensualmente. Usa pocos complementos de fuentes verificadas.",
            summary = "Un navegador actualizado cierra vulnerabilidades conocidas. Las extensiones también requieren atención y deben ser mínimas y confiables.",
            reflectionQuestion = "¿Tienes activadas las actualizaciones automáticas en tu navegador?"
        ),
        Lesson(
            id = 305, moduleId = 3, order = 5,
            title = "Pensar antes de compartir datos",
            introduction = "Cada vez que ingresas datos en un sitio web, debes evaluar si es necesario y si el sitio es digno de confianza. La sobrepoblación de datos es un riesgo creciente.",
            explanation = "Antes de completar cualquier formulario, pregúntate: ¿es necesario proporcionar esta información? ¿Conozco y confío en este sitio? Proporciona solo los datos mínimos requeridos. En redes sociales, configura la privacidad para limitar quién ve tu información personal. Los quizzes y juegos en redes sociales frecuentemente recopilan datos para perfiles de marketing o ataques dirigidos. Lee las políticas de privacidad de los servicios que usas, especialmente qué datos recopilan y con quién los comparten. Usa correos alternativos o servicios de correo desechable para registros en sitios no esenciales. Los datos que compartes hoy pueden ser usados contra ti en ataques de ingeniería social futuros.",
            examples = "Quizzes de Facebook que revelan respuestas a preguntas de seguridad. Formularios de registro que piden datos innecesarios vendidos a terceros. Filtraciones masivas de datos de usuarios afectando millones.",
            tips = "Usa datos mínimos en formularios. Revisa configuración de privacidad mensualmente. Usa alias de correo para registros temporales.",
            summary = "Comparte solo lo necesario con sitios que confías. Tus datos personales son un activo que debes proteger activamente.",
            reflectionQuestion = "¿Cuánta información personal has compartido en internet sin leer las políticas de privacidad?"
        )
    )
}
