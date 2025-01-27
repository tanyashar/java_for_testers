package ru.stqa.addressbook.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.addressbook.model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stqa.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactTests extends TestBase{
    public static ArrayList<ContactData> contactGroupProvider() {
        var contacts = new ArrayList<ContactData>();
        
        for (var lastName: List.of("", "not empty Last Name")) {
            for (var firstName: List.of("", "not empty First Name")) {
                for (var address: List.of("", "not empty Address")) {
                    contacts.add(new ContactData()
                            .withLastName(lastName)
                            .withFirstName(firstName)
                            .withAddress(address)
                            // .withPhoto("src\\test\\resources\\images\\avatar.png"));
                            .withPhoto(randomFile("src\\test\\resources\\images")));
                }
            }
        }
        return contacts;
    }

    @ParameterizedTest
    @MethodSource("contactGroupProvider")
    public void canCreateContact(ContactData contact)
    {
        var oldContactCount = app.contacts().getCount();
        app.contacts().createContact(contact);
        var newContactCount = app.contacts().getCount();
        Assertions.assertEquals(oldContactCount + 1, newContactCount);
    }

    @Test
    public void canCreateContactInGroup() {
        var contact = new ContactData()
                .withFirstName("first name")
                .withLastName("last name")
                .withAddress("address")
                .withPhoto(randomFile("src/test/resources/images"));

        if (app.hbm().getCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }

        var group = app.hbm().getGroupList().get(0);

        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().createContact(contact, group);

        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() + 1, newRelated.size());
    }

    @Test
    void TestPhones() {
        // Метод обратных/обходных проверок
        var contacts = app.hbm().getContactList();
        var contact = contacts.get(0);
        var phones = app.contacts().getPhones(contact);

        var expected = Stream.of(contact.home(), contact.mobile(), contact.work(), contact.secondary())
                .filter(s -> s != null && !"".equals(s))
                .collect(Collectors.joining("\n"));

        Assertions.assertEquals(expected, phones);
    }
    
}
