# AGENTS.md for common-utils-java

## Project Overview
This is a shared utilities library for Java microservices. Provides common functionality like string manipulation, pagination, validation, and HTTP utilities. Used across multiple microservices to avoid code duplication.

## Architecture
- **Package Structure**: `com.dev.common.{utility}`
- **Pure Java**: No Spring dependencies (framework-agnostic)
- **Static Methods**: Utility classes with static methods
- **Immutable**: All utility classes are final with private constructors
- **Thread-Safe**: All methods are thread-safe

## Key Workflows
- **Run Tests**: `mvn test`
- **Build JAR**: `mvn clean package`
- **Generate Coverage**: `mvn clean test jacoco:report`
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
src/test/java/com/dev/common/
├── string/
│   └── StringUtilsTest.java
├── pagination/
│   └── PageTest.java
├── validation/
│   └── ValidationUtilsTest.java
└── http/
    └── HttpUtilsTest.java
```

### Test Naming Conventions
- Class name: `{ClassName}Test`
- Method name: `should{ExpectedBehavior}When{Condition}`
- Use @DisplayName for human-readable descriptions

### Required Test Types
- ✅ Unit tests for all public methods
- ✅ Edge case testing (null, empty, boundary values)
- ✅ Exception testing
- ✅ Thread-safety testing (where applicable)
- ✅ Performance testing (for heavy operations)

## Conventions

### Utility Class Pattern
```java
public final class StringUtils {
    private StringUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }
    
    public static String method(String input) {
        // implementation
    }
}
```

### Documentation
- Every public method must have JavaDoc
- Include `@param`, `@return`, `@throws` tags
- Add examples in JavaDoc where helpful
- Use `@since` tag for version tracking

### Thread Safety
- Utility classes must be thread-safe
- Use `ThreadLocal` where needed
- Avoid mutable shared state
- Document if not thread-safe

### Immutability
- All utility classes are `final`
- Private constructor with assertion error
- No instance variables (except constants)
- Prefer immutable data structures

## Dependencies
- **Core**: Pure Java 17+ (no external deps for production code)
- **Testing**: JUnit 5, Mockito, JaCoCo
- **Optional**: Spring Boot (for integration tests only)

## Integration Points
- **Usage**: Import as Maven dependency
- **Versioning**: Semantic versioning (1.x.x)
- **Distribution**: GitHub Packages
- **Compatibility**: Java 17+

## Patterns
- **Utility Pattern**: Static methods in final class
- **Builder Pattern**: For complex objects (pagination, requests)
- **Fluent API**: Method chaining where applicable
- **Null Safety**: Return empty/null-safe values, never NPE

## Performance
- Cache expensive computations where possible
- Use efficient algorithms (e.g., StringBuilder for string ops)
- Avoid unnecessary object creation
- Document time complexity in JavaDoc

## Debugging
- Use logging in integration tests
- Enable verbose mode for test debugging
- Check Jacoco reports for uncovered lines

## Distribution

### Local Development
```bash
# Install to local Maven repo
mvn clean install

# Verify installation
ls ~/.m2/repository/com/dev/common-utils-java/
```

### GitHub Packages
```xml
<dependency>
    <groupId>com.dev</groupId>
    <artifactId>common-utils-java</artifactId>
    <version>1.0.0</version>
</dependency>
```
