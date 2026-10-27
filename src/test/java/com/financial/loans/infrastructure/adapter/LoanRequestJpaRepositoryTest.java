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