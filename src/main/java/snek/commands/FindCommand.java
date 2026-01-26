package snek.commands;

import java.util.ArrayList;

import snek.common.Messages;
import snek.data.exception.SnekException;
import snek.data.tasks.Task;
import snek.data.tasks.TaskList;
import snek.storage.Storage;
import snek.ui.Ui;

/**
 * Command to find tasks containing a specific keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     * 
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnekException {
        ArrayList<Task> matchedTasks = new ArrayList<>();
        for (Task task : tasks.getTasks()) {
            if (task.getDescription().contains(keyword)) {
                matchedTasks.add(task);
            }
        }

        if (matchedTasks.isEmpty()) {
            ui.print(Messages.MESSAGE_NO_MATCHING_TASKS);
        } else {
            TaskList matchedTasksList = new TaskList(matchedTasks);
            ui.print(String.format(Messages.MESSAGE_FOUND_MATCHING_TASKS, matchedTasksList.getString()));
        }
    }
}
