package ru.stqa.mantis.manager;

import okhttp3.*;
import org.openqa.selenium.io.CircularOutputStream;
import org.openqa.selenium.os.CommandLine;

import java.io.IOException;
import java.net.CookieManager;

// Взаимодействие с почтой используя удаленный программный интерфейс
// REST = Representational State Transfer
// API = Application Programming Interface
public class JamesApiHelper extends HelperBase{

    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client;

    public JamesApiHelper(ApplicationManager manager) {
        super(manager);
        client = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(new CookieManager())).build();
    }

    // Получить список всех юзеров на почтовом сервере из cmd:
    // curl http://localhost:8000/users

    public void addUser(String email, String password) {
        RequestBody body = RequestBody.create(
                String.format("{\"password\":\"%s\"}", password), JSON);

        Request request = new Request.Builder()
                .url(String.format("%s/users/%s", manager.property("james.apiBaseUrl"), email))
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new RuntimeException("Unexpected code " + response);
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }









}
