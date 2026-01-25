import java.util.Scanner;

public class Ui {
    private final String MSG_LINEBREAK = "------------------------------------------------------------";
    private final String MSG_HELLO = "Ssss... Hello I'm Snek! Ssss...\nWhatsss cans I do for you todayss...?";
    private final String MSG_BYE = "Ssss... Bye! Ssss...";

    private final Scanner scanner = new Scanner(System.in);

    public void print(String message) {
        System.out.println(MSG_LINEBREAK + "\n" + message + "\n" + MSG_LINEBREAK);
    }

    public void printError(String message) {
        System.err.println(MSG_LINEBREAK + "\n" + message + "\n" + MSG_LINEBREAK);
    }

    public void printHelloMessage() {
        print(MSG_HELLO);
    }

    public void printByeMessage() {
        print(MSG_BYE);
    }
}
