package expression;

import exception.CalculationException;

import java.util.ArrayDeque;

public class Calculator {

    private final ArrayDeque<String> calculatorStack = new ArrayDeque<>();

    private boolean isOperand(String item) {
        return item.matches("\\d+(.?\\d+)?");
    }

    private void applyOperator(String operator) throws CalculationException {
        double result;
        try {
            switch (operator) {
                case "*":
                    result = Double.parseDouble(calculatorStack.pop()) * Double.parseDouble(calculatorStack.pop());
                    break;
                case "/":
                    result = 1d / Double.parseDouble(calculatorStack.pop()) * Double.parseDouble(calculatorStack.pop());
                    if (result == Double.NEGATIVE_INFINITY || result == Double.POSITIVE_INFINITY) {
                        throw new ArithmeticException("/ by zero");
                    }
                    break;
                case "+":
                    result = Double.parseDouble(calculatorStack.pop()) + Double.parseDouble(calculatorStack.pop());
                    break;
                case "-":
                    result = (-1d) * Double.parseDouble(calculatorStack.pop()) + Double.parseDouble(calculatorStack.pop());
                    break;
                case "_":
                    result = (-1d) * Double.parseDouble(calculatorStack.pop());
                    break;
                default:
                    throw new CalculationException("Unexpected operator: " + operator);
            }
        } catch (ArithmeticException e) {
            throw new CalculationException("Arithmetic exception: " + e.getMessage());
        } catch (Exception e) {
            throw new CalculationException("Other exception: " + e.getMessage());
        }
        calculatorStack.push(String.valueOf(result));
    }

    public double calculate(String expression) throws CalculationException {
        if (expression == null || expression.length() == 0) {
            throw new CalculationException("The expression is null or empty");
        }
        String[] operandsAndOperators = expression.split(" ");
        for (String item : operandsAndOperators) {
            if (isOperand(item)) {
                calculatorStack.push(item);
            } else {
                applyOperator(item);
            }
        }
        return Double.parseDouble(calculatorStack.pop());
    }

}
