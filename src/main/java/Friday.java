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
            default:
                System.out.println("error");
                break;
        }
        return false;
    }

    private static String getCommandWord(String input) {
        String[] parts = input.split(" ", COMMAND_SPLIT_LIMIT);
        return parts[0];
    }

    private static void handleTodo(String input) {
        try {
            String description = input.substring(4).trim();
            if (description.isEmpty()) {
                System.out.println("error");
                return;
            }
            addTask(new Todo(description));
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("error");
        }
    }

    private static void handleDeadline(String input) {
        try {
            String content = input.substring(8).trim();
            if (!content.contains("/by")) {
                System.out.println("error");
                return;
            }
            String[] parts = content.split("/by", 2);
            String description = parts[0].trim();
            String by = parts[1].trim();
            if (description.isEmpty() || by.isEmpty()) {
                System.out.println("error");
                return;
            }
            addTask(new Deadline(description, by));
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("error");
        }
    }

    private static void handleEvent(String input) {
        try {
            String content = input.substring(5).trim();
            if (!content.contains("/from") || !content.contains("/to")) {
                System.out.println("error");
                return;
            }
            String[] fromParts = content.split("/from", 2);
            String description = fromParts[0].trim();
            String[] toParts = fromParts[1].split("/to", 2);
            String from = toParts[0].trim();
            String to = toParts[1].trim();
            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                System.out.println("error");
                return;
            }
            addTask(new Event(description, from, to));
        } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
            System.out.println("error");
        }
    }

    private static void handleMark(String input) {
        try {
            int taskNumber = parseTaskNumber(input, "mark");
            validateTaskNumber(taskNumber);
            tasks[taskNumber - 1].markAsDone();
            printTaskMarked(taskNumber);
        } catch (NumberFormatException e) {
            System.out.println("error");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("error");
        }
    }

    private static void handleUnmark(String input) {
        try {
            int taskNumber = parseTaskNumber(input, "unmark");
            validateTaskNumber(taskNumber);
            tasks[taskNumber - 1].markAsNotDone();
            printTaskUnmarked(taskNumber);
        } catch (NumberFormatException e) {
            System.out.println("error");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("error");
        }
    }

    private static int parseTaskNumber(String input, String command) throws NumberFormatException {
        String numberPart = input.substring(command.length()).trim();
        if (numberPart.isEmpty()) {
            throw new NumberFormatException("No number provided");
        }
        return Integer.parseInt(numberPart);
    }

    private static void validateTaskNumber(int taskNumber) throws IndexOutOfBoundsException {
        if (taskNumber < 1 || taskNumber > taskCount || tasks[taskNumber - 1] == null) {
            throw new IndexOutOfBoundsException("Invalid task number");
        }
    }

    private static void addTask(Task task) {
        if (taskCount >= MAX_TASKS) {
            System.out.println("error");
            return;
        }
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
