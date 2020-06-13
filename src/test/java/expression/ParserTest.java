package expression;

import exception.ParsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    Parser parser = new Parser();

    @Test
    @DisplayName("Test of the null parameter for Parser.parseToPolishNotation(String expression)")
    void testParseToReversePolishNotationNullParam() {
        assertThrows(ParsingException.class, () -> parser.parseToReversePolishNotation(null));
    }

    @Test
    @DisplayName("Test of the empty parameter for Parser.parseToPolishNotation(String expression)")
    void testParseToReversePolishNotationEmptyParam() {
        assertThrows(ParsingException.class, () -> parser.parseToReversePolishNotation(""));
    }

    @ParameterizedTest
    @MethodSource("expression.Providers#testParseToReversePolishNotation")
    @DisplayName("Test of Validator.parseToReversePolishNotation(String expression)")
    void testParseToReversePolishNotation(String param, String expected) throws ParsingException {
        assertEquals(expected, parser.parseToReversePolishNotation(param), "For input param " + param);
    }


}