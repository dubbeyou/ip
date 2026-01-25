package snek.commands;

import java.time.LocalDateTime;

import snek.common.Messages;
import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.ui.Ui;
import snek.storage.Storage;
import snek.data.tasks.Deadline;

public class DeadlineCommand extends Command {
    private final String description;
    private final String by;
    private final LocalDateTime byTime;

    public DeadlineCommand(String description, String by, LocalDateTime byTime) {
        this.description = description;
        this.by = by;
        this.byTime = byTime;
    }

    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
        this.byTime = null;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnekException {
        if (byTime != null) {
            Deadline deadline = new Deadline(description, by, byTime);
            tasks.add(deadline);
            ui.print(String.format(Messages.MESSAGE_ADD_TASK, deadline, tasks.size()));
            storage.write(deadline);
            return;
        }
        Deadline deadline = new Deadline(description, by);
        tasks.add(deadline);
        ui.print(String.format(Messages.MESSAGE_ADD_TASK, deadline, tasks.size()));
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
