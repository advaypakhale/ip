package bob.engine;

import bob.command.Command;
import bob.command.ExitCommand;
import bob.parser.Parser;
import bob.storage.Storage;
import bob.task.TaskList;

/**
 * Class that serves as the core internal engine of the Bob chatbot.
 * Coordinates between the UI, storage, parser and task components to provide task management functionality.
 */
public class Bob {
    /**
     * Base path where tasks are persisted
     */
    private static final String FILE_PATH_BASE = "./data/";

    /**
     * Counter based on how many Bob instances are created, in order to save generated TaskLists.
     * Used to create unique file paths for each Bob instance.
     */
    private static int counter = 0;

    /**
     * List containing all tasks
     */
    private final TaskList tasks;

    /**
     * Parses user input into commands
     */
    private final Parser parser;

    /**
     * Manages saving and loading of tasks
     */
    private final Storage storage;

    /**
     * Flag indicating if the application is still running
     */
    private Boolean isActive = true;

    /**
     * Creates a new Bob application instance with an automatically generated file path.
     * Initializes all components (UI, storage, parser, task list) and loads any existing tasks.
     * The file path is generated using a counter to ensure unique storage locations for multiple instances.
     */
    public Bob() {
        this.tasks = new TaskList();
        this.parser = new Parser();

        counter++;
        String filePath = FILE_PATH_BASE + counter + "/data.txt";
        this.storage = new Storage(filePath, tasks);
    }

    /**
     * Creates a new Bob application instance with a specified file path.
     * Initializes all components (UI, storage, parser, task list) and loads any existing tasks
     * from the specified location.
     *
     * @param filePath The specific file path where tasks should be stored and loaded from
     */
    public Bob(String filePath) {
        this.tasks = new TaskList();
        this.parser = new Parser();
        this.storage = new Storage(filePath, tasks);
    }

    /**
     * Processes user input and generates an appropriate response.
     * This method handles the core logic of:
     * 1. Splitting the input into tokens
     * 2. Parsing the tokens into a command
     * 3. Executing the command
     * 4. Handling any errors that occur during processing
     *
     * @param userInput The raw input string from the user
     * @return A response string that contains either the command execution result or an error message
     */
    public String getResponse(String userInput) {
        try {
            String[] splitUserInput;
            if (userInput.isEmpty()) {
                splitUserInput = new String[0];
            } else {
                splitUserInput = userInput.split(" ");
            }
            Command cmd = parser.parseUserInput(splitUserInput);
            if (cmd instanceof ExitCommand) {
                isActive = false;
            }
            String response = cmd.execute(tasks, storage);
            assert response != null : "All commands should return a response";
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Checks if the Bob application is still running.
     * This status changes to false when an ExitCommand is processed.
     *
     * @return true if the application is still running, false if it should terminate
     */
    public boolean isActive() {
        return isActive;
    }
}