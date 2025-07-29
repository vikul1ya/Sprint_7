package ru.practicum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.practicum.config.Endpoints;
import ru.practicum.config.RestConfig;
import ru.practicum.model.Courier;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RestConfig.HOST)
                .body(courier)
                .when()
                .post(Endpoints.COURIER)
                .then();
    }

    public ValidatableResponse loginCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RestConfig.HOST)
                .body(courier)
                .when()
                .post(Endpoints.COURIER_LOGIN)
                .then();
    }

    public ValidatableResponse deleteCourier(Integer id) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RestConfig.HOST)
                .pathParam("id", id)
                .when()
                .delete(Endpoints.COURIER + "/{id}")
                .then();
    }
}