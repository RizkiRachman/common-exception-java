package com.dev.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link RateLimitExceededException}.
 *
 * @author Dev Team
 * @version 1.0
 * @since 1.0
 */
@DisplayName("RateLimitExceededException Tests")
class RateLimitExceededExceptionTest {

    @Test
    @DisplayName("Should create exception with message only")
    void shouldCreateExceptionWithMessageOnly() {
        RateLimitExceededException exception = new RateLimitExceededException("Rate limit exceeded");

        assertEquals("Rate limit exceeded", exception.getMessage());
        assertEquals("default", exception.getLimitType());
        assertEquals(0, exception.getRetryAfterSeconds());
        assertEquals("RATE_LIMIT_EXCEEDED", exception.getErrorCode());
        assertEquals(429, exception.getHttpStatus());
    }

    @Test
    @DisplayName("Should create exception with limit type")
    void shouldCreateExceptionWithLimitType() {
        RateLimitExceededException exception = new RateLimitExceededException(
                "Too many requests", "api-users"
        );

        assertEquals("Too many requests", exception.getMessage());
        assertEquals("api-users", exception.getLimitType());
        assertEquals(0, exception.getRetryAfterSeconds());
    }

    @Test
    @DisplayName("Should create exception with retry after")
    void shouldCreateExceptionWithRetryAfter() {
        RateLimitExceededException exception = new RateLimitExceededException(
                "Rate limit exceeded", "api-orders", 60
        );

        assertEquals("Rate limit exceeded", exception.getMessage());
        assertEquals("api-orders", exception.getLimitType());
        assertEquals(60, exception.getRetryAfterSeconds());
    }

    @Test
    @DisplayName("Should throw exception when limitType is null")
    void shouldThrowExceptionWhenLimitTypeIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new RateLimitExceededException("message", null);
        });
    }

    @Test
    @DisplayName("Should inherit from BusinessException")
    void shouldInheritFromBusinessException() {
        RateLimitExceededException exception = new RateLimitExceededException("test");
        assertTrue(exception instanceof BusinessException);
    }

    @Test
    @DisplayName("Should provide meaningful toString")
    void shouldProvideMeaningfulToString() {
        RateLimitExceededException exception = new RateLimitExceededException(
                "Rate limit hit", "api-users", 30
        );
        String str = exception.toString();

        assertNotNull(str);
        assertTrue(str.contains("RateLimitExceededException"));
        assertTrue(str.contains("api-users"));
        assertTrue(str.contains("30"));
    }
}