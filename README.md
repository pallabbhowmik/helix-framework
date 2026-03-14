# Helix Automation Framework

A production-grade hybrid test automation framework for **UI + API testing**, built with Java, Selenium WebDriver, RestAssured, TestNG, and Allure Reporting. Designed for maintainability, scalability, and CI/CD readiness.

---

## Tech Stack

| Layer          | Technology                        | Version   |
|----------------|-----------------------------------|-----------|
| Language       | Java                              | 11+       |
| Build Tool     | Gradle (wrapper included)         | 8.14      |
| UI Testing     | Selenium WebDriver                | 4.27.0    |
| API Testing    | RestAssured                       | 5.5.0     |
| Test Runner    | TestNG                            | 7.10.0    |
| Reporting      | Allure Reports                    | 2.29.0    |
| Logging        | SLF4J + Log4j2                    | 2.0.16    |
| Driver Mgmt    | WebDriverManager (auto-download)  | 5.9.2     |
| Data Format    | JSON (Jackson)                    | 2.18.1    |
| CI/CD          | GitHub Actions                    | —         |
| Containerized  | Docker (Dockerfile included)      | —         |

---

## Design Patterns & Architecture

| Pattern                | Implementation                                                  |
|------------------------|-----------------------------------------------------------------|
| **Page Object Model**  | `BasePage` → `HomePage`, `LoginPage`, `AppPage` with fluent API |
| **Factory**            | `WebDriverFactory` — browser-specific driver creation           |
| **ThreadLocal**        | `DriverManager` — thread-safe driver isolation for parallelism  |
| **Builder**            | `RequestSpecFactory` — fluent RestAssured spec construction     |
| **Data-Driven**        | JSON files + `@DataProvider` + `TestDataReader`                 |
| **Singleton (Config)** | `ConfigManager` — layered config resolution with static init    |
| **Listener**           | `AllureTestListener` + `RetryAnnotationTransformer`             |
| **Fluent API**         | Page objects return `this` or next page for method chaining     |

---

## Key Features

- **Page Object Model (POM)** with fluent API and method chaining
- **Abstract `BasePage`** with 30+ reusable web interactions (click, type, hover, drag-drop, scroll, alerts, frames)
- **Allure Reporting** with `@Step`, `@Epic`, `@Feature`, `@Story`, `@Severity` annotations
- **Thread-safe WebDriver** via `ThreadLocal` for parallel execution
- **Data-driven testing** via JSON test data + `@DataProvider`
- **Type-safe API models** — `LoginResponse`, `ApiResponse<T>` POJOs with Jackson
- **`RequestSpecFactory`** — centralized RestAssured request specifications
- **Automatic retry logic** for flaky tests (configurable via `-Dtest.retry.count=N`)
- **Screenshot & log capture** on failures, attached to Allure reports
- **Multi-browser support** — Chrome, Firefox, Edge with headless mode
- **Type-safe enums** — `BrowserType`, `Environment` with safe `fromString()` resolvers
- **Custom `FrameworkException`** — descriptive runtime exceptions for framework failures
- **Environment metadata** — auto-written to Allure reports (`AllureEnvironmentWriter`)
- **Layered configuration** — `.env.config` → ENV vars → `-D` system properties
- **Rolling log files** with time and size-based rotation (Log4j2)
- **Docker support** — run tests in a containerized Chrome environment
- **GitHub Actions CI** — automated pipeline with separate API and UI jobs
- **SoftAssert** — multi-assertion tests that report all failures, not just the first

---

## Project Structure

```
Helix_Automation/
├── .github/workflows/
│   └── ci.yml                      → GitHub Actions CI pipeline
├── Dockerfile                      → Containerized test execution
├── build.gradle                    → Build config with Allure plugin
├── gradlew / gradlew.bat           → Gradle wrapper scripts
│
├── src/main/java/
│   ├── config/
│   │   ├── BrowserType.java        → Browser type enum (CHROME, FIREFOX, EDGE)
│   │   ├── ConfigManager.java      → Layered config resolver (.env → env vars → -D)
│   │   └── Environment.java        → Environment enum (DEV, QA, STAGING, PROD)
│   │
│   ├── core/
│   │   ├── AllureEnvironmentWriter.java → Writes env metadata to Allure reports
│   │   ├── AllureTestListener.java      → Screenshot/log capture on failures
│   │   ├── BasePage.java               → Abstract base with 30+ web interactions
│   │   ├── DriverManager.java          → Thread-safe WebDriver holder (ThreadLocal)
│   │   ├── FrameworkException.java     → Custom runtime exception
│   │   ├── LogCleaner.java             → Log file cleanup between runs
│   │   ├── RetryAnalyzer.java          → Configurable test retry mechanism
│   │   ├── RetryAnnotationTransformer.java → Global retry applicator
│   │   ├── WebDriverFactory.java       → Factory for browser driver creation
│   │   └── api/
│   │       ├── ApiResponse.java        → Generic API response POJO
│   │       ├── LoginResponse.java      → Login endpoint response POJO
│   │       └── RequestSpecFactory.java → RestAssured spec builder
│   │
│   ├── pages/
│   │   ├── AppPage.java             → Dashboard page object
│   │   ├── HomePage.java            → Home/landing page object
│   │   └── LoginPage.java           → Login page object with fluent API
│   │
│   └── utils/
│       └── CommonUtils.java         → Screenshot helpers, file utilities
│
└── src/test/
    ├── java/
    │   ├── tests/
    │   │   ├── api/
    │   │   │   ├── BaseApiTest.java     → API test setup (RestAssured config)
    │   │   │   ├── AuthApiTests.java    → Authentication API tests
    │   │   │   └── NotesApiTests.java   → Notes CRUD API tests
    │   │   └── ui/
    │   │       ├── BaseTest.java        → UI test setup (WebDriver lifecycle)
    │   │       └── LoginTests.java      → Login flow UI tests
    │   └── util/
    │       └── TestDataReader.java      → JSON test data deserializer
    │
    └── resources/
        ├── testng_API.xml           → API test suite definition
        ├── testng_UI.xml            → UI test suite definition
        ├── log4j2.xml               → Logging configuration
        └── testdata/
            └── loginTestData.json   → Login test data (data-driven)
```

