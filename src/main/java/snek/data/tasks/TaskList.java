package snek.data.tasks;

import java.util.ArrayList;

import snek.common.Messages;
import snek.data.exception.InvalidArgumentSnekException;

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>();

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> add(Task task) {
        tasks.add(task);
        return tasks;
    }

    public Task getIndex(int index) throws InvalidArgumentSnekException {
        index = index - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_TASK);
        }
        return tasks.get(index);
    }

    public Task delete(int index) throws InvalidArgumentSnekException {
        index = index - 1;
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidArgumentSnekException(Messages.MESSAGE_INVALID_TASK);
        }
        return tasks.remove(index);
    }

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
