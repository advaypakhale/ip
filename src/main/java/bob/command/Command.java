package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

import java.io.IOException;

/**
 * Represents an abstract command that can be executed.
 * Each command has a message output and user input associated with it.
 */
public abstract class Command {
    protected StringBuilder message;
    protected String[] userInput;

    /**
     * Constructs a Command object with the specified user input.
     *
     * @param userInput An array of strings representing the user's input.
     */
    public Command(String[] userInput) {
        this.message = new StringBuilder();
        this.userInput = userInput;
    }

    /**
     * Executes the command with the given task list, UI, and storage.
     *
     * @param tasks   The task list to be manipulated by the command.
     * @param ui      The UI to interact with the user.
     * @param storage The storage to save or load data.
     * @throws IOException             If an input or output error occurs.
     * @throws IllegalCommandException If the command is illegal or cannot be executed.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException;
}

