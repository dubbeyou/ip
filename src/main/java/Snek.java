import java.util.Scanner;

public class Snek {
    private static final String LINEBREAK = "------------------------------------------------------------";
    private static final String HELLO = "Ssss... Hello I'm Snek! Ssss...\nWhatsss cans I do for you todayss...?";
    private static final String BYE = "Ssss... Bye! Ssss...";

    private static Task[] taskList = new Task[100];
    private static int taskCount = 0;

    private static String frameMessage(String input) {
        return LINEBREAK + "\n" + input + "\n" + LINEBREAK;
    }

    private static void addTask(String task) {
        taskList[taskCount] = new Task(task);
        taskCount++;
    }

    private static void printTaskList() {
        String res = "";
        for (int i = 0; i < taskCount; i++) {
            res += (i + 1) + ". " + taskList[i];
            if (i != taskCount - 1) {
                res += "\n";
            }
        }
        System.out.println(frameMessage(res));
    }

    private static void handleUserInput(String input) {
        switch(input) {
            case "list":
                printTaskList();
                break;
            default:
                System.out.println(frameMessage("I'ves addedss: " + input));
                addTask(input);
                break;
        }
    }

    public static void main(String[] args) {
        System.out.println(frameMessage(HELLO));

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            handleUserInput(input);
            input = sc.nextLine();
        }

        System.out.println(frameMessage(BYE));
        sc.close();
    }
}
