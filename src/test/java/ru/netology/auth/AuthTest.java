package ru.netology.auth;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.auth.UserGenerator.generateRandomUserActive;


class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }
    @Test
    void should200() {
            // Генерируем случайного пользователя и сохраняем значения в переменной userData
            String userData = generateRandomUserActive();

        // Разделяем значения имени пользователя и пароля
            String[] userValues = userData.split(":");
            String savedLogin = userValues[0];
            String savedPassword = userValues[1];

            SelenideElement form = $(".form_theme_alfa-on-white");
        form.$("[data-test-id=login] input").sendKeys(savedLogin);
        form.$("[data-test-id=password] input").sendKeys(savedPassword);
        form.$("[data-test-id=action-login]").click();
        $(".heading_theme_alfa-on-white")
                .shouldHave(Condition.text("Личный кабинет"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }


}