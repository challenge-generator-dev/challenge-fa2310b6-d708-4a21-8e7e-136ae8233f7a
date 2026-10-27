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