import java.util.Scanner;

public class Snek {
    private static final String LINEBREAK = "------------------------------------------------------------";
    private static final String HELLO = "Ssss... Hello I'm Snek! Ssss...\nWhatsss cans I do for you todayss...?";
    private static final String BYE = "Ssss... Bye! Ssss...";

    private static String[] toDoList = new String[100];
    private static int toDoCount = 0;

    private static String frameMessage(String input) {
        return LINEBREAK + "\n" + input + "\n" + LINEBREAK;
    }

    private static void addToDo(String task) {
        toDoList[toDoCount] = task;
        toDoCount++;
    }

    private static void printToDoList() {
        String res = "";
        for (int i = 0; i < toDoCount; i++) {
            res += (i + 1) + ". " + toDoList[i];
            if (i != toDoCount - 1) {
                res += "\n";
            }
        }
        System.out.println(frameMessage(res));
    }

    public static void main(String[] args) {
        System.out.println(frameMessage(HELLO));

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                printToDoList();
            } else {
                System.out.println(frameMessage("I'ves addedss: " + input));
                addToDo(input);
            }
            input = sc.nextLine();
        }

        System.out.println(frameMessage(BYE));
        sc.close();
    }
}
