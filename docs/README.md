# Encik User Guide

Encik is a task manager chatbot that helps you track todos, deadlines, and events.

## Quick Start

1. Ensure you have Java 17 installed
2. Compile: `cd src/main/java && javac *.java`
3. Run: `java Encik`

## Commands

| Command | Format | Example |
|---------|--------|---------|
| Todo | `todo <desc>` | `todo read book` |
| Deadline | `deadline <desc> /by <date>` | `deadline essay /by Sunday` |
| Event | `event <desc> /from <start> /to <end>` | `event meeting /from 2pm /to 4pm` |
| List | `list` | `list` |
| Mark | `mark <n>` | `mark 1` |
| Unmark | `unmark <n>` | `unmark 1` |
| Delete | `delete <n>` | `delete 3` |
| Exit | `bye` | `bye` |

## Examples

### Add Todo
```
todo read book
------------------------------------------------------------
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
------------------------------------------------------------
```

### Add Deadline
```
deadline return book /by Sunday
------------------------------------------------------------
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
------------------------------------------------------------
```

### Add Event
```
event meeting /from Mon 2pm /to 4pm
------------------------------------------------------------
Got it. I've added this task:
  [E][ ] meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
------------------------------------------------------------
```

### List Tasks
```
list
------------------------------------------------------------
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Sunday)
3.[E][ ] meeting (from: Mon 2pm to: 4pm)
------------------------------------------------------------
```

### Mark/Unmark
```
mark 1
------------------------------------------------------------
Nice! I've marked this task as done:
  [T][X] read book
------------------------------------------------------------
```

### Delete Task
```
delete 3
------------------------------------------------------------
Noted. I've removed this task:
  [E][ ] meeting (from: Mon 2pm to: 4pm)
Now you have 2 tasks in the list.
------------------------------------------------------------
```

## Error Handling

Encik validates all inputs and provides helpful error messages:

### Unknown Command
```
blah
------------------------------------------------------------
OOPS!!! I'm sorry, but I don't know what that means :-(
Available commands: todo, deadline, event, list, mark, unmark, bye
------------------------------------------------------------
```

### Empty Description
```
todo
------------------------------------------------------------
OOPS!!! The description of a todo cannot be empty.
Usage: todo <description>
------------------------------------------------------------
```

### Invalid Task Index
```
mark 100
------------------------------------------------------------
OOPS!!! Invalid task index.
Usage: mark <index>
------------------------------------------------------------
```

### Invalid Delete Index
```
delete 100
------------------------------------------------------------
OOPS!!! Invalid task index.
Usage: delete <index>
------------------------------------------------------------
```