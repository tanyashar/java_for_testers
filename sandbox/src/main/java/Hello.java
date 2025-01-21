import java.io.File;

public class Hello {
    public static void main(String[] args) {
        // try {
            var x = 1;
            var y = 1;
            if (y == 0) {
                // then-блок
                System.out.println("Division by zero is not allowed");
            }
            else {
                int z = divide(x, y);
            }

            System.out.println("Hello, world!");

            var configFile = new File("sandbox/build.gradle");
            System.out.println(configFile.getAbsolutePath());
            System.out.println(configFile.exists());

            System.out.println(new File("").getAbsolutePath());
        // }
        // catch (ArithmeticException exception) {
            // отладочная печать = смотреть значение переменных в отладчике
            // exception.printStackTrace();
        //     System.out.println("Division by zero is not allowed");
        // }
    }

    private static int divide(int x, int y) {
        var z = x / y;
        return z;
    }
}
