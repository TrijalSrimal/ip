import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Deadline task that needs to be done before a specific date.
 */
public class Deadline extends Task {
    protected LocalDate byDate;

    /**
     * Constructs a new Deadline task.
     *
     * @param description The description of the task.
     * @param by          The date by which the task should be completed
     *                    (yyyy-MM-dd).
     * @throws EncikException If the date format is invalid.
     */
    public Deadline(String description, String by) throws EncikException {
        super(description);
        try {
            this.byDate = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new EncikException("Invalid date format. Please use yyyy-MM-dd (e.g., 2019-10-15).");
        }
    }

    /**
     * Returns the deadline date string in ISO format for storage.
     *
     * @return The "by" date string (yyyy-MM-dd).
     */
    public String getBy() {
        return byDate.toString();
    }

    /**
     * Returns the string representation of the Deadline task.
     *
     * @return A string formatted as "[D]" followed by the task details and
     *         formatted deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
