package snek.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static snek.common.Messages.MESSAGE_ERROR_WRITE_PERMISSION;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import snek.data.exception.SnekException;
import snek.data.exception.StorageSnekException;
import snek.data.tasks.Task;
import snek.data.tasks.Todo;

public class StorageTest {
    private static final String TEST_FILEPATH = "data_test/test.txt";
    private Storage storage;

    @BeforeEach
    public void setUp() throws StorageSnekException {
        File file = new File(TEST_FILEPATH);
        if (file.exists()) {
            file.delete();
        }
        storage = new Storage(TEST_FILEPATH);
    }

    @AfterEach
    public void tearDown() {
        File file = new File(TEST_FILEPATH);
        if (file.exists()) {
            file.delete();
        }
        File parent = file.getParentFile();
        if (parent != null && parent.exists()) {
            parent.delete();
        }
    }

    @Test
    public void writeThenLoad_multipleTasks_success() throws SnekException {
        Task todo = new Todo("read book");
        Task todo2 = new Todo("play game");
        storage.write(todo);
        storage.write(todo2);

        ArrayList<Task> loadedTasks = storage.loadTasks();
        assertEquals(2, loadedTasks.size());
        assertEquals(todo.getSaveString(), loadedTasks.get(0).getSaveString());
        assertEquals(todo2.getSaveString(), loadedTasks.get(1).getSaveString());
    }

    @Test
    public void overwriteThenLoad_multipleTasks_success() throws SnekException {
        Task todo = new Todo("read book");
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(todo);
        storage.overwrite(taskList);
        ArrayList<Task> loadedTasks = storage.loadTasks();
        assertEquals(1, loadedTasks.size());
        assertEquals(todo.getSaveString(), loadedTasks.get(0).getSaveString());

        Task todo2 = new Todo("play game");
        Task todo3 = new Todo("do homework");
        taskList = new ArrayList<>();
        taskList.add(todo2);
        taskList.add(todo3);
        storage.overwrite(taskList);

        loadedTasks = storage.loadTasks();
        assertEquals(2, loadedTasks.size());
        assertEquals(todo2.getSaveString(), loadedTasks.get(0).getSaveString());
        assertEquals(todo3.getSaveString(), loadedTasks.get(1).getSaveString());
    }

    @Test
    public void loadTasks_readOnlyFile_success() throws SnekException {
        Task todo = new Todo("test task");
        storage.write(todo);

        File file = new File(TEST_FILEPATH);
        file.setReadOnly();

        try {
            // Try to read - should pass since read-only doesn't prevent reading
            ArrayList<Task> loadedTasks = storage.loadTasks();
            assertEquals(1, loadedTasks.size());
        } finally {
            file.setWritable(true);
        }
    }

    @Test
    public void write_readOnlyFile_throwsException() {
        File file = new File(TEST_FILEPATH);
        file.setReadOnly();

        try {
            Task todo = new Todo("test task");
            storage.write(todo);
        } catch (StorageSnekException e) {
            assertEquals(MESSAGE_ERROR_WRITE_PERMISSION, e.getMessage());
        } finally {
            file.setWritable(true);
        }
    }

    @Test
    public void overwrite_readOnlyFile_throwsException() {
        File file = new File(TEST_FILEPATH);
        file.setReadOnly();

        try {
            ArrayList<Task> taskList = new ArrayList<>();
            taskList.add(new Todo("test task"));
            storage.overwrite(taskList);
        } catch (StorageSnekException e) {
            assertEquals(MESSAGE_ERROR_WRITE_PERMISSION, e.getMessage());
        } finally {
            file.setWritable(true);
        }
    }

    @Test
    public void loadTasks_emptyFile_success() throws SnekException {
        ArrayList<Task> loadedTasks = storage.loadTasks();
        assertEquals(0, loadedTasks.size());
    }
}
