package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

public class ListCommand extends Command {
    public ListCommand(String[] userInput) {
        super(userInput);
    }

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
