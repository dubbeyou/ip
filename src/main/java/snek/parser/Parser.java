package snek.parser;

import static snek.common.Messages.MESSAGE_INVALID_DEADLINE_1;
import static snek.common.Messages.MESSAGE_INVALID_DEADLINE_2;
import static snek.common.Messages.MESSAGE_INVALID_EVENT_1;
import static snek.common.Messages.MESSAGE_INVALID_EVENT_2;
import static snek.common.Messages.MESSAGE_INVALID_FILE_FORMAT;
import static snek.common.Messages.MESSAGE_INVALID_FIND;
import static snek.common.Messages.MESSAGE_INVALID_TODO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import snek.commands.ByeCommand;
import snek.commands.Command;
import snek.commands.CommandType;
import snek.commands.DeadlineCommand;
import snek.commands.DeleteCommand;
import snek.commands.EventCommand;
import snek.commands.FindCommand;
import snek.commands.ListCommand;
import snek.commands.MarkCommand;
import snek.commands.TodoCommand;
import snek.commands.UnmarkCommand;
import snek.data.exception.InvalidArgumentSnekException;
import snek.data.exception.InvalidCommandSnekException;
import snek.data.exception.SnekException;
import snek.data.tasks.Deadline;
import snek.data.tasks.Event;
import snek.data.tasks.Task;
import snek.data.tasks.TaskType;
import snek.data.tasks.Todo;

/**
 * Parser class for parsing user input and file data.
 */
public class Parser {
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String FIND_COMMAND = "find";
    private static final String BY_MARKER = "/by";
    private static final String FROM_MARKER = "/from";
    private static final String TO_MARKER = "/to";
    private static final String FILE_DELIMITER = "\\|";
    private static final String DONE_STATUS = "1";
    private static final String UNDONE_STATUS = "0";

    private static final DateTimeFormatter[] DATE_TIME_FORMATS = { DateTimeFormatter.ofPattern("yyyy-M-d H:m"),
            DateTimeFormatter.ofPattern("yyyy-M-d Hmm"), DateTimeFormatter.ofPattern("yyyy/M/d H:m"),
            DateTimeFormatter.ofPattern("yyyy/M/d Hmm"), DateTimeFormatter.ofPattern("d-M-yyyy H:m"),
            DateTimeFormatter.ofPattern("d-M-yyyy Hmm"), DateTimeFormatter.ofPattern("d/M/yyyy H:m"),
            DateTimeFormatter.ofPattern("d/M/yyyy Hmm"), DateTimeFormatter.ISO_LOCAL_DATE_TIME };

    private static final DateTimeFormatter[] DATE_FORMATS = { DateTimeFormatter.ofPattern("yyyy-M-d"),
            DateTimeFormatter.ofPattern("yyyy/M/d"), DateTimeFormatter.ofPattern("d-M-yyyy"),
            DateTimeFormatter.ofPattern("d/M/yyyy"), DateTimeFormatter.ISO_LOCAL_DATE };

