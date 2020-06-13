package expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code Validator} class validates the arithmetic expression.
 * The expression can contain digits, dots, symbols '(' and ')', and
 * arithmeticOperators of {@code Parser} class.
 * The example of the correct expression is:
 * -3 + 4 * 2 / (1 - 5)^2
 */

public class Validator {

    private Parser parser;

    public Validator(Parser parser) {
        this.parser = parser;
    }

    /**
     * Screens symbols which are the part of Regex key symbols
     * @param  text
     *         The text with symbols to be screened where it's required
     */
    private String screenRegexSymbols(String text) {
        String regexSymbols = "<([{\\^-=$!|]})?*+.>";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (regexSymbols.contains(String.valueOf(text.charAt(i)))) {
                stringBuilder.append("\\");
            }
            stringBuilder.append(text, i, i + 1);
        }
        return stringBuilder.toString();
    }

    /**
     * validated whether the passed string is the arithmetic expression
     * @param  expression
     *         The string to be checked for the arithmetic expression
     */
    public boolean validate(String expression) {
        if (expression == null || expression.trim().equals("")) return false;
        expression = parser.removeSpaces(expression);
        String arithmeticOperators = String.join("", parser.getArithmeticOperators());
        String unaryArithmeticOperators = String.join("", parser.getUnaryArithmeticOperators());
        // check if all symbols in the expression are allowed
        if (expression.matches("[\\d()." + arithmeticOperators + "]+")) {
            String expressionWithoutBrackets = "[" + screenRegexSymbols(unaryArithmeticOperators) + "]?\\d+\\.?\\d*([" +
                    screenRegexSymbols(arithmeticOperators) + "]{1}\\d+\\.?\\d*)*";
            String expressionWithBrackets = "\\(" + expressionWithoutBrackets + "\\)";

            Pattern patternExpressionWithBrackets = Pattern.compile(expressionWithBrackets);
            while (true) {
                Matcher matcher = patternExpressionWithBrackets.matcher(expression);
                if (matcher.find()) {
                    expression = expression.replaceAll(expressionWithBrackets, "0");
                } else return expression.matches(expressionWithoutBrackets);
            }
        }
        return false;
    }
}
