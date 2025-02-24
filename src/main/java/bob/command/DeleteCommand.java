package bob.command;

import java.io.IOException;

import bob.exception.IllegalCommandException;
import bob.storage.Storage;
import bob.task.TaskList;

/**
 * Represents a command to delete a task from the task list. The command
 * requires an index parameter to specify which task to delete.
 */
public class DeleteCommand extends Command {
    /**
     * Constructs a DeleteCommand with the given user input array.
     *
     * @param userInput An array of strings containing the command parameters. The
     *                  first element is the command name, and subsequent elements
     *                  are the command arguments.
     */
    public DeleteCommand(String[] userInput) {
        super(userInput);
    }

    /**
     * Executes the delete command to remove a task from the task list. The command
     * format should be "delete <index>" where index is a valid task number.
     *
     * @param tasks   The TaskList containing all tasks
     * @param storage The Storage object to save changes to file
     * @return A string containing the success message or an error message
     * @throws IOException             If there is an error saving to storage
     * @throws IllegalCommandException If the command format is invalid, index is
     *                                 not a number, task list is empty, or index is
     *                                 out of bounds
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws IOException, IllegalCommandException {
        if (userInput.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the delete bob.command is 'delete <index>'. Please try again!");
        }

        int idx;
        try {
            idx = Integer.parseInt(userInput[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalCommandException(
                    "I'm sorry, the index of the task to delete must be a number. The proper usage of the delete command is 'delete <index>'. Please try again!");
        }

        if (tasks.size() == 0) {
            throw new IllegalCommandException(
                    "I'm sorry, you have no tasks in your list to delete. Please add some tasks first!");
        }
        if (idx < 0 || idx >= tasks.size()) {
            throw new IllegalCommandException("I'm sorry, the number of the task to delete must be within 1 and "
                    + tasks.size() + ". Please try again!");
        }

        String removedTask = tasks.deleteTask(idx);
        assert removedTask != null : "Task to delete should not be null";
        message.append("I have removed this task from your list:\n").append(removedTask);

        storage.save();
        return message.toString();
    }
}
