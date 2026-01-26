package snek.commands;

import java.time.LocalDateTime;

import snek.common.Messages;
import snek.data.exception.SnekException;
import snek.data.tasks.TaskList;
import snek.ui.Ui;
import snek.storage.Storage;
import snek.data.tasks.Event;

/**
 * Command to add an event task to the Snek application.
 */
public class EventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;
    private final LocalDateTime fromTime;
    private final LocalDateTime toTime;

    /**
     * Constructs an EventCommand with the given description, from, and to.
     *
     * @param description The description of the event task.
     * @param from The starting time or date of the event.
     * @param to The ending time or date of the event.
     */
    public EventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
        this.fromTime = null;
        this.toTime = null;
    }

    /**
     * Constructs an EventCommand with the given description, from, to, fromTime, and toTime.
     *
     * @param description The description of the event task.
     * @param from The starting time or date of the event.
     * @param to The ending time or date of the event.
     * @param fromTime The starting LocalDateTime of the event.
     * @param toTime The ending LocalDateTime of the event.
     */
    public EventCommand(String description, String from, String to, LocalDateTime fromTime, LocalDateTime toTime) {
        this.description = description;
        this.from = from;
        this.to = to;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnekException {
        if (fromTime != null && toTime != null) {
            Event event = new Event(description, from, to, fromTime, toTime);
            tasks.add(event);
            ui.print(String.format(Messages.MESSAGE_ADD_TASK, event, tasks.size()));
            storage.write(event);
            return;
        }
        Event event = new Event(description, from, to);
        tasks.add(event);
        ui.print(String.format(Messages.MESSAGE_ADD_TASK, event, tasks.size()));
        storage.write(event);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof EventCommand) {
            if (this.fromTime == null && this.toTime == null) {
                EventCommand other = (EventCommand) obj;
                return this.description.equals(other.description) && this.from.equals(other.from)
                        && this.to.equals(other.to) && other.fromTime == null && other.toTime == null;
            }
            EventCommand other = (EventCommand) obj;
            return this.description.equals(other.description) && this.from.equals(other.from)
                    && this.to.equals(other.to) && this.fromTime.equals(other.fromTime)
                    && this.toTime.equals(other.toTime);
        }
        return false;
    }
}