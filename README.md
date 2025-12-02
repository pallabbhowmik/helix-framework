# Helix Automation Framework

A hybrid automation framework for UI + API testing built with Java, Selenium WebDriver, RestAssured, TestNG, and Allure Reporting.
Designed for maintainability, scalability, and CI/CD readiness.

## Prerequisites

- Java 11 or higher
- Gradle (wrapper included)

## Features
* UI automation using Selenium WebDriver
* API automation using RestAssured
* Page Object Model (POM) structure
* Data-driven testing support (JSON / Excel extendable)
* Automatic retry logic for flaky tests
* Screenshots & logs captured on failures
* Parallel execution support
* Allure reporting integration
* Thread-safe WebDriver using ThreadLocal


## Getting Started

1. Clone this repository
2. Copy `.env.config.example` to `.env.config`
3. Update the configuration with your test environment details:
   - Base URL
   - API endpoint
   - Login credentials
   - Browser preferences

## Project Structure

```
src/
├── main/java
│   ├── config/     → Environment & properties handling
│   ├── core/       → Driver factory, listeners, retry logic
│   ├── pages/      → UI Page Objects (POM)
│   └── utils/      → Reusable helpers
└── test/java
    ├── tests/ui    → UI test cases
    ├── tests/api   → API test cases
    └── resources   → TestNG suite files
```

## Running Tests

Execute all tests (UI + API):
```bash
gradlew clean test
```

Run only UI tests:
```bash
gradlew clean test_UI
```

Run only API tests:
```bash
gradlew clean test_API
```
Optional headless mode:
```bash
gradlew clean test -Dheadless=true
```

## Test Reports

Test results are saved to `build/allure-results`. To view the Allure report:

```bash
allure serve build/allure-results
```

## Features

- Page Object Model for maintainable UI tests
- Automatic retry on test failures
- Screenshot capture on failures
- Configurable waits and timeouts
- Support for multiple browsers
- Parallel test execution support
- Allure reporting integration

## Configuration

Key settings in `.env.config`:

- `HELIX_ENV` - Environment (dev/staging/prod)
- `HELIX_BASE_URL` - Application URL
- `HELIX_API_BASE_URL` - API endpoint
- `HELIX_BROWSER` - Browser choice (chrome/firefox/edge)
- `HELIX_TIMEOUT` - Default wait timeout in seconds
- `HELIX_USERNAME` / `HELIX_PASSWORD` - Test credentials

## Notes

* Logging is handled using SLF4J + Log4j2
* WebDriver binaries managed via WebDriverManager
* Works locally and in CI environments
