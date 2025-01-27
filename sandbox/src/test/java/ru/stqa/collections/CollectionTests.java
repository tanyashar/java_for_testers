package ru.stqa.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        var list = new ArrayList<>(List.of("a", "b", "c", "a"));

        Assertions.assertEquals(4, list.size()); // список имеет переменную длину

        list.add("a");
        list.add("b");
        list.add("c");
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals("a", list.get(0));

        list.set(0, "d");
        Assertions.assertEquals("d", list.get(0));
    }

    @Test
    void setTests() {
        // set = множество, неупорядоченный список, БЕЗ повторяющихся элементов
        // если элемент повторяется - IllegalArgumentException: duplicate element

        // немодифицируемый сет (неизменямый)
        var unmodifiableSet = Set.of("a", "b", "c");
        Assertions.assertEquals(3, unmodifiableSet.size());
        // unmodifiableSet.add("a"); // выдаст ошибку UnsupportedOperationException = нельзя добавлять элементы

        // изменяемый сет
        var modifiableSet = new HashSet<>(Set.of("a", "b", "c"));
        modifiableSet.add("c");
        Assertions.assertEquals(3, modifiableSet.size());

        modifiableSet.add("d");
        Assertions.assertEquals(4, modifiableSet.size());

        var set = Set.copyOf(List.of("a", "b", "c", "a"));
        Assertions.assertEquals(3, set.size());

        // получить элемент множества
        var randomElementWithArray = set.toArray()[0];
        var randomElementWithIterator = set.iterator().next();
        var randomElementWithStream = set.stream().findAny().get();
    }

}
