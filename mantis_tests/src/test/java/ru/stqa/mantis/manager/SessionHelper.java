package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class SessionHelper extends HelperBase{
    public SessionHelper(ApplicationManager manager) {
        super(manager);
    }

    public void login(String user, String password) {
        type(By.name("username"), user);
        click(By.cssSelector("input[type='submit']"));
        type(By.name("password"), password);
        click(By.cssSelector("input[type='submit']"));
    }

    public boolean isLoggedIn() {
        return isElementPresent(By.cssSelector("span.user-info"));
    }

    public void signUpForNewAccount(String username, String email) {
        click(By.cssSelector("a[href='signup_page.php']"));
        click(By.name("username"));
        type(By.name("username"), username);
        click(By.name("email"));
        type(By.name("email"), email);
        click(By.cssSelector("input[value='Signup']"));
        click(By.cssSelector("a[href='login_page.php']"));
    }

    public void goToUrl(String url) {
        manager.driver().navigate().to(url);
    }

    public void updateUser(String realName, String password) {
        click(By.id("realname"));
        type(By.id("realname"), realName);

        click(By.id("password"));
        type(By.id("password"), password);

        click(By.id("password-confirm"));
        type(By.id("password-confirm"), password);

        click(By.cssSelector("button[type='submit']"));
    }
}
