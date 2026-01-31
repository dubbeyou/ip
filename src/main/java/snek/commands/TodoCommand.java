package snek.commands;

import static snek.common.Messages.MESSAGE_ADD_TASK;

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
    public String execute(TaskList tasks, Ui ui, Storage storage) throws SnekException {
        Todo todo = new Todo(description);
        tasks.add(todo);
        storage.write(todo);
        return String.format(MESSAGE_ADD_TASK, todo, tasks.size());
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
