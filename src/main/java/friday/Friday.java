package friday;

import friday.task.Task;

/**
 * Entry point and main application loop for Friday.
 */
public class Friday {

    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Creates a Friday app instance with storage at the given file path.
     *
     * @param filePath path to the task storage file
     */
    public Friday(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = storage.loadTasks();
    }

    /**
     * Starts the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        new Friday("data/friday.txt").run();
    }

    /**
     * Runs the command loop until the user exits.
     */
    public void run() {
        ui.showWelcome();
        while (true) {
            String input = ui.readCommand();
            ui.showLine();

            if (processCommand(input)) {
                break;
            }
        }
        ui.close();
    }

    private boolean processCommand(String input) {
        String command = Parser.getCommandWord(input);

        switch (command.toLowerCase()) {
            case "bye":
                ui.showBye();
                return true;
            case "list":
                ui.showTaskList(tasks.getTasks());
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
            case "find":
                handleFind(input);
                break;
            default:
                ui.showError("Unknown command: " + command);
                break;
        }
        return false;
    }

    private void handleTodo(String input) {
        try {
            addTask(Parser.parseTodo(input));
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    private void handleDeadline(String input) {
        try {
            addTask(Parser.parseDeadline(input));
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    private void handleEvent(String input) {
        try {
            addTask(Parser.parseEvent(input));
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    private void handleFind(String input) {
        try {
            String keyword = Parser.parseFindKeyword(input);
            java.util.ArrayList<Task> results = tasks.findTasks(keyword);
            ui.showSearchResults(results);
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    private void handleDelete(String input) {
        try {
            int taskNumber = Parser.parseTaskNumber(input, "delete");
            Task deletedTask = tasks.deleteTask(taskNumber);
            ui.showTaskDeleted(deletedTask, tasks.size());
            storage.saveTasks(tasks);
        } catch (NumberFormatException e) {
            ui.showError("Bro what? Enter a valid task number. Usage: delete <number>");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Please enter a number in our expectation range.");
        }
    }

    private void handleMark(String input) {
        try {
            int taskNumber = Parser.parseTaskNumber(input, "mark");
            tasks.markTask(taskNumber);
            ui.showTaskMarked(tasks.getTask(taskNumber));
            storage.saveTasks(tasks);
        } catch (NumberFormatException e) {
            ui.showError("Bro what? Enter a valid task number. Usage: mark <number>");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Please enter a number in our expectation range.");
        }
    }

    private void handleUnmark(String input) {
        try {
            int taskNumber = Parser.parseTaskNumber(input, "unmark");
            tasks.unmarkTask(taskNumber);
            ui.showTaskUnmarked(tasks.getTask(taskNumber));
            storage.saveTasks(tasks);
        } catch (NumberFormatException e) {
            ui.showError("Bro what? Enter a valid task number. Usage: unmark <number>");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Please enter a number in our expectation range.");
        }
    }

    private void addTask(Task task) {
        tasks.addTask(task);
        ui.showTaskAdded(task);
        storage.saveTasks(tasks);
    }
}
