public class Snek {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Snek(String filepath) {
        ui = new Ui();
        storage = new Storage(filepath);
        try {
            tasks = new TaskList(storage.loadTasks());
            ui.print(Messages.MESSAGE_SUCCESS_LOAD_EXISTING);
        } catch (SnekException e) {
            ui.printError(e.getMessage());
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.printHelloMessage();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (SnekException e) {
                ui.printError(e.getMessage());
            } 
        }
    }

    public static void main(String[] args) {
        new Snek("data/tasks.txt").run();
    }
}
