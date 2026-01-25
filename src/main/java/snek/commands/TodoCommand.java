package snek.commands;

import snek.common.Messages;
import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.ui.Ui;
import snek.storage.Storage;
import snek.data.tasks.Todo;

public class TodoCommand extends Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnekException {
        Todo todo = new Todo(description);
        tasks.add(todo);
        ui.print(String.format(Messages.MESSAGE_ADD_TASK, todo, tasks.size()));
        storage.write(todo);
    }
}
