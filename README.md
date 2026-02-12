# Encik

Encik is a task manager chatbot that helps you track your todos, deadlines, and events.

## Features

- **Todo** - Tasks without dates
- **Deadline** - Tasks with a due date
- **Event** - Tasks with start and end times
- Mark/unmark tasks as done
- List all tasks
- Supports up to 100 tasks

## Usage

```
todo <description>              - Add a todo
deadline <desc> /by <date>      - Add a deadline
event <desc> /from <start> /to <end> - Add an event
list                            - Show all tasks
mark <n>                        - Mark task n as done
unmark <n>                      - Unmark task n
bye                             - Exit
```

## Error Handling

Encik provides helpful error messages for invalid inputs:
- Unknown commands display available commands
- Empty descriptions are rejected with usage hints
- Invalid task indices show the correct format
- Task list full (max 100) warns user

## Running

```bash
cd src/main/java
javac *.java
java Encik
```

## Setting up in IntelliJ

Prerequisites: JDK 17

1. Open IntelliJ → `Open` → Select project directory
2. Configure JDK 17 in `Project Structure`
3. Run `Encik.java`
