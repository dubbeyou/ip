package snek.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static snek.common.Messages.MESSAGE_INVALID_COMMAND;
import static snek.common.Messages.MESSAGE_INVALID_DEADLINE_1;
import static snek.common.Messages.MESSAGE_INVALID_DEADLINE_2;
import static snek.common.Messages.MESSAGE_INVALID_EVENT_1;
import static snek.common.Messages.MESSAGE_INVALID_EVENT_2;
import static snek.common.Messages.MESSAGE_INVALID_FILE_FORMAT;
import static snek.common.Messages.MESSAGE_INVALID_TODO;
import static snek.common.Messages.MESSAGE_INVALID_VIEW;
import static snek.common.Messages.MESSAGE_INVALID_VIEW_DATE;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import snek.commands.Command;
import snek.commands.DeadlineCommand;
import snek.commands.EventCommand;
import snek.commands.TodoCommand;
import snek.commands.ViewCommand;
import snek.data.exception.SnekException;
import snek.data.tasks.Deadline;
import snek.data.tasks.Event;
import snek.data.tasks.Task;
import snek.data.tasks.Todo;

public class ParserTest {
    @Test
    public void parseDateTime_variousFormats_success() {
        String[] dateTimeInputs = { "2026-1-25 23:30", "2026-1-25 2330", "2026/1/25 23:30", "2026/1/25 2330",
                "25-1-2026 23:30", "25-1-2026 2330", "25/1/2026 23:30", "25/1/2026 2330", "2026-01-25T23:30:00" };

        for (String input : dateTimeInputs) {
            assertEquals(2026, Parser.parseDateTime(input).getYear());
            assertEquals(1, Parser.parseDateTime(input).getMonthValue());
            assertEquals(25, Parser.parseDateTime(input).getDayOfMonth());
            assertEquals(23, Parser.parseDateTime(input).getHour());
            assertEquals(30, Parser.parseDateTime(input).getMinute());
        }

        String[] dateOnlyInputs = { "2025-12-1", "2025/12/1", "1-12-2025", "1/12/2025", "2025-12-01" };

        for (String input : dateOnlyInputs) {
            assertEquals(2025, Parser.parseDateTime(input).getYear());
            assertEquals(12, Parser.parseDateTime(input).getMonthValue());
            assertEquals(1, Parser.parseDateTime(input).getDayOfMonth());
            assertEquals(0, Parser.parseDateTime(input).getHour());
            assertEquals(0, Parser.parseDateTime(input).getMinute());
        }
    }

    @Test
    public void parse_emptyInput_throwsException() {
        String input = "    ";
        try {
            Parser.parse(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_COMMAND, e.getMessage());
        }
    }

    @Test
    public void parse_invalidCommand_throwsException() {
        String input = "unknowncommand arg1 arg2";
        try {
            Parser.parse(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_COMMAND, e.getMessage());
        }
    }

