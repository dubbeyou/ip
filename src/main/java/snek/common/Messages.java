package snek.common;

/**
 * Container for user visible messages.
 */
public class Messages {
    // General Messages
    public static final String MESSAGE_LINEBREAK = "------------------------------------------------------------";
    public static final String MESSAGE_HELLO = "Ssss... Hello I'm Snek! Ssss...\nWhatsss cans I do for you todayss...?";
    public static final String MESSAGE_BYE = "Ssss... Bye! Ssss...";

    // Storage Messages
    public static final String MESSAGE_SUCCESS_LOAD = "Ssss... Loaded tasksss from storage..sss .. or maybe I created a new file for yousss...!";
    public static final String MESSAGE_ERROR_LOAD = "Ssss... Error creating storage file!";
    public static final String MESSAGE_ERROR_WRITE = "Ssss... Error writing to storage file!";
    public static final String MESSAGE_INVALID_FILE_FORMAT = "Ssss... Invalid file format in storage!";

    // Command Messages
    public static final String MESSAGE_ADD_TASK = "I'ves addedss:\n\t%s\nYou now havesss %d task(s) in your listssss.";
    public static final String MESSAGE_MARK_TASK = "Ssss... I'ves marked thisss task as donesss:\n  %s";
    public static final String MESSAGE_UNMARK_TASK = "Ssss... I'ves marked thisss task as not done yetss:\n  %s";
    public static final String MESSAGE_DELETE_TASK = "Ssss... I'ves removed thisss task:\n\t%s\nYou now havesss %d task(s) in your listssss.";
    public static final String MESSAGE_FOUND_MATCHING_TASKS = "Here are the matching tasks in your list:\n%s";
    public static final String MESSAGE_NO_MATCHING_TASKS = "Ssss... No matching tasksss found!";
    public static final String MESSAGE_VIEW_TASKS = "Here are the tasks on %s:\n%s";
    public static final String MESSAGE_NO_TASKS_ON_DATE = "Ssss... No tasks found on that date!";
    public static final String MESSAGE_VIEW_TIME_IGNORED = "Note: Time component ignored, only date is used for filtering.";

    // Command Error Messages
    public static final String MESSAGE_INVALID_TASK = "Ssss... Invalid task number!";
    public static final String MESSAGE_INVALID_MARK = "Ssss... Thisss task isss already marked as doness!";
    public static final String MESSAGE_INVALID_UNMARK = "Ssss... Thisss task isss already unmarked!";
    public static final String MESSAGE_INVALID_TODO = "Ssss... Please provide a description for your todo tassskss!";
    public static final String MESSAGE_INVALID_DEADLINE_1 = "Ssss... Please provide a deadline in the format: deadline DESCRIPTION /by TIME";
    public static final String MESSAGE_INVALID_DEADLINE_2 = "Ssss... Missing description or /by time.";
    public static final String MESSAGE_INVALID_EVENT_1 = "Ssss... Please provide event in the format: event DESCRIPTION /from START /to END";
    public static final String MESSAGE_INVALID_EVENT_2 = "Ssss... Missing description or /from or /to details.";
    public static final String MESSAGE_INVALID_COMMAND = "Ssssory, I don't undersssstand the commandsss...";
    public static final String MESSAGE_INVALID_FIND = "Ssss... Please provide a keyword to find tassskss!";
    public static final String MESSAGE_INVALID_VIEW = "Ssss... Please provide a date in the format: view DATE";
    public static final String MESSAGE_INVALID_VIEW_DATE = "Ssss... Please provide a valid date for view!";
    public static final String MESSAGE_INVALID_MARKER_DUPLICATE = "Ssss... Marker %s cannot appear multiple timesss!";
}
