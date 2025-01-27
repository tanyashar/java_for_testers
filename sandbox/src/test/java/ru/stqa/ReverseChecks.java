package ru.stqa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ReverseChecks {
    @Test
    void testSqrt() {
        var input = 4.0;
        var result = Math.sqrt(input);
        Assertions.assertEquals(2.0, result);

        // Обратная проверка
        // = Не предсказывать ожидаемый результат, а выполнить с ним обратное действие, чтобы получиь исходное значение
        input = 5.0;
        result = Math.sqrt(input);
        var reverse = result * result;
        Assertions.assertEquals(input, reverse, 0.00001); // + не забыть задать погрешность вычислений delta
    }

    @Test
    void testSort() {
        var input = new ArrayList<>(List.of(3, 7, 4, 9, 0));
        input.sort(Integer::compareTo); // встроенный компаратор для целых чисел

        Assertions.assertEquals(List.of(0, 3, 4, 7, 9), input);
        // ИЛИ
        for (int i = 0; i < input.size() - 1; i++) {
            Assertions.assertTrue(input.get(i) <= input.get(i + 1));
        }
    }
}
