package ru.stqa.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionTests {
    @Test
    void arrayTests() {
        // var array = new String[] { "a", "b", "c" };
        var array = new String[3]; // массив, не изменияемый по размеру, заполненный пустыми значениями null
        Assertions.assertEquals(3, array.length);
        array[0] = "a";
        Assertions.assertEquals("a", array[0]);

        array[0] = "d";
        Assertions.assertEquals("a", array[0]);
    }

    @Test
    void listTests() {
        // список для хранения строк - нельзя сразу инициализировать набор элементов
        // var list = new ArrayList<String>();

        // List.of() - немодифицируемый список, т.е. нельзя менять элементы этого списка
        // var list = List.of("a", "b", "c");

        // предзаполненный и изменяемый список
        var list = new ArrayList<>(List.of("a", "b", "c"));

        Assertions.assertEquals(3, list.size()); // список имеет переменную длину

        list.add("a");
        list.add("b");
        list.add("c");
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals("a", list.get(0));

        list.set(0, "d");
        Assertions.assertEquals("d", list.get(0));
    }
}
