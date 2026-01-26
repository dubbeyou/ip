package snek.ui;

import static snek.common.Messages.MESSAGE_BYE;
import static snek.common.Messages.MESSAGE_HELLO;
import static snek.common.Messages.MESSAGE_LINEBREAK;

import java.util.Scanner;

/**
 * User interface class for the Snek application. Handles interaction with the
 * user via the console.
 */
public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prints a message to the console with formatting.
     *
     * @param message The message to print.
     */
    public void print(String message) {
        System.out.println(MESSAGE_LINEBREAK + "\n" + message + "\n" + MESSAGE_LINEBREAK);
    }

    /**
     * Prints an error message to the console with formatting.
     *
     * @param message The error message to print.
     */
    public void printError(String message) {
        System.err.println(MESSAGE_LINEBREAK + "\n" + message + "\n" + MESSAGE_LINEBREAK);
    }

    /**
     * Prints the welcome message to the console.
     */
    public void printHelloMessage() {
        print(MESSAGE_HELLO);
    }

    /**
     * Prints the goodbye message to the console.
     */
    public void printByeMessage() {
        print(MESSAGE_BYE);
    }

    /**
     * Reads a command from the user input.
     *
     * @return The command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }
}
