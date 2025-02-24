package bob.command;

import bob.storage.Storage;
import bob.task.TaskList;

/**
 * A command class that handles empty user input. This command is triggered when
 * the user enters nothing or only whitespace.
 */
public class EmptyInputCommand extends Command {
    /**
     * A command that handles empty input from the user. Extends the base Command
     * class.
     *
     * @param userInput Array of strings containing the user's input tokens
     */
    public EmptyInputCommand(String[] userInput) {
        super(userInput);
    }

    /**
     * Executes the empty input command by displaying a message prompting the user
     * to enter a command.
     *
     * @param tasks   The task list containing all tasks
     * @param storage The storage object used to save/load tasks
     * @return A string containing the success message or an error message
     */
    @Override
    public String execute(TaskList tasks, Storage storage) {
        assert userInput.length == 0 : "EmptyInputCommand should not have any user input";
        return "Please enter a command. I'm happy to help!";
    }
}
