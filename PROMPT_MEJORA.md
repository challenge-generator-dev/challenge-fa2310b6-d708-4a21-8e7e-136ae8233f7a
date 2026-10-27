# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/financial/loans/LoansApplication.java` — `com.financial.loans.application.port.out.AuditEventPublisher`: El import com.financial.loans.application.port.out.AuditEventPublisher usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/financial/loans/LoansApplication.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/loans/infrastructure/config/DatabaseConfig.java` — `com.zaxxer.hikari`: El import com.zaxxer.hikari.HikariConfig pertenece a com.zaxxer.hikari, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/loans/infrastructure/config/DatabaseConfig.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/loans/infrastructure/config/LoggingConfig.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/financial/loans/LoansApplication.java` — `DatabaseConfig.getUrl`: Se invoca `getUrl` sobre `DatabaseConfig`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/LoansApplication.java` — `DatabaseConfig.getUsername`: Se invoca `getUsername` sobre `DatabaseConfig`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getId`: Se invoca `getId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getOperationNumber`: Se invoca `getOperationNumber` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getChannel`: Se invoca `getChannel` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getAmount`: Se invoca `getAmount` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getStatus`: Se invoca `getStatus` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getCreatedAt`: Se invoca `getCreatedAt` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getUpdatedAt`: Se invoca `getUpdatedAt` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getCustomerId`: Se invoca `getCustomerId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getErrorMessage`: Se invoca `getErrorMessage` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequestDTO.operationNumber`: Se invoca `operationNumber` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequestDTO.channel`: Se invoca `channel` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequestDTO.amount`: Se invoca `amount` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequest.getStatus`: Se invoca `getStatus` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequest.getId`: Se invoca `getId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequest.getAmount`: Se invoca `getAmount` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequestDTO.customerId`: Se invoca `customerId` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `LoanRequest.getOperationNumber`: Se invoca `getOperationNumber` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `LoanRequest.getCustomerId`: Se invoca `getCustomerId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `LoanRequest.getChannel`: Se invoca `getChannel` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `LoanRequest.getAmount`: Se invoca `getAmount` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `LoanRequest.getId`: Se invoca `getId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `LoanRequest.getStatus`: Se invoca `getStatus` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java` — `LoanRequestDTO.operationNumber`: Se invoca `operationNumber` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java` — `LoanRequestDTO.channel`: Se invoca `channel` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java` — `LoanRequestDTO.id`: Se invoca `id` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java` — `LoanRequestDTO.status`: Se invoca `status` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java` — `LoanRequestService.findById`: Se invoca `findById` sobre `LoanRequestService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.getOperationNumber`: Se invoca `getOperationNumber` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.getChannel`: Se invoca `getChannel` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.getAmount`: Se invoca `getAmount` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.getCustomerId`: Se invoca `getCustomerId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.getStatus`: Se invoca `getStatus` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.getErrorMessage`: Se invoca `getErrorMessage` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequestService.updateStatus`: Se invoca `updateStatus` sobre `LoanRequestService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequestService.findById`: Se invoca `findById` sobre `LoanRequestService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.isPresent`: Se invoca `isPresent` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.get`: Se invoca `get` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.isEmpty`: Se invoca `isEmpty` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepositoryTest.java` — `LoanRequest.getId`: Se invoca `getId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepositoryTest.java` — `LoanRequest.getStatus`: Se invoca `getStatus` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepositoryTest.java` — `LoanRequest.getUpdatedAt`: Se invoca `getUpdatedAt` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepositoryTest.java` — `LoanRequest.withId`: Se invoca `withId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepositoryTest.java` — `LoanRequest.getCreatedAt`: Se invoca `getCreatedAt` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Contexto técnico original
Chapter Backend, Especialidad Desarrollador, Tecnolog�a Java, Advanced � Experiencia comprobable en al menos un marco de trabajo (Framework) en su lenguaje de programaci�n principal (por ejemplo, Spring Framework en Java o ASP .net core en C#) y puede realizar el enrutamiento de solicitudes, controlar las variables de entorno, generar registros de rastros (Logs) y conectarse con bases de datos utilizando un Mapeador Relacional de Objetos (ORM). � El candidato es un desarrollador Backend Advanced con experiencia en estructuras de datos y patrones GRASP. Est� listo para profundizar en frameworks como Spring Boot.

### Reto
- Tema: Experiencia en Frameworks
- Seniority: advanced-l1
- Tipo: practical
- Título: Mejora de la Experiencia en Frameworks
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Integración Inicial con Framework — objetivo: Configurar el framework para manejar solicitudes de préstamos de forma idempotente. — entregable (NO resolver): Framework configurado para aceptar y procesar solicitudes de préstamos de forma idempotente.
- Fase 2: Gestión de Variables de Entorno — objetivo: Configurar el framework para utilizar variables de entorno para la gestión de configuraciones. — entregable (NO resolver): Framework configurado para utilizar variables de entorno para la gestión de configuraciones.
- Fase 3: Conexión con Base de Datos — objetivo: Configurar el framework para conectarse con una base de datos utilizando un ORM. — entregable (NO resolver): Framework configurado para conectarse con una base de datos utilizando un ORM.
- Fase 4: Generación de Registros de Trazos — objetivo: Configurar el framework para generar registros de trazos para la depuración y el seguimiento de solicitudes. — entregable (NO resolver): Framework configurado para generar registros de trazos para la depuración y el seguimiento de solicitudes.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.financial</groupId>
    <artifactId>loans</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>loans</name>
    <description>Sistema de gestión de préstamos con idempotencia y auditoría</description>

    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <!-- Jackson -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.34</version>
            <scope>provided</scope>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.12.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>postgresql</artifactId>
            <version>1.20.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <parameters>true</parameters>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/financial/loans/LoansApplication.java ===
package com.financial.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import com.financial.loans.infrastructure.config.IdempotencyConfig;
import com.financial.loans.infrastructure.config.DatabaseConfig;
import com.financial.loans.application.port.out.AuditEventPublisher;
import com.financial.loans.infrastructure.event.AuditEventPublisherImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableConfigurationProperties({IdempotencyConfig.class, DatabaseConfig.class})
@EnableAsync
public class LoansApplication {
    private static final Logger logger = LoggerFactory.getLogger(LoansApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(LoansApplication.class, args);
            logger.info("Aplicación de préstamos iniciada correctamente");
        } catch (Exception e) {
            logger.error("Error al iniciar la aplicación", e);
            throw e;
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "auditExecutor")
    public Executor auditExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AuditEvent-");
        executor.initialize();
        return executor;
    }

    @Bean
    public AuditEventPublisher auditEventPublisher() {
        return new AuditEventPublisherImpl();
    }

    @Bean
    public AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler() {
        return (throwable, method, params) -> {
            logger.error("Excepción no capturada en método asíncrono: {}", method.getName(), throwable);
        };
    }

    @Bean
    public Object initializeDatabase(DatabaseConfig databaseConfig) {
        logger.info("Configuración de base de datos cargada: URL={}, usuario={}",
            databaseConfig.getUrl(),
            databaseConfig.getUsername());
        return new Object();
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: loans-service
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/loans_db}
    username: ${DB_USERNAME:loans_user}
    password: ${DB_PASSWORD:loans_pass}
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      idle-timeout: 30000
      connection-timeout: 30000
      max-lifetime: 1800000
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        format_sql: true
        jdbc:
          batch_size: 50
        order_inserts: true
        order_updates: true
        generate_statistics: false
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true
    validate-on-migrate: true

server:
  port: ${SERVER_PORT:8080}
  servlet:
    context-path: /api/loans
  tomcat:
    threads:
      max: 200
      min-spare: 10

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    tags:
      application: ${spring.application.name}

logging:
  level:
    root: INFO
    com.financial.loans: DEBUG
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %logger{36} - %msg%n%mdc%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/loans-service.log
    max-size: 10MB
    max-history: 7

idempotency:
  enabled: ${IDEMPOTENCY_ENABLED:true}
  key-prefix: loan_request_
  ttl-hours: 24
  max-retries: 3

audit:
  enabled: ${AUDIT_ENABLED:true}
  event-topic: loan-audit-events

// === ARCHIVO: src/main/java/com/financial/loans/domain/model/LoanRequest.java ===
package com.financial.loans.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
    @NotNull(message = "El ID de la solicitud no puede ser nulo")
    private UUID id;

    @NotBlank(message = "El número de operación no puede estar vacío")
    @Size(max = 50, message = "El número de operación no puede exceder 50 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9-]*$", message = "El número de operación solo puede contener letras, números y guiones")
    private String operationNumber;

    @NotBlank(message = "El canal no puede estar vacío")
    @Size(max = 20, message = "El canal no puede exceder 20 caracteres")
    private String channel;

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
    private BigDecimal amount;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 20, message = "El estado no puede exceder 20 caracteres")
    private String status;

    @NotNull(message = "La fecha de creación no puede ser nula")
    private LocalDateTime createdAt;

    @NotNull(message = "La fecha de actualización no puede ser nula")
    private LocalDateTime updatedAt;

    @NotBlank(message = "El cliente ID no puede estar vacío")
    @Size(max = 50, message = "El cliente ID no puede exceder 50 caracteres")
    private String customerId;

    @Size(max = 200, message = "El mensaje de error no puede exceder 200 caracteres")
    private String errorMessage;

    public String getIdempotencyKey() {
        return operationNumber + "|" + channel;
    }

    public boolean isValidForIdempotency() {
        return operationNumber != null && !operationNumber.isBlank() && 
               channel != null && !channel.isBlank();
    }

    public static LoanRequest fromOperationAndChannel(String operationNumber, String channel) {
        return LoanRequest.builder()
                .operationNumber(operationNumber)
                .channel(channel)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

// === ARCHIVO: src/main/java/com/financial/loans/domain/port/LoanRequestRepository.java ===
package com.financial.loans.domain.port;

import java.util.Optional;
import java.util.UUID;

import com.financial.loans.domain.model.LoanRequest;

public interface LoanRequestRepository {
    LoanRequest save(LoanRequest loanRequest);

    Optional<LoanRequest> findById(UUID id);

    Optional<LoanRequest> findByIdempotencyKey(String idempotencyKey);

    LoanRequest updateStatus(UUID id, String status, String errorMessage);

    Optional<LoanRequest> findExistingRequestWithinWindow(String operationNumber, String channel);
}

// === ARCHIVO: src/main/java/com/financial/loans/domain/port/AuditEventPublisher.java ===
package com.financial.loans.domain.port;

import com.financial.loans.domain.model.LoanRequest;

public interface AuditEventPublisher {
    void publishLoanAcceptedEvent(LoanRequest loanRequest);

    void publishLoanRejectedEvent(LoanRequest loanRequest, String rejectionReason);
}

// === ARCHIVO: src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java ===
package com.financial.loans.infrastructure.adapter;

import com.financial.loans.domain.model.LoanRequest;
import com.financial.loans.domain.port.LoanRequestRepository;
import com.financial.loans.infrastructure.persistence.entity.LoanRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class LoanRequestJpaRepository implements LoanRequestRepository {

    private final LoanRequestJpaAdapter jpaAdapter;

    @Autowired
    public LoanRequestJpaRepository(LoanRequestJpaAdapter jpaAdapter) {
        this.jpaAdapter = jpaAdapter;
    }

    @Override
    public LoanRequest save(LoanRequest loanRequest) {
        LoanRequestEntity entity = toEntity(loanRequest);
        LoanRequestEntity savedEntity = jpaAdapter.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoanRequest> findById(UUID id) {
        return jpaAdapter.findById(id).map(this::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoanRequest> findByIdempotencyKey(String idempotencyKey) {
        return jpaAdapter.findByIdempotencyKey(idempotencyKey).map(this::toDomain);
    }

    @Override
    public LoanRequest updateStatus(UUID id, String status, String errorMessage) {
        Optional<LoanRequestEntity> existingEntity = jpaAdapter.findById(id);
        if (existingEntity.isEmpty()) {
            throw new IllegalArgumentException("Loan request not found with id: " + id);
        }
        
        LoanRequestEntity entity = existingEntity.get();
        entity.setStatus(status);
        entity.setErrorMessage(errorMessage);
        entity.setUpdatedAt(LocalDateTime.now());
        
        LoanRequestEntity updatedEntity = jpaAdapter.save(entity);
        return toDomain(updatedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoanRequest> findExistingRequestWithinWindow(String operationNumber, String channel) {
        LocalDateTime windowStart = LocalDateTime.now().minusHours(24);
        return jpaAdapter.findByOperationNumberAndChannelAndCreatedAtAfter(operationNumber, channel, windowStart)
                .map(this::toDomain);
    }

    private LoanRequestEntity toEntity(LoanRequest loanRequest) {
        LoanRequestEntity entity = new LoanRequestEntity();
        entity.setId(loanRequest.getId());
        entity.setOperationNumber(loanRequest.getOperationNumber());
        entity.setChannel(loanRequest.getChannel());
        entity.setAmount(loanRequest.getAmount());
        entity.setStatus(loanRequest.getStatus());
        entity.setCreatedAt(loanRequest.getCreatedAt());
        entity.setUpdatedAt(loanRequest.getUpdatedAt());
        entity.setCustomerId(loanRequest.getCustomerId());
        entity.setErrorMessage(loanRequest.getErrorMessage());
        
        String idempotencyKey = buildIdempotencyKey(loanRequest.getOperationNumber(), loanRequest.getChannel());
        entity.setIdempotencyKey(idempotencyKey);
        
        return entity;
    }

    private LoanRequest toDomain(LoanRequestEntity entity) {
        LoanRequest loanRequest = LoanRequest.fromOperationAndChannel(
            entity.getOperationNumber(), 
            entity.getChannel()
        );
        
        try {
            var idField = LoanRequest.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(loanRequest, entity.getId());
            
            var amountField = LoanRequest.class.getDeclaredField("amount");
            amountField.setAccessible(true);
            amountField.set(loanRequest, entity.getAmount());
            
            var statusField = LoanRequest.class.getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.set(loanRequest, entity.getStatus());
            
            var createdAtField = LoanRequest.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(loanRequest, entity.getCreatedAt());
            
            var updatedAtField = LoanRequest.class.getDeclaredField("updatedAt");
            updatedAtField.setAccessible(true);
            updatedAtField.set(loanRequest, entity.getUpdatedAt());
            
            var customerIdField = LoanRequest.class.getDeclaredField("customerId");
            customerIdField.setAccessible(true);
            customerIdField.set(loanRequest, entity.getCustomerId());
            
            var errorMessageField = LoanRequest.class.getDeclaredField("errorMessage");
            errorMessageField.setAccessible(true);
            errorMessageField.set(loanRequest, entity.getErrorMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error mapping entity to domain", e);
        }
        
        return loanRequest;
    }

    private String buildIdempotencyKey(String operationNumber, String channel) {
        return operationNumber + ":" + channel;
    }
}

// === ARCHIVO: src/main/java/com/financial/loans/infrastructure/rest/dto/LoanRequestDTO.java ===
package com.financial.loans.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record LoanRequestDTO(
    @NotBlank(message = "El número de operación es requerido")
    @Size(max = 50, message = "El número de operación no puede exceder 50 caracteres")
    String operationNumber,
    
    @NotBlank(message = "El canal es requerido")
    @Size(max = 30, message = "El canal no puede exceder 30 caracteres")
    String channel,
    
    @NotNull(message = "El monto es requerido")
    @Positive(message = "El monto debe ser positivo")
    BigDecimal amount,
    
    @NotBlank(message = "El ID del cliente es requerido")
    @Size(max = 20, message = "El ID del cliente no puede exceder 20 caracteres")
    String customerId
) {
    public String buildIdempotencyKey() {
        return operationNumber + ":" + channel;
    }
}

// === ARCHIVO: src/main/java/com/financial/loans/application/service/LoanRequestService.java ===
package com.financial.loans.application.service;

import com.financial.loans.domain.model.LoanRequest;
import com.financial.loans.domain.port.AuditEventPublisher;
import com.financial.loans.domain.port.LoanRequestRepository;
import com.financial.loans.infrastructure.rest.dto.LoanRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanRequestService {

    private static final Logger logger = LoggerFactory.getLogger(LoanRequestService.class);
    private static final String STATUS_PENDING = "PENDING";
    private static final String STATUS_APPROVED = "APPROVED";
    private static final String STATUS_REJECTED = "REJECTED";
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("100000.00");
    private static final BigDecimal MIN_AMOUNT = new BigDecimal("100.00");

    private final LoanRequestRepository loanRequestRepository;
    private final AuditEventPublisher auditEventPublisher;

    @Autowired
    public LoanRequestService(LoanRequestRepository loanRequestRepository,
                              AuditEventPublisher auditEventPublisher) {
        this.loanRequestRepository = loanRequestRepository;
        this.auditEventPublisher = auditEventPublisher;
    }

    @Transactional
    public LoanRequest processLoanRequest(LoanRequestDTO dto) {
        String idempotencyKey = dto.buildIdempotencyKey();
        
        Map<String, String> context = new HashMap<>();
        context.put("operationNumber", dto.operationNumber());
        context.put("channel", dto.channel());
        context.put("idempotencyKey", idempotencyKey);
        MDC.clear();
        MDC.setContextMap(context);
        
        try {
            logger.info("Processing loan request - operation: {}, channel: {}, amount: {}",
                dto.operationNumber(), dto.channel(), dto.amount());
            
            Optional<LoanRequest> existingRequest = 
                loanRequestRepository.findByIdempotencyKey(idempotencyKey);
            
            if (existingRequest.isPresent()) {
                LoanRequest existing = existingRequest.get();
                logger.info("Returning existing request with status: {} for idempotency key: {}",
                    existing.getStatus(), idempotencyKey);
                return existing;
            }
            
            Optional<LoanRequest> recentRequest = 
                loanRequestRepository.findExistingRequestWithinWindow(
                    dto.operationNumber(), dto.channel());
            
            if (recentRequest.isPresent()) {
                logger.warn("Duplicate request detected within 24h window - operation: {}, channel: {}",
                    dto.operationNumber(), dto.channel());
                return recentRequest.get();
            }
            
            validateRequest(dto);
            
            LoanRequest loanRequest = createLoanRequest(dto);
            
            boolean approved = evaluateCreditRisk(dto);
            
            if (approved) {
                loanRequest = updateLoanRequestStatus(loanRequest, STATUS_APPROVED, null);
                auditEventPublisher.publishLoanAcceptedEvent(loanRequest);
                logger.info("Loan request APPROVED - id: {}, amount: {}", 
                    loanRequest.getId(), loanRequest.getAmount());
            } else {
                String rejectionReason = "Credit risk evaluation failed - insufficient credit score";
                loanRequest = updateLoanRequestStatus(loanRequest, STATUS_REJECTED, rejectionReason);
                auditEventPublisher.publishLoanRejectedEvent(loanRequest, rejectionReason);
                logger.info("Loan request REJECTED - id: {}, reason: {}", 
                    loanRequest.getId(), rejectionReason);
            }
            
            return loanRequest;
            
        } catch (Exception e) {
            logger.error("Error processing loan request - operation: {}, channel: {}",
                dto.operationNumber(), dto.channel(), e);
            throw e;
        } finally {
            MDC.clear();
        }
    }

    @Transactional(readOnly = true)
    public Optional<LoanRequest> getLoanRequest(UUID id) {
        logger.debug("Retrieving loan request with id: {}", id);
        return loanRequestRepository.findById(id);
    }

    private void validateRequest(LoanRequestDTO dto) {
        if (dto.amount().compareTo(MIN_AMOUNT) < 0) {
            throw new IllegalArgumentException(
                "El monto mínimo permitido es: " + MIN_AMOUNT);
        }
        if (dto.amount().compareTo(MAX_AMOUNT) > 0) {
            throw new IllegalArgumentException(
                "El monto máximo permitido es: " + MAX_AMOUNT);
        }
        
        if (dto.operationNumber() == null || dto.operationNumber().isBlank()) {
            throw new IllegalArgumentException("El número de operación no puede estar vacío");
        }
        
        if (dto.channel() == null || dto.channel().isBlank()) {
            throw new IllegalArgumentException("El canal no puede estar vacío");
        }
        
        logger.debug("Request validation passed for operation: {}", dto.operationNumber());
    }

    private LoanRequest createLoanRequest(LoanRequestDTO dto) {
        LoanRequest loanRequest = LoanRequest.fromOperationAndChannel(
            dto.operationNumber(), 
            dto.channel()
        );
        
        try {
            var idField = LoanRequest.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(loanRequest, UUID.randomUUID());
            
            var amountField = LoanRequest.class.getDeclaredField("amount");
            amountField.setAccessible(true);
            amountField.set(loanRequest, dto.amount());
            
            var statusField = LoanRequest.class.getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.set(loanRequest, STATUS_PENDING);
            
            var createdAtField = LoanRequest.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(loanRequest, LocalDateTime.now());
            
            var updatedAtField = LoanRequest.class.getDeclaredField("updatedAt");
            updatedAtField.setAccessible(true);
            updatedAtField.set(loanRequest, LocalDateTime.now());
            
            var customerIdField = LoanRequest.class.getDeclaredField("customerId");
            customerIdField.setAccessible(true);
            customerIdField.set(loanRequest, dto.customerId());
            
            var errorMessageField = LoanRequest.class.getDeclaredField("errorMessage");
            errorMessageField.setAccessible(true);
            errorMessageField.set(loanRequest, null);
            
        } catch (Exception e) {
            throw new RuntimeException("Error creating loan request", e);
        }
        
        return loanRequestRepository.save(loanRequest);
    }

    private LoanRequest updateLoanRequestStatus(LoanRequest loanRequest, String status, String errorMessage) {
        return loanRequestRepository.updateStatus(loanRequest.getId(), status, errorMessage);
    }

    private boolean evaluateCreditRisk(LoanRequestDTO dto) {
        logger.debug("Evaluating credit risk for customer: {}, amount: {}", 
            dto.customerId(), dto.amount());
        
        BigDecimal riskScore = calculateRiskScore(dto);
        boolean approved = riskScore.compareTo(new BigDecimal("500")) >= 0;
        
        logger.debug("Risk score: {}, approved: {}", riskScore, approved);
        return approved;
    }

    private BigDecimal calculateRiskScore(LoanRequestDTO dto) {
        BigDecimal baseScore = new BigDecimal("600");
        
        if (dto.amount().compareTo(new BigDecimal("10000")) > 0) {
            baseScore = baseScore.subtract(new BigDecimal("100"));
        }
        
        String channel = dto.channel().toUpperCase();
        if ("ONLINE".equals(channel) || "MOBILE".equals(channel)) {
            baseScore = baseScore.add(new BigDecimal("50"));
        }
        
        return baseScore;
    }
}

// === ARCHIVO: src/main/java/com/financial/loans/infrastructure/persistence/entity/LoanRequestEntity.java ===
package com.financial.loans.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "loan_requests", indexes = {
    @Index(name = "idx_idempotency_key", columnList = "idempotency_key"),
    @Index(name = "idx_operation_channel", columnList = "operation_number, channel"),
    @Index(name = "idx_created_at", columnList = "created_at"),
    @Index(name = "idx_status", columnList = "status")
})
public class LoanRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "operation_number", nullable = false, length = 50)
    private String operationNumber;

    @Column(name = "channel", nullable = false, length = 30)
    private String channel;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 100)
    private String idempotencyKey;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "customer_id", nullable = false, length = 50)
    private String customerId;

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

    public LoanRequestEntity() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(String operationNumber) {
        this.operationNumber = operationNumber;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

// === ARCHIVO: src/main/java/com/financial/loans/infrastructure/config/IdempotencyConfig.java ===
package com.financial.loans.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.time.Duration;

@Configuration
public class IdempotencyConfig {

    private static final Duration DEFAULT_TTL = Duration.ofHours(24);
    private static final String IDEMPOTENCY_KEY_PREFIX = "loan:idempotency:";
    private static final int MAX_OPERATION_NUMBER_LENGTH = 50;
    private static final int MAX_CHANNEL_LENGTH = 30;

    @Bean
    public IdempotencyService idempotencyService(
            StringRedisTemplate stringRedisTemplate,
            RedisTemplate<String, Object> redisTemplate) {
        return new IdempotencyService(stringRedisTemplate, redisTemplate);
    }

    public static class IdempotencyService {

        private final StringRedisTemplate stringRedisTemplate;
        private final RedisTemplate<String, Object> redisTemplate;

        public IdempotencyService(
                StringRedisTemplate stringRedisTemplate,
                RedisTemplate<String, Object> redisTemplate) {
            this.stringRedisTemplate = stringRedisTemplate;
            this.redisTemplate = redisTemplate;
        }

        public String buildCompositeKey(String operationNumber, String channel) {
            if (operationNumber == null || operationNumber.isBlank()) {
                throw new IllegalArgumentException("El número de operación no puede ser nulo o vacío");
            }
            if (channel == null || channel.isBlank()) {
                throw new IllegalArgumentException("El canal no puede ser nulo o vacío");
            }
            if (operationNumber.length() > MAX_OPERATION_NUMBER_LENGTH) {
                throw new IllegalArgumentException(
                    "El número de operación excede el máximo de " + MAX_OPERATION_NUMBER_LENGTH + " caracteres");
            }
            if (channel.length() > MAX_CHANNEL_LENGTH) {
                throw new IllegalArgumentException(
                    "El canal excede el máximo de " + MAX_CHANNEL_LENGTH + " caracteres");
            }
            return IDEMPOTENCY_KEY_PREFIX + operationNumber.trim() + ":" + channel.trim().toLowerCase();
        }

        public boolean checkAndSetIdempotency(String compositeKey, String requestId) {
            if (compositeKey == null || requestId == null) {
                return false;
            }
            String existingValue = stringRedisTemplate.opsForValue().get(compositeKey);
            if (existingValue != null) {
                return existingValue.equals(requestId);
            }
            stringRedisTemplate.opsForValue().set(compositeKey, requestId, DEFAULT_TTL);
            return true;
        }

        public boolean existsWithinWindow(String compositeKey) {
            return Boolean.TRUE.equals(stringRedisTemplate.hasKey(compositeKey));
        }

        public void removeIdempotencyKey(String compositeKey) {
            if (compositeKey != null) {
                stringRedisTemplate.delete(compositeKey);
            }
        }

        public Duration getDefaultTtl() {
            return DEFAULT_TTL;
        }
    }
}

// === ARCHIVO: src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java ===
package com.financial.loans.infrastructure.event;

import com.financial.loans.domain.model.LoanRequest;
import com.financial.loans.domain.port.AuditEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
public class AuditEventPublisherImpl implements AuditEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(AuditEventPublisherImpl.class);
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final String EVENT_TYPE_ACCEPTED = "LOAN_ACCEPTED";
    private static final String EVENT_TYPE_REJECTED = "LOAN_REJECTED";
    private static final String AUDIT_SYSTEM_ENDPOINT = "http://audit-system:8080/api/events";

    private final Executor auditExecutor;

    @Autowired
    public AuditEventPublisherImpl(Executor auditExecutor) {
        this.auditExecutor = auditExecutor;
    }

    @Override
    @Async("auditExecutor")
    public void publishLoanAcceptedEvent(LoanRequest loanRequest) {
        if (loanRequest == null) {
            logger.warn("Intento de publicar evento de aceptación con solicitud nula");
            return;
        }

        String correlationId = UUID.randomUUID().toString();
        try {
            MDC.put("correlationId", correlationId);
            MDC.put("eventType", EVENT_TYPE_ACCEPTED);
            MDC.put("operationNumber", loanRequest.getOperationNumber());
            MDC.put("customerId", loanRequest.getCustomerId());

            Map<String, Object> eventPayload = buildAcceptedEventPayload(loanRequest, correlationId);
            logger.info("Publicando evento de préstamo aceptado para operación: {}, canal: {}",
                loanRequest.getOperationNumber(), loanRequest.getChannel());

            CompletableFuture.runAsync(() -> sendToAuditSystem(eventPayload), auditExecutor)
                .exceptionally(ex -> {
                    logger.error("Error al enviar evento de aceptación al sistema de auditoría: {}",
                        ex.getMessage(), ex);
                    return null;
                });
        } finally {
            MDC.clear();
        }
    }

    @Override
    @Async("auditExecutor")
    public void publishLoanRejectedEvent(LoanRequest loanRequest, String rejectionReason) {
        if (loanRequest == null) {
            logger.warn("Intento de publicar evento de rechazo con solicitud nula");
            return;
        }

        String correlationId = UUID.randomUUID().toString();
        try {
            MDC.put("correlationId", correlationId);
            MDC.put("eventType", EVENT_TYPE_REJECTED);
            MDC.put("operationNumber", loanRequest.getOperationNumber());
            MDC.put("customerId", loanRequest.getCustomerId());

            Map<String, Object> eventPayload = buildRejectedEventPayload(
                loanRequest, rejectionReason, correlationId);
            logger.info("Publicando evento de préstamo rechazado para operación: {}, razón: {}",
                loanRequest.getOperationNumber(), rejectionReason);

            CompletableFuture.runAsync(() -> sendToAuditSystem(eventPayload), auditExecutor)
                .exceptionally(ex -> {
                    logger.error("Error al enviar evento de rechazo al sistema de auditoría: {}",
                        ex.getMessage(), ex);
                    return null;
                });
        } finally {
            MDC.clear();
        }
    }

    private Map<String, Object> buildAcceptedEventPayload(LoanRequest loanRequest, String correlationId) {
        return Map.of(
            "eventId", correlationId,
            "eventType", EVENT_TYPE_ACCEPTED,
            "timestamp", LocalDateTime.now().format(ISO_FORMATTER),
            "operationNumber", loanRequest.getOperationNumber(),
            "channel", loanRequest.getChannel(),
            "amount", loanRequest.getAmount().toString(),
            "customerId", loanRequest.getCustomerId(),
            "loanRequestId", loanRequest.getId().toString(),
            "status", loanRequest.getStatus()
        );
    }

    private Map<String, Object> buildRejectedEventPayload(
            LoanRequest loanRequest, String rejectionReason, String correlationId) {
        return Map.of(
            "eventId", correlationId,
            "eventType", EVENT_TYPE_REJECTED,
            "timestamp", LocalDateTime.now().format(ISO_FORMATTER),
            "operationNumber", loanRequest.getOperationNumber(),
            "channel", loanRequest.getChannel(),
            "amount", loanRequest.getAmount().toString(),
            "customerId", loanRequest.getCustomerId(),
            "loanRequestId", loanRequest.getId().toString(),
            "status", loanRequest.getStatus(),
            "rejectionReason", rejectionReason != null ? rejectionReason : "No especificado"
        );
    }

    private void sendToAuditSystem(Map<String, Object> eventPayload) {
        logger.debug("Enviando payload al sistema de auditoría: {}", eventPayload);
        logger.info("Evento enviado exitosamente al sistema de auditoría: {}",
            eventPayload.get("eventId"));
    }
}

// === ARCHIVO: src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java ===
package com.financial.loans.infrastructure.rest;

import com.financial.loans.application.service.LoanRequestService;
import com.financial.loans.infrastructure.rest.dto.LoanRequestDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanRequestController {

    private static final Logger logger = LoggerFactory.getLogger(LoanRequestController.class);
    private final LoanRequestService loanRequestService;

    public LoanRequestController(LoanRequestService loanRequestService) {
        this.loanRequestService = loanRequestService;
    }

    @PostMapping("/requests")
    public ResponseEntity<LoanRequestDTO> createLoanRequest(@Valid @RequestBody LoanRequestDTO requestDTO) {
        String correlationId = UUID.randomUUID().toString();
        MDC.put("correlationId", correlationId);
        MDC.put("operationNumber", requestDTO.operationNumber());
        MDC.put("channel", requestDTO.channel());
        
        try {
            logger.info("Iniciando procesamiento de solicitud de prestamo - operationNumber: {}, channel: {}", 
                    requestDTO.operationNumber(), requestDTO.channel());
            
            LoanRequestDTO result = loanRequestService.processLoanRequest(requestDTO);
            
            logger.info("Solicitud de prestamo procesada exitosamente - id: {}, status: {}", 
                    result.id(), result.status());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
            
        } catch (IllegalArgumentException e) {
            logger.warn("Solicitud rechazada por validacion de idempotencia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            logger.error("Error al procesar solicitud de prestamo: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } finally {
            MDC.clear();
        }
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<LoanRequestDTO> getLoanRequest(@PathVariable UUID id) {
        MDC.put("correlationId", UUID.randomUUID().toString());
        MDC.put("loanRequestId", id.toString());
        
        try {
            logger.info("Consultando solicitud de prestamo - id: {}", id);
            
            return loanRequestService.findById(id)
                    .map(dto -> {
                        logger.info("Solicitud encontrada - id: {}, status: {}", dto.id(), dto.status());
                        return ResponseEntity.ok(dto);
                    })
                    .orElseGet(() -> {
                        logger.warn("Solicitud no encontrada - id: {}", id);
                        return ResponseEntity.notFound().build();
                    });
                    
        } catch (Exception e) {
            logger.error("Error al consultar solicitud: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } finally {
            MDC.clear();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        logger.debug("Health check solicitado");
        return ResponseEntity.ok(Map.of("status", "UP", "service", "loans-api"));
    }
}

// === ARCHIVO: src/main/java/com/financial/loans/infrastructure/config/DatabaseConfig.java ===
package com.financial.loans.infrastructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.financial.loans.infrastructure.adapter")
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${spring.datasource.url:jdbc:postgresql://localhost:5432/loans}")
    private String jdbcUrl;

    @Value("${spring.datasource.username:postgres}")
    private String username;

    @Value("${spring.datasource.password:postgres}")
    private String password;

    @Value("${spring.datasource.hikari.maximum-pool-size:20}")
    private int maximumPoolSize;

    @Value("${spring.datasource.hikari.minimum-idle:5}")
    private int minimumIdle;

    @Value("${spring.datasource.hikari.connection-timeout:30000}")
    private long connectionTimeout;

    @Value("${spring.datasource.hikari.idle-timeout:600000}")
    private long idleTimeout;

    @Value("${spring.datasource.hikari.max-lifetime:1800000}")
    private long maxLifetime;

    @Value("${spring.jpa.hibernate.ddl-auto:validate}")
    private String hibernateDdlAuto;

    @Value("${spring.jpa.properties.hibernate.dialect:org.hibernate.dialect.PostgreSQLDialect}")
    private String hibernateDialect;

    @Value("${spring.jpa.properties.hibernate.format_sql:true}")
    private boolean formatSql;

    @Value("${spring.jpa.properties.hibernate.use_sql_comments:true}")
    private boolean useSqlComments;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size:20}")
    private int batchSize;

    @Value("${spring.jpa.properties.hibernate.order_inserts:true}")
    private boolean orderInserts;

    @Value("${spring.jpa.properties.hibernate.order_updates:true}")
    private boolean orderUpdates;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_versioned_data:true}")
    private boolean batchVersionedData;

    @Bean
    public DataSource dataSource() {
        logger.info("Configurando HikariCP datasource - jdbcUrl: {}, maximumPoolSize: {}", 
                jdbcUrl, maximumPoolSize);
        
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setMinimumIdle(minimumIdle);
        config.setConnectionTimeout(connectionTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifetime);
        config.setPoolName("LoansHikariPool");
        config.setAutoCommit(true);
        config.setConnectionTestQuery("SELECT 1");
        config.setRegisterMbeans(true);
        
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        
        HikariDataSource dataSource = new HikariDataSource(config);
        logger.info("Datasource configurado exitosamente - pool: {}", config.getPoolName());
        
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        logger.info("Configurando EntityManagerFactory con Hibernate");
        
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.financial.loans.infrastructure.persistence.entity");
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(false);
        em.setJpaVendorAdapter(vendorAdapter);
        
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", hibernateDialect);
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hibernateDdlAuto);
        hibernateProperties.setProperty("hibernate.format_sql", String.valueOf(formatSql));
        hibernateProperties.setProperty("hibernate.use_sql_comments", String.valueOf(useSqlComments));
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", String.valueOf(batchSize));
        hibernateProperties.setProperty("hibernate.order_inserts", String.valueOf(orderInserts));
        hibernateProperties.setProperty("hibernate.order_updates", String.valueOf(orderUpdates));
        hibernateProperties.setProperty("hibernate.jdbc.batch_versioned_data", String.valueOf(batchVersionedData));
        hibernateProperties.setProperty("hibernate.generate_statistics", "false");
        hibernateProperties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
        
        em.setJpaProperties(hibernateProperties);
        
        logger.info("EntityManagerFactory configurado - dialect: {}, ddl-auto: {}", 
                hibernateDialect, hibernateDdlAuto);
        
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        logger.info("Configurando TransactionManager");
        
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setDataSource(dataSource());
        transactionManager.setDefaultTimeout((int) TimeUnit.SECONDS.toSeconds(30));
        
        logger.info("TransactionManager configurado exitosamente");
        return transactionManager;
    }
}

// === ARCHIVO: src/main/java/com/financial/loans/infrastructure/config/LoggingConfig.java ===
package com.financial.loans.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

@Configuration
public class LoggingConfig {

    @Value("${app.logging.mdc.enabled:true}")
    private boolean mdcEnabled;

    @Value("${app.logging.request-body.enabled:false}")
    private boolean requestBodyLoggingEnabled;

    @Value("${app.logging.response-body.enabled:false}")
    private boolean responseBodyLoggingEnabled;

    @Value("${app.logging.exclude-paths:/api/v1/loans/health}")
    private String excludePaths;

    private static final Logger logger = LoggerFactory.getLogger(LoggingConfig.class);
    private static final Pattern EXCLUDE_PATTERN;

    static {
        String paths = "/api/v1/loans/health";
        EXCLUDE_PATTERN = Pattern.compile("^" + paths.replace(",", "|^") + "$");
    }

    @Bean
    @Order(1)
    public OncePerRequestFilter mdcLoggingFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, 
                    HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                
                String correlationId = request.getHeader("X-Correlation-ID");
                if (correlationId == null || correlationId.isBlank()) {
                    correlationId = UUID.randomUUID().toString();
                }
                
                if (mdcEnabled) {
                    MDC.put("correlationId", correlationId);
                    MDC.put("http.method", request.getMethod());
                    MDC.put("http.uri", request.getRequestURI());
                    MDC.put("http.query", request.getQueryString() != null ? request.getQueryString() : "");
                    MDC.put("http.remoteAddr", getClientIpAddress(request));
                    MDC.put("http.userAgent", request.getHeader("User-Agent"));
                }
                
                long startTime = System.currentTimeMillis();
                
                try {
                    logger.info("Request iniciada - method: {}, uri: {}, correlationId: {}", 
                            request.getMethod(), request.getRequestURI(), correlationId);
                    
                    filterChain.doFilter(request, response);
                    
                } finally {
                    long duration = System.currentTimeMillis() - startTime;
                    
                    logger.info("Request completada - method: {}, uri: {}, status: {}, duration: {}ms, correlationId: {}", 
                            request.getMethod(), request.getRequestURI(), response.getStatus(), 
                            duration, correlationId);
                    
                    MDC.clear();
                }
            }

            private String getClientIpAddress(HttpServletRequest request) {
                String xForwardedFor = request.getHeader("X-Forwarded-For");
                if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
                    return xForwardedFor.split(",")[0].trim();
                }
                
                String xRealIp = request.getHeader("X-Real-IP");
                if (xRealIp != null && !xRealIp.isEmpty()) {
                    return xRealIp;
                }
                
                return request.getRemoteAddr();
            }
        };
    }

    @Bean
    public Logger applicationLogger() {
        logger.info("LoggingConfig inicializado - mdcEnabled: {}, requestBodyLogging: {}, responseBodyLogging: {}", 
                mdcEnabled, requestBodyLoggingEnabled, responseBodyLoggingEnabled);
        return LoggerFactory.getLogger("com.financial.loans");
    }

    @Bean
    public Logger auditLogger() {
        Logger auditLogger = LoggerFactory.getLogger("com.financial.loans.audit");
        logger.info("Audit logger configurado");
        return auditLogger;
    }

    @Bean
    public Logger performanceLogger() {
        Logger perfLogger = LoggerFactory.getLogger("com.financial.loans.performance");
        logger.info("Performance logger configurado");
        return perfLogger;
    }

    private boolean shouldExclude(String path) {
        return EXCLUDE_PATTERN.matcher(path).matches();
    }
}

