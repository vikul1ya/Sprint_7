package ru.practicum.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.model.Courier;
import ru.practicum.steps.CourierSteps;

public class CourierTests extends BaseTest{


        private CourierSteps courierSteps = new CourierSteps();
        private Courier courier;
        private Integer courierId;

        @Before
        public void setUp() {
            courier = new Courier();
            courier.setLogin(RandomStringUtils.randomAlphabetic(12));
            courier.setPassword(RandomStringUtils.randomAlphabetic(12));
            courier.setFirstName(RandomStringUtils.randomAlphabetic(10));
        }

        // Позитивный тест: курьера можно создать
        @Test
        public void shouldCreateCourierSuccessfully() {
            courierSteps
                    .createCourier(courier)
                    .statusCode(201)
                    .body("ok", Matchers.is(true));
        }

        // Негативный тест: нельзя создать двух одинаковых курьеров
        @Test
        public void shouldNotCreateDuplicateCourier() {
            // Создаем первого курьера
            courierSteps.createCourier(courier);

            // Пытаемся создать второго с теми же данными
            courierSteps
                    .createCourier(courier)
                    .statusCode(409)
                    .body("message", Matchers.containsString("Этот логин уже используется"));
        }

        // Негативный тест: для создания курьера нужны все обязательные поля
        @Test
        public void shouldNotCreateCourierWithoutRequiredFields() {
            Courier incompleteCourier = new Courier();
            incompleteCourier.setLogin(RandomStringUtils.randomAlphabetic(12));
            // Отсутствует password и firstName

            courierSteps
                    .createCourier(incompleteCourier)
                    .statusCode(400)
                    .body("message", Matchers.containsString("Недостаточно данных для создания учетной записи"));
        }

        // Негативный тест: создание курьера без логина
        @Test
        public void shouldNotCreateCourierWithoutLogin() {
            Courier incompleteCourier = new Courier();
            incompleteCourier.setPassword(RandomStringUtils.randomAlphabetic(12));
            incompleteCourier.setFirstName(RandomStringUtils.randomAlphabetic(10));

            courierSteps
                    .createCourier(incompleteCourier)
                    .statusCode(400);
        }

        // Негативный тест: создание курьера без пароля
        @Test
        public void shouldNotCreateCourierWithoutPassword() {
            Courier incompleteCourier = new Courier();
            incompleteCourier.setLogin(RandomStringUtils.randomAlphabetic(12));
            incompleteCourier.setFirstName(RandomStringUtils.randomAlphabetic(10));

            courierSteps
                    .createCourier(incompleteCourier)
                    .statusCode(400);
        }

        // Негативный тест: создание курьера без имени
        @Test
        public void shouldNotCreateCourierWithoutFirstName() {
            Courier incompleteCourier = new Courier();
            incompleteCourier.setLogin(RandomStringUtils.randomAlphabetic(12));
            incompleteCourier.setPassword(RandomStringUtils.randomAlphabetic(12));
            // Отсутствует firstName

            courierSteps
                    .createCourier(incompleteCourier)
                    .statusCode(400);
        }

        // Позитивный тест: курьер может авторизоваться
        @Test
        public void shouldLoginCourierSuccessfully() {
            courierSteps.createCourier(courier);

            courierSteps
                    .loginCourier(courier)
                    .statusCode(200)
                    .body("id", Matchers.notNullValue());
        }

        // Негативный тест: для авторизации нужны все обязательные поля (без пароля)
        @Test
        public void shouldNotLoginCourierWithoutPassword() {
            Courier incompleteCourier = new Courier();
            incompleteCourier.setLogin(RandomStringUtils.randomAlphabetic(12));
            // Отсутствует password

            courierSteps
                    .loginCourier(incompleteCourier)
                    .statusCode(400)
                    .body("message", Matchers.containsString("Недостаточно данных для входа"));
        }

        // Негативный тест: для авторизации нужны все обязательные поля (без логина)
        @Test
        public void shouldNotLoginCourierWithoutLogin() {
            Courier incompleteCourier = new Courier();
            incompleteCourier.setPassword(RandomStringUtils.randomAlphabetic(12));
            // Отсутствует login

            courierSteps
                    .loginCourier(incompleteCourier)
                    .statusCode(400)
                    .body("message", Matchers.containsString("Недостаточно данных для входа"));
        }

        // Негативный тест: неверные учетные данные
        @Test
        public void shouldNotLoginWithInvalidCredentials() {
            Courier invalidCourier = new Courier();
            invalidCourier.setLogin("nonexistent_login");
            invalidCourier.setPassword("wrong_password");

            courierSteps
                    .loginCourier(invalidCourier)
                    .statusCode(404)
                    .body("message", Matchers.containsString("Учетная запись не найдена"));
        }

        // Негативный тест: авторизация под несуществующим пользователем
        @Test
        public void shouldNotLoginNonExistentCourier() {
            Courier nonExistentCourier = new Courier();
            nonExistentCourier.setLogin(RandomStringUtils.randomAlphabetic(12));
            nonExistentCourier.setPassword(RandomStringUtils.randomAlphabetic(12));

            courierSteps
                    .loginCourier(nonExistentCourier)
                    .statusCode(404)
                    .body("message", Matchers.containsString("Учетная запись не найдена"));
        }

        @After
        public void tearDown() {
            // Пытаемся получить ID курьера для удаления, если он был успешно создан
            try {
                // Логинимся, чтобы получить ID
                Integer id = courierSteps.loginCourier(courier)
                        .extract()
                        .body()
                        .path("id");
                if (id != null) {
                    courierSteps.deleteCourier(id);
                }
            } catch (Exception e) {
                // Игнорируем ошибки при очистке, например, если курьер не был создан
                // System.out.println("Warning: Could not delete courier in teardown: " + e.getMessage());
            }
        }
    }

