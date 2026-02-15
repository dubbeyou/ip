package snek.commands;

import static snek.common.Messages.MESSAGE_FOUND_MATCHING_TASKS;
import static snek.common.Messages.MESSAGE_NO_MATCHING_TASKS;

import java.util.ArrayList;

import snek.data.exception.SnekException;
import snek.data.tasks.Task;
import snek.data.tasks.TaskList;
import snek.storage.Storage;

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
    public String execute(TaskList tasks, Storage storage) throws SnekException {
        assert tasks != null : "Task list should not be null.";
        assert storage != null : "Storage should not be null.";

        ArrayList<Task> matchedTasks = new ArrayList<>();
        for (Task task : tasks.getTasks()) {
            if (((task.getDescription()).toLowerCase()).contains(keyword.toLowerCase())) {
                matchedTasks.add(task);
            }
        }

        if (matchedTasks.isEmpty()) {
            return MESSAGE_NO_MATCHING_TASKS;
        } else {
            TaskList matchedTasksList = new TaskList(matchedTasks);
            return String.format(MESSAGE_FOUND_MATCHING_TASKS, matchedTasksList.getString());
        }
    }
}