// === ARCHIVO: src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java ===
package com.financial.loans.application.service;

import com.financial.loans.domain.model.LoanRequest;
import com.financial.loans.domain.port.AuditEventPublisher;
import com.financial.loans.domain.port.LoanRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoanRequestService - Tests Unitarios")
class LoanRequestServiceTest {

    @Mock
    private LoanRequestRepository loanRequestRepository;

    @Mock
    private AuditEventPublisher auditEventPublisher;

    private LoanRequestService loanRequestService;

    @BeforeEach
    void setUp() {
        loanRequestService = new LoanRequestService(loanRequestRepository, auditEventPublisher);
    }

    @Nested
    @DisplayName("Escenario: Creación de nueva solicitud de préstamo")
    class NuevaSolicitud {

        @Test
        @DisplayName("Debe crear solicitud exitosamente cuando no existe idempotency key previa")
        void debeCrearSolicitudExitosamente_CuandoNoExisteIdempotencyKeyPrevia() {
            String operationNumber = "OP-2024-001234";
            String channel = "WEB";
            BigDecimal amount = new BigDecimal("50000.00");
            String customerId = "CUST-9876";

            when(loanRequestRepository.findByIdempotencyKey(any())).thenReturn(Optional.empty());
            when(loanRequestRepository.save(any(LoanRequest.class))).thenAnswer(invocation -> {
                LoanRequest request = invocation.getArgument(0);
                return request;
            });

            LoanRequest result = loanRequestService.createLoanRequest(operationNumber, channel, amount, customerId);

            assertNotNull(result);
            assertEquals(operationNumber, result.getOperationNumber());
            assertEquals(channel, result.getChannel());
            assertEquals(amount, result.getAmount());
            assertEquals(customerId, result.getCustomerId());
            assertEquals("PENDING", result.getStatus());

            verify(loanRequestRepository).findByIdempotencyKey(any());
            verify(loanRequestRepository).save(any(LoanRequest.class));
            verify(auditEventPublisher).publishLoanAcceptedEvent(any(LoanRequest.class));
        }

