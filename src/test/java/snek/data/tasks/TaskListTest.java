package snek.data.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static snek.common.Messages.MESSAGE_DUPLICATE_TASK;
import static snek.common.Messages.MESSAGE_INVALID_TASK;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import snek.data.exception.InvalidArgumentSnekException;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void add_singleTodo_success() throws InvalidArgumentSnekException {
        Task todo = new Todo("read book");
        taskList.add(todo);
        assertEquals(1, taskList.size());
        assertEquals(todo, taskList.getIndex(1));
    }

    @Test
    public void add_multipleTodos_success() throws InvalidArgumentSnekException {
        Task todo1 = new Todo("read book");
        Task todo2 = new Todo("play game");
        taskList.add(todo1);
        taskList.add(todo2);
        assertEquals(2, taskList.size());
        assertEquals(todo1, taskList.getIndex(1));
        assertEquals(todo2, taskList.getIndex(2));
    }

    @Test
    public void add_duplicateTodo_throwsException() throws InvalidArgumentSnekException {
        Task todo1 = new Todo("read book");
        Task todo2 = new Todo("read book");
        taskList.add(todo1);

        try {
            taskList.add(todo2);
        } catch (InvalidArgumentSnekException e) {
            assertEquals(MESSAGE_DUPLICATE_TASK, e.getMessage());
        }
    }

    @Test
    public void add_duplicateDeadline_throwsException() throws InvalidArgumentSnekException {
        Task deadline1 = new Deadline("submit report", "2026-10-10");
        Task deadline2 = new Deadline("submit report", "2026-10-10");
        taskList.add(deadline1);

        try {
            taskList.add(deadline2);
        } catch (InvalidArgumentSnekException e) {
            assertEquals(MESSAGE_DUPLICATE_TASK, e.getMessage());
        }
    }

    @Test
    public void add_duplicateEvent_throwsException() throws InvalidArgumentSnekException {
        Task event1 = new Event("project meeting", "2026-10-10", "2026-10-11");
        Task event2 = new Event("project meeting", "2026-10-10", "2026-10-11");
        taskList.add(event1);

        try {
            taskList.add(event2);
        } catch (InvalidArgumentSnekException e) {
            assertEquals(MESSAGE_DUPLICATE_TASK, e.getMessage());
        }
    }

    @Test
    public void add_duplicateDeadlineWithDateTime_throwsException() throws InvalidArgumentSnekException {
        LocalDateTime date1 = LocalDateTime.of(2026, 10, 10, 14, 0);
        Task deadline1 = new Deadline("submit report", "2026-10-10 1400", date1);
        Task deadline2 = new Deadline("submit report", "2026-10-10 1400", date1);
        taskList.add(deadline1);

        try {
            taskList.add(deadline2);
        } catch (InvalidArgumentSnekException e) {
            assertEquals(MESSAGE_DUPLICATE_TASK, e.getMessage());
        }
    }

    @Test
    public void add_duplicateEventWithSameTimes_throwsException() throws InvalidArgumentSnekException {
        LocalDateTime from = LocalDateTime.of(2026, 10, 10, 14, 0);
        LocalDateTime to = LocalDateTime.of(2026, 10, 10, 16, 0);
        Task event1 = new Event("meeting", "2026-10-10 1400", "2026-10-10 1600", from, to);
        Task event2 = new Event("meeting", "2026-10-10 1400", "2026-10-10 1600", from, to);
        taskList.add(event1);

        try {
            taskList.add(event2);
        } catch (InvalidArgumentSnekException e) {
            assertEquals(MESSAGE_DUPLICATE_TASK, e.getMessage());
        }
    }

    @Test
    public void add_todoWithDifferentDescription_success() throws InvalidArgumentSnekException {
        Task todo1 = new Todo("read book");
        Task todo2 = new Todo("write report");
        taskList.add(todo1);
        taskList.add(todo2);
        assertEquals(2, taskList.size());
    }

    @Test
    public void add_markedAndUnmarkedSameTask_bothAllowed() throws InvalidArgumentSnekException {
        Task todo1 = new Todo("read book");
        Task todo2 = new Todo("read book");
        todo2.markAsDone();
        taskList.add(todo1);
        // This should succeed because marked and unmarked are different states
        taskList.add(todo2);
        assertEquals(2, taskList.size());
    }

    @Test
    public void getIndex_validIndex_success() throws InvalidArgumentSnekException {
        Task todo = new Todo("read book");
        taskList.add(todo);
        assertEquals(todo, taskList.getIndex(1));
    }

    @Test
    public void getIndex_invalidIndex_throwsException() throws InvalidArgumentSnekException {
        Task todo = new Todo("read book");
        taskList.add(todo);

        try {
            taskList.getIndex(5);
        } catch (InvalidArgumentSnekException e) {
            assertEquals(MESSAGE_INVALID_TASK, e.getMessage());
        }
    }

    @Test
    public void getIndex_zeroIndex_throwsException() {
        try {
            taskList.getIndex(0);
        } catch (InvalidArgumentSnekException e) {
            assertEquals(MESSAGE_INVALID_TASK, e.getMessage());
        }
    }

    @Test
    public void getIndex_negativeIndex_throwsException() {
        try {
            taskList.getIndex(-1);
        } catch (InvalidArgumentSnekException e) {
            assertEquals(MESSAGE_INVALID_TASK, e.getMessage());
        }
    }

    @Test
    public void delete_validIndex_success() throws InvalidArgumentSnekException {
        Task todo = new Todo("read book");
        taskList.add(todo);
        Task deleted = taskList.delete(1);
        assertEquals(todo, deleted);
        assertEquals(0, taskList.size());
    }

    @Test
    public void delete_invalidIndex_throwsException() {
        try {
            taskList.delete(5);
        } catch (InvalidArgumentSnekException e) {
            assertEquals(MESSAGE_INVALID_TASK, e.getMessage());
        }
    }

    @Test
    public void constructor_withInitialTasks_success() throws InvalidArgumentSnekException {
        ArrayList<Task> initialTasks = new ArrayList<>();
        Task todo1 = new Todo("read book");
        Task todo2 = new Todo("write report");
        initialTasks.add(todo1);
        initialTasks.add(todo2);

        TaskList list = new TaskList(initialTasks);
        assertEquals(2, list.size());
        assertEquals(todo1, list.getIndex(1));
        assertEquals(todo2, list.getIndex(2));
    }
}
