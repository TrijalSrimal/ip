# AI.md

## Week 2 – Level-2
Added task management functionality:
- Implemented `String[]` tasks array with max 100 tasks limit
- Added task counter and list command
- Implemented "added: [task]" confirmation messages
- Added divider lines for output formatting

AI Usage: Used AI to implement the task storage array and list functionality. AI wrote the core loop logic for add/list commands.

## Week 2 – Level-3
Refactored to use Task class and added mark/unmark functionality:
- Created `Task` class with `description` and `isDone` fields
- Implemented `markAsDone()` and `markAsNotDone()` methods
- Added `getStatusIcon()` returning `[X]` or `[ ]`
- Updated list display to show task status
- Added `mark <n>` and `unmark <n>` commands with confirmation messages

AI Usage: Used AI to design the Task class structure and implement mark/unmark commands. AI wrote the Task.java class and updated Encik.java with the new command parsing logic.
