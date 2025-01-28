package ru.stqa.mantis.common;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonFunctions {
    public static String randomString(int n) {
        var rnd = new Random(); // тип данных для рандомных чисел
        Supplier<Integer> randomNumbers = () -> rnd.nextInt(20);
        var result = Stream.generate(randomNumbers)
                .limit(n)
                .map(i -> 'a' + i)
                .map(i -> Character.toString(i))
                .collect(Collectors.joining());

        // if (stringLength < 20)
        //     result += '\'';

        return result;
    }
}
