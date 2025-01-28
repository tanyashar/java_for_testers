package ru.stqa.mantis.manager;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

public class ApplicationManager {
    private WebDriver driver;
    private String browser;
    private Properties properties;
    private SessionHelper sessionHelper;
    private HttpSessionHelper httpSessionHelper;
    private JamesCliHelper jamesCliHelper;

    public void init(String browser, Properties properties) {
        this.browser = browser;
        this.properties = properties;
    }
    
    public WebDriver driver() {
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
        }
        return driver;
    }

    public SessionHelper session() {
        if (sessionHelper == null) {
            sessionHelper = new SessionHelper(this);
        }
        return sessionHelper;
    }

    public HttpSessionHelper http() {
        if (httpSessionHelper == null) {
            httpSessionHelper = new HttpSessionHelper(this);
        }
        return httpSessionHelper;
    }
    public JamesCliHelper jamesCli() {
        if (jamesCliHelper == null) {
            jamesCliHelper = new JamesCliHelper(this);
        }
        return jamesCliHelper;
    }

    public String property(String name) {
        return properties.getProperty(name);
    }
}
