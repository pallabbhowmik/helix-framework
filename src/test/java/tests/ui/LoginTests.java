package tests.ui;

import io.qameta.allure.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AppPage;

import java.time.Duration;

@Epic("User Authentication")
@Feature("Login")
public class LoginTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(LoginTests.class);

    @Test(description = "Verify successful login with valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Valid Login")
    public void testSuccessfulLogin() {
        log.info("Running test: testSuccessfulLogin");

        AppPage appPage = login();
        Assert.assertNotNull(appPage, "Login should return AppPage on success");

        log.info("Login completed successfully");
    }

    @Test(description = "Verify page title after successful login")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Post-Login Validation")
    public void testPageTitleAfterLogin() {
        log.info("Running test: testPageTitleAfterLogin");
        login();

        String expectedTitle = "PassTheNote App Workspace for Automation Practice";
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.titleIs(expectedTitle));

        String actualTitle = driver.getTitle();
        log.info("Page title: '{}'", actualTitle);

        Assert.assertEquals(actualTitle, expectedTitle,
                "Page title after login is incorrect");
    }

    @Test(description = "Verify dashboard elements are visible after login")
    @Severity(SeverityLevel.NORMAL)
    @Story("Post-Login Validation")
    public void testDashboardElementsVisible() {
        log.info("Running test: testDashboardElementsVisible");
        AppPage appPage = login();

        SoftAssert soft = new SoftAssert();
        soft.assertTrue(appPage.isLaunchDashboardVisible(),
                "Launch Dashboard button should be visible");
        soft.assertTrue(appPage.isProductDiscoveryVisible(),
                "Product Discovery section should be visible");
        soft.assertTrue(appPage.isBrowseChallengesEnabled(),
                "Browse Challenges button should be enabled");
        soft.assertAll();

        log.info("Dashboard element validation passed");
    }
}
