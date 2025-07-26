package ru.practicum.tests;

import org.hamcrest.Matchers;
import org.junit.Test;
import ru.practicum.steps.OrderSteps;

import java.util.List;

public class OrderListTest extends BaseTest {

    private OrderSteps orderSteps = new OrderSteps();

    @Test
    public void shouldGetOrdersListSuccessfully() {
        orderSteps.getOrdersList()
                .statusCode(200)
                .body("orders", Matchers.notNullValue())
                .body("orders", Matchers.instanceOf(List.class));

    }
}

