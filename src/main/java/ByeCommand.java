public class ByeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printByeMessage();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
