import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an Event task that starts at a specific date and ends at a
 * specific date.
 */
public class Event extends Task {
    protected LocalDate fromDate;
    protected LocalDate toDate;

    /**
     * Constructs a new Event task.
     *
     * @param description The description of the task.
     * @param from        The start date of the event (yyyy-MM-dd).
     * @param to          The end date of the event (yyyy-MM-dd).
     * @throws EncikException If the date format is invalid.
     */
    public Event(String description, String from, String to) throws EncikException {
        super(description);
        try {
            this.fromDate = LocalDate.parse(from);
            this.toDate = LocalDate.parse(to);
        } catch (DateTimeParseException e) {
            throw new EncikException("Invalid date format. Please use yyyy-MM-dd (e.g., 2019-10-15).");
        }
    }

    /**
     * Returns the start date of the event in ISO format for storage.
     *
     * @return The "from" date string (yyyy-MM-dd).
     */
    public String getFrom() {
        return fromDate.toString();
    }

    /**
     * Returns the end date of the event in ISO format for storage.
     *
     * @return The "to" date string (yyyy-MM-dd).
     */
    public String getTo() {
        return toDate.toString();
    }

    /**
     * Returns the string representation of the Event task.
     *
     * @return A string formatted as "[E]" followed by the task details and
     *         formatted duration.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + toDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
