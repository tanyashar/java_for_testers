package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

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
}
