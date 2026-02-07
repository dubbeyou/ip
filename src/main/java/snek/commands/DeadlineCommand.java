package snek.commands;

import static snek.common.Messages.MESSAGE_ADD_TASK;

import java.time.LocalDateTime;

import snek.data.exception.SnekException;
import snek.data.tasks.Deadline;
import snek.data.tasks.TaskList;
import snek.storage.Storage;

/**
 * Command to add a deadline task to the Snek application.
 */
public class DeadlineCommand extends Command {
    private final String description;
    private final String by;
    private final LocalDateTime byTime;

    /**
     * Constructs a DeadlineCommand with the given description and by.
     *
     * @param description The description of the deadline task.
     * @param by The due time or date of the deadline.
     */
    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
        this.byTime = null;
    }

    /**
     * Constructs a DeadlineCommand with the given description, by, and byTime.
     *
     * @param description The description of the deadline task.
     * @param by The due time or date of the deadline.
     * @param byTime The due LocalDateTime of the deadline.
     */
    public DeadlineCommand(String description, String by, LocalDateTime byTime) {
        this.description = description;
        this.by = by;
        this.byTime = byTime;
    }

    @Override
    public String execute(TaskList tasks, Storage storage) throws SnekException {
        assert tasks != null : "Task list should not be null.";
        assert storage != null : "Storage should not be null.";

        if (byTime != null) {
            Deadline deadline = new Deadline(description, by, byTime);
            tasks.add(deadline);
            storage.write(deadline);
            return String.format(MESSAGE_ADD_TASK, deadline, tasks.size());
        }
        Deadline deadline = new Deadline(description, by);
        tasks.add(deadline);
        storage.write(deadline);
        return String.format(MESSAGE_ADD_TASK, deadline, tasks.size());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DeadlineCommand) {
            if (this.byTime == null) {
                DeadlineCommand other = (DeadlineCommand) obj;
                return this.description.equals(other.description) && this.by.equals(other.by) && other.byTime == null;
            }
            DeadlineCommand other = (DeadlineCommand) obj;
            return this.description.equals(other.description) && this.by.equals(other.by)
                                            && this.byTime.equals(other.byTime);
        }
        return false;
    }
}
