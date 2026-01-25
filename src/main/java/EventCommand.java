import java.time.LocalDateTime;

public class EventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;
    private final LocalDateTime fromTime;
    private final LocalDateTime toTime;

    public EventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
        this.fromTime = null;
        this.toTime = null;
    }

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
            Event event = new Event(description, from, fromTime, to, toTime);
            tasks.add(event);
            return;
        }
        Event event = new Event(description, from, to);
        tasks.add(event);
    }
    
}