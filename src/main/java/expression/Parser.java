package expression;

import exception.ParsingException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code Validator} class to convert the arithmetic expression to
 * the form of Reverse Polish Notation (RPN).
 * The RPN for the expression "(-1 + 2) / 2" is "1 _ 2 + 2 /"
 */
public class Parser {

    /** The set of presentations of unary operator in RPN.
     * For now it's only the unary minus */
    private final HashSet<String> unaryPolishOperators = new HashSet<String>()
    {{add("_");}};

    /** The list of arithmetic operators in scope */
    private final HashSet<String> arithmeticOperators = new HashSet<String>()
    {
        {add("^");}
        {add("+");}
        {add("*");}
        {add("/");}
        {add("-");}
    };

    /** The list of arithmetic operators which can be also used in unary form */
    private final HashSet<String> unaryArithmeticOperators = new HashSet<String>()
    {
        {add("-");}
    };

    /** The set of pairs <operator, priority> to be used for actions with operatorsStack */
    private final HashMap<String, Integer> operatorsPriorities = new HashMap<>();

    /** The stack of operators to be used for RPN to be prepared */
    private final ArrayDeque<String> operatorsStack = new ArrayDeque<>();

    /** Contains the result string in the form of RPN */
    private String polishReverseNotation = "";

    public Parser() {
        operatorsPriorities.put("^", 3);
        operatorsPriorities.put("*", 2);
        operatorsPriorities.put("/", 2);
        operatorsPriorities.put("+", 1);
        operatorsPriorities.put("-", 1);
        operatorsPriorities.put("_", 0);
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

    public Set<String> getUnaryArithmeticOperators() {
        return unaryArithmeticOperators;
    }

    public HashSet<String> getArithmeticOperators() {
        return arithmeticOperators;
    }

    private int getOperatorPriority(String operator) {
        return operatorsPriorities.get(operator) == null ? 0 : operatorsPriorities.get(operator);
    }

    private boolean isUnaryPolishOperator(String operator) {
        return unaryPolishOperators.contains(operator);
    }

    private void processOperator(String operator) {
        if (operatorsStack.size() != 0 && !isUnaryPolishOperator(operator)) {
            while (getOperatorPriority(operator) <= getOperatorPriority(operatorsStack.peek()) ||
                        isUnaryPolishOperator(operatorsStack.peek())) {
                addToTheEndOfPolishNotation(operatorsStack.pop());
            }
        }
        operatorsStack.push(operator);
    }

    private void processUnaryBinaryMinus(String leftSymbol) {
        if (leftSymbol.matches("[\\d)]{1}")) {
            processOperator("-");
        } else {
            processOperator("_");
        }
    }

    private void addToTheEndOfPolishNotation(String polishNotationItem) {
        polishReverseNotation += " " + polishNotationItem;
    }

    private void processOpenBracket() {
        operatorsStack.push("(");
    }

    private void processCloseBracket() {
        boolean isOpenBracketFound = false;
        while (!operatorsStack.isEmpty()) {
            String operand = operatorsStack.pop();
            if (!operand.equals("(")) {
                addToTheEndOfPolishNotation(operand);
            } else {
                break;
            }
        }
    }

    private String getOperand(String expression, int fromPosition) throws ParsingException {
        Pattern pattern = Pattern.compile("\\d+(\\.?\\d+)?");
        Matcher matcher = pattern.matcher(expression.substring(fromPosition));
        if (matcher.find()) {
            return matcher.group();
        } else {
            throw new ParsingException("Parsing failed!");
        }
    }

    private void processOperand(String operand) {
        addToTheEndOfPolishNotation(operand);
    }

    private void init() {
        polishReverseNotation = "";
    }

    /**
     * converts the passed arithmetic expression to the form of RPN
     * @param  expression
     *         The arithmetic expression
     * @return  the string in the form of RPN.
     */
    public String parseToReversePolishNotation(String expression) throws ParsingException {
        if (expression == null || expression.trim().equals("")) {
            throw new ParsingException("Parsing failed!");
        }
        init();
        expression = removeSpaces(expression);
        int i = 0;
        while (i < expression.length()) {
            String currentSymbol = String.valueOf(expression.charAt(i));
            // check for binary/unary minus
            if (currentSymbol.equals("-")) {
                String leftSymbol = i == 0 ? "" : expression.substring(i - 1, i);
                processUnaryBinaryMinus(leftSymbol);
                i++;
            } else if (currentSymbol.equals("(")) {
                processOpenBracket();
                i++;
            } else if (currentSymbol.equals(")")) {
                processCloseBracket();
                i++;
            } else if (operatorsPriorities.containsKey(currentSymbol)) {
                processOperator(currentSymbol);
                i++;
            } else if (currentSymbol.matches("\\d")){
                String operand = getOperand(expression, i);
                processOperand(operand);
                i += operand.length();
            } else {
                throw new ParsingException("Parsing failed!");
            }
        }

        // pop the rest of the stack to the end of the Polish notation
        while (!operatorsStack.isEmpty()) {
            String operand = operatorsStack.pop();
            addToTheEndOfPolishNotation(operand);
        }
        return polishReverseNotation.trim();
    }

}
