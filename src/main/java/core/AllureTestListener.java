package core;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AllureTestListener implements ITestListener {

    private static final Logger log = LoggerFactory.getLogger(AllureTestListener.class);

    // Keep in sync with log4j2.xml
    private static final Path LOG_FILE_PATH = Paths.get("logs", "automation.log");

    private byte[] takeScreenshot() {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            log.warn("Screenshot requested but WebDriver is null");
            return null;
        }

        try {
            if (driver instanceof TakesScreenshot) {
                log.debug("Capturing screenshot from WebDriver");
                return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            } else {
                log.warn("Current WebDriver does not support TakesScreenshot");
            }
        } catch (Exception e) {
            log.error("Failed to capture screenshot", e);
        }
        return null;
    }

    private void attachScreenshot(String name) {
        byte[] screenshot = takeScreenshot();
        if (screenshot != null) {
            log.info("Attaching screenshot to Allure: {}", name);
            Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        } else {
            log.warn("No screenshot captured to attach for: {}", name);
        }
    }

    private void attachLogFile(String name) {
        if (!Files.exists(LOG_FILE_PATH)) {
            log.warn("Log file does not exist, cannot attach to Allure: {}",
                    LOG_FILE_PATH.toAbsolutePath());
            return;
        }

        try (InputStream is = Files.newInputStream(LOG_FILE_PATH)) {
            log.info("Attaching log file to Allure: {} (path={})",
                    name, LOG_FILE_PATH.toAbsolutePath());
            Allure.addAttachment(name, "text/plain", is, ".log");
        } catch (IOException e) {
            log.error("Failed to attach log file to Allure from path: " +
                    LOG_FILE_PATH.toAbsolutePath(), e);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        log.error("Test FAILED: {}. Capturing screenshot and logs.", methodName);

        attachScreenshot("Failure Screenshot - " + methodName);
        attachLogFile("Execution Log - " + methodName);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("Test SKIPPED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Test STARTED: {}.{}",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test PASSED: {} ({}ms)",
                result.getMethod().getMethodName(),
                result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.warn("Test FAILED within success percentage: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("Suite STARTED: {}", context.getName());
        AllureEnvironmentWriter.write();
    }

    @Override
    public void onFinish(ITestContext context) {
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();
        log.info("Suite FINISHED: {} | Passed: {} | Failed: {} | Skipped: {}",
                context.getName(), passed, failed, skipped);
    }
}
