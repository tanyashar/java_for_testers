package ru.stqa.addressbook.manager.hbm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="addressbook")
public class ContactRecord {

    @Id
    public int id;

    public String firstname;
    public String lastName;
    public String address;

    public ContactRecord() { }
    public ContactRecord(int id, String firstname, String lastName, String address) {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.address = address;
    }

}
