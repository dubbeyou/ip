package snek.commands;

import static snek.common.Messages.MESSAGE_INVALID_TASK;
import static snek.common.Messages.MESSAGE_INVALID_UNMARK;
import static snek.common.Messages.MESSAGE_UNMARK_TASK;

import snek.data.exception.InvalidArgumentSnekException;
import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.storage.Storage;

/**
 * Command to unmark a task as done in the Snek application.
 */
public class UnmarkCommand extends Command {
    private final String taskNumber;

    /**
     * Constructs an UnmarkCommand with the given task number.
     *
     * @param taskNumber The task number to unmark as done.
     */
    public UnmarkCommand(String taskNumber) {
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
        if (!tasks.getIndex(index).isDone()) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_UNMARK);
        }
        tasks.getIndex(index).unmarkAsDone();
        storage.overwrite(tasks.getTasks());
        return String.format(MESSAGE_UNMARK_TASK, tasks.getIndex(index));
    }
}
