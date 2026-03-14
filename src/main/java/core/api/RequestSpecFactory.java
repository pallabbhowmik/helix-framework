package core.api;

import config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Centralized factory for building RestAssured RequestSpecifications.
 * Follows the Factory pattern to standardize API request configuration.
 */
public final class RequestSpecFactory {

    private RequestSpecFactory() {
        // utility class
    }

    /**
     * Base spec with URI, content type, and logging.
     */
    public static RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getApiBaseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .build();
    }

    /**
     * Authenticated spec with Bearer token header.
     */
    public static RequestSpecification authSpec(String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(baseSpec())
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
