# AI Rules for common-exception-java

## AI Agent Rules and Guidelines

This document contains rules and guidelines that AI coding assistants must follow when working on this **native Java exception library** (not Spring Boot).

### Security and Privacy Rules

#### Credential Handling
- **NEVER read or display** any files containing credentials, API keys, passwords, or sensitive information
- **AVOID accessing** files with names like:
  - `.env`
  - `credentials.json`
  - `secrets.yml`
  - Any file containing "secret", "key", "password", "token", or "credential" in the name
- **DO NOT suggest** or implement code that hardcodes credentials
- **ALWAYS use** environment variables or external configuration for sensitive data
- **NEVER commit** or suggest committing credential files to version control

#### Data Protection
- **AVOID processing** or displaying user data, personal information, or sensitive business data
- **DO NOT access** files that may contain sensitive information
- **RESPECT privacy** by not reading or analyzing files that may contain user data

### Code Quality Rules

#### Best Practices for Native Java
- **ALWAYS follow** the patterns and conventions outlined in `AGENTS.md`
- **NEVER add Spring dependencies** - this is a pure Java library
- **MAKE all exception classes final** or properly inheritable
- **IMPLEMENT Serializable** for all exception classes
- **USE immutable design** - all fields must be private final
- **USE Objects.requireNonNull()** for constructor validation
- **WRITE comprehensive JavaDoc** for all public methods

#### Library-Specific Rules
- **KEEP it framework-agnostic** - no Spring, Jakarta EE, or framework-specific code
- **MINIMIZE dependencies** - only JUnit and Mockito for testing
- **ENSURE backward compatibility** - don't break existing APIs
- **USE Java 17+ features** appropriately (records, pattern matching, etc.)
- **EXTEND BusinessException** for new business-related exceptions

### Development Workflow Rules

#### File Management
- **ONLY edit** files in `src/main/java/com/dev/common/exception/` and `src/test/java/`
- **DO NOT modify** `pom.xml` unless explicitly requested
- **AVOID creating** unnecessary files or directories
- **FOLLOW package structure**: `com.dev.common.exception`
- **NEVER commit** `target/` folder (already in .gitignore)

#### Testing (CRITICAL)
- **MUST achieve 90%+ code coverage** (enforced by JaCoCo)
- **ALWAYS write** unit tests for new functionality
- **RUN tests before committing**: `mvn test`
- **COVER edge cases**: null, empty, boundary values
- **TEST thread-safety** where applicable
- **USE parameterized tests** for multiple test cases

### Exception Class Patterns

#### BusinessException Structure
```java
public class BusinessException extends RuntimeException implements Serializable {
    private final String errorCode;
    private final int httpStatus;
    private final String errorMessage;
    
    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = Objects.requireNonNull(errorCode, "errorCode must not be null");
        this.errorMessage = Objects.requireNonNull(errorMessage, "errorMessage must not be null");
        this.httpStatus = 500;
    }
    
    // Getters only, no setters (immutable)
}
```

#### Specialized Exception Structure
```java
public class CustomException extends BusinessException implements Serializable {
    private static final String ERROR_CODE = "CUSTOM_ERROR";
    private static final int HTTP_STATUS = 400;
    
    private final String customField;
    
    public CustomException(String message, String customField) {
        super(ERROR_CODE, message, HTTP_STATUS);
        this.customField = Objects.requireNonNull(customField);
    }
    
    public String getCustomField() {
        return customField;
    }
}
```

#### Documentation Requirements
```java
/**
 * Brief description of when this exception is thrown.
 *
 * <p>Detailed explanation with examples:</p>
 * <pre>{@code
 * throw new CustomException("message", "value");
 * }</pre>
 *
 * @author Dev Team
 * @version 1.0
 * @since 1.0
 * @see BusinessException
 */
```

### Communication Rules

#### Response Guidelines
- **BE concise** but informative in responses
- **EXPLAIN complex changes** with clear reasoning
- **PROVIDE examples** when introducing new concepts
- **ASK for clarification** only when absolutely necessary

#### Error Handling
- **REPORT compilation errors** immediately with suggested fixes
- **SUGGEST alternatives** when encountering blocking issues
- **DOCUMENT workarounds** for known limitations

### Project-Specific Rules

#### common-utils-java
- **FRAMEWORK-AGNOSTIC**: No Spring, Jakarta, or framework dependencies
- **PURE JAVA 17+**: Use modern Java features
- **ZERO EXTERNAL DEPENDENCIES** for production code
- **THREAD-SAFE**: All methods must be thread-safe
- **IMMUTABLE**: No mutable shared state
- **COMPREHENSIVE TESTS**: 90%+ coverage required

#### Version Control
- **COMMIT logically** with clear, descriptive messages
- **DO NOT commit** generated files, dependencies, or sensitive data
- **FOLLOW conventional commits** format when possible

### Emergency Rules

#### When in Doubt
- **STOP and ask** if a requested action might violate security rules
- **CONSULT AGENTS.md** before making significant changes
- **PREFER conservative approaches** to avoid breaking existing functionality
- **SUGGEST testing** for any non-trivial changes

#### Recovery
- **DOCUMENT issues** encountered during development
- **PROVIDE rollback instructions** for major changes
- **MAINTAIN backward compatibility** whenever possible

### Common Pitfalls to Avoid

1. **Don't add Spring dependencies** - This is a pure Java library
2. **Don't use external libraries** - Keep it lightweight
3. **Don't break backward compatibility** - Maintain existing APIs
4. **Don't skip tests** - Coverage is enforced
5. **Don't use mutable state** - Keep utilities thread-safe
6. **Don't ignore null handling** - Be null-safe
7. **Don't forget JavaDoc** - Document all public methods

### Quick Reference

```bash
# Run tests (must pass)
mvn test

# Check coverage
mvn jacoco:report

# Build (includes tests)
mvn clean package

# Install locally
mvn clean install
```
