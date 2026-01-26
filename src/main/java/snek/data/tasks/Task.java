package snek.data.tasks;

public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsDone() {
        this.isDone = false;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public String getSaveString() {
        return type.getCode() + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[" + type.getCode() + "]" + "[" + getStatusIcon() + "] " + description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Task) {
            Task other = (Task) obj;
            return this.description.equals(other.description) && this.isDone == other.isDone && this.type == other.type;
        }
        return false;
    }
}
