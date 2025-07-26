package ru.practicum.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practicum.model.Order;
import ru.practicum.steps.OrderSteps;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTests extends BaseTest {

    private OrderSteps orderSteps = new OrderSteps();

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
    public void orderCreateTest(){
        Order order = new Order()
                .setFirstName("Name")
                .setLastName("Last")
                .setAddress("address")
                .setMetroStation("metro")
                .setPhone("phone")
                .setRentTime(5)
                .setDeliveryDate("2020-06-06")
                .setComment("comment");

        // Устанавливаем цвет только если массив не пустой
        if (color.length > 0) {
            order.setColor(java.util.Arrays.asList(color));
        }

        orderSteps.createOrder(order)
                .assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }
}

