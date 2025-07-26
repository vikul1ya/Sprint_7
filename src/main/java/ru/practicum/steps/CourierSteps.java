package ru.practicum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.practicum.config.RestConfig;
import ru.practicum.model.Courier;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    public ValidatableResponse createCourier(Courier courier){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RestConfig.HOST)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse loginCourier(Courier courier) {
        // Для логина отправляем только логин и пароль
        Courier loginPayload = new Courier()
                .setLogin(courier.getLogin())
                .setPassword(courier.getPassword());

        return given()
                .contentType(ContentType.JSON)
                .baseUri(RestConfig.HOST)
                .body(loginPayload)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    public ValidatableResponse deleteCourier(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Courier ID cannot be null for deletion");
        }
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RestConfig.HOST)
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }
}