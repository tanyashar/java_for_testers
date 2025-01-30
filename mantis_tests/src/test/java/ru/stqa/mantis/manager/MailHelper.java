package ru.stqa.mantis.manager;

import jakarta.mail.*;
import ru.stqa.mantis.model.MailMessage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class MailHelper extends HelperBase{
    public MailHelper(ApplicationManager manager) {
        super(manager);
    }


    public List<MailMessage> receive(String username, String password) {
        Store store = null;
        try {
            // старт сессии
            var session = Session.getInstance(new Properties());

            // получить доступ к хранилищу почты
            store = session.getStore("pop3");

            // установить соединение
            store.connect("localhost", username, password);

            // открыть почтовый ящик
            var inbox = store.getFolder("INBOX");
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
            store.close();

            return result;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}