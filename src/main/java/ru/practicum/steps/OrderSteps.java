package ru.practicum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.practicum.config.Endpoints;
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
                .post(Endpoints.ORDERS)
                .then();
    }

    public ValidatableResponse getOrdersList() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RestConfig.HOST)
                .when()
                .get(Endpoints.ORDERS)
                .then();
    }

    public ValidatableResponse cancelOrder(Integer track) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RestConfig.HOST)
                .body("{\"track\": " + track + "}")
                .when()
                .put(Endpoints.ORDER_CANCEL)
                .then();
    }
}
