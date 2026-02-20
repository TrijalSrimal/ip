import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for Encik task manager chatbot.
 * Handles user interaction and task management.
 */
public class Encik {
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
    private static final String COMMAND_DELETE = "delete";

    // Task Syntax Markers
    private static final String DEADLINE_BY = " /by ";
    private static final String EVENT_FROM = " /from ";
    private static final String EVENT_TO = " /to ";

    private static ArrayList<Task> tasks = new ArrayList<>();

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
        } else if (command.equals(COMMAND_DELETE)) {
            deleteTask(input);
        } else {
            throw new EncikException(
                    "OOPS!!! I'm sorry, but I don't know what that means :-(\n"
                            + "Available commands: todo, deadline, event, list, mark, unmark, delete, bye");
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
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks in your list.");
            printLine(LINE_SEPARATOR, LINE_LENGTH);
            return;
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
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
        tasks.get(taskIndex).markAsDone();
        printLine(LINE_SEPARATOR, LINE_LENGTH);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + tasks.get(taskIndex));
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
        tasks.get(taskIndex).markAsNotDone();
        printLine(LINE_SEPARATOR, LINE_LENGTH);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + tasks.get(taskIndex));
        printLine(LINE_SEPARATOR, LINE_LENGTH);
    }

    /**
     * Deletes a task from the list.
     *
     * @param input The user input containing delete command and task index.
     * @throws EncikException If the index is missing or invalid.
     */
    private static void deleteTask(String input) throws EncikException {
        if (input.trim().equalsIgnoreCase(COMMAND_DELETE)) {
            throw new EncikException("OOPS!!! Invalid task index.\nUsage: delete <index>");
        }
        int taskIndex = parseTaskIndex(input, COMMAND_DELETE.length());
        if (!isValidIndex(taskIndex)) {
            throw new EncikException("OOPS!!! Invalid task index.\nUsage: delete <index>");
        }
        Task removedTask = tasks.remove(taskIndex);
        printLine(LINE_SEPARATOR, LINE_LENGTH);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
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
     */
    private static void addTask(Task task) {
        tasks.add(task);
        printLine(LINE_SEPARATOR, LINE_LENGTH);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
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
        return index >= 0 && index < tasks.size();
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
