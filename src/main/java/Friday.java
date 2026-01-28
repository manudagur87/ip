import java.util.Scanner;

public class Friday {
    public static void main(String[] args) {
        String logo = "______________________________\n" +
                "|  ________________________  |\n" +
                "| |                        | |\n" +
                "| |       FRIDAY           | |\n" +
                "| |________________________| |\n" +
                "|____________________________|\n";
        String line = "____________________________________________________________";

        System.out.println(line);
        System.out.println("Hello I'm\n" + logo + "What can I not do for you?");
        System.out.println(line);
        System.out.println("Type 'bye' to exit and never come back.");

        Scanner inputScanner = new Scanner(System.in);
        String input;

        while (true) {
            input = inputScanner.nextLine();
            System.out.println(line);

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you never!");
                System.out.println(line);
                break;
            }

            System.out.println(input);
            System.out.println(line);
        }

        inputScanner.close();
    }
}