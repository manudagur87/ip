package friday;

import java.util.Scanner;

import friday.task.Task;

public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner inputScanner;

    public Ui() {
        this.inputScanner = new Scanner(System.in);
    }

    public String readCommand() {
        return inputScanner.nextLine();
    }

    public void showLine() {
        System.out.println(LINE);
    }

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

    public void showBye() {
        System.out.println("Bye. Hope to see you never!");
        showLine();
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
        showLine();
    }

    public void showTaskAdded(Task task) {
        System.out.println("Added: " + task);
        showLine();
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        showLine();
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        showLine();
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Aight bro. I've removed this task:");
        System.out.println("   " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

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

    public void close() {
        inputScanner.close();
    }
}
