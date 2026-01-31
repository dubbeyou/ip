package snek.data.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task.
 */
public class Event extends Task {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("h:mma, MMM dd yyyy");
    private String from;
    private String to;
    private LocalDateTime fromTime = null;
    private LocalDateTime toTime = null;

    /**
     * Constructs an Event task with the given description, from, and to.
     *
     * @param description The description of the event task.
     * @param from The starting time or date of the event.
     * @param to The ending time or date of the event.
     */
    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs an Event task with the given description, from, to, fromTime,
     * and toTime.
     *
     * @param description The description of the event task.
     * @param from The starting time or date of the event.
     * @param to The ending time or date of the event.
     * @param fromTime The starting LocalDateTime of the event.
     * @param toTime The ending LocalDateTime of the event.
     */
    public Event(String description, String from, String to, LocalDateTime fromTime, LocalDateTime toTime) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    @Override
    public String getSaveString() {
        return super.getSaveString() + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        if (fromTime == null || toTime == null) {
            return super.toString() + " (from: " + from + " to: " + to + ")";
        }
        return super.toString() + " (from: " + fromTime.format(FORMATTER) + " to: " + toTime.format(FORMATTER) + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Event) {
            Event other = (Event) obj;
            boolean isFromTimeEqual = (this.fromTime == null && other.fromTime == null)
                                            || (this.fromTime != null && this.fromTime.equals(other.fromTime));
            boolean isToTimeEqual = (this.toTime == null && other.toTime == null)
                                            || (this.toTime != null && this.toTime.equals(other.toTime));
            return super.equals(obj) && this.from.equals(other.from) && this.to.equals(other.to) && isFromTimeEqual
                                            && isToTimeEqual;
        }
        return false;
    }
}
