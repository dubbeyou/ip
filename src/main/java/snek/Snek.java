package snek;

import snek.commands.Command;
import snek.common.Messages;
import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.parser.Parser;
import snek.storage.Storage;
import snek.ui.Ui;

public class Snek {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Snek(String filepath) {
        try {
            ui = new Ui();
            storage = new Storage(filepath);
            tasks = new TaskList(storage.loadTasks());
            ui.print(Messages.MESSAGE_SUCCESS_LOAD);
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