        @Test
        @DisplayName("Debe rechazar solicitud cuando el monto es menor al mínimo permitido")
        void debeRechazarSolicitud_CuandoMontoEsMenorAlMinimo() {
            String operationNumber = "OP-2024-001235";
            String channel = "MOBILE";
            BigDecimal amount = new BigDecimal("100.00");
            String customerId = "CUST-1234";

            when(loanRequestRepository.findByIdempotencyKey(any())).thenReturn(Optional.empty());

            LoanRequest result = loanRequestService.createLoanRequest(operationNumber, channel, amount, customerId);

            assertNotNull(result);
            assertEquals("REJECTED", result.getStatus());
            assertNotNull(result.getErrorMessage());
            assertTrue(result.getErrorMessage().contains("monto mínimo"));

            verify(loanRequestRepository).findByIdempotencyKey(any());
            verify(loanRequestRepository).save(any(LoanRequest.class));
            verify(auditEventPublisher).publishLoanRejectedEvent(any(LoanRequest.class), anyString());
        }

        @Test
        @DisplayName("Debe rechazar solicitud cuando el monto excede el máximo permitido")
        void debeRechazarSolicitud_CuandoMontoExcedeMaximo() {
            String operationNumber = "OP-2024-001236";
            String channel = "ATM";
            BigDecimal amount = new BigDecimal("1000000.00");
            String customerId = "CUST-5678";

            when(loanRequestRepository.findByIdempotencyKey(any())).thenReturn(Optional.empty());

            LoanRequest result = loanRequestService.createLoanRequest(operationNumber, channel, amount, customerId);

            assertNotNull(result);
            assertEquals("REJECTED", result.getStatus());
            assertTrue(result.getErrorMessage().contains("monto máximo"));

            verify(auditEventPublisher).publishLoanRejectedEvent(any(LoanRequest.class), anyString());
        }
    }

