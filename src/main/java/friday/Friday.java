package friday;

import java.util.ArrayList;

import friday.task.Task;

public class Friday {

    private static Ui ui = new Ui();
    private static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        ui.showWelcome();
        tasks = Storage.loadTasks();

        while (true) {
            String input = ui.readCommand();
            ui.showLine();

            if (processCommand(input)) {
                break;
            }
        }
        ui.close();
    }

    private static boolean processCommand(String input) {
        String command = Parser.getCommandWord(input);

        switch (command.toLowerCase()) {
            case "bye":
                ui.showBye();
                return true;
            case "list":
                ui.showTaskList(tasks);
                break;
            case "delete":
                handleDelete(input);
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
                ui.showError("Unknown command: " + command);
                break;
        }
        return false;
    }

    private static void handleTodo(String input) {
        try {
            addTask(Parser.parseTodo(input));
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage()); // Show specific error message for todo parsing issues
        }
    }

    private static void handleDeadline(String input) {
        try {
            addTask(Parser.parseDeadline(input));
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage()); // Show specific error message for deadline parsing issues
        }
    }

    private static void handleEvent(String input) {
        try {
            addTask(Parser.parseEvent(input));
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage()); // Show specific error message for event parsing issues
        }
    }

    private static void handleDelete(String input) {
        try {
            int taskNumber = Parser.parseTaskNumber(input, "delete");
            validateTaskNumber(taskNumber);
            Task deletedTask = tasks.remove(taskNumber - 1);
            ui.showTaskDeleted(deletedTask, tasks.size());
            Storage.saveTasks(tasks);
        } catch (NumberFormatException e) {
            ui.showError("Bro what? Enter a valid task number. Usage: delete <number>");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Please enter a number in our expectation range."); // Show specific error message for invalid task numbers
        }
    }

    private static void handleMark(String input) {
        try {
            int taskNumber = Parser.parseTaskNumber(input, "mark");
            validateTaskNumber(taskNumber);
            tasks.get(taskNumber - 1).markAsDone();
            ui.showTaskMarked(tasks.get(taskNumber - 1));
            Storage.saveTasks(tasks);
        } catch (NumberFormatException e) {
            ui.showError("Bro what? Enter a valid task number. Usage: mark <number>");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Please enter a number in our expectation range.");
        }
    }

    private static void handleUnmark(String input) {
        try {
            int taskNumber = Parser.parseTaskNumber(input, "unmark");
            validateTaskNumber(taskNumber);
            tasks.get(taskNumber - 1).markAsNotDone();
            ui.showTaskUnmarked(tasks.get(taskNumber - 1));
            Storage.saveTasks(tasks);
        } catch (NumberFormatException e) {
            ui.showError("Bro what? Enter a valid task number. Usage: unmark <number>");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Please enter a number in our expectation range.");
        }
    }

    private static void validateTaskNumber(int taskNumber) throws IndexOutOfBoundsException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task number");
        }
    }

    private static void addTask(Task task) {
        tasks.add(task);
        ui.showTaskAdded(task);
        Storage.saveTasks(tasks);
    }
}
