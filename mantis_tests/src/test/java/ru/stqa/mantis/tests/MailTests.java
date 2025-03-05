package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.manager.MailHelper;

import java.time.Duration;
import java.util.regex.Pattern;

public class MailTests extends TestBase{

    @Test
    void canReceiveEmail() {
        var messages = app.mail().receive("user1@localhost", "password", Duration.ofSeconds(120));

        Assertions.assertEquals(1, messages.size());
        System.out.println(messages);
    }

    @Test
    void canDrainInbox() {
        app.mail().drain("user1@localhost", "password");
    }

    @Test
    void canExtractUrl() {
        var messages = app.mail().receive("user1@localhost", "password", Duration.ofSeconds(120));
        var text = messages.get(0).content();

        var url = MailHelper.extractUrlFromMessage(text);
        if (url != null) {
            System.out.println(url);
        }
    }

}
