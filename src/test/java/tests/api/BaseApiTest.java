package tests.api;

import config.ConfigManager;
import core.LogCleaner;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;

public abstract class BaseApiTest {

    private static final Logger log = LoggerFactory.getLogger(BaseApiTest.class);

    @BeforeClass(alwaysRun = true)
    public void setUpApi() {
        LogCleaner.clean();
        String baseUrl = ConfigManager.getApiBaseUrl();
        if (baseUrl == null || baseUrl.isEmpty()) {
            log.error("API base URL is not configured in ConfigManager");
            throw new IllegalStateException("API base URL is not configured in ConfigManager.");
        }

        RestAssured.baseURI = baseUrl;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        log.info("API base URI set to {}", baseUrl);
    }
}
