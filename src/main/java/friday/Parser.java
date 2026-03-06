package friday;

import friday.task.Deadline;
import friday.task.Event;
import friday.task.Task;
import friday.task.Todo;

public class Parser {
    private static final int COMMAND_SPLIT_LIMIT = 2;

    public static String getCommandWord(String input) {
        String[] parts = input.split(" ", COMMAND_SPLIT_LIMIT);
        return parts[0];
    }

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

    public static int parseTaskNumber(String input, String command) {
        String numberPart = input.substring(command.length()).trim();
        if (numberPart.isEmpty()) {
            throw new NumberFormatException("No number provided");
        }
        return Integer.parseInt(numberPart);
    }

    public static String parseFindKeyword(String input) {
        String keyword = input.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new IllegalArgumentException("Please provide a keyword to search for.");
        }
        return keyword;
    }
}
