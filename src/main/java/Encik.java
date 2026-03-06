import java.io.File;

/**
 * Main class for Encik task manager chatbot.
 * Handles the main application flow.
 */
public class Encik {
    private static final String DATA_FILE_PATH = "data" + File.separator + "encik.txt";

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new Encik chatbot with the specified file path.
     *
     * @param filePath The path to the data file for task persistence.
     */
    public Encik(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (EncikException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main command loop of the chatbot.
     * Reads user input, processes commands, and handles errors.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                isExit = Parser.isExit(fullCommand);
                if (!isExit) {
                    Parser.handleCommand(fullCommand, tasks, ui, storage);
                }
            } catch (EncikException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.showExit();
        ui.close();
    }

    /**
     * Main entry point of the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Encik(DATA_FILE_PATH).run();
    }
}
