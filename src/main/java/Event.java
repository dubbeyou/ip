import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime fromTime = null;
    protected LocalDateTime toTime = null;
    protected String from;
    protected String to;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("h:mma, MMM dd yyyy");

    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    public Event(String description, String from, LocalDateTime fromTime, String to, LocalDateTime toTime) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.fromTime = fromTime;
        this.to = to;
        this.toTime = toTime;
    }

    @Override
    public String getSaveString() {
        return super.getSaveString() + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        if (fromTime == null || toTime == null) {
            return super.toString()+ " (from: " + from + " to: " + to + ")";
        }
        return super.toString()+ " (from: " + fromTime.format(FORMATTER) + " to: " + toTime.format(FORMATTER) + ")";
    }
}
