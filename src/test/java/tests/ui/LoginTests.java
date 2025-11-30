package tests.ui;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AppPage;
import tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTests extends BaseTest {
    @Test(priority = 0)
    public void loginSite() {
        login();
    }

    @Test(dependsOnMethods = "loginSite", priority = 1)
    public void verifyLoginTitle() {
        String expectedTitle = "PassTheNote App Workspace for Automation Practice";
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.titleIs(expectedTitle));
        String actualTitle = driver.getTitle();

        System.out.println("Page Title After Login: " + actualTitle);

        Assert.assertEquals(actualTitle, expectedTitle,
                "Page title after login is incorrect!");
    }

    @Test(priority = 2)
    public void verifyLoginPageItems() {
        AppPage appPage = new AppPage();
        Assert.assertTrue(appPage.isLaunchDashboardVisible(), "The button Launch Dashboard is not visible");
        Assert.assertTrue(appPage.isProductDiscoveryVisible(), "The button Product Discovery is not visible");
        Assert.assertTrue(appPage.isBrowseChallengesEnabled(), "The button Browse Challenges is not enabled");
        System.out.println("Page validation completed");
    }
}
