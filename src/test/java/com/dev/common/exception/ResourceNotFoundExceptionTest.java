package com.dev.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ResourceNotFoundException}.
 *
 * @author Dev Team
 * @version 1.0
 * @since 1.0
 */
@DisplayName("ResourceNotFoundException Tests")
class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("Should create exception with resource type and id")
    void shouldCreateExceptionWithResourceTypeAndId() {
        ResourceNotFoundException exception = new ResourceNotFoundException("User", "123");

        assertEquals("User", exception.getResourceType());
        assertEquals("123", exception.getResourceId());
        assertEquals("RESOURCE_NOT_FOUND", exception.getErrorCode());
        assertEquals(404, exception.getHttpStatus());
        assertEquals("User with id '123' not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should create exception with custom message")
    void shouldCreateExceptionWithCustomMessage() {
        ResourceNotFoundException exception = new ResourceNotFoundException(
                "Order", "456", "Order not found for this customer"
        );

        assertEquals("Order", exception.getResourceType());
        assertEquals("456", exception.getResourceId());
        assertEquals("Order not found for this customer", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when resourceType is null")
    void shouldThrowExceptionWhenResourceTypeIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new ResourceNotFoundException(null, "123");
        });
    }

    @Test
    @DisplayName("Should throw exception when resourceId is null")
    void shouldThrowExceptionWhenResourceIdIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new ResourceNotFoundException("User", null);
        });
    }

    @Test
    @DisplayName("Should inherit from BusinessException")
    void shouldInheritFromBusinessException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Test", "1");
        assertTrue(exception instanceof BusinessException);
    }

    @Test
    @DisplayName("Should provide meaningful toString")
    void shouldProvideMeaningfulToString() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Product", "SKU-001");
        String str = exception.toString();

        assertNotNull(str);
        assertTrue(str.contains("ResourceNotFoundException"));
        assertTrue(str.contains("Product"));
        assertTrue(str.contains("SKU-001"));
    }
}