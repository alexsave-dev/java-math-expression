package expression;

import exception.CalculationException;

import java.util.ArrayDeque;

/**
 * The {@code Calculator} class calculates the result for the given
 * arithmetic expression in the form of Reverse Polish Notation (RPN).
 * The result of the calculation for "1 _ 2 + 2 /" is 0.5
 */
public class Calculator {

    /** The stack to be used by the RPN algorithm*/
    private final ArrayDeque<String> calculatorStack = new ArrayDeque<>();

    private boolean isOperand(String item) {
        return item.matches("\\d+(.?\\d+)?");
    }

    /** Applies the relevant operator to the one or two top stack elements */
    private void applyOperator(String operator) throws CalculationException {
        double result;
        try {
            switch (operator) {
                case "^":
                    double power = Double.parseDouble(calculatorStack.pop());
                    result = Math.pow(Double.parseDouble(calculatorStack.pop()), power);
                    break;
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

    /**
     * Calculates the value for the expression in the form of RPN
     * @param  expression
     *         The expression in the form of RPN
     * @return  the double type result of the calculation.
     */
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
