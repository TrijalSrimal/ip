/**
 * Parses user input and makes sense of user commands.
 */
public class Parser {

    // Command Constants
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_FIND = "find";

    // Task Syntax Markers
    private static final String DEADLINE_BY = " /by ";
    private static final String EVENT_FROM = " /from ";
    private static final String EVENT_TO = " /to ";

    /**
     * Checks if the user input is the exit command.
     *
     * @param input The user input string.
     * @return True if the input is the "bye" command.
     */
    public static boolean isExit(String input) {
        return input.equalsIgnoreCase(COMMAND_BYE);
    }

    /**
     * Parses the user input and executes the corresponding command.
     *
     * @param input   The raw user input string.
     * @param tasks   The current task list.
     * @param ui      The UI handler for output.
     * @param storage The storage handler for persistence.
     * @throws EncikException If the command is unknown or has invalid arguments.
     */
    public static void handleCommand(String input, TaskList tasks, Ui ui, Storage storage)
            throws EncikException {
        String[] parts = input.trim().split("\\s+", 2);
        String command = parts[0].toLowerCase();

        switch (command) {
            case COMMAND_LIST:
                ui.showTaskList(tasks);
                break;
            case COMMAND_MARK:
                handleMark(input, tasks, ui, storage);
                break;
            case COMMAND_UNMARK:
                handleUnmark(input, tasks, ui, storage);
                break;
            case COMMAND_TODO:
                handleTodo(input, tasks, ui, storage);
                break;
            case COMMAND_DEADLINE:
                handleDeadline(input, tasks, ui, storage);
                break;
            case COMMAND_EVENT:
                handleEvent(input, tasks, ui, storage);
                break;
            case COMMAND_DELETE:
                handleDelete(input, tasks, ui, storage);
                break;
            case COMMAND_FIND:
                handleFind(input, tasks, ui);
                break;
            default:
                throw new EncikException(
                        "OOPS!!! I'm sorry, but I don't know what that means :-(\n"
                                + "Available commands: todo, deadline, event, list, mark, unmark, delete, find, bye");
        }
    }

    /**
     * Handles the mark command.
     *
     * @param input   The user input.
     * @param tasks   The task list.
     * @param ui      The UI handler.
     * @param storage The storage handler.
     * @throws EncikException If the index is missing or invalid.
     */
    private static void handleMark(String input, TaskList tasks, Ui ui, Storage storage)
            throws EncikException {
        if (input.trim().equalsIgnoreCase(COMMAND_MARK)) {
            throw new EncikException("OOPS!!! Invalid task index.\nUsage: mark <index>");
        }
        int taskIndex = parseTaskIndex(input, COMMAND_MARK.length());
        if (!tasks.isValidIndex(taskIndex)) {
            throw new EncikException("OOPS!!! Invalid task index.\nUsage: mark <index>");
        }
        tasks.get(taskIndex).markAsDone();
        saveTasksSilently(tasks, ui, storage);
        ui.showTaskMarked(tasks.get(taskIndex));
    }

    /**
     * Handles the unmark command.
     *
     * @param input   The user input.
     * @param tasks   The task list.
     * @param ui      The UI handler.
     * @param storage The storage handler.
     * @throws EncikException If the index is missing or invalid.
     */
    private static void handleUnmark(String input, TaskList tasks, Ui ui, Storage storage)
            throws EncikException {
        if (input.trim().equalsIgnoreCase(COMMAND_UNMARK)) {
            throw new EncikException("OOPS!!! Invalid task index.\nUsage: unmark <index>");
        }
        int taskIndex = parseTaskIndex(input, COMMAND_UNMARK.length());
        if (!tasks.isValidIndex(taskIndex)) {
            throw new EncikException("OOPS!!! Invalid task index.\nUsage: unmark <index>");
        }
        tasks.get(taskIndex).markAsNotDone();
        saveTasksSilently(tasks, ui, storage);
        ui.showTaskUnmarked(tasks.get(taskIndex));
    }

