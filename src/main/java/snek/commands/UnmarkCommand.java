public class UnmarkCommand extends Command {
    private final String taskNumber;

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
