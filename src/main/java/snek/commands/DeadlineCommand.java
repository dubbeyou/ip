package snek.commands;

import static snek.common.Messages.MESSAGE_ADD_TASK;

import java.time.LocalDateTime;

import snek.data.exception.SnekException;
import snek.data.tasks.Deadline;
import snek.data.tasks.TaskList;
import snek.storage.Storage;
import snek.ui.Ui;

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
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnekException {
        if (byTime != null) {
            Deadline deadline = new Deadline(description, by, byTime);
            tasks.add(deadline);
            ui.print(String.format(MESSAGE_ADD_TASK, deadline, tasks.size()));
            storage.write(deadline);
            return;
        }
        Deadline deadline = new Deadline(description, by);
        tasks.add(deadline);
        ui.print(String.format(MESSAGE_ADD_TASK, deadline, tasks.size()));
        storage.write(deadline);
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