    /**
     * Handles the todo command.
     *
     * @param input   The user input.
     * @param tasks   The task list.
     * @param ui      The UI handler.
     * @param storage The storage handler.
     * @throws EncikException If the description is empty.
     */
    private static void handleTodo(String input, TaskList tasks, Ui ui, Storage storage)
            throws EncikException {
        if (input.trim().equalsIgnoreCase(COMMAND_TODO)) {
            throw new EncikException(
                    "OOPS!!! The description of a todo cannot be empty.\nUsage: todo <description>");
        }
        String description = input.substring(COMMAND_TODO.length()).trim();
        if (description.isEmpty()) {
            throw new EncikException(
                    "OOPS!!! The description of a todo cannot be empty.\nUsage: todo <description>");
        }
        Task task = new Todo(description);
        tasks.add(task);
        saveTasksSilently(tasks, ui, storage);
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles the deadline command.
     *
     * @param input   The user input.
     * @param tasks   The task list.
     * @param ui      The UI handler.
     * @param storage The storage handler.
     * @throws EncikException If the format is invalid.
     */
    private static void handleDeadline(String input, TaskList tasks, Ui ui, Storage storage)
            throws EncikException {
        if (input.trim().equalsIgnoreCase(COMMAND_DEADLINE)) {
            throw new EncikException(
                    "OOPS!!! Invalid deadline format.\nUsage: deadline <desc> /by <date>");
        }
        String content = input.substring(COMMAND_DEADLINE.length());
        String[] parts = content.split(DEADLINE_BY);
        if (parts.length < 2) {
            throw new EncikException(
                    "OOPS!!! Invalid deadline format.\nUsage: deadline <desc> /by <date>");
        }

        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new EncikException(
                    "OOPS!!! The description or date cannot be empty.\nUsage: deadline <desc> /by <date>");
        }

        Task task = new Deadline(description, by);
        tasks.add(task);
        saveTasksSilently(tasks, ui, storage);
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles the event command.
     *
     * @param input   The user input.
     * @param tasks   The task list.
     * @param ui      The UI handler.
     * @param storage The storage handler.
     * @throws EncikException If the format is invalid.
     */
    private static void handleEvent(String input, TaskList tasks, Ui ui, Storage storage)
            throws EncikException {
        if (input.trim().equalsIgnoreCase(COMMAND_EVENT)) {
            throw new EncikException(
                    "OOPS!!! Invalid event format.\nUsage: event <desc> /from <start> /to <end>");
        }
        String content = input.substring(COMMAND_EVENT.length());
        String[] parts = content.split(EVENT_FROM);
        if (parts.length < 2) {
            throw new EncikException(
                    "OOPS!!! Invalid event format.\nUsage: event <desc> /from <start> /to <end>");
        }

        String description = parts[0].trim();
        String[] timeParts = parts[1].split(EVENT_TO);
        if (timeParts.length < 2) {
            throw new EncikException(
                    "OOPS!!! Invalid event format.\nUsage: event <desc> /from <start> /to <end>");
        }

        String from = timeParts[0].trim();
        String to = timeParts[1].trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new EncikException(
                    "OOPS!!! The description or time cannot be empty.\nUsage: event <desc> /from <start> /to <end>");
        }

        Task task = new Event(description, from, to);
        tasks.add(task);
        saveTasksSilently(tasks, ui, storage);
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles the delete command.
     *
     * @param input   The user input.
     * @param tasks   The task list.
     * @param ui      The UI handler.
     * @param storage The storage handler.
     * @throws EncikException If the index is missing or invalid.
     */
    private static void handleDelete(String input, TaskList tasks, Ui ui, Storage storage)
            throws EncikException {
        if (input.trim().equalsIgnoreCase(COMMAND_DELETE)) {
            throw new EncikException("OOPS!!! Invalid task index.\nUsage: delete <index>");
        }
        int taskIndex = parseTaskIndex(input, COMMAND_DELETE.length());
        if (!tasks.isValidIndex(taskIndex)) {
            throw new EncikException("OOPS!!! Invalid task index.\nUsage: delete <index>");
        }
        Task removedTask = tasks.remove(taskIndex);
        saveTasksSilently(tasks, ui, storage);
        ui.showTaskDeleted(removedTask, tasks.size());
    }

    /**
     * Handles the find command.
     *
     * @param input The user input.
     * @param tasks The task list.
     * @param ui    The UI handler.
     * @throws EncikException If the keyword is empty.
     */
    private static void handleFind(String input, TaskList tasks, Ui ui)
            throws EncikException {
        if (input.trim().equalsIgnoreCase(COMMAND_FIND)) {
            throw new EncikException(
                    "OOPS!!! The search keyword cannot be empty.\nUsage: find <keyword>");
        }
        String keyword = input.substring(COMMAND_FIND.length()).trim();
        if (keyword.isEmpty()) {
            throw new EncikException(
                    "OOPS!!! The search keyword cannot be empty.\nUsage: find <keyword>");
        }
        TaskList matchingTasks = tasks.find(keyword);
        ui.showFoundTasks(matchingTasks);
    }

    /**
     * Parses the task index from user input.
     *
     * @param input         The user input.
     * @param commandLength The length of the command prefix to skip.
     * @return The 0-based index of the task, or -1 if invalid.
     */
    private static int parseTaskIndex(String input, int commandLength) {
        try {
            return Integer.parseInt(input.substring(commandLength).trim()) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Saves tasks to storage, displaying a warning on failure.
     *
     * @param tasks   The task list to save.
     * @param ui      The UI handler for error display.
     * @param storage The storage handler.
     */
    private static void saveTasksSilently(TaskList tasks, Ui ui, Storage storage) {
        try {
            storage.save(tasks.getTasks());
        } catch (EncikException e) {
            ui.showSavingError();
        }
    }
}
