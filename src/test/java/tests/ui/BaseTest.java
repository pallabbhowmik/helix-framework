package tests.ui;

import config.ConfigManager;
import core.DriverManager;
import core.LogCleaner;
import core.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.HomePage;
import pages.LoginPage;

public abstract class BaseTest {

    protected WebDriver driver;
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        LogCleaner.clean();
        log.info("==== Test Class Setup Started: {} ====", this.getClass().getSimpleName());
        WebDriver createdDriver = WebDriverFactory.create();
        DriverManager.setDriver(createdDriver);
        driver = createdDriver;

        String baseUrl = ConfigManager.getBaseUrl();
        if (baseUrl == null || baseUrl.isEmpty()) {
            log.error("Base URL is not configured in ConfigManager");
            throw new IllegalStateException("Base URL is not configured in ConfigManager.");
        }

        log.info("Navigating to base URL: {}", baseUrl);
        driver.get(baseUrl);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        log.info("==== Test Class Teardown Started: {} ====", this.getClass().getSimpleName());
        DriverManager.quitDriver();
        log.info("==== Test Class Teardown Finished: {} ====", this.getClass().getSimpleName());
    }

    protected void login() {
        String user = ConfigManager.getUsername();
        String pwd  = ConfigManager.getPassword();

        if (user != null && pwd != null && !user.isEmpty() && !pwd.isEmpty()) {
            log.info("Starting login with user '{}'", user);
            HomePage home = new HomePage();
            home.clickLogin();

            LoginPage loginPage = new LoginPage();
            loginPage.enterEmail(user);
            loginPage.enterPassword(pwd);
            loginPage.clickSignInButton();
            log.info("Login flow executed for user '{}'", user);
        } else {
            log.error("Username or password not configured. user='{}', pwdNull={}", user, pwd == null);
            throw new IllegalStateException(
                    "Username or password not configured. Check ConfigManager properties."
            );
        }
    }
}
