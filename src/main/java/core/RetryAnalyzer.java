package com.helix.automation.framework.core;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log = LoggerFactory.getLogger(RetryAnalyzer.class);

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = initMaxRetry();

    private static int initMaxRetry() {
        String value = System.getProperty("test.retry.count", "2");
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 2;
        }
    }

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            log.warn("Retrying test '{}' (attempt {} of {})",
                    result.getMethod().getMethodName(), retryCount, MAX_RETRY_COUNT);
            return true;
        }
        return false;
    }
}
