package snek.commands;

import snek.common.Messages;
import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.data.tasks.Todo;
import snek.storage.Storage;
import snek.ui.Ui;

/**
 * Command to add a todo task to the Snek application.
 */
public class TodoCommand extends Command {
    private final String description;

    /**
     * Constructs a TodoCommand with the given description.
     *
     * @param description The description of the todo task.
     */
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TodoCommand) {
            TodoCommand other = (TodoCommand) obj;
            return this.description.equals(other.description);
        }
        return false;
    }
}
