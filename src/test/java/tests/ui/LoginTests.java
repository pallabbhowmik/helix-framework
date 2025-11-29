package tests.ui;

import core.RetryAnalyzer;
import pages.AppPage;
import tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {
    @Test(priority = 0)
    public void loginSite() {
        login();
    }
    @Test(dependsOnMethods = "loginSite", priority = 1)
    public void verifyLoginTitle() throws InterruptedException {
        String expectedTitle = "PassTheNote App Workspace for Automation Practice";
        Thread.sleep(2000);
        String actualTitle = driver.getTitle();

        System.out.println("Page Title After Login: " + actualTitle);

        Assert.assertEquals(actualTitle, expectedTitle,
                "Page title after login is incorrect!");
    }
    @Test(dependsOnMethods = "loginSite", priority = 2)
    public void verifyLoginPageItems()  {

        AppPage appPage = new AppPage();
        Assert.assertTrue(appPage.btnLaunchDashboard.isDisplayed(),"The button Launch Dashboard is not visible");
        Assert.assertTrue(appPage.btnProductDiscovery.isDisplayed(),"The button Product Discovery is not visible");
        Assert.assertTrue(appPage.btnBrowseChallenges.isEnabled(),"The button Browse Challenges is not enabled");
        System.out.println("Page validation completed");

    }
}
