package com.dev.common.exception;

import java.io.Serializable;
import java.util.Objects;

/**
 * Exception thrown when a requested resource is not found.
 *
 * <p>This exception is typically used when an entity or resource cannot be found
 * by its identifier.</p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * throw new ResourceNotFoundException("User", "123");
 * throw new ResourceNotFoundException("Order", orderId, "Order not found for customer");
 * }</pre>
 *
 * @author Dev Team
 * @version 1.0
 * @since 1.0
 */
public class ResourceNotFoundException extends BusinessException implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String ERROR_CODE = "RESOURCE_NOT_FOUND";
    private static final int HTTP_STATUS = 404;

    private final String resourceType;
    private final String resourceId;

    /**
     * Constructs a new resource not found exception.
     *
     * @param resourceType the type of resource (e.g., "User", "Order")
     * @param resourceId   the identifier of the resource
     */
    public ResourceNotFoundException(String resourceType, String resourceId) {
        super(ERROR_CODE, String.format("%s with id '%s' not found", resourceType, resourceId), HTTP_STATUS);
        this.resourceType = Objects.requireNonNull(resourceType, "resourceType must not be null");
        this.resourceId = Objects.requireNonNull(resourceId, "resourceId must not be null");
    }

    /**
     * Constructs a new resource not found exception with custom message.
     *
     * @param resourceType the type of resource
     * @param resourceId   the identifier of the resource
     * @param message      custom error message
     */
    public ResourceNotFoundException(String resourceType, String resourceId, String message) {
        super(ERROR_CODE, message, HTTP_STATUS);
        this.resourceType = Objects.requireNonNull(resourceType, "resourceType must not be null");
        this.resourceId = Objects.requireNonNull(resourceId, "resourceId must not be null");
    }

    /**
     * Returns the type of resource that was not found.
     *
     * @return the resource type
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * Returns the identifier of the resource that was not found.
     *
     * @return the resource id
     */
    public String getResourceId() {
        return resourceId;
    }

    @Override
    public String toString() {
        return String.format("ResourceNotFoundException[resourceType=%s, resourceId=%s, message=%s]",
                resourceType, resourceId, getMessage());
    }
}