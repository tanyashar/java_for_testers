package ru.stqa.mantis.manager;

import jakarta.mail.*;
import ru.stqa.mantis.model.MailMessage;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MailHelper extends HelperBase{

    public MailHelper(ApplicationManager manager) {
        super(manager);
    }

    public List<MailMessage> receive(String username, String password, Duration duration) {
        var start = System.currentTimeMillis(); // текущее время в милисекундах
        while (System.currentTimeMillis() < start + duration.toMillis()) {
            Store store = null;
            try {
                var inbox = getInbox(username, password);
                inbox.open(Folder.READ_ONLY);
                var messages = inbox.getMessages();

                var result = Arrays.stream(messages).map(m -> {
                            try {
                                return new MailMessage()
                                        .withFrom(m.getFrom()[0].toString())
                                        .withContent(m.getContent().toString());
                            } catch (MessagingException | IOException e) { // catch нескольких типов исключений
                                throw new RuntimeException(e);
                            }
                        })
                        .toList();

                inbox.close();
                inbox.getStore().close();

                if (result.size() > 0) {
                    return result;
                }
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("No mail");
    }

    private static Folder getInbox(String username, String password) {
        try {
            Store store;
            // старт сессии
            var session = Session.getInstance(new Properties());

            // получить доступ к хранилищу почты
            store = session.getStore("pop3");

            // установить соединение
            store.connect("localhost", username, password);

            // открыть почтовый ящик
            var inbox = store.getFolder("INBOX");
            return inbox;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void drain(String username, String password) {
        // "осушить" = удалить все
        try {
        var inbox = getInbox(username, password);
            inbox.open(Folder.READ_WRITE);

            // пометить каждое письмо как удаленное
            Arrays.stream(inbox.getMessages())
                    .forEach(m -> {
                        try {
                            m.setFlag(Flags.Flag.DELETED, true);
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    });
            inbox.close();
            inbox.getStore().close();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}