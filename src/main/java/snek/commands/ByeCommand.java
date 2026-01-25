package snek.commands;

import snek.data.tasks.TaskList;
import snek.ui.Ui;
import snek.storage.Storage;

public class ByeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printByeMessage();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
