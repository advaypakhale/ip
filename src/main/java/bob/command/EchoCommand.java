package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * A command that echoes back the user's input text.
 * Simply joins all input words with spaces and displays them through the UI.
 */
public class EchoCommand extends Command {

    /**
     * Creates a new echo command with the given user input.
     *
     * @param userInput array of strings containing the text to echo
     */
    public EchoCommand(String[] userInput) {
        super(userInput);
    }

    /**
     * Executes the echo command by joining all input words and displaying them.
     * The output is wrapped according to the UI's text wrapping settings.
     *
     * @param tasks   the task list (unused for this command)
     * @param ui      the UI object used to display the echoed text
     * @param storage the storage object (unused for this command)
     * @throws IllegalCommandException if there is an error executing the command
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IllegalCommandException {
        ui.wrapText(String.join(" ", userInput));
    }
}