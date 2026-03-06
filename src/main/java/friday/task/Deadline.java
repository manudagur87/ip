package friday.task;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {

    protected String by;

    /**
     * Creates a deadline task.
     *
     * @param description task description
     * @param by deadline value
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
    * Returns the deadline field.
    *
    * @return deadline value
    */
    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
