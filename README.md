# Common Exception Java

[![Java 17+](https://img.shields.io/badge/Java-17%2B-blue)](https://openjdk.org/projects/jdk/17/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-red)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A lightweight, framework-agnostic exception library for Java microservices. Provides standardized exceptions and error responses without external dependencies (except for testing).

## Features

- **Zero External Dependencies** - Pure Java 17+, no runtime dependencies
- **Framework Agnostic** - Works with Spring Boot, Quarkus, or plain Java
- **Thread-Safe** - All exceptions are immutable and thread-safe
- **Well Tested** - 90%+ code coverage with comprehensive test suite
- **Well Documented** - Every exception has JavaDoc with examples
- **Production Ready** - Standardized error handling patterns
- **Hierarchical Design** - Base BusinessException with specialized subclasses

## Installation

### Maven

```xml
<dependency>
    <groupId>com.dev</groupId>
    <artifactId>common-exception-java</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### Gradle

```groovy
implementation 'com.dev:common-exception-java:1.0.0-SNAPSHOT'
```

## Exception Hierarchy

```
RuntimeException
└── BusinessException (base)
    ├── RateLimitExceededException
    ├── ValidationException
    ├── ResourceNotFoundException
    └── UnauthorizedException
```

## Exceptions

### 1. BusinessException

Base exception for all business logic errors.

```java
// Simple usage
throw new BusinessException("ORDER_NOT_FOUND", "Order with id 123 not found");

// With HTTP status
throw new BusinessException("VALIDATION_ERROR", "Invalid input", 400);

// With cause
throw new BusinessException("DB_ERROR", "Database error", originalException);

// Accessing properties
catch (BusinessException e) {
    String code = e.getErrorCode();        // "ORDER_NOT_FOUND"
    String message = e.getErrorMessage();  // "Order with id 123 not found"
    int status = e.getHttpStatus();        // 500 (or specified)
}
```

### 2. RateLimitExceededException

For rate limiting scenarios (HTTP 429).

```java
// Basic usage
throw new RateLimitExceededException("Rate limit exceeded");

// With limit type
throw new RateLimitExceededException("Too many requests", "api-users");

// With retry after
throw new RateLimitExceededException("Rate limit exceeded", "api-users", 60);

// Accessing properties
catch (RateLimitExceededException e) {
    String type = e.getLimitType();           // "api-users"
    long retryAfter = e.getRetryAfterSeconds(); // 60
    int status = e.getHttpStatus();           // 429
}
```

### 3. ValidationException

For validation errors with support for multiple field errors.

```java
// Single field error
throw new ValidationException("email", "Invalid email format");

// Multiple field errors
ValidationException ex = new ValidationException();
ex.addError("email", "Invalid format")
  .addError("age", "Must be at least 18")
  .addError("name", "Required");
throw ex;

// Accessing errors
catch (ValidationException e) {
    for (ValidationException.ValidationError error : e.getErrors()) {
        String field = error.getField();
        String message = error.getMessage();
    }
    int count = e.getErrorCount();  // 3
}
```

### 4. ResourceNotFoundException

For 404 Not Found scenarios.

```java
// Basic usage
throw new ResourceNotFoundException("User", "123");
// Message: "User with id '123' not found"

// Custom message
throw new ResourceNotFoundException("Order", "456", "Order not found for this customer");

// Accessing properties
catch (ResourceNotFoundException e) {
    String type = e.getResourceType();  // "User"
    String id = e.getResourceId();      // "123"
    int status = e.getHttpStatus();     // 404
}
```

### 5. UnauthorizedException

For authentication (401) and authorization (403) failures.

```java
// Authentication failure (401)
throw new UnauthorizedException("Invalid credentials");

// Authorization failure (403)
throw new UnauthorizedException("Admin access required", 403);

// Factory method for forbidden
throw UnauthorizedException.forbidden("Insufficient permissions");

// Accessing properties
catch (UnauthorizedException e) {
    int status = e.getHttpStatus();  // 401 or 403
}
```

## ErrorResponse

Standardized error response for API error handling.

### Factory Methods

```java
// Rate limiting (429)
ErrorResponse rateLimit = ErrorResponse.rateLimit("Too many requests");
ErrorResponse rateLimitWithRetry = ErrorResponse.rateLimit("Rate limited", 60);

// Server error (500)
ErrorResponse serverError = ErrorResponse.serverError("Internal error occurred");

// Bad request (400)
ErrorResponse badRequest = ErrorResponse.badRequest("Invalid input");

// Not found (404)
ErrorResponse notFound = ErrorResponse.notFound("User not found");

// Unauthorized (401)
ErrorResponse unauthorized = ErrorResponse.unauthorized("Invalid token");

// From BusinessException
ErrorResponse fromEx = ErrorResponse.fromException(businessException);
```

### Builder Pattern

```java
ErrorResponse response = ErrorResponse.builder()
    .errorCode(400)
    .errorMessage("Bad Request")
    .detailMessage("Email format is invalid")
    .build();

// Response properties
String id = response.getId();                 // Auto-generated unique ID
int code = response.getErrorCode();           // 400
String message = response.getErrorMessage();  // "Bad Request"
String detail = response.getDetailMessage();  // "Email format is invalid"
String timestamp = response.getTimestamp();   // ISO-8601 timestamp
```

## Usage Examples

### Spring Boot Integration

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleRateLimit(RateLimitExceededException ex) {
        ErrorResponse response = ErrorResponse.rateLimit(
            ex.getMessage(), 
            ex.getRetryAfterSeconds()
        );
        
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .header("Retry-After", String.valueOf(ex.getRetryAfterSeconds()))
                .body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse response = ErrorResponse.notFound(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
        ErrorResponse response = ErrorResponse.badRequest(
            "Validation failed for " + ex.getErrorCount() + " fields"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex) {
        ErrorResponse response = ErrorResponse.fromException(ex);
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }
}
```

### Service Layer Usage

```java
@Service
public class OrderService {
    
    public Order findById(String orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
    }
    
    public void processOrder(CreateOrderRequest request) {
        ValidationException validation = new ValidationException();
        
        if (!isValidEmail(request.getEmail())) {
            validation.addError("email", "Invalid email format");
        }
        if (request.getQuantity() <= 0) {
            validation.addError("quantity", "Must be greater than 0");
        }
        
        if (validation.hasErrors()) {
            throw validation;
        }
        
        // Process order...
    }
}
```

### Rate Limiting Service

```java
@Service
public class RateLimiterService {
    
    public void checkLimit(String clientId, String endpoint) {
        if (!bucket.tryConsume(1)) {
            throw new RateLimitExceededException(
                "Rate limit exceeded for endpoint: " + endpoint,
                endpoint,
                60
            );
        }
    }
}
```

## Building

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Build Commands

```bash
# Compile
mvn clean compile

# Run tests (generates HTML report automatically)
mvn test

# Build JAR
mvn clean package

# Install to local Maven repository
mvn clean install

# Generate coverage report
mvn jacoco:report
# Report location: target/site/jacoco/index.html
```

### Test Report

When you run `mvn test`, an HTML test report is automatically generated:

- **Location**: `target/test-report/index.html`
- **Features**:
  - Test summary with pass/fail statistics
  - Visual charts showing test distribution
  - Detailed results by test class
  - Category-based test organization
  - Searchable and filterable interface

## Testing

The library has comprehensive test coverage:

- **43+ test cases**
- **90%+ code coverage** (enforced)
- **JUnit 5** with parameterized tests
- **Edge case coverage** for null, empty, and boundary values

Run tests:
```bash
mvn test
```

View coverage report:
```bash
mvn jacoco:report
open target/site/jacoco/index.html
```

View HTML test report:
```bash
open target/test-report/index.html
```

## Design Principles

### 1. Inheritance Hierarchy
All business exceptions extend `BusinessException`, providing consistent error handling:
- Common `errorCode` field
- Common `errorMessage` field
- Common `httpStatus` field
- Consistent `toString()` format

### 2. Thread Safety
All exception classes are immutable:
- All fields are `private final`
- No setters
- Defensive copies where needed

### 3. Null Safety
Constructors validate inputs with `Objects.requireNonNull()`:
```java
new BusinessException(null, "message");  // Throws NullPointerException
```

### 4. Rich Context
Exceptions carry useful context:
- `RateLimitExceededException`: limitType, retryAfter
- `ResourceNotFoundException`: resourceType, resourceId
- `ValidationException`: list of field errors
- `UnauthorizedException`: HTTP status (401/403)

### 5. Factory Methods
`ErrorResponse` provides convenient factory methods for common HTTP status codes.

### 6. Framework Agnostic
No dependencies on Spring, Jakarta EE, or any framework. Use in any Java project.

## Error Response Format

All error responses follow this JSON structure:

```json
{
  "id": "aB3xK9mP2nQ5rT8w",
  "errorCode": 404,
  "errorMessage": "Not Found",
  "detailMessage": "User with id '123' not found",
  "timestamp": "2026-03-28T10:30:00.000Z"
}
```

## API Stability

This library follows semantic versioning:
- **MAJOR**: Breaking changes to public API
- **MINOR**: New features, backward compatible
- **PATCH**: Bug fixes, backward compatible

## Integration with common-utils-java

Works seamlessly with `common-utils-java`:

```xml
<dependencies>
    <!-- Common exceptions -->
    <dependency>
        <groupId>com.dev</groupId>
        <artifactId>common-exception-java</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
    
    <!-- Utilities (optional) -->
    <dependency>
        <groupId>com.dev</groupId>
        <artifactId>common-utils-java</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

## Contributing

### Before Submitting PR

1. **All tests must pass**:
   ```bash
   mvn test
   ```

2. **Coverage must be 90%+**:
   ```bash
   mvn jacoco:report
   ```

3. **No compilation warnings**:
   ```bash
   mvn clean compile
   ```

4. **Follow existing patterns**:
   - All exceptions extend `BusinessException`
   - Use `Objects.requireNonNull()` for validation
   - Add comprehensive JavaDoc
   - Include `@since` tags
   - Write tests for new exceptions

### Code Style

- Follow existing code style
- Extend `BusinessException` for new business exceptions
- Use meaningful field names
- Add JavaDoc with examples
- Include `@since` tags

## License

MIT License - see [LICENSE](LICENSE) file for details.

## Support

For issues, questions, or contributions, please use GitHub Issues and Pull Requests.

---

**Note**: This is a native Java library with zero external runtime dependencies. Do not add Spring, Jakarta EE, or other framework dependencies to keep it lightweight and portable.

## Related Projects

- [common-utils-java](https://github.com/RizkiRachman/common-utils-java) - Utility library for Java microservices