
package pages;

import config.ConfigManager;
import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class AdminPage extends BasePage {
    // Helper to try multiple locator strategies in order: id, name, css, xpath
    private WebElement findElement(String id, String name, String css, String xpath) {
        try { return $(By.id(id)); } catch (Exception ignored) {}
        try { return $(By.name(name)); } catch (Exception ignored) {}
        try { return $(By.cssSelector(css)); } catch (Exception ignored) {}
        try { return $(By.xpath(xpath)); } catch (Exception ignored) {}
        throw new NoSuchElementException("Element not found: " + id + ", " + name + ", " + css + ", " + xpath);
    }

    public AdminPage open() { driver.get(ConfigManager.getBaseUrl() + "/admin"); return this; }

    public boolean isAdminPanelVisible() {
        try {
            WebElement el = findElement("admin-panel", "", "[data-test='admin-panel']", "//div[contains(@class,'admin-panel')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isUserListVisible() {
        try {
            WebElement el = findElement("admin-users", "", "[data-test='admin-users']", "//div[contains(@class,'admin-users')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isForbiddenMessageVisible() {
        try {
            WebElement el = findElement("admin-forbidden", "", "[data-test='admin-forbidden'], .forbidden, .error-403", "//*[contains(@class,'forbidden') or contains(@class,'error-403')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isDashboardVisible() { return isAdminPanelVisible() && isUserListVisible(); }
}
