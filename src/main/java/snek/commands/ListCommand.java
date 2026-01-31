package snek.commands;

import snek.data.tasks.TaskList;
import snek.storage.Storage;

/**
 * Command to list all tasks in the Snek application.
 */
public class ListCommand extends Command {
    @Override
    public String execute(TaskList tasks, Storage storage) {
        return tasks.getString();
    }
}
