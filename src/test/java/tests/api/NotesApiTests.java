package tests.api;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("API Testing")
@Feature("Notes API")
public class NotesApiTests extends BaseApiTest {

    private static final Logger log = LoggerFactory.getLogger(NotesApiTests.class);

    @Test(description = "GET /gateway/notes should return 200 with non-empty list")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Fetch Notes")
    public void shouldReturnNotesList() {
        log.info("Verifying notes endpoint returns 200 and a non-empty list");

        Response response =
                given()
                        .when()
                        .get("/gateway/notes")
                        .then()
                        .statusCode(200)
                        .body("data", is(notNullValue()))
                        .body("data.size()", greaterThan(0))
                        .extract()
                        .response();

        List<?> notes = response.jsonPath().getList("data");
        log.info("Notes endpoint returned {} notes", notes.size());
    }

    @Test(description = "Notes list should contain admin note with expected fields")
    @Severity(SeverityLevel.NORMAL)
    @Story("Fetch Notes")
    public void shouldContainAdminNote() {
        log.info("Validating notes list contains admin note");

        given()
                .when()
                .get("/gateway/notes")
                .then()
                .statusCode(200)
                .body("data.authorId", hasItem("user-admin-001"))
                .body("data.id[0]", equalTo("note-001"));
    }

    @Test(description = "Filter objects by price from external REST API")
    @Severity(SeverityLevel.MINOR)
    @Story("External API Integration")
    public void shouldFilterRestfulApiObjectsByPrice() {
        log.info("Testing external API: restful-api.dev");

        Response response =
                given()
                        .baseUri("https://api.restful-api.dev")
                        .log().all()
                        .when()
                        .get("/objects")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .response();

        Map<String, Object> item =
                response.jsonPath().getMap("find { it.id == '3' }.data");

        List<Map<String, Object>> expensiveItems =
                response.jsonPath().getList(
                        "findAll { it.data != null && it.data.price != null && it.data.price > 50 }"
                );

        SoftAssert soft = new SoftAssert();
        soft.assertNotNull(item, "Item with id=3 should exist");
        soft.assertTrue(expensiveItems.size() > 0, "Should have items with price > 50");
        soft.assertAll();

        log.info("Item id=3 data: {} | Expensive items count: {}", item, expensiveItems.size());
    }
}
