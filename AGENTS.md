# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Mejora de la Experiencia en Frameworks**.

| | |
|---|---|
| Tema | Experiencia en Frameworks |
| Nivel | advanced-l1 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean |
| Tiempo estimado | 8 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web 3.5.6
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.springframework.boot:spring-boot-starter-actuator n/a
- org.springframework.boot:spring-boot-starter-aop n/a
- com.fasterxml.jackson.core:jackson-databind n/a
- org.postgresql:postgresql 42.7.3
- org.projectlombok:lombok 1.18.34
- org.springframework.boot:spring-boot-starter-test n/a
- org.mockito:mockito-core 5.12.0
- org.testcontainers:postgresql 1.20.1

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Integración Inicial con Framework**: Framework configurado para aceptar y procesar solicitudes de préstamos de forma idempotente.
- **Fase 2 — Gestión de Variables de Entorno**: Framework configurado para utilizar variables de entorno para la gestión de configuraciones.
- **Fase 3 — Conexión con Base de Datos**: Framework configurado para conectarse con una base de datos utilizando un ORM.
- **Fase 4 — Generación de Registros de Trazos**: Framework configurado para generar registros de trazos para la depuración y el seguimiento de solicitudes.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Referencias colgando (53)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/financial/loans/LoansApplication.java` — `com.financial.loans.application.port.out.AuditEventPublisher`
      El import com.financial.loans.application.port.out.AuditEventPublisher usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/financial/loans/LoansApplication.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/loans/infrastructure/config/DatabaseConfig.java` — `com.zaxxer.hikari`
      El import com.zaxxer.hikari.HikariConfig pertenece a com.zaxxer.hikari, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/loans/infrastructure/config/DatabaseConfig.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/loans/infrastructure/config/LoggingConfig.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/financial/loans/LoansApplication.java` — `DatabaseConfig.getUrl`
      Se invoca `getUrl` sobre `DatabaseConfig`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/LoansApplication.java` — `DatabaseConfig.getUsername`
      Se invoca `getUsername` sobre `DatabaseConfig`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getId`
      Se invoca `getId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getOperationNumber`
      Se invoca `getOperationNumber` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getChannel`
      Se invoca `getChannel` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getAmount`
      Se invoca `getAmount` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getStatus`
      Se invoca `getStatus` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getCreatedAt`
      Se invoca `getCreatedAt` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getUpdatedAt`
      Se invoca `getUpdatedAt` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getCustomerId`
      Se invoca `getCustomerId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java` — `LoanRequest.getErrorMessage`
      Se invoca `getErrorMessage` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequestDTO.operationNumber`
      Se invoca `operationNumber` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequestDTO.channel`
      Se invoca `channel` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequestDTO.amount`
      Se invoca `amount` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequest.getStatus`
      Se invoca `getStatus` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequest.getId`
      Se invoca `getId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequest.getAmount`
      Se invoca `getAmount` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/application/service/LoanRequestService.java` — `LoanRequestDTO.customerId`
      Se invoca `customerId` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `LoanRequest.getOperationNumber`
      Se invoca `getOperationNumber` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `LoanRequest.getCustomerId`
      Se invoca `getCustomerId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `LoanRequest.getChannel`
      Se invoca `getChannel` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `LoanRequest.getAmount`
      Se invoca `getAmount` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `LoanRequest.getId`
      Se invoca `getId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java` — `LoanRequest.getStatus`
      Se invoca `getStatus` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java` — `LoanRequestDTO.operationNumber`
      Se invoca `operationNumber` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java` — `LoanRequestDTO.channel`
      Se invoca `channel` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java` — `LoanRequestDTO.id`
      Se invoca `id` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java` — `LoanRequestDTO.status`
      Se invoca `status` sobre `LoanRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java` — `LoanRequestService.findById`
      Se invoca `findById` sobre `LoanRequestService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.getOperationNumber`
      Se invoca `getOperationNumber` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.getChannel`
      Se invoca `getChannel` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.getAmount`
      Se invoca `getAmount` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.getCustomerId`
      Se invoca `getCustomerId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.getStatus`
      Se invoca `getStatus` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.getErrorMessage`
      Se invoca `getErrorMessage` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequestService.updateStatus`
      Se invoca `updateStatus` sobre `LoanRequestService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequestService.findById`
      Se invoca `findById` sobre `LoanRequestService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.isPresent`
      Se invoca `isPresent` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.get`
      Se invoca `get` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java` — `LoanRequest.isEmpty`
      Se invoca `isEmpty` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepositoryTest.java` — `LoanRequest.getId`
      Se invoca `getId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepositoryTest.java` — `LoanRequest.getStatus`
      Se invoca `getStatus` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepositoryTest.java` — `LoanRequest.getUpdatedAt`
      Se invoca `getUpdatedAt` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepositoryTest.java` — `LoanRequest.withId`
      Se invoca `withId` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepositoryTest.java` — `LoanRequest.getCreatedAt`
      Se invoca `getCreatedAt` sobre `LoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (17)

