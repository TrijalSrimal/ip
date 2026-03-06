import java.util.Scanner;

/**
 * Handles all interactions with the user.
 * Reads user input and displays messages to the console.
 */
public class Ui {
    private static final int LINE_LENGTH = 60;
    private static final char LINE_SEPARATOR = '-';

    private final Scanner scanner;

    /**
     * Constructs a new Ui and initializes the input scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a line of user input.
     *
     * @return The trimmed user input string.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Prints the welcome message when the chatbot starts.
     */
    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm Encik");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Prints the exit message when the chatbot ends.
     */
    public void showExit() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Displays an error message wrapped in separator lines.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    /**
     * Displays a message indicating a task has been added.
     *
     * @param task      The task that was added.
     * @param taskCount The total number of tasks after adding.
     */
    public void showTaskAdded(Task task, int taskCount) {
        showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

    /**
     * Displays a message indicating a task has been deleted.
     *
     * @param task      The task that was removed.
     * @param taskCount The total number of tasks remaining.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

    /**
     * Displays a message indicating a task has been marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        showLine();
    }

    /**
     * Displays a message indicating a task has been unmarked.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        showLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        showLine();
    }

    /**
     * Displays all tasks in the list.
     *
     * @param tasks The task list to display.
     */
    public void showTaskList(TaskList tasks) {
        showLine();
        if (tasks.size() == 0) {
            System.out.println("There are no tasks in your list.");
            showLine();
            return;
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    /**
     * Displays the tasks that match a search keyword.
     *
     * @param tasks The list of matching tasks.
     */
    public void showFoundTasks(TaskList tasks) {
        showLine();
        if (tasks.size() == 0) {
            System.out.println("No matching tasks found.");
            showLine();
            return;
        }
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    /**
     * Displays a warning about a corrupted data line during file loading.
     *
     * @param line The corrupted line.
     */
    public void showLoadingWarning(String line) {
        System.out.println("Warning: Skipping corrupted line: " + line);
    }

    /**
     * Displays a warning about file loading failure.
     */
    public void showLoadingError() {
        System.out.println("Warning: Unable to load tasks from file.");
    }

    /**
     * Displays a warning about file saving failure.
     */
    public void showSavingError() {
        System.out.println("Warning: Unable to save tasks to file.");
    }

    /**
     * Prints a horizontal separator line.
     */
    public void showLine() {
        for (int i = 0; i < LINE_LENGTH; i++) {
            System.out.print(LINE_SEPARATOR);
        }
        System.out.println();
    }

    /**
     * Closes the input scanner.
     */
    public void close() {
        scanner.close();
    }
}
