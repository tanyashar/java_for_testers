package ru.stqa.addressbook.tests;

import org.junit.jupiter.api.AfterEach;
import ru.stqa.addressbook.manager.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Random;

public class TestBase {

    protected static ApplicationManager app;

    // @BeforeAll
    @BeforeEach
    public void setUp() throws IOException {
        if (app == null) {
            var properties = new Properties();
            properties.load(new FileReader(System.getProperty("target", "local.properties")));

            app = new ApplicationManager();
            // app.init("chrome");
            app.init(System.getProperty("browser", "firefox"), properties);
        }

        // System.getProperty - для считывания входного параметра из параметров запуска -Pbrowser=chrome
        // если в параметрах запуска (Edit Configurations) нет аргумента -Pbrowser, по дефолту юзаем браузер firefox
        // для этого не забыть добавить test {...} в build.gradle
    }

    // @AfterEach
    // public void tearDown() {
    //     ApplicationManager.driver.findElement(By.linkText("Logout")).click();
    //     ApplicationManager.driver.quit();
    //     ApplicationManager.driver = null;
    // }

    public static String randomFile(String dir) {
        // работа с файлами
        var fileNames = new File(dir).list();
        var rnd = new Random();
        var index = rnd.nextInt(fileNames.length);
        return Paths.get(dir, fileNames[index]).toString();
    }

    @AfterEach
    public void checkDataBaseConsistency() {
        // проверяем согласованность БД = проверка БД на консистентность
        // нельзя сделать через hibernate, поэтому используем jdbc
        app.jdbc().checkConsistency();
    }
}