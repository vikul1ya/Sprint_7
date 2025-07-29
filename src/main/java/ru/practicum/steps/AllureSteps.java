package ru.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.practicum.model.Courier;
import ru.practicum.model.Order;

public class AllureSteps {

    private final CourierSteps courierSteps = new CourierSteps();
    private final OrderSteps orderSteps = new OrderSteps();

    @Step("Создать курьера с логином: {courier.login}")
    public ValidatableResponse createCourier(Courier courier) {
        return courierSteps.createCourier(courier);
    }

    @Step("Авторизоваться курьером с логином: {courier.login}")
    public ValidatableResponse loginCourier(Courier courier) {
        return courierSteps.loginCourier(courier);
    }

    @Step("Удалить курьера по ID: {id}")
    public ValidatableResponse deleteCourier(Integer id) {
        if (id != null) {
            return courierSteps.deleteCourier(id);
        }
        return null;
    }

    @Step("Создать заказ")
    public ValidatableResponse createOrder(Order order) {
        return orderSteps.createOrder(order);
    }

    @Step("Получить список заказов")
    public ValidatableResponse getOrdersList() {
        return orderSteps.getOrdersList();
    }

    @Step("Отменить заказ с трек-номером: {track}")
    public ValidatableResponse cancelOrder(Integer track) {
        return orderSteps.cancelOrder(track);
    }
}