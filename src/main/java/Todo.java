/**
 * Represents a Todo task that has no specific date/time attached to it.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task.
     *
     * @param description The description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the Todo task.
     *
     * @return A string formatted as "[T]" followed by the task details.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
