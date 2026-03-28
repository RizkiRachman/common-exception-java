package com.dev.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link BusinessException}.
 *
 * @author Dev Team
 * @version 1.0
 * @since 1.0
 */
@DisplayName("BusinessException Tests")
class BusinessExceptionTest {

    @Test
    @DisplayName("Should create exception with error code and message")
    void shouldCreateExceptionWithErrorCodeAndMessage() {
        BusinessException exception = new BusinessException("ORDER_NOT_FOUND", "Order not found");

        assertEquals("ORDER_NOT_FOUND", exception.getErrorCode());
        assertEquals("Order not found", exception.getErrorMessage());
        assertEquals(500, exception.getHttpStatus());
        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should create exception with custom HTTP status")
    void shouldCreateExceptionWithCustomHttpStatus() {
        BusinessException exception = new BusinessException("VALIDATION_ERROR", "Invalid input", 400);

        assertEquals("VALIDATION_ERROR", exception.getErrorCode());
        assertEquals("Invalid input", exception.getErrorMessage());
        assertEquals(400, exception.getHttpStatus());
    }

    @Test
    @DisplayName("Should create exception with cause")
    void shouldCreateExceptionWithCause() {
        Throwable cause = new RuntimeException("Original error");
        BusinessException exception = new BusinessException("DB_ERROR", "Database error", cause);

        assertEquals("DB_ERROR", exception.getErrorCode());
        assertEquals("Database error", exception.getErrorMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Should throw exception when errorCode is null")
    void shouldThrowExceptionWhenErrorCodeIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new BusinessException(null, "message");
        });
    }

    @Test
    @DisplayName("Should throw exception when errorMessage is null")
    void shouldThrowExceptionWhenErrorMessageIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new BusinessException("CODE", null);
        });
    }

    @Test
    @DisplayName("Should provide meaningful toString")
    void shouldProvideMeaningfulToString() {
        BusinessException exception = new BusinessException("TEST_CODE", "Test message", 400);
        String str = exception.toString();

        assertNotNull(str);
        assertTrue(str.contains("BusinessException"));
        assertTrue(str.contains("TEST_CODE"));
        assertTrue(str.contains("400"));
    }
}