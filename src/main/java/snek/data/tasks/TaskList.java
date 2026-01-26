package snek.data.tasks;

import java.util.ArrayList;

import snek.common.Messages;
import snek.data.exception.InvalidArgumentSnekException;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks The list of tasks to initialise the TaskList with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Gets the list of tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Gets the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     * @return The updated list of tasks.
     */
    public ArrayList<Task> add(Task task) {
        tasks.add(task);
        return tasks;
    }

    /**
     * Gets the task at the specified index.
     *
     * @param index The index of the task to retrieve (1-based).
     * @return The task at the specified index.
     * @throws InvalidArgumentSnekException If the index is out of bounds.
     */
    public Task getIndex(int index) throws InvalidArgumentSnekException {
        index = index - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_TASK);
        }
        return tasks.get(index);
    }

    /**
     * Deletes the task at the specified index.
     *
     * @param index The index of the task to delete (1-based).
     * @return The deleted task.
     * @throws InvalidArgumentSnekException If the index is out of bounds.
     */
    public Task delete(int index) throws InvalidArgumentSnekException {
        index = index - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_TASK);
        }
        return tasks.remove(index);
    }

    /**
     * Returns a string representation of the task list.
     *
     * @return A string representing the task list.
     */
    public String getString() {
        String res = "";
        for (int i = 0; i < tasks.size(); i++) {
            res += (i + 1) + ". " + tasks.get(i);
            if (i != tasks.size() - 1) {
                res += "\n";
            }
        }
        return res;
    }
}
