package ru.stqa.geometry;

import ru.stqa.geometry.figures.Square;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Geometry {
    public static void main(String[] args) {
        // consumer - ничего не принимает, но что-то возвращает = замена цикла со счетчиком for
         var  squares = List.of(new Square(7.0), new Square(5.0), new Square(3.0));
        for (Square square: squares) {
            Square.printArea(square);
        }
        Consumer<Square> print = (square) -> {
            Square.printArea(square);
            Square.printPerimeter(square);
        };
        // Consumer<Square> print = Square::printSquareArea;
        squares.forEach(print);


        // supplier = generator = генератор для стрима
        // функция peek реагирует на те элементы, к-е продвигаются по потоку, но она пассивная
        // если в конце поставить не foreach, а peek, то действия не выполнятся
        // чтобы поток заработал, т.е. по нему начали двигаться объекты, в конце него должен находиться активный потребитель, например foreach (подробнее в документации)
        var streamSquares = Stream.of(new Square(7.0), new Square(5.0), new Square(3.0));
        Supplier<Square> randomSquare = () -> new Square(new Random().nextDouble(100.0));
        var generatedSquares = Stream.generate(randomSquare).limit(5);
        generatedSquares.peek(Square::printArea).forEach(Square::printPerimeter);



        // producer

        // сами функции - что-то принимает на вход, что-то возвращает

        // Rectangle.printRectangleArea(3.0, 5.0);
    }
}
