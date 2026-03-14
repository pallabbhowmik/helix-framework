package core;

import config.ConfigManager;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Base class for all Page Objects implementing the Page Object Model (POM) pattern.
 * Provides reusable methods for element interaction with built-in explicit waits,
 * logging, and Allure step reporting.
 *
 * <p>All page objects should extend this class to inherit common web element
 * operations (click, type, select, hover, scroll, etc.).</p>
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Actions actions;

    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        if (this.driver == null) {
            throw new FrameworkException(
                    "WebDriver is null. Ensure DriverManager.setDriver() is called before creating page objects.");
        }
        long timeout = ConfigManager.getTimeout();
        log.debug("Initializing {} with timeout={} seconds", this.getClass().getSimpleName(), timeout);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        this.actions = new Actions(driver);
    }

    // ── Element finders ──

    protected WebElement $(By locator) {
        return waitForVisible(locator);
    }

    protected List<WebElement> $$(By locator) {
        log.debug("Finding all elements: {}", locator);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElements(locator);
    }

    // ── Wait helpers ──

    protected WebElement waitForVisible(By locator) {
        log.debug("Waiting for visibility: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        log.debug("Waiting for clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForInvisible(By locator) {
        log.debug("Waiting for invisible: {}", locator);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected boolean waitForTextToBe(By locator, String expectedText) {
        log.debug("Waiting for text '{}' in: {}", expectedText, locator);
        return wait.until(ExpectedConditions.textToBe(locator, expectedText));
    }

    // ── Core interactions ──

    protected void click(By locator) {
        log.info("Clicking: {}", locator);
        waitForClickable(locator).click();
    }

    protected void jsClick(By locator) {
        log.info("JS-clicking: {}", locator);
        WebElement element = waitForVisible(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void type(By locator, String text) {
        log.info("Typing into: {}", locator);
        WebElement el = waitForClickable(locator);
        el.click();
        el.clear();
        if (text != null && !text.isEmpty()) {
            el.sendKeys(text);
        }
    }

    protected void clearAndType(By locator, String text) {
        log.info("Clear and type into: {}", locator);
        WebElement el = waitForClickable(locator);
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"), text);
    }

    // ── Value retrieval ──

    protected String getText(By locator) {
        log.debug("Getting text from: {}", locator);
        return waitForVisible(locator).getText();
    }

    protected String getAttribute(By locator, String attribute) {
        log.debug("Getting attribute '{}' from: {}", attribute, locator);
        return waitForVisible(locator).getDomAttribute(attribute);
    }

    protected String getInputValue(By locator) {
        return getAttribute(locator, "value");
    }

    // ── Dropdown interactions ──

    protected void selectByVisibleText(By locator, String text) {
        log.info("Selecting '{}' from dropdown: {}", text, locator);
        new Select(waitForVisible(locator)).selectByVisibleText(text);
    }

    protected void selectByValue(By locator, String value) {
        log.info("Selecting value '{}' from dropdown: {}", value, locator);
        new Select(waitForVisible(locator)).selectByValue(value);
    }

    protected void selectByIndex(By locator, int index) {
        log.info("Selecting index {} from dropdown: {}", index, locator);
        new Select(waitForVisible(locator)).selectByIndex(index);
    }

    // ── Mouse actions ──

    protected void hoverOver(By locator) {
        log.info("Hovering over: {}", locator);
        actions.moveToElement(waitForVisible(locator)).perform();
    }

    protected void doubleClick(By locator) {
        log.info("Double-clicking: {}", locator);
        actions.doubleClick(waitForVisible(locator)).perform();
    }

    protected void rightClick(By locator) {
        log.info("Right-clicking: {}", locator);
        actions.contextClick(waitForVisible(locator)).perform();
    }

    protected void dragAndDrop(By source, By target) {
        log.info("Drag from {} to {}", source, target);
        actions.dragAndDrop(waitForVisible(source), waitForVisible(target)).perform();
    }

    // ── Scroll helpers ──

    protected void scrollToElement(By locator) {
        log.debug("Scrolling to: {}", locator);
        WebElement element = waitForVisible(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element);
    }

    protected void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, document.body.scrollHeight);");
    }

    // ── State checks ──

    public boolean isVisible(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (TimeoutException e) {
            log.debug("Element not visible (timeout): {}", locator);
            return false;
        }
    }

    public boolean isPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isEnabled(By locator) {
        try {
            return waitForVisible(locator).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isSelected(By locator) {
        return waitForVisible(locator).isSelected();
    }

    public int getElementCount(By locator) {
        return driver.findElements(locator).size();
    }

    // ── Page-level utilities ──

    protected void waitForPageLoad() {
        log.debug("Waiting for page to fully load");
        wait.until(d -> ((JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));
    }

    @Step("Navigate to URL: {url}")
    protected void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        driver.get(url);
        waitForPageLoad();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    // ── Frame / Window helpers ──

    protected void switchToFrame(By locator) {
        log.debug("Switching to iframe: {}", locator);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    protected void switchToWindow(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    protected void acceptAlert() {
        log.info("Accepting alert");
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    protected void dismissAlert() {
        log.info("Dismissing alert");
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    protected String getAlertText() {
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    }
}
