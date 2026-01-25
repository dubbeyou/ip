import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    public static Task parseTaskFromFile(String line) throws InvalidArgumentSnekException, InvalidCommandSnekException {
        String[] args = line.split("[\\|]");
        TaskType type;
        type = TaskType.fromCode(args[0].trim());
        boolean isDone = args[1].trim().equals("1");
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
                event = new Event(args[2].trim(), args[3].trim(), fromDateTime, args[4].trim(), toDateTime);
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
