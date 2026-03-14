package tests.ui;

import config.ConfigManager;
import core.DriverManager;
import core.LogCleaner;
import core.WebDriverFactory;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.AppPage;
import pages.HomePage;

/**
 * Base class for all UI tests.
 * Manages WebDriver lifecycle per test method and provides
 * common actions like login.
 *
 * <p>Uses @BeforeMethod/@AfterMethod to ensure each test gets
 * a fresh browser instance — preventing state leakage between tests.</p>
 */
public abstract class BaseTest {

    protected WebDriver driver;
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        LogCleaner.clean();
        ConfigManager.validate("HELIX_BASE_URL");

        log.info("==== Test Setup: {} | Browser: {} | Env: {} ====",
                this.getClass().getSimpleName(),
                ConfigManager.getBrowser(),
                ConfigManager.getEnv().getName());

        WebDriver createdDriver = WebDriverFactory.create();
        DriverManager.setDriver(createdDriver);
        driver = createdDriver;

        String baseUrl = ConfigManager.getBaseUrl();
        log.info("Navigating to: {}", baseUrl);
        driver.get(baseUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("==== Test Teardown: {} ====", this.getClass().getSimpleName());
        DriverManager.quitDriver();
    }

    @Step("Login with configured credentials")
    protected AppPage login() {
        ConfigManager.validate("HELIX_USERNAME", "HELIX_PASSWORD");

        String user = ConfigManager.getUsername();
        String pwd  = ConfigManager.getPassword();

        log.info("Logging in as: {}", user);
        HomePage home = new HomePage();
        return home.clickLogin().loginAs(user, pwd);
    }
}
