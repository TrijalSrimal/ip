/**
 * Represents an Event task that starts at a specific time and ends at a
 * specific time.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs a new Event task.
     *
     * @param description The description of the task.
     * @param from        The start time of the event.
     * @param to          The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the string representation of the Event task.
     *
     * @return A string formatted as "[E]" followed by the task details and
     *         duration.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
