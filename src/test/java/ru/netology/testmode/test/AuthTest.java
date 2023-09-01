package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
        void shouldSuccessfulLoginIfRegisteredActiveUser() {
            var registeredUser = getRegisteredUser("active");
            // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
            //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
            //  пользователя registeredUser

        SelenideElement form = $(".form_theme_alfa-on-white");
        form.$("[data-test-id=login] input").sendKeys(registeredUser.getLogin());
        form.$("[data-test-id=password] input").sendKeys(registeredUser.getPassword());
        form.$("[data-test-id=action-login]").click();
        $(".heading_theme_alfa-on-white")
                .shouldHave(Condition.text("Личный кабинет"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
        SelenideElement form = $(".form_theme_alfa-on-white");
        form.$("[data-test-id=login] input").sendKeys(notRegisteredUser.getLogin());
        form.$("[data-test-id=password] input").sendKeys(notRegisteredUser.getPassword());
        form.$("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
        SelenideElement form = $(".form_theme_alfa-on-white");
        form.$("[data-test-id=login] input").sendKeys(blockedUser.getLogin());
        form.$("[data-test-id=password] input").sendKeys(blockedUser.getPassword());
        form.$("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(Condition.text("Пользователь заблокирован"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
        SelenideElement form = $(".form_theme_alfa-on-white");
        form.$("[data-test-id=login] input").sendKeys(wrongLogin);
        form.$("[data-test-id=password] input").sendKeys(registeredUser.getPassword());
        form.$("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
        @Test
        @DisplayName("Should get error message if login with wrong password")
        void shouldGetErrorIfWrongPassword() {
            var registeredUser = getRegisteredUser("active");
            var wrongPassword = getRandomPassword();
            // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
            //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
            //  "Пароль" - переменную wrongPassword
            SelenideElement form = $(".form_theme_alfa-on-white");
            form.$("[data-test-id=login] input").sendKeys(registeredUser.getLogin());
            form.$("[data-test-id=password] input").sendKeys(wrongPassword);
            form.$("[data-test-id=action-login]").click();
            $("[data-test-id=error-notification]")
                    .shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(15))
                    .shouldBe(Condition.visible);
    }

}