package core;

import config.ConfigManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    public BasePage() {
        this.driver = DriverManager.getDriver();
        if (this.driver == null) {
            log.error("WebDriver is null in BasePage constructor");
            throw new IllegalStateException(
                    "WebDriver is null. Make sure DriverManager.setDriver() is called before creating page objects."
            );
        }
        long timeout = ConfigManager.getTimeout();
        log.debug("Initializing WebDriverWait with timeout={} seconds", timeout);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    protected WebElement $(By locator) {
        return waitForVisible(locator);
    }

    protected WebElement waitForVisible(By locator) {
        log.debug("Waiting for visibility of element located by: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        log.debug("Waiting for element to be clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        log.info("Clicking element: {}", locator);
        waitForClickable(locator).click();
    }

    protected void type(By locator, String text) {
        log.info("Typing into element: {} | value='{}'", locator, text);
        WebElement el = waitForClickable(locator);
        el.click();
        el.clear();
        if (text != null && !text.isEmpty()) {
            el.sendKeys(text);
        }
    }

    protected String getText(By locator) {
        log.debug("Getting text from element: {}", locator);
        return waitForVisible(locator).getText();
    }

    public boolean isVisible(By locator) {
        try {
            boolean visible = waitForVisible(locator).isDisplayed();
            log.debug("Element visibility for {}: {}", locator, visible);
            return visible;
        } catch (TimeoutException e) {
            log.debug("Element not visible (timeout) for locator: {}", locator);
            return false;
        }
    }

    public boolean isPresent(By locator) {
        try {
            driver.findElement(locator);
            log.debug("Element present in DOM: {}", locator);
            return true;
        } catch (NoSuchElementException e) {
            log.debug("Element NOT present in DOM: {}", locator);
            return false;
        }
    }
}
