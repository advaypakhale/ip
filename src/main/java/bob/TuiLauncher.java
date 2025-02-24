package bob;

import bob.engine.Bob;
import bob.tui.Ui;

/**
 * Main class that serves as the entry point and controller for the Bob task
 * management application.
 * Coordinates between the UI, storage, parser and task components to provide
 * task management functionality.
 */
public class TuiLauncher {
    /**
     * Handles user interface interactions
     */
    private final Ui ui;

    private final Bob bob;

    /**
     * Creates a new Bob application instance.
     * Initializes all components (UI, storage, parser, task list) and loads any
     * existing tasks.
     */
    public TuiLauncher() {
        this.ui = new Ui();
        this.bob = new Bob();
    }

    /**
     * Entry point of the application.
     * Creates a launcher instance and starts the application loop.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        TuiLauncher launcher = new TuiLauncher();
        launcher.run();
    }

    /**
     * Main application loop.
     * Displays welcome message and repeatedly:
     * 1. Gets user input
     * 2. Passes user input to bob engine
     * 3. Prints retrieved response from Bob
     * 4. Exits if Bob is no longer active (bye command was used)
     */
    public void run() {
        ui.greet();
        while (bob.isActive()) {
            String userInput = ui.getUserInput();
            String response = bob.getResponse(userInput);
            ui.wrapText(response);
        }
    }
}
