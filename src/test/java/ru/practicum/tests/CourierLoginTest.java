package ru.practicum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;

import ru.practicum.model.Courier;
import ru.practicum.steps.AllureSteps;

@Epic("Тесты на авторизацию курьера")
public class CourierLoginTest extends BaseTest {

    private Courier courier;
    private Integer courierId;
    private final AllureSteps allureSteps = new AllureSteps();

    @Before
    public void setUp() {
        courier = new Courier()
                .setLogin(RandomStringUtils.randomAlphabetic(12))
                .setPassword(RandomStringUtils.randomAlphabetic(12))
                .setFirstName(RandomStringUtils.randomAlphabetic(10));

        allureSteps.createCourier(courier);
        courierId = allureSteps.loginCourier(courier)
                .extract()
                .body()
                .path("id");
    }

    @Test
    @DisplayName("Тест: успешная авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться и получить ID")
    public void shouldLoginCourierSuccessfullyTest() {
        allureSteps.loginCourier(courier)
                .statusCode(SC_OK)
                .body("id", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Тест: авторизация без пароля")
    @Description("Проверка, что авторизация без пароля возвращает ошибку")
    public void shouldNotLoginCourierWithoutPasswordTest() {
        Courier incompleteCourier = new Courier()
                .setLogin(courier.getLogin());
        allureSteps.loginCourier(incompleteCourier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", Matchers.containsString("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест: авторизация без логина")
    @Description("Проверка, что авторизация без логина возвращает ошибку")
    public void shouldNotLoginCourierWithoutLoginTest() {
        Courier incompleteCourier = new Courier()
                .setPassword(courier.getPassword());
        allureSteps.loginCourier(incompleteCourier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", Matchers.containsString("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест: авторизация с неверными данными")
    @Description("Проверка, что неверные учетные данные возвращают ошибку")
    public void shouldNotLoginWithInvalidCredentialsTest() {
        Courier invalidCourier = new Courier()
                .setLogin("nonexistent_login")
                .setPassword("wrong_password");
        allureSteps.loginCourier(invalidCourier)
                .statusCode(SC_NOT_FOUND)
                .body("message", Matchers.containsString("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Тест: авторизация несуществующего курьера")
    @Description("Проверка, что несуществующий курьер не может авторизоваться")
    public void shouldNotLoginNonExistentCourierTest() {
        Courier nonExistentCourier = new Courier()
                .setLogin(RandomStringUtils.randomAlphabetic(12))
                .setPassword(RandomStringUtils.randomAlphabetic(12));
        allureSteps.loginCourier(nonExistentCourier)
                .statusCode(SC_NOT_FOUND)
                .body("message", Matchers.containsString("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        try {
            Integer id = allureSteps.loginCourier(courier)
                    .extract()
                    .body()
                    .path("id");
            if (id != null) {
                allureSteps.deleteCourier(id);
            }
        } catch (Exception e) {
            // Игнорируем
        }
    }
}