    @Nested
    @DisplayName("Escenario: Idempotencia de solicitudes")
    class Idempotencia {

        @Test
        @DisplayName("Debe retornar solicitud existente cuando la idempotency key ya existe")
        void debeRetornarSolicitudExistente_CuandoIdempotencyKeyYaExiste() {
            String operationNumber = "OP-2024-001237";
            String channel = "WEB";
            BigDecimal amount = new BigDecimal("75000.00");
            String customerId = "CUST-1111";

            LoanRequest existingRequest = LoanRequest.fromOperationAndChannel(operationNumber, channel);
            existingRequest = new LoanRequest() {
                @Override public UUID getId() { return UUID.randomUUID(); }
                @Override public String getOperationNumber() { return operationNumber; }
                @Override public String getChannel() { return channel; }
                @Override public BigDecimal getAmount() { return amount; }
                @Override public String getStatus() { return "APPROVED"; }
                @Override public LocalDateTime getCreatedAt() { return LocalDateTime.now(); }
                @Override public LocalDateTime getUpdatedAt() { return LocalDateTime.now(); }
                @Override public String getCustomerId() { return customerId; }
                @Override public String getErrorMessage() { return null; }
                @Override public String getIdempotencyKey() { return operationNumber + "-" + channel; }
                @Override public boolean isValidForIdempotency() { return true; }
                @Override public LoanRequest withId(UUID id) { return this; }
            };

            when(loanRequestRepository.findByIdempotencyKey(operationNumber + "-" + channel))
                    .thenReturn(Optional.of(existingRequest));

            LoanRequest result = loanRequestService.createLoanRequest(operationNumber, channel, amount, customerId);

            assertNotNull(result);
            assertEquals(operationNumber, result.getOperationNumber());
            assertEquals("APPROVED", result.getStatus());

            verify(loanRequestRepository).findByIdempotencyKey(operationNumber + "-" + channel);
            verify(loanRequestRepository, never()).save(any());
            verify(auditEventPublisher, never()).publishLoanAcceptedEvent(any());
        }

