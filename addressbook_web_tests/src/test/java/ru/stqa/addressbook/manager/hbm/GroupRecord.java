package ru.stqa.addressbook.manager.hbm;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "group_list")
public class GroupRecord {
    // библиотека Hibernate не умеет работать с record (с неизменяемыми свойствами), ей нужны изменяемые свойства - т.е. class
    // поэтому создаем обертку GroupRecord

    @Id // ключ в БД
    @Column(name = "group_id")
    public int id;

    @Column(name = "group_name")
    public String name;

    @Column(name = "group_header")
    public String header;

    @Column(name = "group_footer")
    public String footer;
}
