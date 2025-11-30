# Helix Automation Framework

A test automation framework built with Java that supports both UI and API testing. Uses Selenium WebDriver for browser automation, RestAssured for API testing, and TestNG as the test runner.

## Prerequisites

- Java 11 or higher
- Gradle (wrapper included)

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
├── main/java/
│   ├── config/      # Configuration management
│   ├── core/        # WebDriver setup, base page, listeners
│   ├── pages/       # Page objects for UI tests
│   └── utils/       # Helper utilities
└── test/
    ├── java/tests/  # Test classes
    └── resources/   # TestNG XML suites
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
