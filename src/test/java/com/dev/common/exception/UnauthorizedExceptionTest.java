package com.dev.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UnauthorizedException}.
 *
 * @author Dev Team
 * @version 1.0
 * @since 1.0
 */
@DisplayName("UnauthorizedException Tests")
class UnauthorizedExceptionTest {

    @Test
    @DisplayName("Should create exception with default status 401")
    void shouldCreateExceptionWithDefaultStatus() {
        UnauthorizedException exception = new UnauthorizedException("Invalid credentials");

        assertEquals("Invalid credentials", exception.getMessage());
        assertEquals("UNAUTHORIZED", exception.getErrorCode());
        assertEquals(401, exception.getHttpStatus());
    }

    @Test
    @DisplayName("Should create exception with custom status")
    void shouldCreateExceptionWithCustomStatus() {
        UnauthorizedException exception = new UnauthorizedException("Forbidden", 403);

        assertEquals("Forbidden", exception.getMessage());
        assertEquals(403, exception.getHttpStatus());
    }

    @Test
    @DisplayName("Should create forbidden exception using factory method")
    void shouldCreateForbiddenException() {
        UnauthorizedException exception = UnauthorizedException.forbidden("Admin access required");

        assertEquals("Admin access required", exception.getMessage());
        assertEquals(403, exception.getHttpStatus());
        assertEquals("UNAUTHORIZED", exception.getErrorCode());
    }

    @Test
    @DisplayName("Should inherit from BusinessException")
    void shouldInheritFromBusinessException() {
        UnauthorizedException exception = new UnauthorizedException("test");
        assertTrue(exception instanceof BusinessException);
    }

    @Test
    @DisplayName("Should provide meaningful toString")
    void shouldProvideMeaningfulToString() {
        UnauthorizedException exception = new UnauthorizedException("Access denied", 403);
        String str = exception.toString();

        assertNotNull(str);
        assertTrue(str.contains("UnauthorizedException"));
        assertTrue(str.contains("403"));
    }
}