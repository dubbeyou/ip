package snek.commands;

import snek.common.Messages;
import snek.data.exception.InvalidArgumentSnekException;
import snek.data.exception.SnekException;
import snek.data.tasks.Task;
import snek.data.tasks.TaskList;
import snek.storage.Storage;
import snek.ui.Ui;

/**
 * Command to delete a task from the Snek application.
 */
public class DeleteCommand extends Command {
    private final String taskNumber;

    /**
     * Constructs a DeleteCommand with the given task number.
     *
     * @param taskNumber The task number to delete.
     */
    public DeleteCommand(String taskNumber) {
        this.taskNumber = taskNumber;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnekException {
        int index;
        try {
            index = Integer.valueOf(taskNumber);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_TASK);
        }
        Task removedTask = tasks.delete(index);
        ui.print(String.format(Messages.MESSAGE_DELETE_TASK, removedTask, tasks.size()));
        storage.overwrite(tasks.getTasks());
    }
}
