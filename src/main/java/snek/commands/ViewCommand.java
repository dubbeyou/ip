package snek.commands;

import static snek.common.Messages.MESSAGE_NO_TASKS_ON_DATE;
import static snek.common.Messages.MESSAGE_VIEW_TASKS;
import static snek.common.Messages.MESSAGE_VIEW_TIME_IGNORED;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import snek.data.exception.SnekException;
import snek.data.tasks.Deadline;
import snek.data.tasks.Event;
import snek.data.tasks.Task;
import snek.data.tasks.TaskList;
import snek.storage.Storage;

/**
 * Command to list tasks that match a specific date.
 */
public class ViewCommand extends Command {
    private final LocalDateTime viewDateTime;

    /**
     * Constructs a ViewCommand with the specified date-time.
     *
     * @param viewDateTime The date-time to filter tasks by.
     */
    public ViewCommand(LocalDateTime viewDateTime) {
        this.viewDateTime = viewDateTime;
    }

    @Override
    public String execute(TaskList tasks, Storage storage) throws SnekException {
        assert tasks != null : "Task list should not be null.";
        assert storage != null : "Storage should not be null.";

        LocalDate targetDate = viewDateTime.toLocalDate();
        ArrayList<Task> matchedTasks = new ArrayList<>();

        for (Task task : tasks.getTasks()) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                LocalDateTime byTime = deadline.getByTime();
                if (byTime != null && byTime.toLocalDate().equals(targetDate)) {
                    matchedTasks.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                LocalDateTime fromTime = event.getFromTime();
                LocalDateTime toTime = event.getToTime();
                if (fromTime != null && toTime != null && isDateInRange(targetDate, fromTime, toTime)) {
                    matchedTasks.add(task);
                }
            }
        }

        if (matchedTasks.isEmpty()) {
            return MESSAGE_NO_TASKS_ON_DATE;
        }

        TaskList matchedTasksList = new TaskList(matchedTasks);
        String result = String.format(MESSAGE_VIEW_TASKS, targetDate, matchedTasksList.getString());

        if (hasTimeComponent(viewDateTime)) {
            result = MESSAGE_VIEW_TIME_IGNORED + "\n" + result;
        }

        return result;
    }

    private boolean hasTimeComponent(LocalDateTime dateTime) {
        return dateTime.getHour() != 0 || dateTime.getMinute() != 0 || dateTime.getSecond() != 0;
    }

    private boolean isDateInRange(LocalDate targetDate, LocalDateTime fromTime, LocalDateTime toTime) {
        LocalDate fromDate = fromTime.toLocalDate();
        LocalDate toDate = toTime.toLocalDate();
        return !targetDate.isBefore(fromDate) && !targetDate.isAfter(toDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ViewCommand) {
            ViewCommand other = (ViewCommand) obj;
            return this.viewDateTime.equals(other.viewDateTime);
        }
        return false;
    }
}
