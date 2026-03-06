import java.util.ArrayList;

/**
 * Contains the task list and provides operations to manage tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index The 0-based index of the task to remove.
     * @return The removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index The 0-based index of the task.
     * @return The task at the given index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying ArrayList of tasks.
     *
     * @return The ArrayList containing all tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Checks if the given index is a valid task index.
     *
     * @param index The 0-based index to validate.
     * @return True if the index is within bounds, false otherwise.
     */
    public boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    /**
     * Finds tasks whose descriptions contain the given keyword.
     *
     * @param keyword The keyword to search for (case-insensitive).
     * @return A new TaskList containing only the matching tasks.
     */
    public TaskList find(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDescription().toLowerCase().contains(lowerKeyword)) {
                matchingTasks.add(tasks.get(i));
            }
        }
        return new TaskList(matchingTasks);
    }
}
