package expression;

import java.util.Scanner;

/**
 * The {@code Calculator} class to handle I/O console actions.
 */
public class ConsoleHelper {


    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static void writeMessageWithoutNewLine(String message) {
        System.out.print(message);
    }

    public static String readMessage() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
