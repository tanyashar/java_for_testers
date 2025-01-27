package ru.stqa.geometry.figures;

public record Square(double side) {

    // конструктор для record
    // (!) набор параметров такой же, как в record
    public Square {
        if (side < 0) {
            throw new IllegalArgumentException("Square side should be non-negative");
        }
    }

    // public double side;

    // public Square(double side) {
        // this.side = side;
    // }

    public static void printArea(Square s){
        String text = String.format("Площадь квадрата со стороной %f = %f", s.side, s.area());
        System.out.println(text);
    }

    public static double area(double a) {
        return a * a;
    }

    public static void printPerimeter(Square square) {
        System.out.println(square.side * 4);
    }

    public double area() {
        return this.side * this.side;
    }

    public double perimeter() {
        return this.side * 4;
    }
}
