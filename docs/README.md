# Encik User Guide

Encik is a personal assistant chatbot that helps you manage your tasks and more.

## Quick Start

1. Ensure you have Java 17 installed
2. Compile: `javac src/main/java/Encik.java -d out`
3. Run: `java -cp out Encik`

## Features

### Add Task

Add a task by typing any text. Encik will store it and confirm the addition.

Example:
```
read book
```

Expected output:
```
------------------------------------------------------------
added: read book
------------------------------------------------------------
```

### List Tasks

Type `list` to display all stored tasks with status icons.

Example:
```
list
```

Expected output:
```
------------------------------------------------------------
Here are the tasks in your list:
1.[X] read book
2.[ ] return book
3.[ ] buy bread
------------------------------------------------------------
```

### Mark Task as Done

Type `mark <task number>` to mark a task as done.

Example:
```
mark 2
```

Expected output:
```
------------------------------------------------------------
Nice! I've marked this task as done:
  [X] return book
------------------------------------------------------------
```

### Unmark Task

Type `unmark <task number>` to mark a task as not done.

Example:
```
unmark 2
```

Expected output:
```
------------------------------------------------------------
OK, I've marked this task as not done yet:
  [ ] return book
------------------------------------------------------------
```

### Exit

Type `bye` (case-insensitive) to exit the program.

Example: `bye`, `BYE`, or `Bye`

Expected output:
```
------------------------------------------------------------
Bye. Hope to see you again soon!
------------------------------------------------------------
```