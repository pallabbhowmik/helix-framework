package tests.ui;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AppPage;

import java.time.Duration;

public class LoginTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(LoginTests.class);

    @Test(priority = 0)
    public void loginSite() {
        log.info("Running test: loginSite");
        login();
    }

    @Test(dependsOnMethods = "loginSite", priority = 1)
    public void verifyLoginTitle() {
        log.info("Running test: verifyLoginTitle");
        String expectedTitle = "PassTheNote App Workspace for Automation Practice";

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.titleIs(expectedTitle));

        String actualTitle = driver.getTitle();
        log.info("Page Title After Login: '{}'", actualTitle);

        Assert.assertEquals(
                actualTitle,
                expectedTitle,
                "Page title after login is incorrect!"
        );
    }

    @Test(dependsOnMethods = "loginSite", priority = 2)
    public void verifyLoginPageItems() {
        log.info("Running test: verifyLoginPageItems");
        AppPage appPage = new AppPage();

        Assert.assertTrue(
                appPage.isLaunchDashboardVisible(),
                "The button Launch Dashboard is not visible"
        );
        Assert.assertTrue(
                appPage.isProductDiscoveryVisible(),
                "The button Product Discovery is not visible"
        );
        Assert.assertTrue(
                appPage.isBrowseChallengesEnabled(),
                "The button Browse Challenges is not enabled"
        );

        log.info("Page validation completed successfully");
    }
}
