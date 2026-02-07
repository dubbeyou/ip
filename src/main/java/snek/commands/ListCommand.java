package snek.commands;

import snek.data.tasks.TaskList;
import snek.storage.Storage;

/**
 * Command to list all tasks in the Snek application.
 */
public class ListCommand extends Command {
    @Override
    public String execute(TaskList tasks, Storage storage) {
        assert tasks != null : "Task list should not be null.";
        assert storage != null : "Storage should not be null.";

        return tasks.getString();
    }
}
