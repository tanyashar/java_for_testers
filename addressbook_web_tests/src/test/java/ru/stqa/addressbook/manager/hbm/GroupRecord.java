package ru.stqa.addressbook.manager.hbm;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

// библиотека Hibernate не умеет работать с record (с неизменяемыми свойствами), ей нужны изменяемые свойства - т.е. class
// поэтому создаем обертку GroupRecord

@Entity
@Table(name = "group_list")
public class GroupRecord {
    @Id // ключ в БД
    @Column(name = "group_id")
    public int id;

    @Column(name = "group_name")
    public String name;

    @Column(name = "group_header")
    public String header;

    @Column(name = "group_footer")
    public String footer;

    public Date deprecated = new Date();

    // описание связей между объектами в декларативном виде
    // тип связи @ManyToMany = контакт может входить в несколько групп + группа может содержать несколько контактов
    // hibernate информацию об объектах загружает сразу (EAGER), но о связях - как можно позже (LAZY)
    // чтобы это изменить, можно принудительно задать параметр: @ManyToMany(fetch = FetchType.EAGER)
    // @ManyToMany(fetch = FetchType.EAGER)
    @ManyToMany
    @JoinTable(name = "address_in_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    public List<ContactRecord> contacts;

    public GroupRecord() { }

    public GroupRecord(int id, String name, String header, String footer) {
        this.id = id;
        this.name = name;
        this.header = header;
        this.footer = footer;
    }
}


