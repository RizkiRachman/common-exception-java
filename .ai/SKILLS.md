# SKILLS.md for common-exception-java

## AI Agent Skills and Capabilities

This document outlines the skills and capabilities of the AI coding assistant for this native Java exception library.

### Core Skills

- **Java Programming**: Expert knowledge of Java 17+with modern features including records, sealed classes, pattern matching, text blocks, and switch expressions
- **Exception Class Design**: Proficient in creating exception hierarchies with proper inheritance
- **Maven Build System**: Full understanding of Maven lifecycle, dependencies, plugins, and deployment
- **Testing**: Comprehensive knowledge of unit testing with JUnit 5, parameterized tests, and mocking with Mockito
- **Code Coverage**: Expertise in JaCoCo configuration and achieving 90%+ coverage requirements
- **Thread Safety**: Understanding of concurrent programming, ThreadLocal, and thread-safe design patterns
- **Performance Optimization**: Skills in optimizing algorithms, reducing object creation, and efficient string operations

### Specialized Skills

- **Exception Design**: Creating meaningful exception hierarchies for business logic
- **Error Response**: Building standardized error response objects (ErrorResponse)
- **HTTP Status Mapping**: Mapping exceptions to appropriate HTTP status codes
- **Exception Handling**: Best practices for catching, wrapping, and re-throwing exceptions
- **Validation Exceptions**: Multi-field validation error handling
- **Rate Limiting**: Rate limit exception patterns with retry-after support
- **Builder Pattern**: ErrorResponse.Builder for flexible error response construction
- **Factory Methods**: Static factory methods for common error responses
- **Immutability**: Designing immutable exception classes with final fields
- **Null Safety**: Defensive programming with Objects.requireNonNull()
- **Serializable**: Proper implementation of Serializable for distributed systems

### Development Practices

- **Clean Code**: Writing maintainable, readable, and well-documented code
- **JavaDoc**: Comprehensive documentation with `@param`, `@return`, `@throws`, and examples
- **Design Patterns**: Application of appropriate patterns (Exception Hierarchy, Builder, Factory)
- **SOLID Principles**: Adherence to Single Responsibility, Open-Closed, Liskov Substitution, Interface Segregation, and Dependency Inversion
- **TDD**: Test-Driven Development with comprehensive test coverage
- **Code Reviews**: Understanding of code review best practices and common anti-patterns
- **Refactoring**: Skills in improving code structure without breaking backward compatibility
- **Version Control**: Proficient use of Git with branching strategies and pull request workflows

### Library Design Principles

- **Zero Dependencies**: Keep production code free of external dependencies
- **Framework Agnostic**: Write pure Java that works in any environment (Spring, Quarkus, plain Java)
- **Backward Compatibility**: Maintain API stability across versions
- **Thread-Safety**: All methods must be safe for concurrent access
- **Immutability**: Prefer immutable designs over mutable state
- **Defensive Programming**: Validate inputs, handle edge cases, fail fast with meaningful errors
- **Performance**: Document time complexity, use efficient algorithms, avoid unnecessary allocations

### Tools and Technologies

- **IDEs**: IntelliJ IDEA, Eclipse, VS Code with Java extensions
- **Build Tools**: Maven (primary), understanding of Gradle
- **Testing**: JUnit 5, Mockito, JaCoCo
- **Version Control**: Git, GitHub
- **Documentation**: JavaDoc, Markdown

### Utility Class Patterns

- **Final Class**: Prevent extension with `final` modifier
- **Private Constructor**: Throw `AssertionError` to prevent instantiation
- **Static Methods Only**: No instance methods or instance state
- **Constants Only**: Use `private static final` for shared constants
- **Thread-Local Where Needed**: Use `ThreadLocalRandom` for random generation
- **Compiled Patterns**: Cache compiled regex patterns as static constants

### Testing Standards

- **Unit Tests**: Every public method must have tests
- **Parameterized Tests**: Use for testing multiple inputs/outputs
- **Edge Cases**: Test null, empty, boundary values
- **Exception Testing**: Verify exception throwing with expected messages
- **Thread Safety**: Test concurrent access where applicable
- **Coverage**: Maintain 90%+ line and branch coverage
- **Test Naming**: Use `should{Behavior}When{Condition}` pattern

### Communication Skills

- **Technical Writing**: Clear JavaDoc and documentation
- **Code Comments**: Minimal but meaningful comments for complex logic
- **Commit Messages**: Descriptive commits following conventional commits format
- **Pull Request Descriptions**: Clear explanations of changes and rationale

### Quality Assurance

- **Static Analysis**: Understanding of code quality tools
- **Code Coverage**: Mastery of JaCoCo coverage requirements
- **Backward Compatibility**: Ability to add features without breaking existing APIs
- **Performance Awareness**: Understanding of algorithmic complexity and optimization

### Learning and Adaptation

- **Java Evolution**: Staying current with Java LTS releases and new features
- **Library Design**: Researching best practices for exception library design
- **Testing Patterns**: Learning new testing techniques and patterns
- **Problem Solving**: Analytical thinking and debugging complex issues
- **Adaptation**: Quickly adapting to new requirements while maintaining backward compatibility