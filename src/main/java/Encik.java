import java.util.Scanner;

/**
 * Main class for Encik task manager chatbot.
 */
public class Encik {
    private static final int MAX_TASKS = 100;
    private static Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        String logo = "Encik";
        Scanner scanner = new Scanner(System.in);

        // Greeting
        printLine('-', 60);
        System.out.println("Hello! I'm " + logo);
        System.out.println("What can I do for you?");
        printLine('-', 60);

        // Main loop
        String input;
        while (true) {
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("list")) {
                // List all tasks
                printLine('-', 60);
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                printLine('-', 60);
            } else if (input.toLowerCase().startsWith("mark ")) {
                // Mark task as done
                int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                tasks[taskIndex].markAsDone();
                printLine('-', 60);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[taskIndex]);
                printLine('-', 60);
            } else if (input.toLowerCase().startsWith("unmark ")) {
                // Mark task as not done
                int taskIndex = Integer.parseInt(input.substring(7)) - 1;
                tasks[taskIndex].markAsNotDone();
                printLine('-', 60);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[taskIndex]);
                printLine('-', 60);
            } else if (input.toLowerCase().startsWith("todo ")) {
                // Add Todo task
                String description = input.substring(5);
                tasks[taskCount] = new Todo(description);
                taskCount++;
                printLine('-', 60);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                printLine('-', 60);
            } else if (input.toLowerCase().startsWith("deadline ")) {
                // Add Deadline task: deadline <desc> /by <date>
                String content = input.substring(9);
                String[] parts = content.split(" /by ");
                String description = parts[0];
                String by = parts[1];
                tasks[taskCount] = new Deadline(description, by);
                taskCount++;
                printLine('-', 60);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                printLine('-', 60);
            } else if (input.toLowerCase().startsWith("event ")) {
                // Add Event task: event <desc> /from <start> /to <end>
                String content = input.substring(6);
                String[] parts = content.split(" /from ");
                String description = parts[0];
                String[] timeParts = parts[1].split(" /to ");
                String from = timeParts[0];
                String to = timeParts[1];
                tasks[taskCount] = new Event(description, from, to);
                taskCount++;
                printLine('-', 60);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                printLine('-', 60);
            } else {
                // Unknown command
                printLine('-', 60);
                System.out.println("Unknown command. Try:");
                System.out.println("  list | todo | deadline /by | event /from /to");
                System.out.println("  mark <n> | unmark <n> | bye");
                printLine('-', 60);
            }
        }

        // Goodbye
        printLine('-', 60);
        System.out.println("Bye. Hope to see you again soon!");
        printLine('-', 60);

        scanner.close();
    }

    /**
     * Prints a line of repeated characters.
     *
     * @param ch The character to repeat.
     * @param length The number of times to repeat the character.
     */
    public static void printLine(char ch, int length) {
        for (int i = 0; i < length; i++) {
            System.out.print(ch);
        }
        System.out.println();
    }
}
