package com.dev.common.exception;

import java.io.Serializable;
import java.util.Objects;

/**
 * Base exception for business logic errors.
 *
 * <p>This exception serves as the root for all business-related exceptions in microservices.
 * It provides a standardized way to handle business errors with error codes and detailed messages.</p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * throw new BusinessException("ORDER_NOT_FOUND", "Order with id 123 not found");
 * throw new BusinessException("INSUFFICIENT_FUNDS", "Balance too low", 400);
 * }</pre>
 *
 * @author Dev Team
 * @version 1.0
 * @since 1.0
 */
public class BusinessException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String errorCode;
    private final int httpStatus;
    private final String errorMessage;

    /**
     * Constructs a new business exception with the specified error code and message.
     *
     * @param errorCode    the business error code (e.g., "ORDER_NOT_FOUND")
     * @param errorMessage the detailed error message
     */
    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = Objects.requireNonNull(errorCode, "errorCode must not be null");
        this.errorMessage = Objects.requireNonNull(errorMessage, "errorMessage must not be null");
        this.httpStatus = 500;
    }

    /**
     * Constructs a new business exception with error code, message, and HTTP status.
     *
     * @param errorCode    the business error code
     * @param errorMessage the detailed error message
     * @param httpStatus   the HTTP status code to return
     */
    public BusinessException(String errorCode, String errorMessage, int httpStatus) {
        super(errorMessage);
        this.errorCode = Objects.requireNonNull(errorCode, "errorCode must not be null");
        this.errorMessage = Objects.requireNonNull(errorMessage, "errorMessage must not be null");
        this.httpStatus = httpStatus;
    }

    /**
     * Constructs a new business exception with error code, message, and cause.
     *
     * @param errorCode    the business error code
     * @param errorMessage the detailed error message
     * @param cause        the underlying cause
     */
    public BusinessException(String errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = Objects.requireNonNull(errorCode, "errorCode must not be null");
        this.errorMessage = Objects.requireNonNull(errorMessage, "errorMessage must not be null");
        this.httpStatus = 500;
    }

    /**
     * Returns the error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Returns the HTTP status code.
     *
     * @return the HTTP status code
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     * Returns the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return String.format("BusinessException[errorCode=%s, httpStatus=%d, message=%s]",
                errorCode, httpStatus, errorMessage);
    }
}