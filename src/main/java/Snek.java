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
        System.out.println(frameMessage("I'ves addedss: " + task));
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

    private static void markTask(String taskNumber) {
        int index = Integer.valueOf(taskNumber) - 1;
        if (index < 0 || index >= taskCount) {
            System.out.println(frameMessage("Ssss... Invalid task number!"));
            return;
        }
        if (taskList[index].isDone()) {
            System.out.println(frameMessage("Ssss... Thisss task isss already marked as done!"));
            return;
        }
        taskList[index].markAsDone();
        System.out.println(frameMessage("Ssss... I'ves marked thisss task as donesss:\n  " + taskList[index]));
    }

    private static void unmarkTask(String taskNumber) {
        int index = Integer.parseInt(taskNumber) - 1;
        if (index < 0 || index >= taskCount) {
            System.out.println(frameMessage("Ssss... Invalid task number!"));
            return;
        }
        if (!taskList[index].isDone()) {
            System.out.println(frameMessage("Ssss... Thisss task isss already unmarked!"));
            return;
        }
        taskList[index].unmarkAsDone();
        System.out.println(frameMessage("Ssss... I'ves marked thisss task as not done yet:\n  " + taskList[index]));
    }

    private static void handleUserInput(String input) {
        String[] args = input.split("[\\s]");
        switch (args[0]) {
            case "list":
                printTaskList();
                break;
            case "mark":
                markTask(args[1]);
                break;
            case "unmark":
                unmarkTask(args[1]);
                break;
            default:
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
