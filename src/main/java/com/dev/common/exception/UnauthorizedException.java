package com.dev.common.exception;

import java.io.Serializable;

/**
 * Exception thrown when authentication or authorization fails.
 *
 * <p>This exception can be used for both authentication failures (401) and
 * authorization failures (403) depending on the scenario.</p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * // Authentication failure (401)
 * throw new UnauthorizedException("Invalid credentials");
 *
 * // Authorization failure (403)
 * throw new UnauthorizedException("Insufficient permissions", 403);
 * }</pre>
 *
 * @author Dev Team
 * @version 1.0
 * @since 1.0
 */
public class UnauthorizedException extends BusinessException implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String ERROR_CODE = "UNAUTHORIZED";
    private static final int DEFAULT_HTTP_STATUS = 401;

    /**
     * Constructs a new unauthorized exception with the specified message.
     *
     * @param message the detail message
     */
    public UnauthorizedException(String message) {
        super(ERROR_CODE, message, DEFAULT_HTTP_STATUS);
    }

    /**
     * Constructs a new unauthorized exception with message and HTTP status.
     *
     * @param message    the detail message
     * @param httpStatus the HTTP status code (401 for auth, 403 for forbidden)
     */
    public UnauthorizedException(String message, int httpStatus) {
        super(ERROR_CODE, message, httpStatus);
    }

    /**
     * Constructs a new unauthorized exception for forbidden access (403).
     *
     * @param message the detail message
     * @return a new UnauthorizedException with 403 status
     */
    public static UnauthorizedException forbidden(String message) {
        return new UnauthorizedException(message, 403);
    }

    @Override
    public String toString() {
        return String.format("UnauthorizedException[httpStatus=%d, message=%s]",
                getHttpStatus(), getMessage());
    }
}