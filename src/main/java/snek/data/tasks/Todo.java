package snek.data.tasks;

/**
 * Represents a Todo task.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the given description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description, TaskType.TODO);
    }
}