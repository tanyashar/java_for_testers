package ru.stqa.geometry;

import ru.stqa.geometry.figures.Rectangle;
import ru.stqa.geometry.figures.Square;

public class Geometry {
    public static void main(String[] args) {
        Square.printSquareArea(new Square(7.0));

        Rectangle.printRectangleArea(3.0, 5.0);

    }

}
