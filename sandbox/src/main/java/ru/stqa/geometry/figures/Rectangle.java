package ru.stqa.geometry.figures;

import java.util.Objects;

// ключевое слово record - аналог class
// но описание класса (свойства) в аргументах
// а конструктор - по умолчанию
public record Rectangle(
        double a,
        double b
) {
    public Rectangle {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Rectangle side should be non-negative");
        }
    }

    public static void printRectangleArea(
            double a,
            double b) {
        var text = String.format("Площадь прямоугольника со сторонами %f и %f = %f", a, b, rectangle(a, b));
        System.out.println(text);
    }

    private static double rectangle(double a, double b) {
        return a * b;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(this.a, rectangle.a) == 0 && Double.compare(this.b, rectangle.b) == 0
                || Double.compare(this.a, rectangle.b) == 0 && Double.compare(this.b, rectangle.a) == 0;
    }

    @Override
    public int hashCode() {
        // нужен для ускорения сравнения объектов
        // предварительное быстрое сравнение двух объектов
        return Objects.hash(a, b);
    }
}
