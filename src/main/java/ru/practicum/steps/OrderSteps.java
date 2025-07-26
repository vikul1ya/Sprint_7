package ru.practicum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.practicum.config.RestConfig;
import ru.practicum.model.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    public ValidatableResponse createOrder(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RestConfig.HOST)
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    public ValidatableResponse getOrdersList() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RestConfig.HOST)
                .when()
                .get("/api/v1/orders")
                .then();
    }
}