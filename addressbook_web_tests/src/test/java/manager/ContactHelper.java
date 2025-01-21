package manager;

import model.ContactData;
import org.openqa.selenium.By;

public class ContactHelper extends HelperBase {
    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void createContact(ContactData contact) {
        openContactPage();
        initContactCreation();
        fillContactForm(contact);
        submitContactCreation();
        returnToContactsPage();
    }

    public void removeContact(ContactData contact) {
        openContactPage();
        selectContact(contact);
        removeSelectedContact();
        returnToContactsPage();
    }

    public void modifyContact(ContactData contact, ContactData modifiedContact) {
        openContactPage();
        selectContact(contact);
        initContactModification(contact);
        fillContactForm(modifiedContact);
        submitContactModification();
        returnToContactsPage();
    }

    private void submitContactModification() {
        click(By.name("update"));
    }

    private void initContactModification(ContactData contact) {
        click(By.cssSelector(String.format("img:nth-child(%s)", contact.id())));
    }

    private void removeSelectedContact() {
        click(By.name("delete"));
    }

    private void selectContact(ContactData contact) {
        click(By.cssSelector(String.format("input[value='%s']", contact.id())));
    }

    private void submitContactCreation() {
        click(By.name("submit"));
    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstName());        
        type(By.name("lastname"), contact.lastName());
        type(By.name("address"), contact.address());
    }

    private void initContactCreation() {
        click(By.linkText("add new"));
    }

    private void openContactPage() {
        if (!manager._isElementPresent(By.name("add"))) {
            click(By.linkText("home"));
        }
    }

    private void returnToContactsPage() {
        click(By.linkText("home page"));
    }

    public int getCount()
    {
        openContactPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }


}
