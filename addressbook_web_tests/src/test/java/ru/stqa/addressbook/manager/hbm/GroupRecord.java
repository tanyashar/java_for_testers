package ru.stqa.addressbook.manager.hbm;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

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

    public GroupRecord() { }

    public GroupRecord(int id, String name, String header, String footer) {
        this.id = id;
        this.name = name;
        this.header = header;
        this.footer = footer;
    }
}


