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

## Week 4 – Level-4
Implemented three task types using inheritance:
- Created `Todo` class extending Task with `[T]` prefix
- Created `Deadline` class with `by` field and `[D]` prefix
- Created `Event` class with `from`/`to` fields and `[E]` prefix
- Updated `Encik.java` to parse `todo`, `deadline /by`, `event /from /to` commands
- Improved error messages to show available commands

AI Usage: Used AI to create the inheritance hierarchy (Todo, Deadline, Event extending Task) and implement command parsing. AI wrote all three new classes and updated the main loop logic.

## Week 4 – A-CodeQuality
Applied code quality improvements:
- Extracted magic number `100` to `MAX_TASKS` constant
- Added JavaDoc comments to classes and methods
- Ensured naming follows SE-EDU coding standards

AI Usage: AI reviewed code against coding standards and made improvements including extracting constants and adding documentation comments.

