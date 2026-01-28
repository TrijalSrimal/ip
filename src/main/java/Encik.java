import java.util.Scanner;

//main class
public class Encik {
    // Fixed size array to store tasks (max 100 as per assignment)
    private static String[] tasks = new String[100];
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
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                printLine('-', 60);
            } else {
                // Add task
                tasks[taskCount] = input;
                taskCount++;
                printLine('-', 60);
                System.out.println("added: " + input);
                printLine('-', 60);
            }
        }

        // Goodbye
        printLine('-', 60);
        System.out.println("Bye. Hope to see you again soon!");
        printLine('-', 60);

        scanner.close();
    }

    //function to print a line of characters
    public static void printLine(char ch, int length) {
        for (int i = 0; i < length; i++) {
            System.out.print(ch);
        }
        System.out.println();
    }
}
