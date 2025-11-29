package com.helix.automation.tests;

import com.helix.automation.framework.config.ConfigManager;
import com.helix.automation.framework.core.DriverManager;
import com.helix.automation.framework.core.WebDriverFactory;
import com.helix.automation.framework.pages.HomePage;
import com.helix.automation.framework.pages.LoginPage;
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

    protected void login() throws InterruptedException {
        String user = ConfigManager.getUsername();
        String pwd  = ConfigManager.getPassword();

        if (user != null && pwd != null && !user.isEmpty() && !pwd.isEmpty()) {
            HomePage home = new HomePage();
            home.clickLogin();

            LoginPage loginPage = new LoginPage();
            Thread.sleep(5000);
            loginPage.enterEmail(user);
            loginPage.enterPassword(pwd);
            loginPage.clickSignInButton();
        }
    }
}
