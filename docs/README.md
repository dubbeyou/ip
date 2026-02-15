# Snek User Guide

![Snek Screenshot](./Ui.png)

Snek is a **task management chatbot** that helps you keep track of your todos, deadlines, and events through a simple chat interface.

## Features

- Add todo, deadline and event tasks
- Mark tasks as done / Unmark tasks as undone
- Delete tasks
- Find tasks by keyword
- View tasks on a certain date
- Save and load tasks

### Notes

- Words in `UPPER_CASE` are the parameters to be supplied by the user.
  e.g. in `todo DESCRIPTION`, `DESCRIPTION` is a parameter which can be used as `todo read book`.

- Date and time formats are flexible. Snek supports multiple formats:
    - Dates: `yyyy-M-d`, `yyyy/M/d`, `d-M-yyyy`, `d/M/yyyy`
    - Times (optional): `H:m` or `Hmm` after the date
  e.g., `2026-2-15`, `15/2/2026`, `15-2-2026 14:30`, `2026/2/15 1430`

- Parameters must follow the specified order for each command.
  e.g. for `deadline DESCRIPTION /by TIME`, the `/by` marker must come after the description.

- Task indices must be positive integers (1, 2, 3, ...) as shown in the task list.

### Listing all tasks: `list`

Shows a list of all tasks.

Format: `list`

Example:
```
list
```

Expected output:
```
Here are your tasksss:
1.[T][ ] read book
2.[D][X] return book (by: Feb 15 2026, 2:00 PM)
3.[E][ ] project meeting (from: Feb 20 2026, 2:00 PM to: Feb 20 2026, 4:00 PM)
```

### Adding a todo task: `todo`

Adds a todo task without any date or time.

Format: `todo DESCRIPTION`

Examples:
- `todo read book`
- `todo buy groceries`
- `todo water the plants`

Expected output:
```
I'ves addedss:
	[T][ ] read book
You now havesss 1 task(s) in your listssss.
```

### Adding a deadline: `deadline`

Adds a task with a deadline.

Format: `deadline DESCRIPTION /by TIME`

*If you provide a date-time in a recognized format (e.g., `2026-2-15 14:30`), Snek will parse it for smart date filtering with the `view` command. Otherwise, it will store the time as plain text.*

Examples:
- `deadline return book /by 2026-2-15 14:00`
- `deadline submit assignment /by Sunday 2pm`
- `deadline pay bills /by 28/2/2026`

Expected output:
```
I'ves addedss:
	[D][ ] return book (by: Feb 15 2026, 2:00 PM)
You now havesss 2 task(s) in your listssss.
```

### Adding an event: `event`

Adds a task that spans a period of time.

Format: `event DESCRIPTION /from START /to END`

*If both START and END times are parsed as date-times, Snek will validate that the start time is before the end time. The event cannot start at or after it ends!*

Examples:
- `event project meeting /from 2026-2-20 14:00 /to 2026-2-20 16:00`
- `event conference /from Monday 9am /to Friday 5pm`
- `event vacation /from 1/3/2026 /to 15/3/2026`

Expected output:
```
I'ves addedss:
	[E][ ] project meeting (from: Feb 20 2026, 2:00 PM to: Feb 20 2026, 4:00 PM)
You now havesss 3 task(s) in your listssss.
```

### Marking a task as done: `mark`

Marks the specified task as completed.

Format: `mark INDEX`

- Marks the task at the specified `INDEX` as done.
- The index refers to the index number shown in the displayed task list.
- The index **must be a positive integer** 1, 2, 3, ...
- The task must not already be marked as done.

Examples:
- `list` followed by `mark 2` marks the 2nd task as done.

Expected output:
```
Ssss... I'ves marked thisss task as donesss:
  [D][X] return book (by: Feb 15 2026, 2:00 PM)
```

### Unmarking a task: `unmark`

Marks the specified task as **not** done.

Format: `unmark INDEX`

- Marks the task at the specified `INDEX` as **not** done.
- The index refers to the index number shown in the displayed task list.
- The index **must be a positive integer** 1, 2, 3, ...
- The task must already be marked as done.

Examples:
- `list` followed by `unmark 2` marks the 2nd task as not done.

Expected output:
```
Ssss... I'ves marked thisss task as not done yetss:
  [D][ ] return book (by: Feb 15 2026, 2:00 PM)
```

### Finding tasks by keyword: `find`

Finds tasks whose descriptions contain the given keyword.

Format: `find KEYWORD`

- The search is case-insensitive.
- Only the task description is searched.
- Partial matches are supported (e.g., `book` will match `books` and `booking`).
- Tasks containing the keyword anywhere in the description will be returned.

Examples:
- `find book` returns tasks with "book" in the description
- `find meeting` returns tasks with "meeting" in the description

Expected output:
```
Here are the matching tasks in your list:
1.[T][ ] read book
2.[D][X] return book (by: Feb 15 2026, 2:00 PM)
```

### Viewing tasks on a date: `view`

Shows all deadlines and events that fall on a specific date.

Format: `view DATE`

- The date must be in a recognized format (e.g., `2026-2-15`, `15/2/2026`).
- Only deadlines and events with parsed date-times are included.
- For deadlines, tasks are shown if they are due on the specified date.
- For events, tasks are shown if the date falls within the event period (from start to end).
- Time component is ignored - only the date is considered.

Examples:
- `view 2026-2-15`
- `view 15/2/2026`

Expected output:
```
Here are the tasks on 2026-02-15:
1.[D][X] return book (by: Feb 15 2026, 2:00 PM)
```

### Deleting a task: `delete`

Deletes the specified task.

Format: `delete INDEX`

- Deletes the task at the specified `INDEX`.
- The index refers to the index number shown in the displayed task list.
- The index **must be a positive integer** 1, 2, 3, ...

Examples:
- `list` followed by `delete 3` deletes the 3rd task from the task list.

Expected output:
```
Ssss... I'ves removed thisss task:
	[T][ ] read book
You now havesss 2 task(s) in your listssss.
```

### Exiting the program: `bye`

Exits Snek and closes the application.

Format: `bye`

Expected output:
```
Ssss... Bye! Ssss...
```

### Saving the data

Task data is saved to the hard disk automatically after any command that changes the data. There is no need to save manually. Tasks are stored in `[JAR file location]/data/tasks.txt`.

### Editing the data file

Snek's task data is saved automatically as a text file `[JAR file location]/data/tasks.txt`. Advanced users are welcome to update data directly by editing that data file.

## Command Summary

| Command    | Format                                              | Examples                                                    |
|------------|-----------------------------------------------------|-------------------------------------------------------------|
| **list**   | `list`                                              | `list`                                                      |
| **todo**   | `todo DESCRIPTION`                                  | `todo read book`                                            |
| **deadline** | `deadline DESCRIPTION /by TIME`                   | `deadline return book /by 2026-2-15 14:00`                 |
| **event**  | `event DESCRIPTION /from START /to END`             | `event meeting /from 2026-2-20 14:00 /to 2026-2-20 16:00` |
| **mark**   | `mark INDEX`                                        | `mark 2`                                                    |
| **unmark** | `unmark INDEX`                                      | `unmark 2`                                                  |
| **find**   | `find KEYWORD`                                      | `find book`                                                 |
| **view**   | `view DATE`                                         | `view 2026-2-15`                                            |
| **delete** | `delete INDEX`                                      | `delete 3`                                                  |
| **bye**    | `bye`                                               | `bye`                                                       |
