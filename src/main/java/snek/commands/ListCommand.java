package snek.commands;

import snek.data.tasks.TaskList;
import snek.storage.Storage;
import snek.ui.Ui;

/**
 * Command to list all tasks in the Snek application.
 */
public class ListCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return tasks.getString();
    }
}
