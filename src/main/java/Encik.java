import java.util.Scanner;
//main class
public class Encik {
    public static void main(String[] args) {
        String logo = "Encik";
        Scanner scanner = new Scanner(System.in);

        // Greeting
        printLine('-', 60);
        System.out.println("Hello! I'm " + logo);
        System.out.println("What can I do for you?");
        printLine('-', 60);

        // Echo loop
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                break;
            }
            printLine('-', 60);
            System.out.println(input);
            printLine('-', 60);
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
