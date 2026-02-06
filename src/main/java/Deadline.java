/**
 * Represents a Deadline task that needs to be done before a specific date/time.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Constructs a new Deadline task.
     *
     * @param description The description of the task.
     * @param by          The date/time by which the task should be completed.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the string representation of the Deadline task.
     *
     * @return A string formatted as "[D]" followed by the task details and
     *         deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
