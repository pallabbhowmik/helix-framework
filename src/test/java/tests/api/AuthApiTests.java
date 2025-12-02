package tests.api;

import io.restassured.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.TestDataReader;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

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
    public void loginApiTest(String email, String password, int expectedStatus) {

        log.info("Executing loginApiTest for email='{}', expecting status {}", email, expectedStatus);

        Map<String, Object> payload = Map.of(
                "email", email,
                "password", password
        );

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(expectedStatus);
    }
}
