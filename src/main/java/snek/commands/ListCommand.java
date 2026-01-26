package snek.commands;

import snek.data.tasks.TaskList;
import snek.ui.Ui;
import snek.storage.Storage;

/**
 * Command to list all tasks in the Snek application.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.print(tasks.getString());
    }
}
