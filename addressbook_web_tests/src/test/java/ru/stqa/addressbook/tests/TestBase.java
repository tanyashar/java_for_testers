package ru.stqa.addressbook.tests;

import ru.stqa.addressbook.manager.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;

public class TestBase {

    protected static ApplicationManager app;

    // @BeforeAll
    @BeforeEach
    public void setUp() {
        if (app == null)
            app = new ApplicationManager();
        // app.ini t("chrome");
        app.init(System.getProperty("browser", "firefox"));
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
}