    @Test
    public void parse_validTodo_returnsTodo() throws SnekException {
        String input = "todo read book";
        Command expected = new TodoCommand("read book");
        Command actual = Parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_validTodoWithExtraSpaces_returnsTodo() throws SnekException {
        String input = "   todo    read book   ";
        Command expected = new TodoCommand("read book");
        Command actual = Parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_invalidTodo_throwsException() {
        String input = "todo    ";
        try {
            Parser.parse(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_TODO, e.getMessage());
        }
    }

    @Test
    public void parse_validDeadlineWithStringDateTime_returnsDeadline() throws SnekException {
        String input = "deadline return book /by sunday";
        Command expected = new DeadlineCommand("return book", "sunday");
        Command actual = Parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_validDeadlineWithDateTime_returnsDeadlineWithDateTime() throws SnekException {
        String input = "deadline return book /by 2/2/2026 1800";
        Command expected = new DeadlineCommand("return book", "2/2/2026 1800", LocalDateTime.of(2026, 2, 2, 18, 00));
        Command actual = Parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_validDeadlineWithExtraSpaces_returnsDeadline() throws SnekException {
        String input = "   deadline    return book    /by    sunday   ";
        Command expected = new DeadlineCommand("return book", "sunday");
        Command actual = Parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_invalidDeadlineMissingBy_throwsException() {
        String input = "deadline return book";
        try {
            Parser.parse(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_DEADLINE_1, e.getMessage());
        }
    }

    @Test
    public void parse_invalidDeadlineMissingDescription_throwsException() {
        String input = "deadline /by sunday";
        try {
            Parser.parse(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_DEADLINE_2, e.getMessage());
        }
    }

    @Test
    public void parse_invalidDeadlineMissingTime_throwsException() {
        String input = "deadline return book /by   ";
        try {
            Parser.parse(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_DEADLINE_2, e.getMessage());
        }
    }

    @Test
    public void parse_validEventWithStringDateTime_returnsEvent() throws SnekException {
        String input = "event project meeting /from monday /to tuesday";
        Command expected = new EventCommand("project meeting", "monday", "tuesday");
        Command actual = Parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_validEventWithDateTime_returnsEventWithDateTime() throws SnekException {
        String input = "event project meeting /from 2/2/2026 1400 /to 2/2/2026 1600";
        Command expected = new EventCommand("project meeting", "2/2/2026 1400", "2/2/2026 1600",
                                        LocalDateTime.of(2026, 2, 2, 14, 00), LocalDateTime.of(2026, 2, 2, 16, 00));
        Command actual = Parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_validEventWithExtraSpaces_returnsEvent() throws SnekException {
        String input = "   event    project meeting    /from    monday    /to    tuesday   ";
        Command expected = new EventCommand("project meeting", "monday", "tuesday");
        Command actual = Parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_invalidEventMissingFrom_throwsException() {
        String input = "event project meeting /to tuesday";
        try {
            Parser.parse(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_EVENT_1, e.getMessage());
        }
    }

    @Test
    public void parse_invalidEventMissingTo_throwsException() {
        String input = "event project meeting /from monday";
        try {
            Parser.parse(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_EVENT_1, e.getMessage());
        }
    }

    @Test
    public void parse_invalidEventMissingDescription_throwsException() {
        String input = "event /from monday /to tuesday";
        try {
            Parser.parse(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_EVENT_2, e.getMessage());
        }
    }

    @Test
    public void parse_invalidEventMissingFromTime_throwsException() {
        String input = "event project meeting /from   /to tuesday";
        try {
            Parser.parse(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_EVENT_2, e.getMessage());
        }
    }

    @Test
    public void parse_invalidEventMissingToTime_throwsException() {
        String input = "event project meeting /from monday /to   ";
        try {
            Parser.parse(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_EVENT_2, e.getMessage());
        }
    }

    @Test
    public void parse_validViewDate_returnsViewCommand() throws SnekException {
        String input = "view 2026-2-7";
        Command expected = new ViewCommand(LocalDateTime.of(2026, 2, 7, 0, 0));
        Command actual = Parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_invalidViewMissingDate_throwsException() {
        String input = "view   ";
        try {
            Parser.parse(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_VIEW, e.getMessage());
        }
    }

    @Test
    public void parse_invalidViewDate_throwsException() {
        String input = "view someday";
        try {
            Parser.parse(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_VIEW_DATE, e.getMessage());
        }
    }

    @Test
    public void parseTaskFromFile_validTodoUnmarked_returnsTodo() throws SnekException {
        String input = "T | 0 | read book";
        Task expected = new Todo("read book");
        Task actual = Parser.parseTaskFromFile(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTaskFromFile_validTodoMarked_returnsTodo() throws SnekException {
        String input = "T | 1 | read book";
        Task expected = new Todo("read book");
        expected.markAsDone();
        Task actual = Parser.parseTaskFromFile(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTaskFromFile_validDeadlineUnmarkedWithStringDateTime_returnsDeadline() throws SnekException {
        String input = "D | 0 | return book | sunday";
        Task expected = new Deadline("return book", "sunday");
        Task actual = Parser.parseTaskFromFile(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTaskFromFile_validDeadlineMarkedWithStringDateTime_returnsDeadline() throws SnekException {
        String input = "D | 1 | return book | sunday";
        Task expected = new Deadline("return book", "sunday");
        expected.markAsDone();
        Task actual = Parser.parseTaskFromFile(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTaskFromFile_validDeadlineUnmarkedWithDateTime_returnsDeadlineWithDateTime() throws SnekException {
        String input = "D | 0 | return book | 31/1/2026";
        Task expected = new Deadline("return book", "31/1/2026", LocalDateTime.of(2026, 1, 31, 00, 00));
        Task actual = Parser.parseTaskFromFile(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTaskFromFile_validDeadlineMarkedWithDateTime_returnsDeadlineWithDateTime() throws SnekException {
        String input = "D | 1 | return book | 31/1/2026";
        Task expected = new Deadline("return book", "31/1/2026", LocalDateTime.of(2026, 1, 31, 00, 00));
        expected.markAsDone();
        Task actual = Parser.parseTaskFromFile(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTaskFromFile_validEventUnmarkedWithStringDateTime_returnsEvent() throws SnekException {
        String input = "E | 0 | project meeting | monday | tuesday";
        Task expected = new Event("project meeting", "monday", "tuesday");
        Task actual = Parser.parseTaskFromFile(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTaskFromFile_validEventMarkedWithStringDateTime_returnsEvent() throws SnekException {
        String input = "E | 1 | project meeting | monday | tuesday";
        Task expected = new Event("project meeting", "monday", "tuesday");
        expected.markAsDone();
        Task actual = Parser.parseTaskFromFile(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTaskFromFile_validEventUnmarkedWithDateTime_returnsEventWithDateTime() throws SnekException {
        String input = "E | 0 | project meeting | 2/2/2026 1400 | 2/2/2026 1600";
        Task expected = new Event("project meeting", "2/2/2026 1400", "2/2/2026 1600",
                                        LocalDateTime.of(2026, 2, 2, 14, 00), LocalDateTime.of(2026, 2, 2, 16, 00));
        Task actual = Parser.parseTaskFromFile(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTaskFromFile_validEventMarkedWithDateTime_returnsEventWithDateTime() throws SnekException {
        String input = "E | 1 | project meeting | 2/2/2026 1400 | 2/2/2026 1600";
        Task expected = new Event("project meeting", "2/2/2026 1400", "2/2/2026 1600",
                                        LocalDateTime.of(2026, 2, 2, 14, 00), LocalDateTime.of(2026, 2, 2, 16, 00));
        expected.markAsDone();
        Task actual = Parser.parseTaskFromFile(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTaskFromFile_invalidTaskType_throwsException() {
        String input = "X | 0 | some task";
        try {
            Parser.parseTaskFromFile(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_FILE_FORMAT, e.getMessage());
        }
    }

    @Test
    public void parseTaskFromFile_invalidFormat_throwsException() {
        String input = "This is an invalid format";
        try {
            Parser.parseTaskFromFile(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_FILE_FORMAT, e.getMessage());
        }
    }

    @Test
    public void parseTaskFromFile_incompleteData_throwsException() {
        String input = "T | 1";
        try {
            Parser.parseTaskFromFile(input);
        } catch (SnekException e) {
            assertEquals(MESSAGE_INVALID_FILE_FORMAT, e.getMessage());
        }
    }
}
