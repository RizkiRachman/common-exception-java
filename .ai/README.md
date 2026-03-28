# AI Documentation Index

All AI agent documentation is centralized here for easy reference.

## Quick Reference

| File | Purpose |
|------|---------|
| [AGENTS.md](AGENTS.md) | Project conventions, workflows, and development guide |
| [RULES.md](RULES.md) | Coding standards and project rules |
| [SKILLS.md](SKILLS.md) | Technical skills and capabilities reference |

## For Human Developers

This `.ai/` folder contains documentation specifically for AI agents working on this project. 

**For human developers**, please see:
- [README.md](../README.md) - Project overview and usage guide
- [LICENSE](../LICENSE) - MIT License

## How AI Agents Use This

1. **Before starting work** - Read AGENTS.md for project conventions
2. **Before coding** - Check RULES.md for standards and restrictions
3. **Before completing work** - Run all tests and verify coverage

## Key Reminders

**This is a NATIVE JAVA EXCEPTION library** (not Spring Boot!):
- No Spring dependencies allowed
- No framework-specific code
- Pure Java 17+ only
- Exception classes with proper inheritance
- Immutable design (all fields final)

## Pre-Work Requirements

All AI agents MUST complete:
1. Read AGENTS.md for project context
2. Read RULES.md for constraints
3. Check existing code patterns in `src/main/java/`

## Pre-Completion Requirements

Before finishing work, verify:
1. All tests pass (`mvn test`)
2. Coverage is 90%+ (`mvn jacoco:report`)
3. No compilation errors (`mvn clean compile`)
4. Follows exception class pattern (extends BusinessException, immutable, Serializable)
5. Thread-safe implementation
6. Comprehensive JavaDoc added
7. Objects.requireNonNull() used for constructor validation

## Common Commands

```bash
# Run all tests (generates HTML report automatically)
mvn test

# Check code coverage
mvn jacoco:report

# View test report
open target/test-report/index.html

# Build JAR
mvn clean package

# Install locally
mvn clean install
```

## Test Reports

Two types of reports are generated:

1. **JaCoCo Coverage Report**: `target/site/jacoco/index.html`
2. **HTML Test Report**: `target/test-report/index.html` - Comprehensive visual report with:
   - Test summary and statistics
   - Visual charts (pass/fail distribution, categories)
   - Detailed results by test class
   - Searchable interface
   - Auto-opens in browser after test completion

---

*This documentation helps AI agents understand the project and maintain code quality.*
