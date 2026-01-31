package snek.commands;

import static snek.common.Messages.MESSAGE_BYE;

import snek.data.tasks.TaskList;
import snek.storage.Storage;
import snek.ui.Ui;

/**
 * Command to exit the Snek application.
 */
public class ByeCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return MESSAGE_BYE;
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
