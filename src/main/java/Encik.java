import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main class for Encik task manager chatbot.
 * Handles user interaction and task management.
 */
public class Encik {
    private static final int MAX_TASKS = 100;
    private static final int LINE_LENGTH = 60;
    private static final char LINE_SEPARATOR = '-';

    // Command Constants
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";

    // Task Syntax Markers
    private static final String DEADLINE_BY = " /by ";
    private static final String EVENT_FROM = " /from ";
    private static final String EVENT_TO = " /to ";

    // File Storage Constants
    private static final String DATA_DIRECTORY = "data";
    private static final String DATA_FILE_PATH = DATA_DIRECTORY + File.separator + "encik.txt";
    private static final String FILE_DELIMITER = " | ";

    private static Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    /**
     * Main entry point of the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        loadTasks();
        printWelcome();
        runCommandLoop();
        printExit();
    }

    /**
     * Runs the main command loop to process user input until exit command is
     * received.
     */
    private static void runCommandLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase(COMMAND_BYE)) {
                break;
            }
            try {
                handleCommand(input);
            } catch (EncikException e) {
                printLine(LINE_SEPARATOR, LINE_LENGTH);
                System.out.println(e.getMessage());
                printLine(LINE_SEPARATOR, LINE_LENGTH);
            }
        }
        scanner.close();
    }

    /**
     * Handles a single user command.
     *
     * @param input The raw input string from the user.
     * @throws EncikException If the command is unknown or invalid.
     */
    private static void handleCommand(String input) throws EncikException {
        String[] p = input.trim().split("\\s+", 2);
        String command = p[0].toLowerCase();

        if (command.equals(COMMAND_LIST)) {
            listTasks();
        } else if (command.equals(COMMAND_MARK)) {
            markTask(input);
        } else if (command.equals(COMMAND_UNMARK)) {
            unmarkTask(input);
        } else if (command.equals(COMMAND_TODO)) {
            addTodo(input);
        } else if (command.equals(COMMAND_DEADLINE)) {
            addDeadline(input);
        } else if (command.equals(COMMAND_EVENT)) {
            addEvent(input);
        } else {
            throw new EncikException(
                    "OOPS!!! I'm sorry, but I don't know what that means :-(\n"
                            + "Available commands: todo, deadline, event, list, mark, unmark, bye");
        }
    }

    /**
     * Prints the welcome message.
     */
    private static void printWelcome() {
        String logo = "Encik";
        printLine(LINE_SEPARATOR, LINE_LENGTH);
        System.out.println("Hello! I'm " + logo);
        System.out.println("What can I do for you?");
        printLine(LINE_SEPARATOR, LINE_LENGTH);
    }

    /**
     * Lists all current tasks.
     */
    private static void listTasks() {
        printLine(LINE_SEPARATOR, LINE_LENGTH);
        if (taskCount == 0) {
            System.out.println("There are no tasks in your list.");
            printLine(LINE_SEPARATOR, LINE_LENGTH);
            return;
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
        printLine(LINE_SEPARATOR, LINE_LENGTH);
    }

    /**
     * Marks a task as done.
     *
     * @param input The user input containing mark command and task index.
     * @throws EncikException If the index is missing or invalid.
     */
    private static void markTask(String input) throws EncikException {
        if (input.trim().equalsIgnoreCase(COMMAND_MARK)) {
            throw new EncikException("OOPS!!! Invalid task index.\nUsage: mark <index>");
        }
        int taskIndex = parseTaskIndex(input, COMMAND_MARK.length());
        if (!isValidIndex(taskIndex)) {
            throw new EncikException("OOPS!!! Invalid task index.\nUsage: mark <index>");
        }
        tasks[taskIndex].markAsDone();
        saveTasks();
        printLine(LINE_SEPARATOR, LINE_LENGTH);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + tasks[taskIndex]);
        printLine(LINE_SEPARATOR, LINE_LENGTH);
    }

    /**
     * Marks a task as not done.
     *
     * @param input The user input containing unmark command and task index.
     * @throws EncikException If the index is missing or invalid.
     */
    private static void unmarkTask(String input) throws EncikException {
        if (input.trim().equalsIgnoreCase(COMMAND_UNMARK)) {
            throw new EncikException("OOPS!!! Invalid task index.\nUsage: unmark <index>");
        }
        int taskIndex = parseTaskIndex(input, COMMAND_UNMARK.length());
        if (!isValidIndex(taskIndex)) {
            throw new EncikException("OOPS!!! Invalid task index.\nUsage: unmark <index>");
        }
        tasks[taskIndex].markAsNotDone();
        saveTasks();
        printLine(LINE_SEPARATOR, LINE_LENGTH);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + tasks[taskIndex]);
        printLine(LINE_SEPARATOR, LINE_LENGTH);
    }

    /**
     * Adds a Todo task.
     *
     * @param input The user input containing todo command and description.
     * @throws EncikException If the description is empty.
     */
    private static void addTodo(String input) throws EncikException {
        if (input.trim().equalsIgnoreCase(COMMAND_TODO)) {
            throw new EncikException("OOPS!!! The description of a todo cannot be empty.\nUsage: todo <description>");
        }
        String description = input.substring(COMMAND_TODO.length()).trim();
        if (description.isEmpty()) {
            throw new EncikException("OOPS!!! The description of a todo cannot be empty.\nUsage: todo <description>");
        }
        addTask(new Todo(description));
    }

    /**
     * Adds a Deadline task.
     *
     * @param input The user input containing deadline command, description and by
     *              date.
     * @throws EncikException If the format is invalid.
     */
    private static void addDeadline(String input) throws EncikException {
        if (input.trim().equalsIgnoreCase(COMMAND_DEADLINE)) {
            throw new EncikException("OOPS!!! Invalid deadline format.\nUsage: deadline <desc> /by <date>");
        }
        String content = input.substring(COMMAND_DEADLINE.length());
        String[] parts = content.split(DEADLINE_BY);
        if (parts.length < 2) {
            throw new EncikException("OOPS!!! Invalid deadline format.\nUsage: deadline <desc> /by <date>");
        }

        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new EncikException(
                    "OOPS!!! The description or date cannot be empty.\nUsage: deadline <desc> /by <date>");
        }

        addTask(new Deadline(description, by));
    }

    /**
     * Adds an Event task.
     *
     * @param input The user input containing event command, description, from and
     *              to dates.
     * @throws EncikException If the format is invalid.
     */
    private static void addEvent(String input) throws EncikException {
        if (input.trim().equalsIgnoreCase(COMMAND_EVENT)) {
            throw new EncikException("OOPS!!! Invalid event format.\nUsage: event <desc> /from <start> /to <end>");
        }
        String content = input.substring(COMMAND_EVENT.length());
        String[] parts = content.split(EVENT_FROM);
        if (parts.length < 2) {
            throw new EncikException("OOPS!!! Invalid event format.\nUsage: event <desc> /from <start> /to <end>");
        }

        String description = parts[0].trim();
        String[] timeParts = parts[1].split(EVENT_TO);
        if (timeParts.length < 2) {
            throw new EncikException("OOPS!!! Invalid event format.\nUsage: event <desc> /from <start> /to <end>");
        }

        String from = timeParts[0].trim();
        String to = timeParts[1].trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new EncikException(
                    "OOPS!!! The description or time cannot be empty.\nUsage: event <desc> /from <start> /to <end>");
        }

        addTask(new Event(description, from, to));
    }

    /**
     * Adds a task to the list and prints confirmation.
     *
     * @param task The task to add.
     * @throws EncikException If the task list is full.
     */
    private static void addTask(Task task) throws EncikException {
        if (taskCount >= MAX_TASKS) {
            throw new EncikException("OOPS!!! Task list is full (max " + MAX_TASKS + " tasks).");
        }
        tasks[taskCount] = task;
        taskCount++;
        saveTasks();
        printLine(LINE_SEPARATOR, LINE_LENGTH);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + tasks[taskCount - 1]);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        printLine(LINE_SEPARATOR, LINE_LENGTH);
    }

    /**
     * Saves all tasks to the data file.
     * Creates the data directory if it does not exist.
     * Format: TYPE | DONE | DESCRIPTION [| extra fields]
     */
    private static void saveTasks() {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            FileWriter writer = new FileWriter(DATA_FILE_PATH);
            for (int i = 0; i < taskCount; i++) {
                writer.write(taskToFileString(tasks[i]) + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Warning: Unable to save tasks to file.");
        }
    }

    /**
     * Loads tasks from the data file.
     * Handles missing file/directory and corrupted data gracefully.
     */
    private static void loadTasks() {
        File file = new File(DATA_FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    Task task = parseTaskFromFile(line);
                    if (taskCount < MAX_TASKS) {
                        tasks[taskCount] = task;
                        taskCount++;
                    }
                } catch (EncikException e) {
                    System.out.println("Warning: Skipping corrupted line: " + line);
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Warning: Unable to load tasks from file.");
        }
    }

    /**
     * Converts a task to its file storage string representation.
     *
     * @param task The task to convert.
     * @return The file format string for the task.
     */
    private static String taskToFileString(Task task) {
        String doneFlag = task.isDone ? "1" : "0";
        if (task instanceof Todo) {
            return "T" + FILE_DELIMITER + doneFlag + FILE_DELIMITER + task.description;
        } else if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "D" + FILE_DELIMITER + doneFlag + FILE_DELIMITER + d.description
                    + FILE_DELIMITER + d.by;
        } else if (task instanceof Event) {
            Event e = (Event) task;
            return "E" + FILE_DELIMITER + doneFlag + FILE_DELIMITER + e.description
                    + FILE_DELIMITER + e.from + FILE_DELIMITER + e.to;
        }
        return "";
    }

    /**
     * Parses a task from a file storage line.
     *
     * @param line The line from the data file.
     * @return The parsed Task object.
     * @throws EncikException If the line format is corrupted.
     */
    private static Task parseTaskFromFile(String line) throws EncikException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new EncikException("Corrupted data");
        }

        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < 4) {
                    throw new EncikException("Corrupted deadline data");
                }
                task = new Deadline(description, parts[3].trim());
                break;
            case "E":
                if (parts.length < 5) {
                    throw new EncikException("Corrupted event data");
                }
                task = new Event(description, parts[3].trim(), parts[4].trim());
                break;
            default:
                throw new EncikException("Unknown task type: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Prints the exit message.
     */
    private static void printExit() {
        printLine(LINE_SEPARATOR, LINE_LENGTH);
        System.out.println("Bye. Hope to see you again soon!");
        printLine(LINE_SEPARATOR, LINE_LENGTH);
    }

    /**
     * Parses the task index from user input.
     *
     * @param input         The user input.
     * @param commandLength The length of the command prefix to skip.
     * @return The 0-based index of the task.
     */
    private static int parseTaskIndex(String input, int commandLength) {
        try {
            return Integer.parseInt(input.substring(commandLength).trim()) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Checks if a task index is valid.
     *
     * @param index The index to check.
     * @return True if valid, false otherwise.
     */
    private static boolean isValidIndex(int index) {
        return index >= 0 && index < taskCount;
    }

    /**
     * Prints a line of repeated characters.
     *
     * @param ch     The character to repeat.
     * @param length The number of times to repeat the character.
     */
    public static void printLine(char ch, int length) {
        for (int i = 0; i < length; i++) {
            System.out.print(ch);
        }
        System.out.println();
    }
}
