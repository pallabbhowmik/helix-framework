package com.helix.automation.framework.core;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverHolder = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverHolder.get();
    }

    public static void setDriver(WebDriver driver) {
        driverHolder.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = driverHolder.get();
        if (driver != null) {
            driver.quit();
            driverHolder.remove();
        }
    }
}
