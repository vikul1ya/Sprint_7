package ru.practicum.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.hamcrest.Matchers;
import org.junit.Test;
import ru.practicum.steps.AllureSteps;

import java.util.List;

@Epic("Тесты на заказы")
@Feature("Список заказов")
public class OrderListTest extends BaseTest {

    private final AllureSteps allureSteps = new AllureSteps();

    @Test
    public void shouldGetOrdersListSuccessfullyTest() {
        allureSteps.getOrdersList()
                .statusCode(200)
                .body("orders", Matchers.notNullValue())
                .body("orders", Matchers.instanceOf(List.class));
    }
}