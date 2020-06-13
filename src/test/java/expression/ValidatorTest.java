package expression;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    Parser parser = new Parser();
    Validator validator = new Validator(parser);
    Calculator calculator = new Calculator();

    @ParameterizedTest
    @MethodSource("expression.Providers#testValidate")
    @DisplayName("Test of Validator.validate(String expression)")
    void testValidate(String param, boolean expected) {
        assertEquals(expected, validator.validate(param), "For input param " + param);
    }
}