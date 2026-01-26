package snek.commands;

import snek.data.tasks.TaskList;
import snek.storage.Storage;
import snek.ui.Ui;

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
