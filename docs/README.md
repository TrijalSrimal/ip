# Encik User Guide

Encik is a personal assistant chatbot that helps you manage your tasks and more.

## Quick Start

1. Ensure you have Java 17 installed
2. Compile: `javac src/main/java/Encik.java -d out`
3. Run: `java -cp out Encik`

## Features

### Echo

Encik echoes back any text you type.

Example:
```
hello world
```

Expected output:
```
------------------------------------------------------------
hello world
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