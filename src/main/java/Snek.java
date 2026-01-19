import java.util.Scanner;

public class Snek {
    private static final String LINEBREAK = "------------------------------------------------------------";
    private static final String HELLO = "Ssss... Hello I'm Snek! Ssss...\nWhatsss cans I do for you todayss...?";
    private static final String BYE = "Ssss... Bye! Ssss...";

    private static String frameMessage(String input) {
        return LINEBREAK + "\n" + input + "\n" + LINEBREAK;
    }

    public static void main(String[] args) {
        System.out.println(frameMessage(HELLO));

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            System.out.println(frameMessage(input));
            input = sc.nextLine();
        }

        System.out.println(frameMessage(BYE));
        sc.close();
    }
}
