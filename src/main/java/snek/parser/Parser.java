package snek.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import snek.common.Messages;
import snek.data.exception.SnekException;
import snek.data.exception.InvalidArgumentSnekException;
import snek.data.exception.InvalidCommandSnekException;

import snek.commands.Command;
import snek.commands.CommandType;
import snek.commands.ListCommand;
import snek.commands.MarkCommand;
import snek.commands.UnmarkCommand;
import snek.commands.TodoCommand;
import snek.commands.DeadlineCommand;
import snek.commands.EventCommand;
import snek.commands.DeleteCommand;
import snek.commands.ByeCommand;

import snek.data.tasks.Task;
import snek.data.tasks.TaskType;
import snek.data.tasks.Todo;
import snek.data.tasks.Deadline;
import snek.data.tasks.Event;

/**
 * Parser class for parsing user input and file data.
 */
public class Parser {
    private static final DateTimeFormatter[] DATE_TIME_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-M-d H:m"),
            DateTimeFormatter.ofPattern("yyyy-M-d Hmm"),
            DateTimeFormatter.ofPattern("yyyy/M/d H:m"),
            DateTimeFormatter.ofPattern("yyyy/M/d Hmm"),
            DateTimeFormatter.ofPattern("d-M-yyyy H:m"),
            DateTimeFormatter.ofPattern("d-M-yyyy Hmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy H:m"),
            DateTimeFormatter.ofPattern("d/M/yyyy Hmm"),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME
    };

    private static final DateTimeFormatter[] DATE_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-M-d"),
            DateTimeFormatter.ofPattern("yyyy/M/d"),
            DateTimeFormatter.ofPattern("d-M-yyyy"),
            DateTimeFormatter.ofPattern("d/M/yyyy"),
            DateTimeFormatter.ISO_LOCAL_DATE
    };

    /**
     * Parses a date-time string into a LocalDateTime object.
     * Supports multiple date and date-time formats.
     *
     * @param input The date-time string to parse.
     * @return A LocalDateTime object if parsing is successful; null otherwise.
     */
    public static LocalDateTime parseDateTime(String input) {
        for (DateTimeFormatter formatter : DATE_TIME_FORMATS) {
            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException ignored) {}
        }

        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                return LocalDate.parse(input, formatter).atStartOfDay();
            } catch (DateTimeParseException ignored) {}
        }
        
        return null;
    }

    private static Command handleTodo(String input) throws SnekException {
        int todoLen = "todo".length();
        String description = input.substring(todoLen).trim();
        if (description.isEmpty()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_TODO);
        }
        return new TodoCommand(description);
    }

    private static Command handleDeadline(String input) throws SnekException {
        String byMarker = "/by";
        int byIdx = input.indexOf(byMarker);
        int deadlineLen = "deadline".length();

        if (byIdx == -1 || byIdx <= deadlineLen) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_DEADLINE_1);
        }

        String description = input.substring(deadlineLen, byIdx).trim();
        String by = input.substring(byIdx + byMarker.length()).trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_DEADLINE_2);
        }

        LocalDateTime byTime = parseDateTime(by);

        if (byTime != null) {
            return new DeadlineCommand(description, by, byTime);
        }
        return new DeadlineCommand(description, by);
    }

    private static Command handleEvent(String input) throws SnekException {
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

        LocalDateTime fromTime = parseDateTime(from);
        LocalDateTime toTime = parseDateTime(to);

        if (fromTime != null && toTime != null) {
            return new EventCommand(description, from, to, fromTime, toTime);
        }
        return new EventCommand(description, from, to);
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
        String[] args = line.trim().split("\\|");
        TaskType type;

        if (line == null || line.trim().isEmpty()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_FILE_FORMAT);
        }

        if (args.length < 3) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_FILE_FORMAT);
        }

        try {
            type = TaskType.fromCode(args[0].trim());
        } catch (InvalidCommandSnekException e) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_FILE_FORMAT);
        }

        String doneField = args[1].trim();
        
        if (!doneField.equals("0") && !doneField.equals("1")) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_FILE_FORMAT);
        }

        if (type == TaskType.DEADLINE && args.length < 4) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_FILE_FORMAT);
        }

        if (type == TaskType.EVENT && args.length < 5) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_FILE_FORMAT);
        }

        boolean isDone = doneField.equals("1");
        switch (type) {
        case TODO:
            Todo todo = new Todo(args[2].trim());
            if (isDone) {
                todo.markAsDone();
            }
            return todo;
        case DEADLINE:
            LocalDateTime byDateTime = parseDateTime(args[3].trim());
            Deadline deadline;
            if (byDateTime == null) {
                deadline = new Deadline(args[2].trim(), args[3].trim());
            } else {
                deadline = new Deadline(args[2].trim(), args[3].trim(), byDateTime);
            }
            if (isDone) {
                deadline.markAsDone();
            }
            return deadline;
        case EVENT:
            LocalDateTime fromDateTime = parseDateTime(args[3].trim());
            LocalDateTime toDateTime = parseDateTime(args[4].trim());
            Event event;
            if (fromDateTime == null || toDateTime == null) {
                event = new Event(args[2].trim(), args[3].trim(), args[4].trim());
            } else {
                event = new Event(args[2].trim(), args[3].trim(), args[4].trim(), fromDateTime, toDateTime);
            }
            if (isDone) {
                event.markAsDone();
            }
            return event;
        default:
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_FILE_FORMAT);
        }
    }
}
