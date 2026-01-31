package snek.commands;

import static snek.common.Messages.MESSAGE_INVALID_MARK;
import static snek.common.Messages.MESSAGE_INVALID_TASK;
import static snek.common.Messages.MESSAGE_MARK_TASK;

import snek.data.exception.InvalidArgumentSnekException;
import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.storage.Storage;
import snek.ui.Ui;

/**
 * Command to mark a task as done in the Snek application.
 */
public class MarkCommand extends Command {
    private final String taskNumber;

    /**
     * Constructs a MarkCommand with the given task number.
     *
     * @param taskNumber The task number to mark as done.
     */
    public MarkCommand(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws SnekException {
        int index;
        try {
            index = Integer.valueOf(taskNumber);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_TASK);
        }
        if (tasks.getIndex(index).isDone()) {
            throw new InvalidArgumentSnekException(MESSAGE_INVALID_MARK);
        }
        tasks.getIndex(index).markAsDone();
        storage.overwrite(tasks.getTasks());
        return String.format(MESSAGE_MARK_TASK, tasks.getIndex(index));
    }
}
