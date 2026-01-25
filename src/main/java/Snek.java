import java.util.Scanner;
import java.time.LocalDateTime;

public class Snek {
    private static final Ui ui = new Ui();
    private static final Storage storage = new Storage();
    private static TaskList taskList = new TaskList();

    private static void addTask(Task task) {
        taskList.add(task);
        storage.write(task);
        ui.print(String.format(Messages.MESSAGE_ADD_TASK, task, taskList.size()));
    }

    private static void markTask(String taskNumber) throws SnekException {
        int index;
        try {
            index = Integer.valueOf(taskNumber);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_TASK);
        }
        if (taskList.getIndex(index).isDone()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_MARK);
        }
        taskList.getIndex(index).markAsDone();
        ui.print(String.format(Messages.MESSAGE_MARK_TASK, taskList.getIndex(index)));
        storage.overwrite(taskList.getTasks());
    }

    private static void unmarkTask(String taskNumber) throws SnekException {
        int index;
        try {
            index = Integer.valueOf(taskNumber);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_TASK);
        }
        if (!taskList.getIndex(index).isDone()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_UNMARK);
        }
        taskList.getIndex(index).unmarkAsDone();
        ui.print(String.format(Messages.MESSAGE_UNMARK_TASK, taskList.getIndex(index)));
        storage.overwrite(taskList.getTasks());
    }

    private static void createTodo(String description) {
        Todo todo = new Todo(description);
        addTask(todo);
    }

    private static void createDeadline(String description, String by) {
        LocalDateTime byDateTime = Parser.parseDateTime(by);
        if (byDateTime != null) {
            Deadline deadline = new Deadline(description, by, byDateTime);
            addTask(deadline);
            return;
        }
        Deadline deadline = new Deadline(description, by);
        addTask(deadline);
    }

    private static void createEvent(String description, String from, String to) {
        LocalDateTime fromDateTime = Parser.parseDateTime(from);
        LocalDateTime toDateTime = Parser.parseDateTime(to);
        if (fromDateTime != null && toDateTime != null) {
            Event event = new Event(description, from, fromDateTime, to, toDateTime);
            addTask(event);
            return;
        }
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
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_DEADLINE_1);
        }

        String description = input.substring(commandLen, byIdx).trim();
        String by = input.substring(byIdx + byMarker.length()).trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_DEADLINE_2);
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
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_EVENT_1);
        }

        String description = input.substring(commandLen, fromIdx).trim();
        String from = input.substring(fromIdx + fromMarker.length(), toIdx).trim();
        String to = input.substring(toIdx + toMarker.length()).trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_EVENT_2);
        }

        createEvent(description, from, to);
    }

    private static void handleDelete(String taskNumber) throws SnekException {
        int index;
        try {
            index = Integer.valueOf(taskNumber) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_TASK);
        }
        if (index < 0 || index >= taskList.size()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_TASK);
        }
        Task removedTask = taskList.delete(index);
        ui.print(String.format(Messages.MESSAGE_DELETE_TASK, removedTask, taskList.size()));

        storage.overwrite(taskList.getTasks());
    }

    private static void handleUserInput(String input) throws SnekException{
        String[] args = input.split("[\\s]");
        CommandType cmd = CommandType.from(args[0]);
        switch (cmd) {
            case LIST:
                ui.print(taskList.getString());
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
                throw new InvalidCommandSnekException();
        }
    }

    public static void main(String[] args) {
        ui.printHelloMessage();

        try {
            taskList = new TaskList(storage.loadTasks());
            ui.print(Messages.MESSAGE_SUCCESS_LOAD_EXISTING);
        } catch (SnekException e) {
            ui.printError(Messages.MESSAGE_ERROR_LOAD);
            taskList = new TaskList();
        }

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            try {
                handleUserInput(input);
            } catch (SnekException e) {
                ui.printError(e.getMessage());
            } finally {
                input = sc.nextLine();
            }
        }

        ui.printByeMessage();
        sc.close();
    }
}
