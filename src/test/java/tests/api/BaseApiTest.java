package tests.api;

import config.ConfigManager;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public abstract class BaseApiTest {

    @BeforeClass
    public void setUpApi() {
        // Base API URL from your .env.config
        RestAssured.baseURI = ConfigManager.getApiBaseUrl();
    }
}
