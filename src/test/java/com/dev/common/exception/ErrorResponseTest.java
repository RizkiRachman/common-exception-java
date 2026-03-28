package com.dev.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ErrorResponse}.
 *
 * @author Dev Team
 * @version 1.0
 * @since 1.0
 */
@DisplayName("ErrorResponse Tests")
class ErrorResponseTest {

    @Test
    @DisplayName("Should create error response using builder")
    void shouldCreateErrorResponseUsingBuilder() {
        ErrorResponse response = ErrorResponse.builder()
                .errorCode(400)
                .errorMessage("Bad Request")
                .detailMessage("Invalid input")
                .build();

        assertNotNull(response.getId());
        assertEquals(16, response.getId().length());
        assertEquals(400, response.getErrorCode());
        assertEquals("Bad Request", response.getErrorMessage());
        assertEquals("Invalid input", response.getDetailMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    @DisplayName("Should create rate limit response")
    void shouldCreateRateLimitResponse() {
        ErrorResponse response = ErrorResponse.rateLimit("Too many requests");

        assertEquals(429, response.getErrorCode());
        assertEquals("Too Many Requests", response.getErrorMessage());
        assertEquals("Too many requests", response.getDetailMessage());
    }

    @Test
    @DisplayName("Should create rate limit response with retry after")
    void shouldCreateRateLimitResponseWithRetryAfter() {
        ErrorResponse response = ErrorResponse.rateLimit("Rate limited", 60);

        assertEquals(429, response.getErrorCode());
        assertTrue(response.getDetailMessage().contains("Retry after: 60s"));
    }

    @Test
    @DisplayName("Should create server error response")
    void shouldCreateServerErrorResponse() {
        ErrorResponse response = ErrorResponse.serverError("Internal error");

        assertEquals(500, response.getErrorCode());
        assertEquals("Internal Server Error", response.getErrorMessage());
    }

    @Test
    @DisplayName("Should create bad request response")
    void shouldCreateBadRequestResponse() {
        ErrorResponse response = ErrorResponse.badRequest("Validation failed");

        assertEquals(400, response.getErrorCode());
        assertEquals("Bad Request", response.getErrorMessage());
    }

    @Test
    @DisplayName("Should create not found response")
    void shouldCreateNotFoundResponse() {
        ErrorResponse response = ErrorResponse.notFound("User not found");

        assertEquals(404, response.getErrorCode());
        assertEquals("Not Found", response.getErrorMessage());
    }

    @Test
    @DisplayName("Should create unauthorized response")
    void shouldCreateUnauthorizedResponse() {
        ErrorResponse response = ErrorResponse.unauthorized("Invalid token");

        assertEquals(401, response.getErrorCode());
        assertEquals("Unauthorized", response.getErrorMessage());
    }

    @Test
    @DisplayName("Should create response from BusinessException")
    void shouldCreateResponseFromBusinessException() {
        BusinessException exception = new BusinessException("TEST_ERROR", "Test message", 400);
        ErrorResponse response = ErrorResponse.fromException(exception);

        assertEquals(400, response.getErrorCode());
        assertEquals("Test message", response.getErrorMessage());
        assertEquals("Test message", response.getDetailMessage());
    }

    @Test
    @DisplayName("Should generate unique IDs")
    void shouldGenerateUniqueIds() {
        ErrorResponse response1 = ErrorResponse.builder().errorCode(500).errorMessage("Error").build();
        ErrorResponse response2 = ErrorResponse.builder().errorCode(500).errorMessage("Error").build();

        assertNotEquals(response1.getId(), response2.getId());
    }

    @Test
    @DisplayName("Should throw exception when errorMessage is null")
    void shouldThrowExceptionWhenErrorMessageIsNull() {
        assertThrows(NullPointerException.class, () -> {
            ErrorResponse.builder().errorCode(500).errorMessage(null).build();
        });
    }

    @Test
    @DisplayName("Should provide meaningful toString")
    void shouldProvideMeaningfulToString() {
        ErrorResponse response = ErrorResponse.serverError("Test");
        String str = response.toString();

        assertNotNull(str);
        assertTrue(str.contains("ErrorResponse"));
        assertTrue(str.contains("500"));
    }
}