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

@Epic("Тесты на создание курьера")
public class CourierCreateTest extends BaseTest {

    private Courier courier;
    private final AllureSteps allureSteps = new AllureSteps();

    @Before
    public void setUp() {
        // Создаём уникального курьера перед каждым тестом
        courier = new Courier()
                .setLogin(RandomStringUtils.randomAlphabetic(12))
                .setPassword(RandomStringUtils.randomAlphabetic(12))
                .setFirstName(RandomStringUtils.randomAlphabetic(10));
    }

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка, что курьер создаётся с полными данными и возвращает ok: true")
    public void shouldCreateCourierSuccessfullyTest() {
        allureSteps.createCourier(courier)
                .statusCode(SC_CREATED)
                .body("ok", Matchers.is(true));
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина")
    @Description("Проверка, что отсутствие login вызывает ошибку")
    public void shouldNotCreateCourierWithoutLoginTest() {

        Courier noLogin = new Courier()
                .setPassword("password")
                .setFirstName("Name");
        allureSteps.createCourier(noLogin)
                .statusCode(SC_BAD_REQUEST)
                .body("message", Matchers.containsString("Недостаточно данных для создания учетной записи"));
    }

        @Test
        @DisplayName("Нельзя создать курьера без пароля")
        @Description("Проверка, что отсутствие password вызывает ошибку")
        public void shouldNotCreateCourierWithoutPasswordTest() {
            Courier noPassword = new Courier()
                    .setLogin("login")
                    .setFirstName("Name");
            allureSteps.createCourier(noPassword)
                    .statusCode(SC_BAD_REQUEST)
                    .body("message", Matchers.containsString("Недостаточно данных для создания учетной записи"));
        }

    @Test
    @DisplayName("Нельзя создать курьера без имени")
    @Description("Проверка, что отсутствие firstName  вызывает ошибку")
    public void shouldNotCreateCourierWithoutFirstNameTest() {
        Courier noFirstName = new Courier()
                .setLogin("login")
                .setPassword("password");
        allureSteps.createCourier(noFirstName)
                .statusCode(SC_BAD_REQUEST)
                .body("message", Matchers.containsString("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Проверка, что повторное создание курьера с тем же логином возвращает ошибку")
    public void shouldNotCreateDuplicateCourierTest() {
        // 1. Успешное создание
        allureSteps.createCourier(courier)
                .statusCode(SC_CREATED)
                .body("ok", Matchers.is(true));

        // 2. Повторное создание — должно провалиться
        allureSteps.createCourier(courier)
                .statusCode(SC_CONFLICT)
                .body("message", Matchers.containsString("Этот логин уже используется"));
    }


    @After
    public void tearDown() {
        try {
            // Авторизуемся, чтобы получить ID
            Integer id = allureSteps.loginCourier(courier)
                    .extract()
                    .body()
                    .path("id");
            if (id != null) {
                allureSteps.deleteCourier(id);
            }
        } catch (Exception e) {
            // Игнорируем — курьер мог не создаться
        }
    }
}