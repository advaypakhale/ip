package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

import java.io.IOException;

public class DeleteCommand extends Command {
    public DeleteCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        if (userInput.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the delete bob.command is 'delete <index>'. Please try again!");
        }

        int idx;
        try {
            idx = Integer.parseInt(userInput[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalCommandException(
                    "I'm sorry, the index of the bob.task to delete must be a number. The proper usage of the delete bob.command is 'delete <index>'. Please try again!");
        }

        if (tasks.size() == 0) {
            throw new IllegalCommandException(
                    "I'm sorry, you have no tasks in your list to delete. Please add some tasks first!");
        }
        if (idx < 0 || idx >= tasks.size()) {
            throw new IllegalCommandException("I'm sorry, the number of the bob.task to delete must be within 1 and "
                    + tasks.size() + ". Please try again!");
        }

        String removedTask = tasks.deleteTask(idx);
        message.append("I have removed this bob.task from your list:\n").append(removedTask);

        storage.save();
        ui.wrapText(message);
    }
}
