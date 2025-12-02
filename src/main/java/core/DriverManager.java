package core;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverManager {

    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);

    private static final ThreadLocal<WebDriver> driverHolder = new ThreadLocal<>();

    private DriverManager() {
        // prevent instantiation
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverHolder.get();
        if (driver == null) {
            log.warn("getDriver() called but no WebDriver is associated with current thread");
        }
        return driver;
    }

    public static void setDriver(WebDriver driver) {
        log.info("Associating WebDriver with current thread: {}", Thread.currentThread().getName());
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
            log.warn("quitDriver() called but WebDriver is already null for current thread");
        }
    }
}
