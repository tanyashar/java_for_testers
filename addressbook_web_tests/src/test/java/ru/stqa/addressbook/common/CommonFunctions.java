package ru.stqa.addressbook.common;

import java.util.Random;

public class CommonFunctions {
    public static String randomString(int stringLength) {
        var rnd = new Random(); // тип данных для рандомных чисел
        var result = "";
        for (int i = 0; i < stringLength; i++) {
            result += (char)('a' + rnd.nextInt(0, 26));
        }

        // if (stringLength < 20)
        //     result += '\'';

        return result;
    }
}
