package snek.commands;

import snek.common.Messages;
import snek.data.exception.InvalidArgumentSnekException;
import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.storage.Storage;
import snek.ui.Ui;

public class MarkCommand extends Command {
    private final String taskNumber;

    public MarkCommand(String taskNumber) {
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
        if (tasks.getIndex(index).isDone()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_MARK);
        }
        tasks.getIndex(index).markAsDone();
        ui.print(String.format(Messages.MESSAGE_MARK_TASK, tasks.getIndex(index)));
        storage.overwrite(tasks.getTasks());
    }
}
