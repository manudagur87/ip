import java.util.Scanner;

public class Friday {
    private static Task[] tasks = new Task[100];

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
        System.out.println("Type 'display' to display all tasks that you are never going to do.");

        Scanner inputScanner = new Scanner(System.in);
        String input;

        int taskCount = 0;
        while (true) {
            input = inputScanner.nextLine();
            System.out.println(line);

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you never!");
                System.out.println(line);
                break;
            }

            if (input.equalsIgnoreCase("display")) {
                if (taskCount == 0) {
                    System.out.println("You have no tasks yet.");
                    continue;
                }
                System.out.println("Here you go bro!");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(
                            "[" + tasks[i].getStatusIcon() + "] " + (i + 1) + ". " + tasks[i].getDescription());
                }
                System.out.println(line);
                continue;
            }

            if (input.startsWith("mark")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                tasks[taskNumber - 1].markAsDone();
                continue;
            }

            if (input.startsWith("unmark")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                tasks[taskNumber - 1].markAsNotDone();
                continue;
            }

            System.out.println("Added:" + input);
            tasks[taskCount] = new Task(input);
            taskCount++;
            System.out.println(line);
        }

        inputScanner.close();
    }
}