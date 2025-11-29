package tests;

import config.ConfigManager;
import core.DriverManager;
import core.WebDriverFactory;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.out.println("Starting driver");
        DriverManager.setDriver(WebDriverFactory.create());
        driver = DriverManager.getDriver();
        driver.get(ConfigManager.getBaseUrl());
    }

    @AfterClass
    public void tearDown() {
        System.out.println("Quitting driver");
        DriverManager.quitDriver();
    }
    protected void login() {
        String user = ConfigManager.getUsername();
        String pwd  = ConfigManager.getPassword();

        if (user != null && pwd != null && !user.isEmpty() && !pwd.isEmpty()) {
            HomePage home = new HomePage();
            home.clickLogin();

            LoginPage loginPage = new LoginPage();
            loginPage.enterEmail(user);
            loginPage.enterPassword(pwd);
            loginPage.clickSignInButton();
        }
    }
}
