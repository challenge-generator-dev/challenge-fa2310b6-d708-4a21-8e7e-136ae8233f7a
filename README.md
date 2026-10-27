# Mejora de la Experiencia en Frameworks

El sistema de préstamos de una entidad financiera requiere una integración más robusta con su framework principal para mejorar la gestión de solicitudes de préstamos. Los actores involucrados son el originador de créditos, el motor antifraude, y el core bancario. El sistema debe manejar 1 500 solicitudes por segundo en hora pico, con un SLA del 99.9%. Las solicitudes deben ser idempotentes por número de operación y canal, con una ventana de 24 horas para reintentos. El sistema debe emitir un evento al sistema de auditoría por cada aceptación.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Experiencia en Frameworks |
| **Nivel** | advanced-l1 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Integración Inicial con Framework

**Objetivo:** Configurar el framework para manejar solicitudes de préstamos de forma idempotente.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Configurar el framework para aceptar solicitudes de préstamos.
- Implementar idempotencia por número de operación y canal.
- Emitir evento al sistema de auditoría por cada aceptación.

**Entregable:** Framework configurado para aceptar y procesar solicitudes de préstamos de forma idempotente.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo el framework maneja solicitudes repetidas.
- Piensa en cómo el framework puede interactuar con otros servicios.

</details>

### Fase 2: Gestión de Variables de Entorno

**Objetivo:** Configurar el framework para utilizar variables de entorno para la gestión de configuraciones.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Configurar el framework para leer variables de entorno.
- Utilizar variables de entorno para gestionar configuraciones como URLs de servicios externos y credenciales de bases de datos.

**Entregable:** Framework configurado para utilizar variables de entorno para la gestión de configuraciones.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la seguridad y la gestión de configuraciones al utilizar variables de entorno.
- Piensa en cómo el framework puede adaptarse a diferentes entornos (desarrollo, staging, producción).

</details>

### Fase 3: Conexión con Base de Datos

**Objetivo:** Configurar el framework para conectarse con una base de datos utilizando un ORM.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Configurar el framework para conectarse con una base de datos.
- Utilizar un ORM para mapear entidades y realizar operaciones CRUD.

**Entregable:** Framework configurado para conectarse con una base de datos utilizando un ORM.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la eficiencia y la seguridad al conectarse con una base de datos.
- Piensa en cómo el ORM puede simplificar las operaciones CRUD.

</details>

### Fase 4: Generación de Registros de Trazos

**Objetivo:** Configurar el framework para generar registros de trazos para la depuración y el seguimiento de solicitudes.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Configurar el framework para generar registros de trazos.
- Asegurar que los registros incluyan información relevante para la depuración y el seguimiento de solicitudes.

**Entregable:** Framework configurado para generar registros de trazos para la depuración y el seguimiento de solicitudes.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la cantidad y la calidad de la información que se incluirá en los registros de trazos.
- Piensa en cómo los registros de trazos pueden ayudar en la depuración y el seguimiento de solicitudes.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es un framework y para qué sirve en el contexto de este reto?
- **paraQueSirve**: ¿Para qué sirve la idempotencia en el procesamiento de solicitudes de préstamos?
- **comoSeUsa**: ¿Cómo se utiliza un ORM para conectarse con una base de datos en este reto?
- **erroresComunes**: ¿Cuáles son los errores comunes al configurar variables de entorno y cómo se pueden evitar?
- **queDecisionesImplica**: ¿Qué decisiones implica la elección de un ORM para este reto?

## Criterios de Evaluacion

- Configurar el framework para aceptar y procesar solicitudes de préstamos de forma idempotente.
- Utilizar variables de entorno para gestionar configuraciones.
- Conectar el framework con una base de datos utilizando un ORM.
- Generar registros de trazos para la depuración y el seguimiento de solicitudes.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
