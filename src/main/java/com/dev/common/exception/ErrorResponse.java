package com.dev.common.exception;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Standardized error response for API errors.
 *
 * <p>This class provides a consistent structure for error responses across all microservices.
 * It is framework-agnostic and can be used with Spring, Quarkus, Micronaut, or any other framework.</p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * // Using factory methods
 * ErrorResponse response = ErrorResponse.rateLimit("Too many requests", 60);
 *
 * // Using builder
 * ErrorResponse response = ErrorResponse.builder()
 *     .errorCode(404)
 *     .errorMessage("Not Found")
 *     .detailMessage("User with id 123 not found")
 *     .build();
 * }</pre>
 *
 * @author Dev Team
 * @version 1.0
 * @since 1.0
 */
public final class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final int errorCode;
    private final String errorMessage;
    private final String detailMessage;
    private final String timestamp;

    private ErrorResponse(Builder builder) {
        this.id = builder.id;
        this.errorCode = builder.errorCode;
        this.errorMessage = builder.errorMessage;
        this.detailMessage = builder.detailMessage;
        this.timestamp = builder.timestamp;
    }

    /**
     * Generates a fast unique ID for error tracking.
     *
     * @return a unique error ID
     */
    private static String generateErrorId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(16);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 16; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * Creates an error response for rate limiting (429).
     *
     * @param detailMessage detailed error message
     * @return the error response
     */
    public static ErrorResponse rateLimit(String detailMessage) {
        return builder()
                .errorCode(429)
                .errorMessage("Too Many Requests")
                .detailMessage(detailMessage)
                .build();
    }

    /**
     * Creates an error response for rate limiting with retry after.
     *
     * @param detailMessage detailed error message
     * @param retryAfter    seconds until retry
     * @return the error response
     */
    public static ErrorResponse rateLimit(String detailMessage, long retryAfter) {
        return builder()
                .errorCode(429)
                .errorMessage("Too Many Requests")
                .detailMessage(detailMessage + " (Retry after: " + retryAfter + "s)")
                .build();
    }

    /**
     * Creates an error response for server errors (500).
     *
     * @param detailMessage detailed error message
     * @return the error response
     */
    public static ErrorResponse serverError(String detailMessage) {
        return builder()
                .errorCode(500)
                .errorMessage("Internal Server Error")
                .detailMessage(detailMessage)
                .build();
    }

    /**
     * Creates an error response for bad requests (400).
     *
     * @param detailMessage detailed error message
     * @return the error response
     */
    public static ErrorResponse badRequest(String detailMessage) {
        return builder()
                .errorCode(400)
                .errorMessage("Bad Request")
                .detailMessage(detailMessage)
                .build();
    }

    /**
     * Creates an error response for not found (404).
     *
     * @param detailMessage detailed error message
     * @return the error response
     */
    public static ErrorResponse notFound(String detailMessage) {
        return builder()
                .errorCode(404)
                .errorMessage("Not Found")
                .detailMessage(detailMessage)
                .build();
    }

    /**
     * Creates an error response for unauthorized access (401).
     *
     * @param detailMessage detailed error message
     * @return the error response
     */
    public static ErrorResponse unauthorized(String detailMessage) {
        return builder()
                .errorCode(401)
                .errorMessage("Unauthorized")
                .detailMessage(detailMessage)
                .build();
    }

    /**
     * Creates an error response from a business exception.
     *
     * @param exception the business exception
     * @return the error response
     */
    public static ErrorResponse fromException(BusinessException exception) {
        return builder()
                .errorCode(exception.getHttpStatus())
                .errorMessage(exception.getErrorMessage())
                .detailMessage(exception.getMessage())
                .build();
    }

    /**
     * Returns a new builder instance.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    // Getters
    public String getId() {
        return id;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("ErrorResponse[id=%s, code=%d, message=%s, timestamp=%s]",
                id, errorCode, errorMessage, timestamp);
    }

    /**
     * Builder for ErrorResponse.
     */
    public static class Builder {
        private String id = generateErrorId();
        private int errorCode;
        private String errorMessage;
        private String detailMessage;
        private String timestamp = Instant.now().toString();

        public Builder id(String id) {
            this.id = Objects.requireNonNull(id, "id must not be null");
            return this;
        }

        public Builder errorCode(int errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = Objects.requireNonNull(errorMessage, "errorMessage must not be null");
            return this;
        }

        public Builder detailMessage(String detailMessage) {
            this.detailMessage = detailMessage;
            return this;
        }

        public Builder timestamp(String timestamp) {
            this.timestamp = Objects.requireNonNull(timestamp, "timestamp must not be null");
            return this;
        }

        public ErrorResponse build() {
            Objects.requireNonNull(id, "id must not be null");
            Objects.requireNonNull(errorMessage, "errorMessage must not be null");
            Objects.requireNonNull(timestamp, "timestamp must not be null");
            return new ErrorResponse(this);
        }
    }
}