import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading tasks from the data file and saving tasks to the data file.
 */
public class Storage {
    private static final String FILE_DELIMITER = " | ";

    private final String filePath;
    private final String directoryPath;

    /**
     * Constructs a Storage with the specified file path.
     *
     * @param filePath The path to the data file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        File file = new File(filePath);
        this.directoryPath = file.getParent();
    }

    /**
     * Loads tasks from the data file.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws EncikException If the file cannot be read.
     */
    public ArrayList<Task> load() throws EncikException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
        }

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    Task task = parseTaskFromFile(line);
                    tasks.add(task);
                } catch (EncikException e) {
                    System.out.println("Warning: Skipping corrupted line: " + line);
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            throw new EncikException("Unable to load tasks from file.");
        }
        return tasks;
    }

    /**
     * Saves all tasks to the data file.
     * Creates the data directory if it does not exist.
     *
     * @param tasks The list of tasks to save.
     * @throws EncikException If the file cannot be written.
     */
    public void save(ArrayList<Task> tasks) throws EncikException {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            FileWriter writer = new FileWriter(filePath);
            for (int i = 0; i < tasks.size(); i++) {
                writer.write(taskToFileString(tasks.get(i)) + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new EncikException("Unable to save tasks to file.");
        }
    }

    /**
     * Converts a task to its file storage string representation.
     * Format: TYPE | DONE | DESCRIPTION [| extra fields]
     *
     * @param task The task to convert.
     * @return The file format string for the task.
     */
    private String taskToFileString(Task task) {
        String doneFlag = task.isDone() ? "1" : "0";
        if (task instanceof Todo) {
            return "T" + FILE_DELIMITER + doneFlag + FILE_DELIMITER + task.getDescription();
        } else if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "D" + FILE_DELIMITER + doneFlag + FILE_DELIMITER + d.getDescription()
                    + FILE_DELIMITER + d.getBy();
        } else if (task instanceof Event) {
            Event e = (Event) task;
            return "E" + FILE_DELIMITER + doneFlag + FILE_DELIMITER + e.getDescription()
                    + FILE_DELIMITER + e.getFrom() + FILE_DELIMITER + e.getTo();
        }
        return "";
    }

    /**
     * Parses a task from a file storage line.
     *
     * @param line The line from the data file.
     * @return The parsed Task object.
     * @throws EncikException If the line format is corrupted.
     */
    private Task parseTaskFromFile(String line) throws EncikException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new EncikException("Corrupted data: " + line);
        }

        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < 4) {
                    throw new EncikException("Corrupted deadline data: " + line);
                }
                task = new Deadline(description, parts[3].trim());
                break;
            case "E":
                if (parts.length < 5) {
                    throw new EncikException("Corrupted event data: " + line);
                }
                task = new Event(description, parts[3].trim(), parts[4].trim());
                break;
            default:
                throw new EncikException("Unknown task type: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
