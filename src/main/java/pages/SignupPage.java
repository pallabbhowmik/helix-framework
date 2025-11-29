package com.helix.automation.framework.pages;

import com.helix.automation.framework.core.BasePage;
import com.helix.automation.framework.config.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

public class SignupPage extends BasePage {
    // Helper to try multiple locator strategies in order: id, name, css, xpath
    private WebElement findElement(String id, String name, String css, String xpath) {
        try { return $(By.id(id)); } catch (Exception ignored) {}
        try { return $(By.name(name)); } catch (Exception ignored) {}
        try { return $(By.cssSelector(css)); } catch (Exception ignored) {}
        try { return $(By.xpath(xpath)); } catch (Exception ignored) {}
        throw new NoSuchElementException("Element not found: " + id + ", " + name + ", " + css + ", " + xpath);
    }

    public SignupPage open() { driver.get(ConfigManager.getBaseUrl() + "/auth/signup"); return this; }

    public SignupPage enterFullName(String name) {
        WebElement el = findElement("signup-fullname", "fullName", "[data-test='signup-fullname'], input[name='fullName']", "//input[@name='fullName']");
        el.clear();
        el.sendKeys(name);
        return this;
    }

    public SignupPage enterEmail(String emailStr) {
        WebElement el = findElement("signup-email", "email", "[data-test='signup-email'], input[name='email']", "//input[@type='email']");
        el.clear();
        el.sendKeys(emailStr);
        return this;
    }

    public SignupPage enterPassword(String pwd) {
        WebElement el = findElement("signup-password", "password", "[data-test='signup-password'], input[name='password']", "//input[@type='password']");
        el.clear();
        el.sendKeys(pwd);
        return this;
    }

    public SignupPage enterConfirmPassword(String pwd) {
        WebElement el = findElement("signup-confirm-password", "confirmPassword", "[data-test='signup-confirm-password'], input[name='confirmPassword']", "//input[@name='confirmPassword']");
        el.clear();
        el.sendKeys(pwd);
        return this;
    }

    public void clickContinue() {
        WebElement el = findElement("signup-submit", "", "[data-test='signup-submit'], button[type='submit']", "//button[contains(.,'Continue') or @type='submit']");
        el.click();
    }

    public boolean isErrorVisible() {
        try {
            WebElement el = findElement("signup-error", "", "[data-test='signup-error'], .error-message, .MuiAlert-message", "//*[contains(@class,'error') or contains(@class,'MuiAlert-message')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public String getErrorText() {
        try {
            WebElement el = findElement("signup-error", "", "[data-test='signup-error'], .error-message, .MuiAlert-message", "//*[contains(@class,'error') or contains(@class,'MuiAlert-message')]" );
            return el.getText();
        } catch (Exception e) { return ""; }
    }

    public boolean isFieldErrorVisible() {
        try {
            WebElement el = findElement("", "", ".MuiFormHelperText-root, .field-error", "//*[contains(@class,'field-error') or contains(@class,'MuiFormHelperText-root')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public String getFieldErrorText() {
        try {
            WebElement el = findElement("", "", ".MuiFormHelperText-root, .field-error", "//*[contains(@class,'field-error') or contains(@class,'MuiFormHelperText-root')]" );
            return el.getText();
        } catch (Exception e) { return ""; }
    }
}
