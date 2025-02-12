package bob.command;

import bob.exception.IllegalCommandException;
import bob.storage.Storage;
import bob.task.TaskList;

import java.io.IOException;

/**
 * Represents a command to unmark a task as done in the task list.
 * This command takes an index as input and marks the corresponding task as undone.
 */
public class UnmarkCommand extends Command {
    /**
     * Constructor for UnmarkCommand to unmark a task as done.
     *
     * @param userInput Array of strings containing the command and its parameters
     */
    public UnmarkCommand(String[] userInput) {
        super(userInput);
    }

    /**
     * Executes the unmark command which marks a task as not done.
     * The command format should be 'unmark <index>'.
     *
     * @param tasks   The task list containing all tasks
     * @param storage The storage to save tasks
     * @return A string containing the success message or an error message
     * @throws IOException             If there's an error saving to storage
     * @throws IllegalCommandException If the command format is invalid, if the index is not a number,
     *                                 if the task is already undone, if the task list is empty, or if the index is out of bounds
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws IOException, IllegalCommandException {
        if (userInput.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the unmark command is 'unmark <index>'. Please try again!");
        }

        int idx;
        try {
            idx = Integer.parseInt(userInput[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalCommandException(
                    "I'm sorry, the index of the task to unmark must be a number. The proper usage of the unmark command is 'unmark <index>'. Please try again!");
        }

        try {
            boolean valid = tasks.markAsUndone(idx);
            if (!valid) {
                throw new IllegalCommandException(
                        "I'm sorry, the task you are trying to mark as undone is already not done. You can mark it as done or enter another command!");
            }
        } catch (IndexOutOfBoundsException e) {
            if (tasks.size() == 0) {
                throw new IllegalCommandException(
                        "I'm sorry, you have no tasks in your list to mark as undone. Please add some tasks first!");
            } else {
                throw new IllegalCommandException("I'm sorry, the number of the task to mark must be within 1 and "
                        + tasks.size() + ". Please try again!");
            }
        }

        message.append("I have marked this task as not done, get on it!\n").append(tasks.getTaskString(idx));
        storage.save();
        return message.toString();
    }
}