        @Test
        @DisplayName("Debe validar que la clave de idempotencia se genera correctamente")
        void debeValidarClaveIdempotencia_SeGeneraCorrectamente() {
            String operationNumber = "OP-2024-001238";
            String channel = "BRANCH";

            LoanRequest request = LoanRequest.fromOperationAndChannel(operationNumber, channel);

            String expectedKey = operationNumber + "-" + channel;
            assertEquals(expectedKey, request.getIdempotencyKey());
            assertTrue(request.isValidForIdempotency());
        }
    }

    @Nested
    @DisplayName("Escenario: Actualización de estado de solicitud")
    class ActualizacionEstado {

        @Test
        @DisplayName("Debe actualizar estado exitosamente")
        void debeActualizarEstadoExitosamente() {
            UUID requestId = UUID.randomUUID();
            String newStatus = "APPROVED";

            LoanRequest updatedRequest = LoanRequest.fromOperationAndChannel("OP-TEST", "WEB");
            when(loanRequestRepository.updateStatus(requestId, newStatus, null))
                    .thenReturn(updatedRequest);

            LoanRequest result = loanRequestService.updateStatus(requestId, newStatus, null);

            assertNotNull(result);
            verify(loanRequestRepository).updateStatus(requestId, newStatus, null);
        }

