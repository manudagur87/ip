package friday;

import java.util.Scanner;

import friday.task.Task;

/**
 * Handles user input and output for the command-line interface.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner inputScanner;

    /**
     * Creates a UI instance that reads from standard input.
     */
    public Ui() {
        this.inputScanner = new Scanner(System.in);
    }

    /**
     * Reads the next command from user input.
     *
     * @return raw input line
     */
    public String readCommand() {
        return inputScanner.nextLine();
    }

    /**
     * Prints the horizontal divider line.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays the welcome banner and basic usage hints.
     */
    public void showWelcome() {
        String logo = "______________________________\n"
                + "|  ________________________  |\n"
                + "| |                        | |\n"
                + "| |       FRIDAY           | |\n"
                + "| |________________________| |\n"
                + "|____________________________|\n";
        showLine();
        System.out.println("Hello I'm\n" + logo + "What can I not do for you?");
        showLine();
        System.out.println("Type 'bye' to exit and never come back.");
        System.out.println("Type 'list' to display all tasks.");
    }

    /**
     * Displays the exit message.
     */
    public void showBye() {
        System.out.println("Bye. Hope to see you never!");
        showLine();
    }

    /**
     * Displays an error message.
     *
     * @param message error details
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
        showLine();
    }

    /**
     * Displays confirmation that a task was added.
     *
     * @param task added task
     */
    public void showTaskAdded(Task task) {
        System.out.println("Added: " + task);
        showLine();
    }

    /**
     * Displays confirmation that a task was marked done.
     *
     * @param task marked task
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        showLine();
    }

    /**
     * Displays confirmation that a task was marked not done.
     *
     * @param task unmarked task
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        showLine();
    }

    /**
     * Displays confirmation that a task was deleted.
     *
     * @param task deleted task
     * @param taskCount remaining number of tasks
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Aight bro. I've removed this task:");
        System.out.println("   " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

    /**
     * Displays all tasks in the list.
     *
     * @param tasks tasks to display
     */
    public void showTaskList(java.util.ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks yet.");
            showLine();
            return;
        }
        System.out.println("Here you go bro!");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    /**
     * Displays tasks matching a search keyword.
     *
     * @param results matched tasks
     */
    public void showSearchResults(java.util.ArrayList<Task> results) {
        if (results.isEmpty()) {
            System.out.println("No matching tasks found.");
            showLine();
            return;
        }
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + "." + results.get(i));
        }
        showLine();
    }

    /**
     * Closes input resources used by the UI.
     */
    public void close() {
        inputScanner.close();
    }
}
