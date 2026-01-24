import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime byTime = null;
    protected String by;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("h:mma, MMM dd yyyy");

    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    public Deadline(String description, String by, LocalDateTime byTime) {
        super(description, TaskType.DEADLINE);
        this.by = by;
        this.byTime = byTime;
    }

    @Override
    public String getSaveString() {
        return super.getSaveString() + " | " + by;
    }

    @Override
    public String toString() {
        if (byTime == null) {
            return super.toString() + " (by: " + by + ")";
        }
        return super.toString() + " (by: " + byTime.format(FORMATTER) + ")";
    }
}
