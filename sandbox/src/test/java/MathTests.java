import org.junit.jupiter.api.Test;

public class MathTests {
    @Test
    void testDivideByZero() {
        // в тестах не нужен try catch
        // ошибки в одном тесте не мешают запуску дргуих тестов
        var x = 1;
        var y = 0;
        var z = x / y;
        System.out.println(z);
    }
}
