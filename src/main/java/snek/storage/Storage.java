package snek.storage;

import static snek.common.Messages.MESSAGE_ERROR_LOAD;
import static snek.common.Messages.MESSAGE_ERROR_WRITE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import snek.data.exception.SnekException;
import snek.data.exception.StorageSnekException;
import snek.data.tasks.Task;
import snek.parser.Parser;

/**
 * Storage class for loading and saving tasks to a file.
 */
public class Storage {
    private final File file;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filepath The file path to load and save tasks.
     * @throws StorageSnekException If there is an error creating the storage
     * file.
     */
    public Storage(String filepath) throws StorageSnekException {
        this.file = new File(filepath);
        createFile();
    }

    private void createFile() throws StorageSnekException {
        createParentDir();
        createNewFile();
    }

    private void createParentDir() {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }

    private void createNewFile() throws StorageSnekException {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageSnekException(MESSAGE_ERROR_LOAD);
        }

        assert file.exists() : "Storage file should exist after creation.";
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws SnekException If there is an error reading the file or parsing
     * tasks.
     */
    public ArrayList<Task> loadTasks() throws SnekException {
        assert file.exists() : "Storage file should exist before loading tasks.";

        ArrayList<Task> taskList = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            readAllTasks(scanner, taskList);
        } catch (FileNotFoundException ignore) {
            return new ArrayList<>();
        }
        return taskList;
    }

    private void readAllTasks(Scanner scanner, ArrayList<Task> taskList) throws SnekException {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Task task = Parser.parseTaskFromFile(line);
            taskList.add(task);
        }
    }

    /**
     * Appends a task to the storage file.
     *
     * @param task The task to write to the file.
     * @throws StorageSnekException If there is an error writing to the file.
     */
    public void write(Task task) throws StorageSnekException {
        assert file.exists() : "Storage file should exist before writing tasks.";
        assert task != null : "Task to write should not be null.";

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(task.getSaveString() + "\n");
        } catch (IOException e) {
            throw new StorageSnekException(MESSAGE_ERROR_WRITE);
        }
    }

    /**
     * Overwrites the storage file with the given list of tasks.
     *
     * @param taskList The list of tasks to write to the file.
     * @throws StorageSnekException If there is an error writing to the file.
     */
    public void overwrite(ArrayList<Task> taskList) throws StorageSnekException {
        assert file.exists() : "Storage file should exist before overwriting tasks.";
        assert taskList != null : "Task list to overwrite should not be null.";

        try (FileWriter fw = new FileWriter(file, false)) {
            writeAllTasks(fw, taskList);
        } catch (IOException e) {
            throw new StorageSnekException(MESSAGE_ERROR_WRITE);
        }
    }

    private void writeAllTasks(FileWriter fw, ArrayList<Task> taskList) throws IOException {
        for (Task task : taskList) {
            fw.write(task.getSaveString() + "\n");
        }
    }
}
