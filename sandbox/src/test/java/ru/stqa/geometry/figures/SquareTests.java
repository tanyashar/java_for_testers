package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SquareTests {
    // Аннотации для запуска тестов - начинаются с @
    @Test
    void canCalculateArea(){
        var s = new Square(5.0);
        double result = s.area();

        Assertions.assertEquals(25.0, result);
    }

    // Тесты driven-development = тесты, которые пишутся до кода, который будем тестировать

    @Test
    void canCalculatePerimeter() {
        Assertions.assertEquals(20.0, new Square(5.0).perimeter());
    }

    @Test
    void cannotCreateSquareWithNegativeSide() {
        try {
            new Square(-5.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }

    }

    @Test
    void testEquality() {
        var s1 = new Square(5.0);
        var s2 = new Square(5.0);
        Assertions.assertEquals(s1, s2);
    }

    @Test
    void testNonEquality() {
        var s1 = new Square(5.0);
        var s2 = new Square(4.0);
        Assertions.assertNotEquals(s1, s2);
    }

    @Test
    void testPass() {
        var s1 = new Square(5.0);
        var s2 = new Square(5.0);
        // "==" - использовать только для сравнения только примитивных типов
        Assertions.assertTrue(s1.equals(s2));
    }
}
