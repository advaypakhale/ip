package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

/**
 * Represents a command to display all tasks in the task list.
 * This command does not take any additional arguments.
 */
public class ListCommand extends Command {
    /**
     * Constructs a new ListCommand with the given user input.
     *
     * @param userInput Array of strings containing the command and its arguments
     */

    public ListCommand(String[] userInput) {
        super(userInput);
    }

    /**
     * Executes the list command to display all tasks in the task list.
     * If the list is empty, displays a message indicating no tasks.
     * Otherwise, displays a numbered list of all tasks.
     *
     * @param tasks The TaskList containing all tasks
     * @param ui The user interface to display messages
     * @param storage The storage manager for tasks
     * @throws IllegalCommandException If the command is used with arguments
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IllegalCommandException {
        if (userInput.length != 1) {
            throw new IllegalCommandException(
                    "I'm sorry, the bob.command 'list' does not take any arguments. Please try again!");
        }

        if (tasks.size() == 0) {
            message.append(
                    "You have no tasks in your list! Use the 'todo', 'deadline', or 'event' commands to add a bob.task.");
        } else {
            message.append("Here are the items in your list:\n");
            for (int i = 0; i < tasks.size(); ++i) {
                message.append(i + 1).append(". ").append(tasks.getTaskString(i)).append("\n");
            }
        }
        ui.wrapText(message);
    }
}
