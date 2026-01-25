import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private String from;
    private String to;
    private LocalDateTime fromTime = null;
    private LocalDateTime toTime = null;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("h:mma, MMM dd yyyy");

    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

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
            return super.toString()+ " (from: " + from + " to: " + to + ")";
        }
        return super.toString()+ " (from: " + fromTime.format(FORMATTER) + " to: " + toTime.format(FORMATTER) + ")";
    }
}
