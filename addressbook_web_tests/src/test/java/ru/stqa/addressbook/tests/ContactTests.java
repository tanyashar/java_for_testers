package ru.stqa.addressbook.tests;

import ru.stqa.addressbook.model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

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
//                            .withPhoto("src\\test\\resources\\images\\avatar.png"));
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
    
}
