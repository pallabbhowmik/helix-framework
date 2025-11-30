package tests.api;

import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.TestDataReader;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthApiTests extends BaseApiTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginTestData() {

        List<Map<String, Object>> data = TestDataReader.readJson("testdata/loginTestData.json");

        return data.stream()
                .map(entry -> new Object[]{
                        entry.get("email"),
                        entry.get("password"),
                        entry.get("expectedStatus")
                })
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "loginData")
    public void loginApiTest(String email, String password, int expectedStatus) {

        given()
                .contentType(ContentType.JSON)
                .body("""
                  {
                    "email": "%s",
                    "password": "%s"
                  }
                  """.formatted(email, password))
                .when()
                .post("/auth/login")
                .then()
                .statusCode(expectedStatus);
    }
}