        @Test
        @DisplayName("Debe lanzar excepción cuando el ID no existe")
        void debeLanzarExcepcion_CuandoIdNoExiste() {
            UUID requestId = UUID.randomUUID();
            when(loanRequestRepository.updateStatus(eq(requestId), anyString(), anyString()))
                    .thenThrow(new RuntimeException("Solicitud no encontrada"));

            assertThrows(RuntimeException.class, () ->
                    loanRequestService.updateStatus(requestId, "APPROVED", null));
        }
    }

    @Nested
    @DisplayName("Escenario: Consulta de solicitudes")
    class Consulta {

        @Test
        @DisplayName("Debe encontrar solicitud por ID")
        void debeEncontrarSolicitudPorId() {
            UUID requestId = UUID.randomUUID();
            LoanRequest request = LoanRequest.fromOperationAndChannel("OP-CONSULT", "WEB");

            when(loanRequestRepository.findById(requestId)).thenReturn(Optional.of(request));

            Optional<LoanRequest> result = loanRequestService.findById(requestId);

            assertTrue(result.isPresent());
            assertEquals("OP-CONSULT", result.get().getOperationNumber());
        }

        @Test
        @DisplayName("Debe retornar vacío cuando no existe solicitud con el ID")
        void debeRetornarVacio_CuandoNoExisteSolicitud() {
            UUID requestId = UUID.randomUUID();
            when(loanRequestRepository.findById(requestId)).thenReturn(Optional.empty());

            Optional<LoanRequest> result = loanRequestService.findById(requestId);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("Escenario: Eventos de auditoría")
    class EventosAuditoria {

        @Test
        @DisplayName("Debe emitir evento de aceptación cuando la solicitud es aprobada")
        void debeEmitirEventoAceptacion_CuandoSolicitudEsAprobada() {
            String operationNumber = "OP-2024-001239";
            String channel = "WEB";
            BigDecimal amount = new BigDecimal("25000.00");
            String customerId = "CUST-2222";

            when(loanRequestRepository.findByIdempotencyKey(any())).thenReturn(Optional.empty());
            when(loanRequestRepository.save(any(LoanRequest.class))).thenAnswer(i -> i.getArgument(0));

            LoanRequest result = loanRequestService.createLoanRequest(operationNumber, channel, amount, customerId);

            ArgumentCaptor<LoanRequest> captor = ArgumentCaptor.forClass(LoanRequest.class);
            verify(auditEventPublisher).publishLoanAcceptedEvent(captor.capture());

            LoanRequest capturedRequest = captor.getValue();
            assertEquals(operationNumber, capturedRequest.getOperationNumber());
        }

        @Test
        @DisplayName("Debe emitir evento de rechazo cuando la solicitud es rechazada por validación")
        void debeEmitirEventoRechazo_CuandoSolicitudEsRechazada() {
            String operationNumber = "OP-2024-001240";
            String channel = "WEB";
            BigDecimal amount = new BigDecimal("50.00");
            String customerId = "CUST-3333";

            when(loanRequestRepository.findByIdempotencyKey(any())).thenReturn(Optional.empty());
            when(loanRequestRepository.save(any(LoanRequest.class))).thenAnswer(i -> i.getArgument(0));

            LoanRequest result = loanRequestService.createLoanRequest(operationNumber, channel, amount, customerId);

            ArgumentCaptor<LoanRequest> captor = ArgumentCaptor.forClass(LoanRequest.class);
            ArgumentCaptor<String> reasonCaptor = ArgumentCaptor.forClass(String.class);
            verify(auditEventPublisher).publishLoanRejectedEvent(captor.capture(), reasonCaptor.capture());

            assertEquals("REJECTED", result.getStatus());
            assertNotNull(reasonCaptor.getValue());
        }

        @Test
        @DisplayName("No debe emitir eventos duplicados en reintentos con misma idempotency key")
        void noDebeEmitirEventosDuplicados_EnReintentos() {
            String operationNumber = "OP-2024-001241";
            String channel = "MOBILE";
            BigDecimal amount = new BigDecimal("30000.00");
            String customerId = "CUST-4444";

            LoanRequest existingRequest = LoanRequest.fromOperationAndChannel(operationNumber, channel);
            when(loanRequestRepository.findByIdempotencyKey(any())).thenReturn(Optional.of(existingRequest));

            loanRequestService.createLoanRequest(operationNumber, channel, amount, customerId);

            verify(auditEventPublisher, never()).publishLoanAcceptedEvent(any());
            verify(auditEventPublisher, never()).publishLoanRejectedEvent(any(), any());
        }
    }
}

// === ARCHIVO: src/test/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepositoryTest.java ===
package com.financial.loans.infrastructure.adapter;


import com.financial.loans.application.service.Consulta;
import com.financial.loans.domain.model.LoanRequest;
import com.financial.loans.domain.port.LoanRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("LoanRequestJpaRepository - Tests de Integración")
class LoanRequestJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LoanRequestJpaRepository jpaRepository;

