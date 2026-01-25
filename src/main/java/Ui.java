import java.util.Scanner;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public void print(String message) {
        System.out.println(Messages.MESSAGE_LINEBREAK + "\n" + message + "\n" + Messages.MESSAGE_LINEBREAK);
    }

    public void printError(String message) {
        System.err.println(Messages.MESSAGE_LINEBREAK + "\n" + message + "\n" + Messages.MESSAGE_LINEBREAK);
    }

    public void printHelloMessage() {
        print(Messages.MESSAGE_HELLO);
    }

    public void printByeMessage() {
        print(Messages.MESSAGE_BYE);
    }

    public String getUserInput() {
        return scanner.nextLine();
    }
}
