package expression;

import exception.CalculationException;
import exception.ParsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Parser parser = new Parser();
    Calculator calculator = new Calculator();

    @ParameterizedTest
    @MethodSource("expression.Providers#testCalculate")
    @DisplayName("Test of Calculator.calculate(String expression)")
    void testCalculate(String param, double expected) throws CalculationException, ParsingException {
        assertEquals(expected, calculator.calculate(parser.parseToReversePolishNotation(param)), "For input param " + param);
    }

    @Test
    @DisplayName("Test of the empty parameter for Calculator.calculate(String expression)")
    void testCalculateEmptyParam() {
        assertThrows(CalculationException.class, () -> calculator.calculate(""));
    }

    @Test
    @DisplayName("Test of the null parameter for Calculator.calculate(String expression)")
    void testCalculateNullParam() {
        assertThrows(CalculationException.class, () -> calculator.calculate(""));
    }

    @Test
    @DisplayName("Test of the invalid operator for Calculator.calculate(String expression)")
    void testInvalidOperator() {
        assertThrows(CalculationException.class, () -> calculator.calculate("2 3 ,"));
    }

    @Test
    @DisplayName("Test of the division by zero for Calculator.calculate(String expression)")
    void testDivisionByZero() {
        assertThrows(CalculationException.class, () -> calculator.calculate("1 0 /"));
    }
}