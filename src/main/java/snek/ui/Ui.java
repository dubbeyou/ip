package snek.ui;

import java.util.Scanner;

import snek.common.Messages;

/**
 * User interface class for the Snek application.
 * Handles interaction with the user via the console.
 */
public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prints a message to the console with formatting.
     *
     * @param message The message to print.
     */
    public void print(String message) {
        System.out.println(Messages.MESSAGE_LINEBREAK + "\n" + message + "\n" + Messages.MESSAGE_LINEBREAK);
    }

    /**
     * Prints an error message to the console with formatting.
     *
     * @param message The error message to print.
     */
    public void printError(String message) {
        System.err.println(Messages.MESSAGE_LINEBREAK + "\n" + message + "\n" + Messages.MESSAGE_LINEBREAK);
    }

    /**
     * Prints the welcome message to the console.
     */
    public void printHelloMessage() {
        print(Messages.MESSAGE_HELLO);
    }

    /**
     * Prints the goodbye message to the console.
     */
    public void printByeMessage() {
        print(Messages.MESSAGE_BYE);
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
