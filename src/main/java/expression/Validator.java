package expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Validator {

    private Parser parser;

    public Validator(Parser parser) {
        this.parser = parser;
    }

    public String removeSpaces(String text) {

        if (text == null || text.length() == 0) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (!(text.charAt(i) == ' ')) {
                stringBuilder.append(text, i, i + 1);
            }
        }
        return stringBuilder.toString();
    }

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

    public boolean validate(String expression) {

        //String arithmeticOperators = parser.getArithmeticOperators().stream().collect(Collectors.joining());
        String arithmeticOperators = String.join("", parser.getArithmeticOperators());
        String unaryArithmeticOperators = String.join("", parser.getUnaryArithmeticOperators());
        // check if all symbols in the expression are allowed
        //if (expression.matches("[\\d\\(\\)\\." + arithmeticOperators + "]+")) {
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
