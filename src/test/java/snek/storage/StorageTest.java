package snek.storage;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import snek.data.exception.SnekException;
import snek.data.exception.StorageSnekException;
import snek.data.tasks.Task;
import snek.data.tasks.Todo;

public class StorageTest {
    private static final String TEST_FILEPATH = "data_test/test.txt";
    private Storage storage;

    @BeforeEach
    void setUp() throws StorageSnekException {
        File file = new File(TEST_FILEPATH);
        if (file.exists()) {
            file.delete();
        }
        storage = new Storage(TEST_FILEPATH);
    }

    @AfterEach
    void tearDown() {
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
    void writeThenLoad_multipleTasks_success() throws SnekException {
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
    void overwriteThenLoad_multipleTasks_success() throws SnekException {
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
}
