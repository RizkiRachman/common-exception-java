package com.dev.common.exception;

import java.io.Serializable;
import java.util.Objects;

/**
 * Exception thrown when a rate limit has been exceeded.
 *
 * <p>This exception is framework-agnostic and can be used across different frameworks
 * (Spring, Quarkus, Micronaut, etc.) to indicate that a rate limit has been exceeded.</p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * throw new RateLimitExceededException("Rate limit exceeded for API: users");
 * throw new RateLimitExceededException("Too many requests", "api-users", 60);
 * }</pre>
 *
 * @author Dev Team
 * @version 1.0
 * @since 1.0
 */
public class RateLimitExceededException extends BusinessException implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String ERROR_CODE = "RATE_LIMIT_EXCEEDED";
    private static final int HTTP_STATUS = 429;

    private final String limitType;
    private final long retryAfterSeconds;

    /**
     * Constructs a new rate limit exception with the specified message.
     *
     * @param message the detail message
     */
    public RateLimitExceededException(String message) {
        super(ERROR_CODE, message, HTTP_STATUS);
        this.limitType = "default";
        this.retryAfterSeconds = 0;
    }

    /**
     * Constructs a new rate limit exception with message and limit type.
     *
     * @param message   the detail message
     * @param limitType the type of limit (e.g., "api-users", "requests-per-minute")
     */
    public RateLimitExceededException(String message, String limitType) {
        super(ERROR_CODE, message, HTTP_STATUS);
        this.limitType = Objects.requireNonNull(limitType, "limitType must not be null");
        this.retryAfterSeconds = 0;
    }

    /**
     * Constructs a new rate limit exception with message, limit type, and retry after.
     *
     * @param message          the detail message
     * @param limitType        the type of limit
     * @param retryAfterSeconds seconds until the client can retry
     */
    public RateLimitExceededException(String message, String limitType, long retryAfterSeconds) {
        super(ERROR_CODE, message, HTTP_STATUS);
        this.limitType = Objects.requireNonNull(limitType, "limitType must not be null");
        this.retryAfterSeconds = retryAfterSeconds;
    }

    /**
     * Returns the type of rate limit that was exceeded.
     *
     * @return the limit type
     */
    public String getLimitType() {
        return limitType;
    }

    /**
     * Returns the number of seconds the client should wait before retrying.
     *
     * @return retry after seconds, or 0 if not specified
     */
    public long getRetryAfterSeconds() {
        return retryAfterSeconds;
    }

    @Override
    public String toString() {
        return String.format("RateLimitExceededException[limitType=%s, retryAfter=%ds, message=%s]",
                limitType, retryAfterSeconds, getMessage());
    }
}