    /**
     * Parses a date-time string into a LocalDateTime object. Supports multiple
     * date and date-time formats.
     *
     * @param input The date-time string to parse.
     * @return A LocalDateTime object if parsing is successful; null otherwise.
     */
    public static LocalDateTime parseDateTime(String input) {
        for (DateTimeFormatter formatter : DATE_TIME_FORMATS) {
            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException ignored) {
                continue;
            }
        }

        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                return LocalDate.parse(input, formatter).atStartOfDay();
            } catch (DateTimeParseException ignored) {
                continue;
            }
        }

        return null;
    }

    private static Command handleTodo(String input) throws SnekException {
        String description = extractTodoDescription(input);
        validateNotEmpty(description, MESSAGE_INVALID_TODO);
        return new TodoCommand(description);
    }

    private static String extractTodoDescription(String input) {
        int todoLen = TODO_COMMAND.length();
        return input.substring(todoLen).trim();
    }

    private static void validateNotEmpty(String value, String errorMessage) throws SnekException {
        if (value.isEmpty()) {
            throw new InvalidArgumentSnekException(errorMessage);
        }
    }

    private static Command handleDeadline(String input) throws SnekException {
        int byIdx = findMarkerIndex(input, BY_MARKER);
        int deadlineLen = DEADLINE_COMMAND.length();

        validateDeadlineFormat(byIdx, deadlineLen);

        String description = input.substring(deadlineLen, byIdx).trim();
        String by = input.substring(byIdx + BY_MARKER.length()).trim();

        validateDeadlineComponents(description, by);

        return createDeadlineCommand(description, by);
    }

    private static int findMarkerIndex(String input, String marker) {
        return input.indexOf(marker);
    }

    private static void validateDeadlineFormat(int byIdx, int deadlineLen) throws SnekException {
        if (byIdx == -1 || byIdx <= deadlineLen) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_DEADLINE_1);
        }
    }

    private static void validateDeadlineComponents(String description, String by) throws SnekException {
        if (description.isEmpty() || by.isEmpty()) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_DEADLINE_2);
        }
    }

    private static Command createDeadlineCommand(String description, String by) {
        LocalDateTime byTime = parseDateTime(by);
        if (byTime != null) {
            return new DeadlineCommand(description, by, byTime);
        }
        return new DeadlineCommand(description, by);
    }

    private static Command handleEvent(String input) throws SnekException {
        int fromIdx = findMarkerIndex(input, FROM_MARKER);
        int toIdx = findMarkerIndex(input, TO_MARKER);
        int commandLen = EVENT_COMMAND.length();

        validateEventFormat(fromIdx, toIdx);

        String description = input.substring(commandLen, fromIdx).trim();
        String from = input.substring(fromIdx + FROM_MARKER.length(), toIdx).trim();
        String to = input.substring(toIdx + TO_MARKER.length()).trim();

        validateEventComponents(description, from, to);

        return createEventCommand(description, from, to);
    }

    private static void validateEventFormat(int fromIdx, int toIdx) throws SnekException {
        if (fromIdx == -1 || toIdx == -1 || fromIdx > toIdx) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_EVENT_1);
        }
    }

    private static void validateEventComponents(String description, String from, String to) throws SnekException {
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_EVENT_2);
        }
    }

    private static Command createEventCommand(String description, String from, String to) {
        LocalDateTime fromTime = parseDateTime(from);
        LocalDateTime toTime = parseDateTime(to);

        if (fromTime != null && toTime != null) {
            return new EventCommand(description, from, to, fromTime, toTime);
        }
        return new EventCommand(description, from, to);
    }

    private static Command handleFind(String input) throws SnekException {
        String keyword = extractFindKeyword(input);
        validateNotEmpty(keyword, MESSAGE_INVALID_FIND);
        return new FindCommand(keyword);
    }

    private static String extractFindKeyword(String input) {
        int findLen = FIND_COMMAND.length();
        return input.substring(findLen).trim();
    }

    /**
     * Parses a user input string into a Command object.
     *
     * @param input The user input string.
     * @return A Command object representing the parsed command.
     * @throws SnekException If the command is invalid or has invalid arguments.
     */
    public static Command parse(String input) throws SnekException {
        input = input.trim();
        String[] args = input.split("[\\s]");
        CommandType cmd = CommandType.from(args[0]);
        switch (cmd) {
        case LIST:
            return new ListCommand();
        case MARK:
            return new MarkCommand(args[1]);
        case UNMARK:
            return new UnmarkCommand(args[1]);
        case TODO:
            return handleTodo(input);
        case DEADLINE:
            return handleDeadline(input);
        case EVENT:
            return handleEvent(input);
        case DELETE:
            return new DeleteCommand(args[1]);
        case FIND:
            return handleFind(input);
        case BYE:
            return new ByeCommand();
        default:
            throw new InvalidCommandSnekException();
        }
    }

    /**
     * Parses a line from the storage file into a Task object.
     *
     * @param line The line from the storage file.
     * @return A Task object representing the parsed task.
     * @throws SnekException If the line has an invalid format.
     */
    public static Task parseTaskFromFile(String line) throws SnekException {
        validateLine(line);
        String[] args = line.trim().split(FILE_DELIMITER);
        validateMinimumFields(args);

        TaskType type = parseTaskType(args[0].trim());
        boolean isDone = parseDoneStatus(args[1].trim());
        validateFieldsForType(type, args);

        return createTaskFromArgs(type, args, isDone);
    }

    private static void validateLine(String line) throws SnekException {
        if (line == null || line.trim().isEmpty()) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_FILE_FORMAT);
        }
    }

    private static void validateMinimumFields(String[] args) throws SnekException {
        if (args.length < 3) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_FILE_FORMAT);
        }
    }

    private static TaskType parseTaskType(String typeCode) throws SnekException {
        try {
            return TaskType.fromCode(typeCode);
        } catch (InvalidCommandSnekException e) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_FILE_FORMAT);
        }
    }

    private static boolean parseDoneStatus(String doneField) throws SnekException {
        if (!doneField.equals(UNDONE_STATUS) && !doneField.equals(DONE_STATUS)) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_FILE_FORMAT);
        }
        return doneField.equals(DONE_STATUS);
    }

    private static void validateFieldsForType(TaskType type, String[] args) throws SnekException {
        if (type == TaskType.DEADLINE && args.length < 4) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_FILE_FORMAT);
        }
        if (type == TaskType.EVENT && args.length < 5) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_FILE_FORMAT);
        }
    }

    private static Task createTaskFromArgs(TaskType type, String[] args, boolean isDone) throws SnekException {
        switch (type) {
        case TODO:
            return createTodoFromFile(args[2].trim(), isDone);
        case DEADLINE:
            return createDeadlineFromFile(args[2].trim(), args[3].trim(), isDone);
        case EVENT:
            return createEventFromFile(args[2].trim(), args[3].trim(), args[4].trim(), isDone);
        default:
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_FILE_FORMAT);
        }
    }

    private static Task createTodoFromFile(String description, boolean isDone) {
        Todo todo = new Todo(description);
        if (isDone) {
            todo.markAsDone();
        }
        return todo;
    }

    private static Task createDeadlineFromFile(String description, String by, boolean isDone) {
        LocalDateTime byDateTime = parseDateTime(by);
        Deadline deadline;
        if (byDateTime == null) {
            deadline = new Deadline(description, by);
        } else {
            deadline = new Deadline(description, by, byDateTime);
        }
        if (isDone) {
            deadline.markAsDone();
        }
        return deadline;
    }

    private static Task createEventFromFile(String description, String from, String to, boolean isDone) {
        LocalDateTime fromDateTime = parseDateTime(from);
        LocalDateTime toDateTime = parseDateTime(to);
        Event event;
        if (fromDateTime == null || toDateTime == null) {
            event = new Event(description, from, to);
        } else {
            event = new Event(description, from, to, fromDateTime, toDateTime);
        }
        if (isDone) {
            event.markAsDone();
        }
        return event;
    }
}
