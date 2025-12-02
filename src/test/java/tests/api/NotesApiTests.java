package tests.api;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class NotesApiTests extends BaseApiTest {

    private static final Logger log = LoggerFactory.getLogger(NotesApiTests.class);

    @Test
    public void shouldReturnNotesList() {
        log.info("Verifying that notes endpoint returns 200 and a non-empty list");

        Response response =
                given()
                        .when()
                        .get("/gateway/notes")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        List<?> notes = response.jsonPath().getList("data");
        log.info("Notes endpoint returned {} notes", notes != null ? notes.size() : 0);

        Assert.assertNotNull(notes, "Notes list should not be null");
        Assert.assertFalse(notes.isEmpty(), "Notes list should not be empty");
    }

    @Test
    public void shouldContainAdminNote() {
        log.info("Validating that notes list contains admin note");

        given()
                .when()
                .get("/gateway/notes")
                .then()
                .statusCode(200)
                .body("data.authorId", hasItem("user-admin-001"))
                .body("data.id[0]", equalTo("note-001"));
    }

    @Test
    public void shouldFilterRestfulApiObjectsByPrice() {
        log.info("Calling external sample API: https://api.restful-api.dev/objects");

        Response response =
                given()
                        .baseUri("https://api.restful-api.dev")
                        .log().all()   // log REQUEST
                        .when()
                        .get("/objects")
                        .then()
                        .log().all()   // log RESPONSE
                        .statusCode(200)
                        .extract()
                        .response();

        // Item with id = 3
        Map<String, Object> item =
                response.jsonPath().getMap("find { it.id == '3' }.data");
        log.info("Item with id=3 data: {}", item);

        // All objects where data.price > 50
        List<Map<String, Object>> expensiveItems =
                response.jsonPath().getList(
                        "findAll { it.data != null && it.data.price != null && it.data.price > 50 }"
                );

        log.info("Found {} items with price > 50", expensiveItems.size());
    }
}
