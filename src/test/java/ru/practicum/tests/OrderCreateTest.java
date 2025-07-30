package ru.practicum.tests;

import io.qameta.allure.Epic;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practicum.model.Order;
import ru.practicum.steps.AllureSteps;

import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
@Epic("Тесты на создание заказа")
public class OrderCreateTest extends BaseTest {

    private final AllureSteps allureSteps = new AllureSteps();
    private Integer track;

    @Parameterized.Parameter()
    public String[] color;

    @Parameterized.Parameters(name = "Цвет: {0}")
    public static Object[][] data() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        };
    }

    @Test
    public void orderCreateTest() {
        Order order = new Order()
                .setFirstName("Name")
                .setLastName("Last")
                .setAddress("address")
                .setMetroStation("metro")
                .setPhone("phone")
                .setRentTime(5)
                .setDeliveryDate("2020-06-06")
                .setComment("comment");

        if (color.length > 0) {
            order.setColor(java.util.Arrays.asList(color));
        }

        ValidatableResponse response = allureSteps.createOrder(order);
        response
                .assertThat()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());

        track = response.extract().body().jsonPath().getInt("track");
    }

    @After
    public void tearDown() {
        if (track != null) {
            allureSteps.cancelOrder(track);
        }
    }
}