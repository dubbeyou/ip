package snek.commands;

import static snek.common.Messages.MESSAGE_DELETE_TASK;
import static snek.common.Messages.MESSAGE_INVALID_TASK;

import snek.data.exception.InvalidArgumentSnekException;
import snek.data.exception.SnekException;
import snek.data.tasks.Task;
import snek.data.tasks.TaskList;
import snek.storage.Storage;

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
    public String execute(TaskList tasks, Storage storage) throws SnekException {
        int index;
        try {
            index = Integer.valueOf(taskNumber);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_TASK);
        }
        Task removedTask = tasks.delete(index);
        storage.overwrite(tasks.getTasks());
        return String.format(MESSAGE_DELETE_TASK, removedTask, tasks.size());
    }
}
