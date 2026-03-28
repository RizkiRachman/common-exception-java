package com.dev.common.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Exception thrown when validation fails.
 *
 * <p>This exception can hold multiple validation errors for batch validation scenarios.
 * It is framework-agnostic and suitable for use in microservices.</p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * // Single validation error
 * throw new ValidationException("email", "Email format is invalid");
 *
 * // Multiple validation errors
 * ValidationException ex = new ValidationException();
 * ex.addError("email", "Invalid format");
 * ex.addError("age", "Must be at least 18");
 * throw ex;
 * }</pre>
 *
 * @author Dev Team
 * @version 1.0
 * @since 1.0
 */
public class ValidationException extends BusinessException implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String ERROR_CODE = "VALIDATION_ERROR";
    private static final int HTTP_STATUS = 400;

    private final List<ValidationError> errors;

    /**
     * Represents a single validation error.
     */
    public static class ValidationError implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String field;
        private final String message;

        public ValidationError(String field, String message) {
            this.field = Objects.requireNonNull(field, "field must not be null");
            this.message = Objects.requireNonNull(message, "message must not be null");
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return String.format("ValidationError[field=%s, message=%s]", field, message);
        }
    }

    /**
     * Constructs a new validation exception.
     */
    public ValidationException() {
        super(ERROR_CODE, "Validation failed", HTTP_STATUS);
        this.errors = new ArrayList<>();
    }

    /**
     * Constructs a new validation exception with a single error.
     *
     * @param field   the field that failed validation
     * @param message the error message
     */
    public ValidationException(String field, String message) {
        super(ERROR_CODE, "Validation failed", HTTP_STATUS);
        this.errors = new ArrayList<>();
        addError(field, message);
    }

    /**
     * Constructs a new validation exception with a single error.
     *
     * @param error the validation error
     */
    public ValidationException(ValidationError error) {
        super(ERROR_CODE, "Validation failed", HTTP_STATUS);
        this.errors = new ArrayList<>();
        this.errors.add(Objects.requireNonNull(error, "error must not be null"));
    }

    /**
     * Adds a validation error.
     *
     * @param field   the field name
     * @param message the error message
     * @return this exception for method chaining
     */
    public ValidationException addError(String field, String message) {
        errors.add(new ValidationError(field, message));
        return this;
    }

    /**
     * Adds a validation error.
     *
     * @param error the validation error to add
     * @return this exception for method chaining
     */
    public ValidationException addError(ValidationError error) {
        errors.add(Objects.requireNonNull(error, "error must not be null"));
        return this;
    }

    /**
     * Returns an unmodifiable list of validation errors.
     *
     * @return list of validation errors
     */
    public List<ValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    /**
     * Returns whether there are any validation errors.
     *
     * @return true if has errors, false otherwise
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Returns the number of validation errors.
     *
     * @return the error count
     */
    public int getErrorCount() {
        return errors.size();
    }

    @Override
    public String toString() {
        return String.format("ValidationException[errorCount=%d, errors=%s]", errors.size(), errors);
    }
}