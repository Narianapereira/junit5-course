import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

public class CalculatorTest {

    private Calculator calculator;

    @Test
    public void shouldReturnDecimalOnDivision(){
        calculator = new Calculator();
        float result = calculator.division(10,3);
        Assertions.assertEquals(3.3333332538604736, result);
    }

    @Test
    public void shouldReturnZeroOnZeroNumerator(){
        calculator = new Calculator();
        float result = calculator.division(0,6);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void shouldThrowExceptionOnDivisionByZero(){
        ArithmeticException exeption = Assertions.assertThrows(ArithmeticException.class, () -> {
            calculator = new Calculator();
            float result = calculator.division(10,0);
        });
        Assertions.assertEquals("/ by zero", exeption.getMessage());
    }

    @Test
    public void toSum(){
        calculator = new Calculator();
        Assertions.assertTrue(calculator.soma(2, 3) == 5);
        Assertions.assertEquals(5, calculator.soma(3, 2));
    }

    @Test
    public void assertions(){
        Assertions.assertEquals("casa", "casa");
        Assertions.assertNotEquals("casa", "Casa");
        Assertions.assertTrue("casa".equalsIgnoreCase(("Casa")));

        List<String> s1 = new ArrayList<>();
        List<String> s2 = new ArrayList<>();
        List<String> s3 = new ArrayList<>();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Teste1", "Teste2"})
    public void testStrings(String param){
        System.out.println(param);
        Assertions.assertNotNull(param);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6,2,3",
            "6, -2, -3",
            "10, 3, 3.3333332538604736",
            "0,2,0"})
    public void shouldDivideCorrectly(int num, int den, double finalResult){
        calculator = new Calculator();
        double result = calculator.division(num, den);
        Assertions.assertEquals(finalResult, result);
    }

}
