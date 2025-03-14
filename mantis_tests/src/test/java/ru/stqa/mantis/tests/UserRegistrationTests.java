package ru.stqa.mantis.tests;


import kotlin.text.Regex;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.manager.JamesCliHelper;
import ru.stqa.mantis.manager.MailHelper;
import ru.stqa.mantis.model.DeveloperMailUser;

import java.time.Duration;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class UserRegistrationTests extends TestBase {

    DeveloperMailUser user;

    public static Stream<String> randomUsernames() {
        var sLength = 5;
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return CommonFunctions.randomString(sLength);
            }
        } ;

        var n = 1;
        var stream = Stream.generate(supplier).limit(n);
        return stream;
    }

    @ParameterizedTest
    @MethodSource("randomUsernames")
    void canRegisterUser(String username) {
        var email = String.format("%s@localhost", username);

        var password = CommonFunctions.randomString(10);
        app.jamesCli().addUser(email, password);
        app.session().signUpForNewAccount(username, email);

        var messages = app.mail().receive(email, password, Duration.ofSeconds(120));
        var url = MailHelper.extractUrlFromMessage(messages.get(0).content());
        Assertions.assertNotNull(url);

        app.session().goToUrl(url);
        app.session().updateUser(username, password);

        app.http().login(username, password);
        Assertions.assertTrue(app.http().isLoggedIn());

        // 8.6 ДОМАШКА - Разработать тест для регистрации нового пользователя в баг-трекинговой системе MANTIS
        // (*) mantis - Signup for a new account
        // + 0. получать username из параметров - void canRegisterUser(String username)
        // + 1. создать пользователя (адрес) на почтовом сервере (браузер) (JamesHelper)
        // + 2. заполняем форму создания и отправляем (браузер)
        // + 3. ждем почту = получаем ссылку из письма (MailHelper)
        // + 4. извлекаем ссылку из письма (вспомогательный метод - регулярка)
        // + 5. проходим по ссылке и завершаем регистрацию пользователя (браузер)
        // + 6. проверяем, что пользователь может залогиниться (HttpSessionHelper)
    }

    @ParameterizedTest
    @MethodSource("randomUsernames")
    public void canRegisterUserWithJamesApi(String username) {
        var email = String.format("%s@localhost", username);
        var password = "password";

        app.jamesApi().addUser(email, password);

        app.session().signUpForNewAccount(username, email);

        var messages = app.mail().receive(email, password, Duration.ofSeconds(120));
        var url = MailHelper.extractUrlFromMessage(messages.get(0).content());
        Assertions.assertNotNull(url);

        app.session().goToUrl(url);
        app.session().updateUser(username, password);

        app.http().login(username, password);
        Assertions.assertTrue(app.http().isLoggedIn());
    }

    @Test
    public void canRegisterUserWithDeveloperMail() {
        var password = "password";
        user = app.developerMail().addUser();
        var email = String.format("%s@developermail.com", user);

//        app.jamesApi().addUser(email, password);
//
//        app.session().signUpForNewAccount(username, email);
//
//        var messages = app.mail().receive(email, password, Duration.ofSeconds(120));
//        var url = MailHelper.extractUrlFromMessage(messages.get(0).content());
//        Assertions.assertNotNull(url);
//
//        app.session().goToUrl(url);
//        app.session().updateUser(username, password);
//
//        app.http().login(username, password);
//        Assertions.assertTrue(app.http().isLoggedIn());
    }

    @AfterEach
    void deleteMailUser() {
        app.developerMail().deleteUser(user);
    }
}
