package bob.command;

import bob.exception.IllegalCommandException;
import bob.storage.Storage;
import bob.task.TaskList;

import java.io.IOException;

/**
 * Represents a command to exit the application.
 * This command saves all tasks and terminates the program.
 */
public class ExitCommand extends Command {
    /**
     * Constructs a new ExitCommand with the given user input.
     *
     * @param userInput Array of strings containing the command and its arguments
     */
    public ExitCommand(String[] userInput) {
        super(userInput);
    }

    /**
     * Executes the exit command. Saves all tasks and displays goodbye message.
     * Throws an exception if any arguments are provided with the 'bye' command.
     *
     * @param tasks   The task list containing all tasks
     * @param storage The storage object for saving tasks
     * @return A string containing the success message or an error message
     * @throws IOException             If there is an error saving the tasks
     * @throws IllegalCommandException If arguments are provided with the exit
     *                                 command
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws IOException, IllegalCommandException {
        if (userInput.length != 1) {
            throw new IllegalCommandException(
                    "I'm sorry, the command 'bye' does not take any arguments. Please try again!");
        }
        message.append("Goodbye! Have a great day!");
        storage.save();
        return message.toString();
    }
}
