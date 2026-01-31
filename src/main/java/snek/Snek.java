package snek;

import snek.commands.Command;
import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.storage.Storage;

/**
 * Main class for the Snek application.
 */
public class Snek {
    private Storage storage;
    private TaskList tasks;

    /**
     * Constructs a Snek application with the specified file path for storage.
     *
     * @param filepath The file path to load and save tasks.
     */
    public Snek(String filepath) {
        try {
            storage = new Storage(filepath);
            tasks = new TaskList(storage.loadTasks());
        } catch (SnekException e) {
            tasks = new TaskList();
        }
    }

    /**
     * Gets the response from Snek for a given command.
     *
     * @param command The command to be executed.
     * @return The response string from Snek.
     */
    public String getResponse(Command command) {
        try {
            String response = command.execute(tasks, storage);
            return response;
        } catch (SnekException e) {
            return e.getMessage();
        }
    }
}
