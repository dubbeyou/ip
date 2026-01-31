package snek;

import static snek.common.Messages.MESSAGE_SUCCESS_LOAD;

import snek.commands.Command;
import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.parser.Parser;
import snek.storage.Storage;
import snek.ui.Ui;

/**
 * Main class for the Snek application.
 */
public class Snek {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Snek application with the specified file path for storage.
     *
     * @param filepath The file path to load and save tasks.
     */
    public Snek(String filepath) {
        try {
            ui = new Ui();
            storage = new Storage(filepath);
            tasks = new TaskList(storage.loadTasks());
            ui.print(MESSAGE_SUCCESS_LOAD);
        } catch (SnekException e) {
            ui.printError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main loop of the Snek application.
     */
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

    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            return c.execute(tasks, ui, storage);
        } catch (SnekException e) {
            return e.getMessage();
        }
    }

    /**
     * The main method to start the Snek application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Snek("data/tasks.txt").run();
    }
}
