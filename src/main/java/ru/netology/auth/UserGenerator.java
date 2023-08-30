package ru.netology.auth;


import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class UserGenerator {

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

        public static String generateRandomUserActive() {
            Faker faker = new Faker();
            String login = faker.name().username();
            String password = faker.internet().password();

            // Отправляем POST-запрос для сохранения пользователя и получаем его в ответе
            RegistrationDto registrationDto = new RegistrationDto(login, password, "active");
            Response response = given()
                    .spec(requestSpec)
                    .body(registrationDto)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200)
                    .log().all() // строчка для логирования ответа
                    .extract()
                    .response();
            // Извлекаем значения пользователя из ответа
            String savedLogin = response.jsonPath().getString("login");
            String savedPassword = response.jsonPath().getString("password");

            // Возвращаем значения в виде строки
            return savedLogin + ":" + savedPassword;

        }

}

