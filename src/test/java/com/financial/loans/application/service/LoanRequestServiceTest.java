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