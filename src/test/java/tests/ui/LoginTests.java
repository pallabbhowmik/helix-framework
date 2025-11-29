package com.helix.automation.tests.ui.tests;

import com.helix.automation.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {
    @Test(groups = "Login")
    public void verifyLoginTitle() throws InterruptedException {
        login();

        String expectedTitle = "PassTheNote App Workspace for Automation Practice";
        String actualTitle = driver.getTitle();

        System.out.println("Page Title After Login: " + actualTitle);

        Assert.assertEquals(actualTitle, expectedTitle,
                "Page title after login is incorrect!");
    }
}
