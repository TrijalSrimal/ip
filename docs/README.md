# Encik User Guide

Encik is a **command-line task manager chatbot** that helps you track todos, deadlines, and events.

## Quick Start

1. Ensure you have **Java 17** installed.
2. Download the latest `Encik.jar` from the [Releases](https://github.com/TrijalSrimal/ip/releases) page.
3. Copy the JAR file to an empty folder.
4. Open a terminal, navigate to the folder, and run:
   ```
   java -jar Encik.jar
   ```
5. Type commands and press Enter. Type `bye` to exit.

## Features

### Adding a Todo: `todo`

Adds a task with no specific date.

Format: `todo <description>`

Example:
```
todo read book
------------------------------------------------------------
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
------------------------------------------------------------
```

### Adding a Deadline: `deadline`

Adds a task with a due date.
*Note: Dates must be in `yyyy-MM-dd` format.*

Format: `deadline <description> /by <date>`

Example:
```
deadline return book /by 2026-03-10
------------------------------------------------------------
Got it. I've added this task:
  [D][ ] return book (by: Mar 10 2026)
Now you have 2 tasks in the list.
------------------------------------------------------------
```

### Adding an Event: `event`

Adds a task with a start and end time.
*Note: Dates must be in `yyyy-MM-dd` format.*

Format: `event <description> /from <start> /to <end>`

Example:
```
event camp /from 2026-06-01 /to 2026-06-05
------------------------------------------------------------
Got it. I've added this task:
  [E][ ] camp (from: Jun 1 2026 to: Jun 5 2026)
Now you have 3 tasks in the list.
------------------------------------------------------------
```

### Listing all tasks: `list`

Shows all tasks currently in the list.

Format: `list`

Example:
```
list
------------------------------------------------------------
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Mar 10 2026)
3.[E][ ] camp (from: Jun 1 2026 to: Jun 5 2026)
------------------------------------------------------------
```

### Marking a task as done: `mark`

Marks the specified task as done.

Format: `mark <index>`

Example:
```
mark 1
------------------------------------------------------------
Nice! I've marked this task as done:
  [T][X] read book
------------------------------------------------------------
```

### Unmarking a task: `unmark`

Marks the specified task as not done.

Format: `unmark <index>`

Example:
```
unmark 1
------------------------------------------------------------
OK, I've marked this task as not done yet:
  [T][ ] read book
------------------------------------------------------------
```

### Deleting a task: `delete`

Removes the specified task from the list.

Format: `delete <index>`

Example:
```
delete 3
------------------------------------------------------------
Noted. I've removed this task:
  [E][ ] camp (from: Jun 1 2026 to: Jun 5 2026)
Now you have 2 tasks in the list.
------------------------------------------------------------
```

### Finding tasks by keyword: `find`

Searches for tasks whose descriptions contain the given keyword (case-insensitive).

Format: `find <keyword>`

Example:
```
find book
------------------------------------------------------------
Here are the matching tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Mar 10 2026)
------------------------------------------------------------
```

### Exiting the program: `bye`

Saves all tasks and exits the chatbot.

Format: `bye`

## Data Storage

Tasks are automatically saved to `data/encik.txt` in the same folder as the JAR file. Tasks are loaded automatically when Encik starts.

> ⚠️ **Warning**: Do not manually edit the data file unless you know the correct format. Corrupted lines will be skipped during loading. Use `yyyy-MM-dd` for all dates in raw data.

## Command Summary

| Command | Format | Example |
|---------|--------|---------|
| Todo | `todo <desc>` | `todo read book` |
| Deadline | `deadline <desc> /by <date>` | `deadline essay /by 2026-03-10` |
| Event | `event <desc> /from <start> /to <end>` | `event camp /from 2026-06-01 /to 2026-06-05` |
| List | `list` | `list` |
| Mark | `mark <index>` | `mark 1` |
| Unmark | `unmark <index>` | `unmark 1` |
| Delete | `delete <index>` | `delete 3` |
| Find | `find <keyword>` | `find book` |
| Exit | `bye` | `bye` |