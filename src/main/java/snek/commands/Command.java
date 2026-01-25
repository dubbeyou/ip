package snek.commands;

import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.ui.Ui;
import snek.storage.Storage;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws SnekException;
    
    public boolean isExit() {
        return false;
    }
}
