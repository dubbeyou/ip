package snek.commands;

import static snek.common.Messages.MESSAGE_BYE;

import snek.data.tasks.TaskList;
import snek.storage.Storage;

/**
 * Command to exit the Snek application.
 */
public class ByeCommand extends Command {
    @Override
    public String execute(TaskList tasks, Storage storage) {
        assert tasks != null : "Task list should not be null.";
        assert storage != null : "Storage should not be null.";

        return MESSAGE_BYE;
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
