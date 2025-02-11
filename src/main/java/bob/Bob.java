package bob;

import bob.command.Command;
import bob.command.ExitCommand;
import bob.parser.Parser;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Main class that serves as the entry point and controller for the Bob task management application.
 * Coordinates between the UI, storage, parser and task components to provide task management functionality.
 */
public class Bob {
    /** Flag indicating if the application is still running */
    private Boolean isActive;

    /** File path where tasks are persisted */
    private static final String FILE_PATH = "./data/tasks.txt";

    /** List containing all tasks */
    private final TaskList tasks;

    /** Handles user interface interactions */
    private final Ui ui;

    /** Parses user input into commands */
    private final Parser parser;

    /** Manages saving and loading of tasks */
    private final Storage storage;

    /**
     * Creates a new Bob application instance.
     * Initializes all components (UI, storage, parser, task list) and loads any existing tasks.
     */
    public Bob() {
        this.isActive = true;
        this.tasks = new TaskList();
        this.ui = new Ui();
        this.parser = new Parser();
        this.storage = new Storage(FILE_PATH, tasks);
    }

    /**
     * Entry point of the application.
     * Creates a Bob instance and starts the application loop.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Bob bob = new Bob();
        bob.run();
    }

    /**
     * Main application loop.
     * Displays welcome message and repeatedly:
     * 1. Gets user input
     * 2. Parses input into a command
     * 3. Executes the command
     * 4. Exits if an ExitCommand is received
     * 
     * Any exceptions during execution are caught and their messages displayed to the user.
     */
    public void run() {
        ui.greet();
        while (isActive) {
            try {
                String[] userInput = ui.getUserInput();
                Command cmd = parser.parseUserInput(userInput);
                cmd.execute(tasks, ui, storage);
                if (cmd instanceof ExitCommand) {
                    isActive = false;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}