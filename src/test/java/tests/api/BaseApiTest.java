package tests.api;

import config.ConfigManager;
import core.LogCleaner;
import core.api.RequestSpecFactory;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;

/**
 * Base class for all API tests.
 * Configures RestAssured with base URI and request specification.
 */
public abstract class BaseApiTest {

    private static final Logger log = LoggerFactory.getLogger(BaseApiTest.class);

    @BeforeClass(alwaysRun = true)
    public void setUpApi() {
        LogCleaner.clean();
        ConfigManager.validate("HELIX_API_BASE_URL");

        RestAssured.baseURI = ConfigManager.getApiBaseUrl();
        RestAssured.requestSpecification = RequestSpecFactory.baseSpec();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        log.info("API base URI: {} | Environment: {}",
                RestAssured.baseURI, ConfigManager.getEnv().getName());
    }
}
