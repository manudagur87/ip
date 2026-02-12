import java.util.Scanner;

public class Friday {

    private static final int MAX_TASKS = 100;
    private static final int COMMAND_SPLIT_LIMIT = 2;
    private static final String LINE = "____________________________________________________________";

    private static Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        printWelcome();
        Scanner inputScanner = new Scanner(System.in);

        while (true) {
            String input = inputScanner.nextLine();
            printLine();

            if (processCommand(input)) {
                break;
            }
        }

        inputScanner.close();
    }

    private static boolean processCommand(String input) {
        String command = getCommandWord(input);

        switch (command.toLowerCase()) {
            case "bye":
                printBye();
                return true;
            case "list":
                printList();
                break;
            case "mark":
                handleMark(input);
                break;
            case "unmark":
                handleUnmark(input);
                break;
            case "todo":
                handleTodo(input);
                break;
            case "deadline":
                handleDeadline(input);
                break;
            case "event":
                handleEvent(input);
                break;
        }
        return false;
    }

    private static String getCommandWord(String input) {
        String[] parts = input.split(" ", COMMAND_SPLIT_LIMIT);
        return parts[0];
    }

    private static void handleTodo(String input) {
        String description = input.substring(4).trim();
        addTask(new Todo(description));
    }

    private static void handleDeadline(String input) {
        String content = input.substring(8).trim();
        String[] parts = content.split("/by", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();
        addTask(new Deadline(description, by));
    }

    private static void handleEvent(String input) {
        String content = input.substring(5).trim();
        String[] fromParts = content.split("/from", 2);
        String description = fromParts[0].trim();
        String[] toParts = fromParts[1].split("/to", 2);
        String from = toParts[0].trim();
        String to = toParts[1].trim();
        addTask(new Event(description, from, to));
    }

    private static void handleMark(String input) {
        int taskNumber = Integer.parseInt(input.substring(4).trim());
        tasks[taskNumber - 1].markAsDone();
        printTaskMarked(taskNumber);
    }

    private static void handleUnmark(String input) {
        int taskNumber = Integer.parseInt(input.substring(6).trim());
        tasks[taskNumber - 1].markAsNotDone();
        printTaskUnmarked(taskNumber);
    }

    private static void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;
        printTaskAdded(task);
    }

    private static void printLine() {
        System.out.println(LINE);
    }

    private static void printTaskAdded(Task task) {
        System.out.println("Added: " + task);
        printLine();
    }

    private static void printTaskMarked(int taskNumber) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + tasks[taskNumber - 1]);
        printLine();
    }

    private static void printTaskUnmarked(int taskNumber) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + tasks[taskNumber - 1]);
        printLine();
    }

    private static void printList() {
        if (taskCount == 0) {
            System.out.println("You have no tasks yet.");
            printLine();
            return;
        }
        System.out.println("Here you go bro!");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
        printLine();
    }

    private static void printBye() {
        System.out.println("Bye. Hope to see you never!");
        printLine();
    }

    private static void printWelcome() {
        String logo = "______________________________\n" +
                "|  ________________________  |\n" +
                "| |                        | |\n" +
                "| |       FRIDAY           | |\n" +
                "| |________________________| |\n" +
                "|____________________________|\n";
        printLine();
        System.out.println("Hello I'm\n" + logo + "What can I not do for you?");
        printLine();
        System.out.println("Type 'bye' to exit and never come back.");
        System.out.println("Type 'list' to display all tasks.");
    }
}
