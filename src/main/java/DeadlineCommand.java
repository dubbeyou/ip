import java.time.LocalDateTime;

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
        LocalDateTime byDateTime = Parser.parseDateTime(by);
        if (byDateTime != null) {
            Deadline deadline = new Deadline(description, by, byDateTime);
            tasks.add(deadline);
            return;
        }
        Deadline deadline = new Deadline(description, by);
        tasks.add(deadline);
    }
    
}
