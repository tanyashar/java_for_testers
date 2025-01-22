package ru.stqa.addressbook.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.stqa.addressbook.common.Common;
import ru.stqa.addressbook.model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {
    // фикстура = fixture = "тиски" = предусловия
    // = код, который выполняется перед сценарием и после сценария
    // @BeforeEach, @AfterEach = фикстура

    /*@AfterEach // @AfterAll
     public void tearDown() {
     driver.findElement(By.linkText("Logout")).click();
     driver.quit();
     }*/

    // Генератор тестовых данных - должен быт статическим
    public static ArrayList<GroupData> groupProvider() throws IOException {
        /*var result = new ArrayList<>(List.of(
                new GroupData(), // покрываем canCreateGroupWithEmptyName
                new GroupData().withName("some name"), // покрываем canCreateGroupWithNameOnly
                new GroupData("group name", "", ""),
                new GroupData("group name'", "", "")));*/ // чтобы покрыть в т.ч. тесты canCreateGroup

        var result = new ArrayList<GroupData>();

        /*for (var name: List.of("not empty name")) {
            for (var header: List.of("not empty header")) {
                for (var footer: List.of("not empty footer")) {
                    result.add(new GroupData()
                            .withName(name)
                            .withHeader(header)
                            .withFooter(footer));
                }
            }
        }*/


        ObjectMapper mapper = new XmlMapper();
        // ObjectMapper mapper = new ObjectMapper(); // для JSON

        var json = "";
        try (var reader = new FileReader("groups.xml");
            var bReader = new BufferedReader(reader);
        ) {
            var line = bReader.readLine();
            while (line != null) {
                json = json + line;
                line = bReader.readLine();
            }
        }
        // ИЛИ
        // var json = Files.readString(Paths.get("groups.json"));

        var value = mapper.readValue(json, new TypeReference<List<GroupData>>() {}); // = сериализация инфы из файла в объектё

        // ИЛИ
        // var value = mapper.readValue(new File("groups.json"), new TypeReference<List<GroupData>>() {});
        // result.addAll(value);
        return result;
    }

    public static ArrayList<GroupData> negativeGroupProvider() {
        var result = new ArrayList<>(List.of(
                new GroupData("", "group name'", "", "")));
        return result;
    }


    // Параметризованные тесты
    @ParameterizedTest
    @ValueSource(strings = { "group name", "group name'" })
    public void canCreateGroup(String name) {
        int groupCount = app.groups().getCount();
        app.groups().createGroup(new GroupData("", name, "group header", "group footer"));
        int newGroupCount = app.groups().getCount();
        Assertions.assertEquals(groupCount + 1, newGroupCount);
    }

    // data-driven testing = тестирование, направляемое данными
    @ParameterizedTest
    @MethodSource("groupProvider")
    public void canCreateMultipleGroupsWithDifferentNames(GroupData group) {
        var oldGroups = app.groups().getList();
        app.groups().createGroup(group);
        var newGroups = app.groups().getList();
        var expectedList = new ArrayList<>(oldGroups);

        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroups.sort(compareById);

        expectedList.add(group.withId(newGroups.get(newGroups.size() - 1).id()).withHeader("").withFooter(""));
        expectedList.sort(compareById);
        Assertions.assertEquals(newGroups, expectedList);
    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void cannotCreateGroup(GroupData group) {
        var oldGroups = app.groups().getList();
        app.groups().createGroup(group);
        var newGroups = app.groups().getList();
        Assertions.assertEquals(newGroups, oldGroups);
    }

    /*@Test
    public void canCreateGroupWithEmptyName() {
        app.groups().createGroup(new GroupData());
    }

    @Test
    public void canCreateGroupWithNameOnly() {
        app.groups().createGroup(new GroupData().withName("some name"));
    }*/
}

