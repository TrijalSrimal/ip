public class Encik {
    public static void main(String[] args) {
        String logo = "Encik";
        printLine('-',100);
        System.out.println("Hello! I'm " + logo );
        System.out.println("What can I do for you?");
        printLine('-',100);
        System.out.println("Bye. Hope to see you again soon!");
        printLine('-',100);

    }
    public static void printLine(char ch, int length) {
        for (int i = 0; i < length; i++) {
            System.out.print(ch);
        }
        System.out.println();
    }
}
