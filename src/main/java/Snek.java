import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

public class Snek {
    private static final String LINEBREAK = "------------------------------------------------------------";
    private static final String HELLO = "Ssss... Hello I'm Snek! Ssss...\nWhatsss cans I do for you todayss...?";
    private static final String BYE = "Ssss... Bye! Ssss...";

    private static ArrayList<Task> taskList = new ArrayList<>();
    private static String STORAGEPATH = "./data";
    private static String FILENAME = "snek.txt";

    private static String frameMessage(String input) {
        return LINEBREAK + "\n" + input + "\n" + LINEBREAK;
    }

    private static void initialiseStorage(String path, String filename) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(path, filename);
        try {
            if (!file.createNewFile()) {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    // Load tasks from file
                }
                sc.close();
                System.out.println("Ssss... Loaded existing tasksss from storage..sss!");
            } else {
                System.out.println("Ssss... Created new storage file for tasksss..sss!");
            }
        } catch (IOException e) {
            System.err.println("Ssss... Error creating storage file!");
        }
    }

    private static void addTask(Task task) {
        taskList.add(task);
        System.out.println(frameMessage("I'ves addedss:\n\t" + task + "\nYou now havesss " + taskList.size() + " task(s) in your listssss."));
    }

    private static void printTaskList() {
        String res = "";
        for (int i = 0; i < taskList.size(); i++) {
            res += (i + 1) + ". " + taskList.get(i);
            if (i != taskList.size() - 1) {
                res += "\n";
            }
        }
        System.out.println(frameMessage(res));
    }

    private static void markTask(String taskNumber) throws SnekException {
        int index;
        try {
            index = Integer.valueOf(taskNumber) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidArgumentSnekException("Ssss... Invalid task number!");
        }
        if (index < 0 || index >= taskList.size()) {
            throw new InvalidArgumentSnekException("Ssss... Invalid task number!");
        }
        if (taskList.get(index).isDone()) {
            throw new InvalidArgumentSnekException("Ssss... Thisss task isss already marked as done!");
        }
        taskList.get(index).markAsDone();
        System.out.println(frameMessage("Ssss... I'ves marked thisss task as donesss:\n  " + taskList.get(index)));
    }

    private static void unmarkTask(String taskNumber) throws SnekException {
        int index;
        try {
            index = Integer.valueOf(taskNumber) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidArgumentSnekException("Ssss... Invalid task number!");
        }
        if (index < 0 || index >= taskList.size()) {
            throw new InvalidArgumentSnekException("Ssss... Invalid task number!");
        }
        if (!taskList.get(index).isDone()) {
            throw new InvalidArgumentSnekException("Ssss... Thisss task isss already unmarked!");
        }
        taskList.get(index).unmarkAsDone();
        System.out.println(frameMessage("Ssss... I'ves marked thisss task as not done yet:\n  " + taskList.get(index)));
    }

    private static void createTodo(String description) {
        ToDo todo = new ToDo(description);
        addTask(todo);
    }

    private static void createDeadline(String description, String by) {
        Deadline deadline = new Deadline(description, by);
        addTask(deadline);
    }

    private static void createEvent(String description, String from, String to) {
        Event event = new Event(description, from, to);
        addTask(event);
    }

    private static void handleTodo(String input) {
        int commandLen = "todo".length();
        String description = input.substring(commandLen).trim();
        createTodo(description);
    }

    private static void handleDeadline(String input) throws SnekException {
        String byMarker = "/by";
        int byIdx = input.indexOf(byMarker);
        int commandLen = "deadline".length();

        if (byIdx == -1 || byIdx <= commandLen) {
            throw new InvalidArgumentSnekException("Ssss... Please provide a deadline in the format: deadline DESCRIPTION /by TIME");
        }

        String description = input.substring(commandLen, byIdx).trim();
        String by = input.substring(byIdx + byMarker.length()).trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new InvalidArgumentSnekException("Ssss... Missing description or /by time.");
        }

        createDeadline(description, by);
    }

    private static void handleEvent(String input) throws SnekException {
        String fromMarker = "/from";
        String toMarker = "/to";

        int fromIdx = input.indexOf(fromMarker);
        int toIdx = input.indexOf(toMarker);
        int commandLen = "event".length();

        if (fromIdx == -1 || toIdx == -1 || fromIdx > toIdx) {
            throw new InvalidArgumentSnekException("Ssss... Please provide event in the format: event DESCRIPTION /from START /to END");
        }

        String description = input.substring(commandLen, fromIdx).trim();
        String from = input.substring(fromIdx + fromMarker.length(), toIdx).trim();
        String to = input.substring(toIdx + toMarker.length()).trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new InvalidArgumentSnekException("Ssss... Missing description or /from or /to details.");
        }

        createEvent(description, from, to);
    }

    private static void handleDelete(String taskNumber) throws SnekException {
        int index;
        try {
            index = Integer.valueOf(taskNumber) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidArgumentSnekException("Ssss... Invalid task number!");
        }
        if (index < 0 || index >= taskList.size()) {
            throw new InvalidArgumentSnekException("Ssss... Invalid task number!");
        }
        Task removedTask = taskList.remove(index);
        System.out.println(frameMessage("Ssss... I'ves removed thisss task:\n\t" + removedTask + "\nYou now havesss " + taskList.size() + " task(s) in your listssss."));
    }

    private static void handleUserInput(String input) throws SnekException{
        String[] args = input.split("[\\s]");
        Command cmd = Command.from(args[0]);
        switch (cmd) {
            case LIST:
                printTaskList();
                break;
            case MARK:
                markTask(args[1]);
                break;
            case UNMARK:
                unmarkTask(args[1]);
                break;
            case TODO:
                handleTodo(input);
                break;
            case DEADLINE:
                handleDeadline(input);
                break;
            case EVENT:
                handleEvent(input);
                break;
            case DELETE:
                handleDelete(args[1]);
                break;
            default:
                throw new InvalidCommandSnekException(input);
        }
    }

    public static void main(String[] args) {
        System.out.println(frameMessage(HELLO));

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            try {
                handleUserInput(input);
            } catch (SnekException e) {
                System.err.println(frameMessage(e.getMessage()));
            } finally {
                input = sc.nextLine();
            }
        }

        System.out.println(frameMessage(BYE));
        sc.close();
    }
}
