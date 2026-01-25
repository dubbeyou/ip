package snek.commands;

import snek.data.tasks.TaskList;
import snek.ui.Ui;
import snek.storage.Storage;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.print(tasks.getString());
    }
}
