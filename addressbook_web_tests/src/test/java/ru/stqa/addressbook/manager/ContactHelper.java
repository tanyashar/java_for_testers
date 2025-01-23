package ru.stqa.addressbook.manager;

import org.openqa.selenium.support.ui.Select;
import ru.stqa.addressbook.model.ContactData;
import org.openqa.selenium.By;
import ru.stqa.addressbook.model.GroupData;

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

    public void createContact(ContactData contact, GroupData group) {
        openContactPage();
        initContactCreation();
        fillContactForm(contact);
        selectGroup(group);
        submitContactCreation();
        returnToContactsPage();
    }

    private void selectGroup(GroupData group) {
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
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
        attach(By.name("photo"), contact.photo());
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
