package snek.commands;

import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.storage.Storage;

/**
 * Abstract class representing a command in the Snek application.
 */
public abstract class Command {
    /**
     * Executes the command using the given task list and storage.
     *
     * @param tasks The task list to operate on.
     * @param storage The storage to read from or write to.
     * @return A string response after executing the command.
     * @throws SnekException If an error occurs during command execution.
     */
    public abstract String execute(TaskList tasks, Storage storage) throws SnekException;

    /**
     * Indicates whether this command will exit the application.
     *
     * @return true if the command will exit the application, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
