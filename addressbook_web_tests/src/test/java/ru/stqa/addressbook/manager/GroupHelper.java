package ru.stqa.addressbook.manager;

import ru.stqa.addressbook.model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GroupHelper extends HelperBase{

    public GroupHelper(ApplicationManager manager) {
        super(manager);
    }

    public void createGroup(GroupData group) {
        openGroupsPage();
        initGroupCreation();
        fillGroupForm(group);
        submitGroupCreation();
        returnToGroupsPage();
    }

    public void removeGroup(GroupData group) {
        openGroupsPage();
        selectGroup(group);
        removeSelectedGroups();
        returnToGroupsPage();
    }

    public void modifyGroup(GroupData group, GroupData modifiedGroup) {
        openGroupsPage();
        selectGroup(group);
        initGroupModification(); // нажать кнопку с модификацией (Edit)
        fillGroupForm(modifiedGroup);
        submitGroupModification();
        returnToGroupsPage();
    }

    private void submitGroupCreation() {
        click(By.name("submit"));
    }

    private void initGroupCreation() {
        click(By.name("new"));
    }

    public boolean isGroupPresent() {
        openGroupsPage();
         return manager._isElementPresent(By.name("selected[]"));
     }


    private void removeSelectedGroups() {
        click(By.name("delete"));
    }

    public void openGroupsPage() {
        if (!manager._isElementPresent(By.name("new"))) {
            click(By.linkText("groups"));
        }
    }

    private void returnToGroupsPage() {
        click(By.linkText("group page"));
    }

    private void submitGroupModification() {
        click(By.name("update"));
    }

    private void fillGroupForm(GroupData modifiedGroup) {
        type(By.name("group_name"), modifiedGroup.name());
        type(By.name("group_header"), modifiedGroup.header());
        type(By.name("group_footer"), modifiedGroup.footer());
    }

    private void initGroupModification() {
        click(By.name("edit"));
    }

    private void selectGroup(GroupData group) {
        click(By.cssSelector(String.format("input[value='%s']", group.id())));
    }

    public int getCount() {
        openGroupsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    public void removeAllGroups() {
        openGroupsPage();
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        selectAllGroups(checkboxes);
        removeSelectedGroups();
    }

    private static void selectAllGroups(List<WebElement> checkboxes) {
        checkboxes.forEach(WebElement::click);
    }

    public List<GroupData> getList() {
        openGroupsPage();
        var groups = new ArrayList<GroupData>();
        var spans = manager.driver.findElements(By.cssSelector("span.group")); // элементы с тегом span, к-е имеют класс group

        return spans.stream()
                .map(span -> {
                    var name = span.getText();
                    var checkBox = span.findElement(By.name("selected[]"));
                    var id = checkBox.getAttribute("value");
                    return new GroupData().withId(id).withName(name);
                })
                .collect(Collectors.toList());
    }
}
