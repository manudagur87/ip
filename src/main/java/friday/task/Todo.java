package friday.task;

/**
 * Represents a todo task.
 */
public class Todo extends Task {

    /**
     * Creates a todo task.
     *
     * @param description task description
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
