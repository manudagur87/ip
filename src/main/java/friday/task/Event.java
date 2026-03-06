package friday.task;

/**
 * Represents an event task with start and end time.
 */
public class Event extends Task {

    protected String from;
    protected String to;

    /**
     * Creates an event task.
     *
     * @param description task description
     * @param from start time or period
     * @param to end time or period
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the event start field.
     *
     * @return start value
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the event end field.
     *
     * @return end value
     */
    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
