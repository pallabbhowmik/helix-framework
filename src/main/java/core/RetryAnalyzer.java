package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log = LoggerFactory.getLogger(RetryAnalyzer.class);

    private int retryCount = 0;

    /**
     * Maximum retry attempts controlled via:
     *   -Dtest.retry.count=#
     * Example:
     *   gradle test -Dtest.retry.count=3
     */
    private static final int MAX_RETRY_COUNT = resolveRetryCount();

    private static int resolveRetryCount() {
        String value = System.getProperty("test.retry.count", "2");
        try {
            int parsed = Integer.parseInt(value);
            log.info("Retry logic enabled — Max retry count: {}", parsed);
            return parsed;
        } catch (NumberFormatException e) {
            log.warn("Invalid test.retry.count='{}'. Falling back to default value: 2", value);
            return 2;
        }
    }

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            log.warn(
                    "Retrying test '{}' for class [{}] (attempt {} of {})",
                    result.getMethod().getMethodName(),
                    result.getTestClass().getName(),
                    retryCount,
                    MAX_RETRY_COUNT
            );
            return true;
        }

        log.error(
                "Test '{}' FAILED after {} retries — no more attempts",
                result.getMethod().getMethodName(),
                MAX_RETRY_COUNT
        );
        return false;
    }
}
