package com.dev.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ValidationException}.
 *
 * @author Dev Team
 * @version 1.0
 * @since 1.0
 */
@DisplayName("ValidationException Tests")
class ValidationExceptionTest {

    @Test
    @DisplayName("Should create empty validation exception")
    void shouldCreateEmptyValidationException() {
        ValidationException exception = new ValidationException();

        assertEquals("VALIDATION_ERROR", exception.getErrorCode());
        assertEquals("Validation failed", exception.getErrorMessage());
        assertEquals(400, exception.getHttpStatus());
        assertFalse(exception.hasErrors());
        assertEquals(0, exception.getErrorCount());
    }

    @Test
    @DisplayName("Should create exception with single error")
    void shouldCreateExceptionWithSingleError() {
        ValidationException exception = new ValidationException("email", "Invalid email format");

        assertTrue(exception.hasErrors());
        assertEquals(1, exception.getErrorCount());
        assertEquals("email", exception.getErrors().get(0).getField());
        assertEquals("Invalid email format", exception.getErrors().get(0).getMessage());
    }

    @Test
    @DisplayName("Should add multiple errors")
    void shouldAddMultipleErrors() {
        ValidationException exception = new ValidationException()
                .addError("email", "Invalid format")
                .addError("age", "Must be at least 18")
                .addError("name", "Required");

        assertEquals(3, exception.getErrorCount());
        assertTrue(exception.hasErrors());
    }

    @Test
    @DisplayName("Should add ValidationError object")
    void shouldAddValidationErrorObject() {
        ValidationException.ValidationError error = new ValidationException.ValidationError(
                "username", "Too short"
        );
        ValidationException exception = new ValidationException(error);

        assertEquals(1, exception.getErrorCount());
        assertEquals("username", exception.getErrors().get(0).getField());
    }

    @Test
    @DisplayName("Should return unmodifiable error list")
    void shouldReturnUnmodifiableErrorList() {
        ValidationException exception = new ValidationException("field", "error");

        assertThrows(UnsupportedOperationException.class, () -> {
            exception.getErrors().add(new ValidationException.ValidationError("x", "y"));
        });
    }

    @Test
    @DisplayName("Should throw exception when field is null")
    void shouldThrowExceptionWhenFieldIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new ValidationException.ValidationError(null, "message");
        });
    }

    @Test
    @DisplayName("Should throw exception when message is null")
    void shouldThrowExceptionWhenMessageIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new ValidationException.ValidationError("field", null);
        });
    }

    @Test
    @DisplayName("Should provide meaningful ValidationError toString")
    void shouldProvideMeaningfulValidationErrorToString() {
        ValidationException.ValidationError error = new ValidationException.ValidationError(
                "email", "Invalid"
        );
        String str = error.toString();

        assertNotNull(str);
        assertTrue(str.contains("ValidationError"));
        assertTrue(str.contains("email"));
    }

    @Test
    @DisplayName("Should support method chaining")
    void shouldSupportMethodChaining() {
        ValidationException exception = new ValidationException()
                .addError("a", "error a")
                .addError("b", "error b");

        assertEquals(2, exception.getErrorCount());
    }
}