    private LoanRequestRepository repository;

    @BeforeEach
    void setUp() {
        repository = jpaRepository;
    }

    @Nested
    @DisplayName("Escenario: Persistencia de solicitudes de préstamo")
    class Persistencia {

        @Test
        @DisplayName("Debe guardar y recuperar solicitud por ID")
        void debeGuardarYRecuperarSolicitudPorId() {
            LoanRequest request = createSampleRequest("OP-SAVE-001", "WEB",
                    new BigDecimal("50000.00"), "CUST-001");

            LoanRequest saved = repository.save(request);
            assertNotNull(saved.getId());

            Optional<LoanRequest> found = repository.findById(saved.getId());
            assertTrue(found.isPresent());
            assertEquals("OP-SAVE-001", found.get().getOperationNumber());
            assertEquals("WEB", found.get().getChannel());
            assertEquals(new BigDecimal("50000.00"), found.get().getAmount());
        }

        @Test
        @DisplayName("Debe actualizar estado de solicitud correctamente")
        void debeActualizarEstadoDeSolicitud() {
            LoanRequest request = createSampleRequest("OP-UPDATE-001", "MOBILE",
                    new BigDecimal("25000.00"), "CUST-002");
            LoanRequest saved = repository.save(request);

            LoanRequest updated = repository.updateStatus(saved.getId(), "APPROVED", null);

            assertNotNull(updated);
            assertEquals("APPROVED", updated.getStatus());
            assertNotNull(updated.getUpdatedAt());
        }

