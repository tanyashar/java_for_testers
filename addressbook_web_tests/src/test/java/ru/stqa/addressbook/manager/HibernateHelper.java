package ru.stqa.addressbook.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.schema.Action;
import ru.stqa.addressbook.manager.hbm.ContactRecord;
import ru.stqa.addressbook.manager.hbm.GroupRecord;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.GroupData;
import java.util.ArrayList;
import java.util.List;

public class HibernateHelper extends HelperBase {
    private SessionFactory sessionFactory;

    public HibernateHelper(ApplicationManager manager) {
        super(manager);

        sessionFactory =
                new Configuration()
                        .addAnnotatedClass(GroupRecord.class)
                        .addAnnotatedClass(ContactRecord.class)
                        .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:mysql://localhost/addressbook?zeroDateTimeBehavior=convertToNull")
                        .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "root")
                        .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "")
                        .buildSessionFactory();

    }

    static List<GroupData> convertList(List<GroupRecord> records) {
        List<GroupData> result = new ArrayList<>();
        for (var record: records) {
            result.add(convert(record));
        }
        return result;
    }

    static List<ContactData> convertContactList(List<ContactRecord> records) {
        List<ContactData> result = new ArrayList<>();
        for (var record: records) {
            result.add(convert(record));
        }
        return result;
    }

    private static ContactData convert(ContactRecord record) {
        return new ContactData().withId("" + record.id)
                .withFirstName(record.firstname)
                .withLastName(record.lastName)
                .withAddress(record.address);
    }

    private static ContactRecord convert(ContactData contactData) {
        return new ContactRecord(Integer.parseInt(contactData.id()), contactData.firstName(), contactData.lastName(), contactData.address());
    }

    private static GroupData convert(GroupRecord record) {
        return new GroupData("" + record.id, record.name, record.header, record.footer);
    }

    private static GroupRecord convert(GroupData groupData) {
        var id = groupData.id();
        if ("".equals(id))
            id = "0";

        return new GroupRecord(Integer.parseInt(id), groupData.name(), groupData.header(), groupData.footer());
    }

    public List<GroupData> getGroupList() {
        // запрос на языке OQL = Object Query Language
        return convertList(sessionFactory.fromSession(session -> {
            return session.createQuery("from GroupRecord", GroupRecord.class).list();
        }));
    }

    public long getCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from GroupRecord", Long.class).getSingleResult();
        });
    }

    public void createGroup(GroupData groupData) {
        // fromSession = нужно что-то вернуть после запроса к БД
        // inSession = ничего не возвращать, а просто выполнить действие
        sessionFactory.inSession(session -> {
            session.getTransaction().begin(); // открыть транзакцию
            session.persist(convert(groupData)); // сохранить данные
            session.getTransaction().commit(); // закрыть транзакцию
        });
    }

    public List<ContactData> getContactsInGroup(GroupData group) {
        return sessionFactory.fromSession(session -> {
            return convertContactList(session.get(GroupRecord.class, group.id()).contacts);
        });
    }
}

