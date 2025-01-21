package tests;

import manager.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;

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

    public static String randomString(int stringLength) {
        var rnd = new Random(); // тип данных для рандомных чисел
        var result = "";
        for (int i = 0; i < stringLength; i++) {
            result += (char)('a' + rnd.nextInt(0, 26));
        }

        // if (stringLength < 20)
        //     result += '\'';

        return result;
    }
}
