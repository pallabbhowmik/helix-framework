package core;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class AllureTestListener implements ITestListener {

    private byte[] takeScreenshot() {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            return null;
        }

        try {
            if (driver instanceof TakesScreenshot ts) {
                return ts.getScreenshotAs(OutputType.BYTES);
            }
        } catch (Exception e) {
            // ignore, return null below
        }
        return null;
    }

    private void attachScreenshot(String name) {
        byte[] screenshot = takeScreenshot();
        if (screenshot != null) {
            Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        attachScreenshot("Failure - " + result.getMethod().getMethodName());
    }

    // Optional: screenshot on skip or success, if you want
    @Override
    public void onTestSkipped(ITestResult result) {
        // attachScreenshot("Skipped - " + result.getMethod().getMethodName());
    }

    @Override public void onTestStart(ITestResult result) { }
    @Override public void onTestSuccess(ITestResult result) { }
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) { }
    @Override public void onStart(ITestContext context) { }
    @Override public void onFinish(ITestContext context) { }
}
