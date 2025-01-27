package ru.stqa.addressbook.tests;

import ru.stqa.addressbook.model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Set;

public class GroupModificationTests extends TestBase {

    @Test
    public void canModifyGroup() {
        // if (!app.groups().isGroupPresent()) {
        if (app.hbm().getCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }

        // select random group for modification
        var oldGroups = app.hbm().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        var testData = new GroupData().withName("modified name");
        app.groups().modifyGroup(oldGroups.get(index), testData);

        var newGroups = app.hbm().getGroupList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.set(index, testData.withId(oldGroups.get(index).id()));


        //region Сравнить два списка с помощью компаратора
        // comparator - комапаратор - анонимная функция, характерно для функционального программирования
        // т.е. в функцию можно передавать другую функцию
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };

        newGroups.sort(compareById);
        expectedList.sort(compareById);

        Assertions.assertEquals(newGroups, expectedList);
        //endregion

        // ИЛИ
        //region Сравнить два множества (т.е. неупорядоченных списка)
        Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedList));
        //endregion
    }
}
