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
        storage.overwrite(tasks.getTasks());
    }
    
}
