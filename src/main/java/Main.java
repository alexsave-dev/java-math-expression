import exception.CalculationException;
import exception.ParsingException;
import expression.Calculator;
import expression.ConsoleHelper;
import expression.Parser;
import expression.Validator;

/**
 * The {@code Main} class allows the user to input the arithmetic expression into
 * the console and see the result of the calculation.
 */
public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser();
        Validator validator = new Validator(parser);
        Calculator calculator = new Calculator();

        while (true) {
            ConsoleHelper.writeMessageWithoutNewLine("Please enter the expression: ");
            String expression = ConsoleHelper.readMessage();
            if (expression.equals("exit")) {
                return;
            }
            if (validator.validate(expression)) {
                try {
                    ConsoleHelper.writeMessage("The result is " + calculator.calculate(parser.parseToReversePolishNotation(expression)));
                } catch (ParsingException | CalculationException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                ConsoleHelper.writeMessage("Validation failed!");
            }
        }
    }
}
