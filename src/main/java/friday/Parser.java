package friday;

import friday.task.Deadline;
import friday.task.Event;
import friday.task.Task;
import friday.task.Todo;

/**
 * Parses raw user input into commands and task objects.
 */
public class Parser {
    private static final int COMMAND_SPLIT_LIMIT = 2;

    /**
     * Extracts the first word of the input as the command.
     *
     * @param input full user input
     * @return command word
     */
    public static String getCommandWord(String input) {
        String[] parts = input.split(" ", COMMAND_SPLIT_LIMIT);
        return parts[0];
    }

    /**
     * Parses a todo command into a {@code Todo} task.
     *
     * @param input full todo command
     * @return parsed task
     */
    public static Task parseTodo(String input) {
        try {
            String description = input.substring(4).trim();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("The description of a todo cannot be empty. Usage: todo <description>");
            }
            return new Todo(description);
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("The description of a todo cannot be empty. Usage: todo <description>");
        }
    }

    /**
     * Parses a deadline command into a {@code Deadline} task.
     *
     * @param input full deadline command
     * @return parsed task
     */
    public static Task parseDeadline(String input) {
        try {
            String content = input.substring(8).trim();
            if (!content.contains("/by")) {
                throw new IllegalArgumentException("Deadline must include /by. Usage: deadline <description> /by <date>");
            }
            String[] parts = content.split("/by", 2);
            String description = parts[0].trim();
            String by = parts[1].trim();
            if (description.isEmpty() || by.isEmpty()) {
                throw new IllegalArgumentException("Deadline description and date cannot be empty.");
            }
            return new Deadline(description, by);
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid deadline format. Usage: deadline <description> /by <date>");
        }
    }

    /**
     * Parses an event command into an {@code Event} task.
     *
     * @param input full event command
     * @return parsed task
     */
    public static Task parseEvent(String input) {
        try {
            String content = input.substring(5).trim();
            if (!content.contains("/from") || !content.contains("/to")) {
                throw new IllegalArgumentException("Event must include /from and /to. Usage: event <description> /from <start> /to <end>");
            }
            String[] fromParts = content.split("/from", 2);
            String description = fromParts[0].trim();
            String[] toParts = fromParts[1].split("/to", 2);
            String from = toParts[0].trim();
            String to = toParts[1].trim();
            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new IllegalArgumentException("Event description, start time, and end time cannot be empty.");
            }
            return new Event(description, from, to);
        } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid event format. Usage: event <description> /from <start> /to <end>");
        }
    }

    /**
     * Parses the task number portion of a command.
     *
     * @param input full command input
     * @param command command keyword prefix
     * @return parsed task number
     */
    public static int parseTaskNumber(String input, String command) {
        String numberPart = input.substring(command.length()).trim();
        if (numberPart.isEmpty()) {
            throw new NumberFormatException("No number provided");
        }
        return Integer.parseInt(numberPart);
    }

    /**
     * Parses the keyword used for find.
     *
     * @param input full find command
     * @return keyword string
     */
    public static String parseFindKeyword(String input) {
        String keyword = input.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new IllegalArgumentException("Please provide a keyword to search for.");
        }
        return keyword;
    }
}
