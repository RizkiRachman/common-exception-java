# AGENTS.md for common-exception-java

## Project Overview
This is a shared exception library for Java microservices. Provides standardized exceptions and error responses for consistent error handling across multiple microservices. Framework-agnostic with no runtime dependencies.

## Architecture
- **Package Structure**: `com.dev.common.exception`
- **Pure Java**: No Spring dependencies (framework-agnostic)
- **Exception Hierarchy**: BusinessException base with specialized subclasses
- **Immutable**: All exceptions are immutable with final fields
- **Thread-Safe**: All exceptions are thread-safe
- **Serializable**: All exceptions implement Serializable

## Key Workflows
- **Run Tests**: `mvn test` (generates HTML report automatically)
- **Build JAR**: `mvn clean package`
- **Generate Coverage**: `mvn clean test jacoco:report`
- **Generate Test Report**: `python3 generate-test-report.py`
- **Install Local**: `mvn clean install` (installs to local Maven repo)

## Pre-PR Requirements (CRITICAL)

Before creating any Pull Request, AI MUST:

1. **Clean Compile**
   ```bash
   mvn clean compile -q
   ```
   - Must complete without errors
   - No compilation warnings

2. **Run Tests**
   ```bash
   mvn test
   ```
   - All tests must pass
   - Minimum 90% code coverage required
   - Generate coverage report: `mvn jacoco:report`
   - View report: `target/site/jacoco/index.html`
   - View HTML test report: `target/test-report/index.html`

3. **Code Review**
   - Review all public APIs
   - Ensure JavaDoc is complete
   - Check for thread-safety issues
   - Verify no breaking changes

4. **Verify No Breaking Changes**
   - Check method signatures are backward compatible
   - Verify no removed public methods
   - Ensure constants values unchanged

**⚠️ DO NOT CREATE PR if any step fails. Fix issues first!**

## Testing Standards

### Coverage Requirements
- **Minimum**: 90% line coverage
- **Minimum**: 90% branch coverage
- **Target**: 100% for all public methods
- Check coverage: `mvn clean test jacoco:report`

### Test Organization
```
src/test/java/com/dev/common/exception/
├── BusinessExceptionTest.java
├── RateLimitExceededExceptionTest.java
├── ValidationExceptionTest.java
├── ResourceNotFoundExceptionTest.java
├── UnauthorizedExceptionTest.java
└── ErrorResponseTest.java
```

### Test Naming Conventions
- Class name: `{ClassName}Test`
- Method name: `should{ExpectedBehavior}When{Condition}`
- Use @DisplayName for human-readable descriptions

### Required Test Types
- ✅ Unit tests for all public methods
- ✅ Edge case testing (null, empty, boundary values)
- ✅ Exception testing
- ✅ Constructor validation testing
- ✅ Getter method testing

## Conventions

### Exception Class Pattern
```java
public class BusinessException extends RuntimeException implements Serializable {
    private final String errorCode;
    private final int httpStatus;
    
    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = Objects.requireNonNull(errorCode);
        // ...
    }
    
    // Getters only, no setters (immutable)
}
```

### Documentation
- Every public method must have JavaDoc
- Include `@param`, `@return`, `@throws` tags
- Add examples in JavaDoc where helpful
- Use `@since` tag for version tracking
- Document HTTP status codes

### Thread Safety
- All fields must be `private final`
- No mutable state
- Use `Objects.requireNonNull()` for validation
- Document thread-safety in class-level JavaDoc

### Immutability
- All fields are `final`
- No setters
- Defensive copies where needed
- Unmodifiable collections returned from getters

## Dependencies
- **Core**: Pure Java 17+ (no external deps for production code)
- **Testing**: JUnit 5, Mockito, JaCoCo
- **Report Generation**: Python 3 (optional, for HTML report)

## Integration Points
- **Usage**: Import as Maven dependency
- **Versioning**: Semantic versioning (1.x.x)
- **Distribution**: GitHub Packages
- **Compatibility**: Java 17+
- **Related**: Works with common-utils-java

## Patterns
- **Exception Hierarchy**: BusinessException base class
- **Builder Pattern**: ErrorResponse.Builder
- **Factory Methods**: ErrorResponse.rateLimit(), ErrorResponse.serverError(), etc.
- **Null Safety**: Use Objects.requireNonNull() in constructors
- **Immutability**: All fields final, no setters

## Debugging
- Use logging in integration tests
- Enable verbose mode for test debugging
- Check Jacoco reports for uncovered lines
- Use HTML test report for detailed test analysis

## Distribution

### Local Development
```bash
# Install to local Maven repo
mvn clean install

# Verify installation
ls ~/.m2/repository/com/dev/common-exception-java/
```

### GitHub Packages
```xml
<dependency>
    <groupId>com.dev</groupId>
    <artifactId>common-exception-java</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Related Projects
- **common-utils-java**: Utility library that complements this exception library
- Use both together for complete microservice foundation
