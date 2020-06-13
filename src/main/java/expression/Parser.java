package expression;

import exception.ParsingException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private final HashSet<String> unaryPolishOperators = new HashSet<String>()
    {{add("_");}};

    private final HashSet<String> arithmeticOperators = new HashSet<String>()
    {
        {add("+");}
        {add("*");}
        {add("/");}
        {add("-");}
    };

    private final HashSet<String> unaryArithmeticOperators = new HashSet<String>()
    {
        {add("-");}
    };

    private final HashMap<String, Integer> operatorsPriorities = new HashMap<>();
    private final ArrayDeque<String> operatorsStack = new ArrayDeque<>();
    private String polishNotation = "";

    public Parser() {
        operatorsPriorities.put("*", 2);
        operatorsPriorities.put("/", 2);
        operatorsPriorities.put("+", 1);
        operatorsPriorities.put("-", 1);
        operatorsPriorities.put("_", 0);
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
        if (operatorsStack.size() != 0 && !isUnaryPolishOperator(operator) &&
                (getOperatorPriority(operator) == getOperatorPriority(operatorsStack.peek()) ||
                        isUnaryPolishOperator(operatorsStack.peek()))) {
            addToTheEndOfPolishNotation(operatorsStack.pop());
        }
        operatorsStack.push(operator);
    }

    private void processUnaryBinaryMinus(String leftSymbol) {
        //if (leftSymbol.matches("[\\d\\)]{1}")) {
        if (leftSymbol.matches("[\\d)]{1}")) {
            processOperator("-");
        } else {
            processOperator("_");
        }
    }

    private void addToTheEndOfPolishNotation(String polishNotationItem) {
        polishNotation += " " + polishNotationItem;
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

    public String parseToPolishNotation(String expression) throws ParsingException {
        if (expression == null || expression.length() == 0) {
            throw new ParsingException("Parsing failed!");
        }
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
        return polishNotation.trim();
    }

}
