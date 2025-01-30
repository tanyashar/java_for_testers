package ru.stqa.addressbook.manager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

// класс для управления тестируемым приложением (для взаимодействия с ним)
public class ApplicationManager {
    protected WebDriver driver;
    private LoginHelper session;
    private GroupHelper groups;
    private ContactHelper contacts;
    private Properties properties;
    private JdbcHelper jdbc;
    private HibernateHelper hbm;

    public void init(String browser, Properties properties) {
        this.properties = properties;
        if (driver == null) {
            if ("firefox".equals(browser))
                driver = new FirefoxDriver();
            else if ("chrome".equals(browser))
                driver = new ChromeDriver();
            else
                throw new IllegalArgumentException(String.format("Unknown browser %s", browser));

            // регистрируем код, который должен произойти в конце
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));

            driver.get(properties.getProperty("web.baseUrl"));
            driver.manage().window().setSize(new Dimension(974, 1032));
            session().login(properties.getProperty("web.username"), properties.getProperty("web.password"), this);
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

    public JdbcHelper jdbc() {
        if (jdbc == null)  {
            jdbc = new JdbcHelper(this);
        }
        return jdbc;
    }

    public HibernateHelper hbm() {
        if (hbm == null)  {
            hbm = new HibernateHelper(this);
        }
        return hbm;
    }

    public boolean _isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    public JavascriptExecutor getDriver() {
        return (JavascriptExecutor) driver;
    }
}
