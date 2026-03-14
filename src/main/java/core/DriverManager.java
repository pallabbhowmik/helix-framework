package core;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread-safe WebDriver holder using {@link ThreadLocal}.
 * Ensures each test thread gets its own WebDriver instance,
 * enabling safe parallel execution.
 *
 * <p>Lifecycle: setUp() calls {@link #setDriver(WebDriver)},
 * tearDown() calls {@link #quitDriver()} to prevent resource leaks.</p>
 */
public final class DriverManager {

    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverHolder = new ThreadLocal<>();

    private DriverManager() {
        // prevent instantiation
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverHolder.get();
        if (driver == null) {
            log.warn("getDriver() called but no WebDriver is set for thread: {}",
                    Thread.currentThread().getName());
        }
        return driver;
    }

    public static void setDriver(WebDriver driver) {
        log.info("Setting WebDriver for thread: {}", Thread.currentThread().getName());
        driverHolder.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = driverHolder.get();
        if (driver != null) {
            try {
                log.info("Quitting WebDriver for thread: {}", Thread.currentThread().getName());
                driver.quit();
            } catch (Exception e) {
                log.error("Error while quitting WebDriver", e);
            } finally {
                driverHolder.remove();
            }
        } else {
            log.debug("quitDriver() called but WebDriver is already null");
        }
    }

    /**
     * Checks if a WebDriver is currently active for this thread.
     */
    public static boolean hasDriver() {
        return driverHolder.get() != null;
    }
}
