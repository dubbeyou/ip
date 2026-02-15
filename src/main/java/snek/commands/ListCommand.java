package snek.commands;

import static snek.common.Messages.MESSAGE_EMPTY_LIST;

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

        if (tasks.size() == 0) {
            return MESSAGE_EMPTY_LIST;
        }

        return tasks.getString();
    }
}
