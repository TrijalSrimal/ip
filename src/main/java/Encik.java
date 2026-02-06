import java.util.Scanner;

/**
 * Main class for Encik task manager chatbot.
 * Hanldes user interaction and task management.
 */
public class Encik {
    private static final int MAX_TASKS = 100;
    private static final int LINE_LENGTH = 60;
    private static final char LINE_SEPARATOR = '-';

    // Command Constants
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark ";
    private static final String COMMAND_UNMARK = "unmark ";
    private static final String COMMAND_TODO = "todo ";
    private static final String COMMAND_DEADLINE = "deadline ";
    private static final String COMMAND_EVENT = "event ";

    // Task Syntax Markers
    private static final String DEADLINE_BY = " /by ";
    private static final String EVENT_FROM = " /from ";
    private static final String EVENT_TO = " /to ";

    private static Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    /**
     * Main entry point of the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
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
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase(COMMAND_BYE)) {
                break;
            }
            handleCommand(input);
        }
        scanner.close();
    }

    /**
     * Handles a single user command.
     *
     * @param input The raw input string from the user.
     */
    private static void handleCommand(String input) {
        if (input.equalsIgnoreCase(COMMAND_LIST)) {
            listTasks();
        } else if (input.toLowerCase().startsWith(COMMAND_MARK)) {
            markTask(input);
        } else if (input.toLowerCase().startsWith(COMMAND_UNMARK)) {
            unmarkTask(input);
        } else if (input.toLowerCase().startsWith(COMMAND_TODO)) {
            addTodo(input);
        } else if (input.toLowerCase().startsWith(COMMAND_DEADLINE)) {
            addDeadline(input);
        } else if (input.toLowerCase().startsWith(COMMAND_EVENT)) {
            addEvent(input);
        } else {
            printUnknownCommand();
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
     */
    private static void markTask(String input) {
        int taskIndex = parseTaskIndex(input, COMMAND_MARK.length());
        if (isValidIndex(taskIndex)) {
            tasks[taskIndex].markAsDone();
            printLine(LINE_SEPARATOR, LINE_LENGTH);
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks[taskIndex]);
            printLine(LINE_SEPARATOR, LINE_LENGTH);
        }
    }

    /**
     * Marks a task as not done.
     *
     * @param input The user input containing unmark command and task index.
     */
    private static void unmarkTask(String input) {
        int taskIndex = parseTaskIndex(input, COMMAND_UNMARK.length());
        if (isValidIndex(taskIndex)) {
            tasks[taskIndex].markAsNotDone();
            printLine(LINE_SEPARATOR, LINE_LENGTH);
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + tasks[taskIndex]);
            printLine(LINE_SEPARATOR, LINE_LENGTH);
        }
    }

    /**
     * Adds a Todo task.
     *
     * @param input The user input containing todo command and description.
     */
    private static void addTodo(String input) {
        String description = input.substring(COMMAND_TODO.length());
        addTask(new Todo(description));
    }

    /**
     * Adds a Deadline task.
     *
     * @param input The user input containing deadline command, description and by
     *              date.
     */
    private static void addDeadline(String input) {
        String content = input.substring(COMMAND_DEADLINE.length());
        String[] parts = content.split(DEADLINE_BY);
        if (parts.length < 2) {
            System.out.println("Usage: deadline <desc> /by <date>");
            return;
        }

        String description = parts[0];
        String by = parts[1];
        addTask(new Deadline(description, by));
    }

    /**
     * Adds an Event task.
     *
     * @param input The user input containing event command, description, from and
     *              to dates.
     */
    private static void addEvent(String input) {
        String content = input.substring(COMMAND_EVENT.length());
        String[] parts = content.split(EVENT_FROM);
        if (parts.length < 2) {
            System.out.println("Usage: event <desc> /from <start> /to <end>");
            return;
        }

        String description = parts[0];
        String[] timeParts = parts[1].split(EVENT_TO);
        if (timeParts.length < 2) {
            System.out.println("Usage: event <desc> /from <start> /to <end>");
            return;
        }

        String from = timeParts[0];
        String to = timeParts[1];
        addTask(new Event(description, from, to));
    }

    /**
     * Adds a task to the list and prints confirmation.
     *
     * @param task The task to add.
     */
    private static void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;
        printLine(LINE_SEPARATOR, LINE_LENGTH);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + tasks[taskCount - 1]);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        printLine(LINE_SEPARATOR, LINE_LENGTH);
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
     * Prints message for unknown command.
     */
    private static void printUnknownCommand() {
        printLine(LINE_SEPARATOR, LINE_LENGTH);
        System.out.println("Unknown command. Try:");
        System.out.println("  list | todo | deadline /by | event /from /to");
        System.out.println("  mark <n> | unmark <n> | bye");
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
            return Integer.parseInt(input.substring(commandLength)) - 1;
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