---

## Getting Started

### Prerequisites
- Java 11 or higher (`java -version`)
- Allure CLI (optional, for viewing reports)

### Setup

1. Clone this repository:
   ```bash
   git clone <repo-url>
   cd Helix_Automation
   ```

2. Create your environment config:
   ```bash
   cp .env.config.example .env.config
   ```

3. Update `.env.config` with your test environment details.

---

## Running Tests

### Execute all tests (UI + API):
```bash
./gradlew clean test
```

### Run only UI tests:
```bash
./gradlew clean test_UI
```

### Run only API tests:
```bash
./gradlew clean test_API
```

### Headless mode:
```bash
./gradlew clean test -Dheadless=true
```

### Select browser:
```bash
./gradlew clean test_UI -Dbrowser=firefox
```

### Custom retry count:
```bash
./gradlew clean test -Dtest.retry.count=3
```

### Docker execution:
```bash
docker build -t helix-automation .
docker run --env-file .env.config helix-automation
```

---

## Test Reports

Generate and view Allure reports:

```bash
allure serve build/allure-results
```

Reports include:
- Test execution timeline and history
- Step-by-step breakdown with `@Step` annotations
- Screenshots attached on failures
- Full log file attachments
- Environment widget (browser, OS, Java version, base URL)
- Severity and feature categorization via `@Epic`/`@Feature`/`@Story`

---

## Configuration

All settings are managed via `.env.config` (not committed to VCS):

| Property              | Description                        | Default    |
|-----------------------|------------------------------------|------------|
| `HELIX_ENV`           | Environment (dev/qa/staging/prod)  | `dev`      |
| `HELIX_BASE_URL`      | Application URL for UI tests       | —          |
| `HELIX_API_BASE_URL`  | API base URL                       | —          |
| `HELIX_BROWSER`       | Browser (chrome/firefox/edge)      | `chrome`   |
| `HELIX_TIMEOUT`       | Explicit wait timeout (seconds)    | `10`       |
| `HELIX_USERNAME`      | Test login username                | —          |
| `HELIX_PASSWORD`      | Test login password                | —          |
| `headless`            | Run browsers headlessly            | `false`    |
| `test.retry.count`    | Max retry attempts for failed tests| `2`        |

**Resolution order:** `.env.config` → Environment Variables → System Properties (`-D`).
System properties have the highest priority, enabling CI/CD overrides without modifying files.

---

## CI/CD Pipeline

The included GitHub Actions workflow (`.github/workflows/ci.yml`) provides:

- **Parallel jobs** — API tests and UI tests run concurrently
- **JDK 11** setup via `actions/setup-java`
- **Chrome browser** setup via `browser-actions/setup-chrome`
- **Secret-based config** — `.env.config` injected from GitHub Secrets
- **Allure artifact upload** — test results uploaded as build artifacts

---

## Architecture Highlights

- **ConfigManager** — layered config from `.env.config` + env vars + system properties; includes `validate()` for fail-fast startup
- **DriverManager** — `ThreadLocal<WebDriver>` ensures each test thread has an isolated browser instance
- **WebDriverFactory** — uses `BrowserType` enum to create the correct driver with optimized options
- **BasePage** — abstract class providing `$()` / `$$()` shorthands, explicit waits, JS click fallback, scroll, hover, drag-and-drop, dropdown selection, frame/window/alert handling, and page load waiting
- **RequestSpecFactory** — builds pre-configured RestAssured specs with `baseSpec()` and `authSpec(token)`
- **AllureTestListener** — captures screenshots and logs on failure; writes environment metadata via `AllureEnvironmentWriter`
- **RetryAnalyzer + RetryAnnotationTransformer** — applies retry logic globally without per-test annotation boilerplate
- **TestDataReader** — type-safe JSON deserialization with generic `readJson(path, Class<T>)` and `readJsonList()` overloads
