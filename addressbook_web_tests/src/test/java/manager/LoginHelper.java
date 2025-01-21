package manager;

import org.openqa.selenium.By;

public class LoginHelper extends HelperBase{

    public LoginHelper(ApplicationManager manager){
        // Использовать конструктор базового класса HelperBase
        super(manager);
    }

    void login(String user, String password, ApplicationManager applicationManager) {
        type(By.name("user"), "admin");
        type(By.name("pass"), "secret");
        click(By.xpath("//input[@value=\'Login\']"));
    }
}
