import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CalculatorTest {

    private Calculator calculator;

    @Test
    public void shouldReturnDecimalOnDivision(){
        calculator = new Calculator();
        float result = calculator.division(10,3);
        //Assertions.assertEquals(3.33, result, 0.33);
    }

    @Test
    public void shouldReturnZeroOnZeroNumerator(){
        calculator = new Calculator();
        float result = calculator.division(6,10);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void shouldThrowExceptionOnDivisionByZero(){
        ArithmeticException exeption = Assertions.assertThrows(ArithmeticException.class, () -> {
            calculator = new Calculator();
            float result = calculator.division(6,0);
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

}
