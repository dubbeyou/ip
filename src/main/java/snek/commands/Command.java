package snek.commands;

import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.storage.Storage;
import snek.ui.Ui;

/**
 * Abstract class representing a command in the Snek application.
 */
public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws SnekException;

    /**
     * Indicates whether this command will exit the application.
     *
     * @return true if the command will exit the application, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