- `pom.xml`
- `src/main/java/com/financial/loans/LoansApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/financial/loans/domain/model/LoanRequest.java`
- `src/main/java/com/financial/loans/domain/port/LoanRequestRepository.java`
- `src/main/java/com/financial/loans/domain/port/AuditEventPublisher.java`
- `src/main/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepository.java`
- `src/main/java/com/financial/loans/infrastructure/rest/dto/LoanRequestDTO.java`
- `src/main/java/com/financial/loans/application/service/LoanRequestService.java`
- `src/main/java/com/financial/loans/infrastructure/persistence/entity/LoanRequestEntity.java`
- `src/main/java/com/financial/loans/infrastructure/config/IdempotencyConfig.java`
- `src/main/java/com/financial/loans/infrastructure/event/AuditEventPublisherImpl.java`
- `src/main/java/com/financial/loans/infrastructure/rest/LoanRequestController.java`
- `src/main/java/com/financial/loans/infrastructure/config/DatabaseConfig.java`
- `src/main/java/com/financial/loans/infrastructure/config/LoggingConfig.java`
- `src/test/java/com/financial/loans/application/service/LoanRequestServiceTest.java`
- `src/test/java/com/financial/loans/infrastructure/adapter/LoanRequestJpaRepositoryTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/financial/loans`
- `src/main/java/com/financial/loans/domain`
- `src/main/java/com/financial/loans/domain/model`
- `src/main/java/com/financial/loans/domain/port`
- `src/main/java/com/financial/loans/application`
- `src/main/java/com/financial/loans/application/service`
- `src/main/java/com/financial/loans/infrastructure`
- `src/main/java/com/financial/loans/infrastructure/adapter`
- `src/main/java/com/financial/loans/infrastructure/config`
- `src/main/java/com/financial/loans/infrastructure/event`
- `src/main/java/com/financial/loans/infrastructure/persistence`
- `src/main/java/com/financial/loans/infrastructure/rest`
- `src/main/resources`
- `src/test/java/com/financial/loans`

## Verificacion

```bash
mvn clean compile
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Brecha que el reto ataca: Chapter Backend, Especialidad Desarrollador, Tecnolog�a Java, Advanced � Experiencia comprobable en al menos un marco de trabajo (Framework) en su lenguaje de programaci�n principal (por ejemplo, Spring Framework en Java o ASP .net core en C#) y puede realizar el enrutamiento de solicitudes, controlar las variables de entorno, generar registros de rastros (Logs) y conectarse con bases de datos utilizando un Mapeador Relacional de Objetos (ORM). � El candidato es un desarrollador Backend Advanced con experiencia en estructuras de datos y patrones GRASP. Est� listo para profundizar en frameworks como Spring Boot.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
