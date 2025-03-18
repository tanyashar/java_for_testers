package ru.stqa.addressbook.tests;

import ru.stqa.addressbook.model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupRemovalTests extends TestBase {

    @Test
    public void canRemoveGroup() {
        // if (!app.groups().isGroupPresent()) {
        if (app.hbm().getCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }

        List<GroupData> oldGroups = app.hbm().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());

        app.groups().removeGroup(oldGroups.get(index));

        List<GroupData> newGroups = app.hbm().getGroupList();
        var expectedList = new ArrayList<>(oldGroups); // копия списка oldGroups
        expectedList.remove(index);

        Assertions.assertEquals(newGroups, expectedList);
    }

    @Test
    void canRemoveAllGroupsAtOnce() {
        if (app.hbm().getCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }

        app.groups().removeAllGroups();
        Assertions.assertEquals(1, app.hbm().getCount());
    }
}
