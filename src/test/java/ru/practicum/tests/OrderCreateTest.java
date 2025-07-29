package ru.practicum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practicum.model.Order;
import ru.practicum.steps.OrderSteps;
import ru.practicum.utils.DataGenerator;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseTest {

    private final OrderSteps steps = new OrderSteps();
    private Integer track;

    @Parameterized.Parameter()
    public String[] color;

    @Parameterized.Parameter(1)
    public String description;

    @Parameterized.Parameters(name = "Цвет: {1}")
    public static Object[][] data() {
        List<String[]> colors = DataGenerator.colorCombinations();
        return new Object[][]{
                {colors.get(0), "чёрный"},
                {colors.get(1), "серый"},
                {colors.get(2), "чёрный и серый"},
                {colors.get(3), "без цвета"}
        };
    }

    @Test
    @DisplayName("Создание заказа с разными цветами")
    @Description("Проверка, что заказ создаётся при разных комбинациях цветов")
    public void shouldCreateOrderWithColor() {
        Order order = DataGenerator.randomOrder();
        if (color.length > 0) {
            order.setColor(List.of(color));
        }

        var response = steps.createOrder(order);
        response
                .statusCode(SC_CREATED)
                .body("track", notNullValue());

        track = response.extract().path("track");
    }

    @After
    public void tearDown() {
        if (track != null) {
            steps.cancelOrder(track)
                    .statusCode(SC_OK);
        }
    }
}