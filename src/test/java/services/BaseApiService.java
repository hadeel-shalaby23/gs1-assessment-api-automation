package services;

import config.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseApiService {

    protected RequestSpecification request() {
        return given()
                .baseUri(ConfigManager.get("base.url"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    protected Response get(String endpoint) {
        return request()
                .when()
                .get(endpoint);
    }

    protected Response post(String endpoint, Object body) {
        return request()
                .body(body)
                .when()
                .post(endpoint);
    }

    protected Response put(String endpoint, Object body) {
        return request()
                .body(body)
                .when()
                .put(endpoint);
    }

    protected Response delete(String endpoint) {
        return request()
                .when()
                .delete(endpoint);
    }

}