        @Test
        @DisplayName("Debe guardar solicitud con todos los campos llenos")
        void debeGuardarSolicitudConTodosLosCampos() {
            LoanRequest request = LoanRequest.fromOperationAndChannel("OP-FULL-001", "ATM");
            request = request.withId(UUID.randomUUID());

            LoanRequest saved = repository.save(request);

            assertNotNull(saved.getId());
            assertNotNull(saved.getCreatedAt());
            assertNotNull(saved.getUpdatedAt());
            assertEquals("PENDING", saved.getStatus());
        }
    }

    @Nested
    @DisplayName("Escenario: Consulta por idempotency key")
    class ConsultaIdempotency {

        @Test
        @DisplayName("Debe encontrar solicitud por idempotency key exacta")
        void debeEncontrarSolicitudPorIdempotencyKey() {
            String operationNumber = "OP-IDEM-001";
            String channel = "WEB";
            LoanRequest request = createSampleRequest(operationNumber, channel,
                    new BigDecimal("100000.00"), "CUST-003");
            repository.save(request);

            String idempotencyKey = operationNumber + "-" + channel;
            Optional<LoanRequest> found = repository.findByIdempotencyKey(idempotencyKey);

            assertTrue(found.isPresent());
            assertEquals(idempotencyKey, found.get().getIdempotencyKey());
        }

        @Test
        @DisplayName("Debe retornar vacío para idempotency key inexistente")
        void debeRetornarVacioParaIdempotencyKeyInexistente() {
            Optional<LoanRequest> found = repository.findByIdempotencyKey("NONEXISTENT-KEY");
            assertTrue(found.isEmpty());
        }

        @Test
        @DisplayName("Debe distinguir solicitudes del mismo operationNumber en diferentes canales")
        void debeDistinguirMismaOperacionEnDiferentesCanales() {
            String operationNumber = "OP-SAME-001";

            LoanRequest webRequest = createSampleRequest(operationNumber, "WEB",
                    new BigDecimal("50000.00"), "CUST-004");
            repository.save(webRequest);

            LoanRequest mobileRequest = createSampleRequest(operationNumber, "MOBILE",
                    new BigDecimal("50000.00"), "CUST-004");
            repository.save(mobileRequest);

            Optional<LoanRequest> foundWeb = repository.findByIdempotencyKey(operationNumber + "-WEB");
            Optional<LoanRequest> foundMobile = repository.findByIdempotencyKey(operationNumber + "-MOBILE");

            assertTrue(foundWeb.isPresent());
            assertTrue(foundMobile.isPresent());
            assertNotEquals(foundWeb.get().getId(), foundMobile.get().getId());
        }
    }

    @Nested
    @DisplayName("Escenario: Consulta de ventana de idempotencia")
    class VentanaIdempotencia {

        @Test
        @DisplayName("Debe encontrar solicitud existente dentro de la ventana de 24 horas")
        void debeEncontrarSolicitudExistenteEnVentana() {
            String operationNumber = "OP-WINDOW-001";
            String channel = "BRANCH";

            LoanRequest request = createSampleRequest(operationNumber, channel,
                    new BigDecimal("75000.00"), "CUST-005");
            LoanRequest saved = repository.save(request);

            Optional<LoanRequest> found = repository.findExistingRequestWithinWindow(operationNumber, channel);

            assertTrue(found.isPresent());
            assertEquals(saved.getId(), found.get().getId());
        }

        @Test
        @DisplayName("Debe retornar vacío cuando no hay solicitud previa en la ventana")
        void debeRetornarVacio_CuandoNoHaySolicitudPrevia() {
            Optional<LoanRequest> found = repository.findExistingRequestWithinWindow("OP-NEW-001", "WEB");
            assertTrue(found.isEmpty());
        }
    }

    @Nested
    @DisplayName("Escenario: Manejo de errores en persistencia")
    class ManejoErrores {

        @Test
        @DisplayName("Debe lanzar excepción cuando se intenta actualizar ID inexistente")
        void debeLanzarExcepcion_CuandoActualizaIdInexistente() {
            UUID nonExistentId = UUID.randomUUID();

            assertThrows(Exception.class, () ->
                    repository.updateStatus(nonExistentId, "APPROVED", null));
        }

        @Test
        @DisplayName("Debe permitir guardar múltiples solicitudes con diferentes IDs")
        void debePermitirGuardarMultiplesSolicitudes() {
            LoanRequest request1 = createSampleRequest("OP-MULT-001", "WEB",
                    new BigDecimal("10000.00"), "CUST-006");
            LoanRequest request2 = createSampleRequest("OP-MULT-002", "MOBILE",
                    new BigDecimal("20000.00"), "CUST-007");
            LoanRequest request3 = createSampleRequest("OP-MULT-003", "ATM",
                    new BigDecimal("30000.00"), "CUST-008");

            LoanRequest saved1 = repository.save(request1);
            LoanRequest saved2 = repository.save(request2);
            LoanRequest saved3 = repository.save(request3);

            assertNotEquals(saved1.getId(), saved2.getId());
            assertNotEquals(saved2.getId(), saved3.getId());
            assertNotEquals(saved1.getId(), saved3.getId());

            assertTrue(repository.findById(saved1.getId()).isPresent());
            assertTrue(repository.findById(saved2.getId()).isPresent());
            assertTrue(repository.findById(saved3.getId()).isPresent());
        }
    }

    private LoanRequest createSampleRequest(String operationNumber, String channel,
                                            BigDecimal amount, String customerId) {
        LoanRequest request = LoanRequest.fromOperationAndChannel(operationNumber, channel);
        request = request.withId(UUID.randomUUID());
        return request;
    }
}
```
