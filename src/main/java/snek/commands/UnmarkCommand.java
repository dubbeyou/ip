package snek.commands;

import snek.common.Messages;
import snek.data.exception.InvalidArgumentSnekException;
import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.storage.Storage;
import snek.ui.Ui;

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
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnekException {
        int index;
        try {
            index = Integer.valueOf(taskNumber);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_TASK);
        }
        if (!tasks.getIndex(index).isDone()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_UNMARK);
        }
        tasks.getIndex(index).unmarkAsDone();
        ui.print(String.format(Messages.MESSAGE_UNMARK_TASK, tasks.getIndex(index)));
        storage.overwrite(tasks.getTasks());
    }
}
