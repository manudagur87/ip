package friday;

import java.util.ArrayList;

import friday.task.Deadline;
import friday.task.Event;
import friday.task.Task;
import friday.task.Todo;

public class Friday {

    private static final int COMMAND_SPLIT_LIMIT = 2;
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
        String command = getCommandWord(input);

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

    private static String getCommandWord(String input) {
        String[] parts = input.split(" ", COMMAND_SPLIT_LIMIT);
        return parts[0];
    }

    private static void handleTodo(String input) {
        try {
            String description = input.substring(4).trim();
            if (description.isEmpty()) {
                ui.showError("The description of a todo cannot be empty. Usage: todo <description>");
                return;
            }
            addTask(new Todo(description));
        } catch (StringIndexOutOfBoundsException e) {
            ui.showError("The description of a todo cannot be empty. Usage: todo <description>");
        }
    }

    private static void handleDeadline(String input) {
        try {
            String content = input.substring(8).trim();
            if (!content.contains("/by")) {
                ui.showError("Deadline must include /by. Usage: deadline <description> /by <date>");
                return;
            }
            String[] parts = content.split("/by", 2);
            String description = parts[0].trim();
            String by = parts[1].trim();
            if (description.isEmpty() || by.isEmpty()) {
                ui.showError("Deadline description and date cannot be empty.");
                return;
            }
            addTask(new Deadline(description, by));
        } catch (StringIndexOutOfBoundsException e) {
            ui.showError("Invalid deadline format. Usage: deadline <description> /by <date>");
        }
    }

    private static void handleEvent(String input) {
        try {
            String content = input.substring(5).trim();
            if (!content.contains("/from") || !content.contains("/to")) {
                ui.showError("Event must include /from and /to. Usage: event <description> /from <start> /to <end>");
                return;
            }
            String[] fromParts = content.split("/from", 2);
            String description = fromParts[0].trim();
            String[] toParts = fromParts[1].split("/to", 2);
            String from = toParts[0].trim();
            String to = toParts[1].trim();
            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                ui.showError("Event description, start time, and end time cannot be empty.");
                return;
            }
            addTask(new Event(description, from, to));
        } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
            ui.showError("Invalid event format. Usage: event <description> /from <start> /to <end>");
        }
    }

    private static void handleDelete(String input) {
        try {
            int taskNumber = parseTaskNumber(input, "delete");
            validateTaskNumber(taskNumber);
            Task deletedTask = tasks.remove(taskNumber - 1);
            ui.showTaskDeleted(deletedTask, tasks.size());
            Storage.saveTasks(tasks);
        } catch (NumberFormatException e) {
            ui.showError("Bro what? Enter a valid task number. Usage: delete <number>");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Please enter a number in our expectation range.");
        }
    }

    private static void handleMark(String input) {
        try {
            int taskNumber = parseTaskNumber(input, "mark");
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
            int taskNumber = parseTaskNumber(input, "unmark");
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

    private static int parseTaskNumber(String input, String command) throws NumberFormatException {
        String numberPart = input.substring(command.length()).trim();
        if (numberPart.isEmpty()) {
            throw new NumberFormatException("No number provided");
        }
        return Integer.parseInt(numberPart);
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
