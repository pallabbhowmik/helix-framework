package tests.api;

import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.TestDataReader;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Epic("API Testing")
@Feature("Authentication API")
public class AuthApiTests extends BaseApiTest {

    private static final Logger log = LoggerFactory.getLogger(AuthApiTests.class);

    @DataProvider(name = "loginData")
    public Object[][] getLoginTestData() {
        List<Map<String, Object>> data = TestDataReader.readJson("testdata/loginTestData.json");
        log.info("Loaded {} login test data rows from JSON", data.size());

        return data.stream()
                .map(entry -> new Object[]{
                        entry.get("email"),
                        entry.get("password"),
                        ((Number) entry.get("expectedStatus")).intValue()
                })
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "loginData",
            description = "Login API should return the expected HTTP status for each credential set")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Login Endpoint")
    public void loginApiTest(String email, String password, int expectedStatus) {
        log.info("Testing login for email='{}', expecting HTTP {}", email, expectedStatus);

        Map<String, Object> payload = Map.of(
                "email", email,
                "password", password
        );

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(expectedStatus)
                .extract()
                .response();

        if (expectedStatus == 200) {
            String token = response.jsonPath().getString("token");
            log.info("Login successful, token received (length={})",
                    token != null ? token.length() : 0);
            Assert.assertNotNull(token, "Token should not be null on successful login");
        }
    }
}
