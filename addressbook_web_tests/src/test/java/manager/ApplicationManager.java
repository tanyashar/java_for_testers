package manager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

// класс для управления тестируемым приложением (для взаимодействия с ним)
public class ApplicationManager {
    protected WebDriver driver;
    private LoginHelper session;
    private GroupHelper groups;
    private ContactHelper contacts;

    public void init(String browser) {
        if (driver == null) {
            if ("firefox".equals(browser))
                driver = new FirefoxDriver();
            else if ("chrome".equals(browser))
                driver = new ChromeDriver();
            else
                throw new IllegalArgumentException(String.format("Unknown browser %s", browser));

            // регистрируем код, который должен произойти в конце
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));

            driver.get("http://localhost/addressbook/");
            driver.manage().window().setSize(new Dimension(974, 1032));
            session().login("admin", "secret", this);
        }
    }

    // Ленивая инициализация
    public LoginHelper session() {
        if (session == null) {
            session = new LoginHelper(this);
        }
        return session;
    }

    // Ленивая инициализация
    public GroupHelper groups() {
        if (groups == null) {
            groups = new GroupHelper(this);
        }
        return groups;
    }

    public ContactHelper contacts() {
        if (contacts == null)  {
            contacts = new ContactHelper(this);
        }
        return contacts;
    }

    public boolean _isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        }
        catch (NoSuchElementException exception) {
            return false;
        }
    }

    public JavascriptExecutor getDriver() {
        return (JavascriptExecutor) driver;
    }
}
