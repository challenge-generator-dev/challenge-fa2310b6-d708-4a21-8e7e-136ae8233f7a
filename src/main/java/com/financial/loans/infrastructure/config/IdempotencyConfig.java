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