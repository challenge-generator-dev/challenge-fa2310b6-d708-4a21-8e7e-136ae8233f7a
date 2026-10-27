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