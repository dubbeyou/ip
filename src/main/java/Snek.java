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

    private static void addTask(Task task) {
        taskList[taskCount] = task;
        taskCount++;
        System.out.println(frameMessage("I'ves addedss: " + task));
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

    private static void createTodo(String description) {
        ToDo todo = new ToDo(description);
        addTask(todo);
    }

    private static void createDeadline(String description, String by) {
        Deadline deadline = new Deadline(description, by);
        addTask(deadline);
    }

    private static void handleTodo(String input) {
        String description = input.substring(5).trim();
        createTodo(description);
    }

    private static void handleDeadline(String input) {
        String byMarker = "/by";
        int byIdx = input.indexOf(byMarker);
        int commandLen = "deadline".length();

        if (byIdx == -1 || byIdx <= commandLen) {
            System.out.println(frameMessage("Ssss... Please provide a deadline in the format: deadline DESCRIPTION /by TIME"));
            return;
        }

        String description = input.substring(commandLen, byIdx).trim();
        String by = input.substring(byIdx + byMarker.length()).trim();

        if (description.isEmpty() || by.isEmpty()) {
            System.out.println(frameMessage("Ssss... Missing description or /by time."));
            return;
        }

        createDeadline(description, by);
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
            case "todo":
                handleTodo(input);
                break;
            case "deadline":
                handleDeadline(input);
                break;
            default:
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
