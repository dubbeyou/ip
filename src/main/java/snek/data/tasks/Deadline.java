package snek.data.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private String by;
    private LocalDateTime byTime = null;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Deadline) {
            Deadline other = (Deadline) obj;
            boolean isByTimeEqual = (this.byTime == null && other.byTime == null)
                                            || (this.byTime != null && this.byTime.equals(other.byTime));
            return super.equals(obj) && this.by.equals(other.by) && isByTimeEqual;
        }
        return false;
    }
}
