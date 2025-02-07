package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

import java.io.IOException;

public class UnmarkCommand extends Command {
    public UnmarkCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        if (userInput.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the unmark bob.command is 'unmark <index>'. Please try again!");
        }

        int idx;
        try {
            idx = Integer.parseInt(userInput[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalCommandException(
                    "I'm sorry, the index of the bob.task to unmark must be a number. The proper usage of the unmark bob.command is 'unmark <index>'. Please try again!");
        }

        try {
            boolean valid = tasks.markAsUndone(idx);
            if (!valid) {
                throw new IllegalCommandException(
                        "I'm sorry, the bob.task you are trying to mark as undone is already not done. You can mark it as done or enter another bob.command!");
            }
        } catch (IndexOutOfBoundsException e) {
            if (tasks.size() == 0) {
                throw new IllegalCommandException(
                        "I'm sorry, you have no tasks in your list to mark as undone. Please add some tasks first!");
            } else {
                throw new IllegalCommandException("I'm sorry, the number of the bob.task to mark must be within 1 and "
                        + tasks.size() + ". Please try again!");
            }
        }

        message.append("I have marked this bob.task as not done, get on it!\n").append(tasks.getTaskString(idx));
        storage.save();
        ui.wrapText(message);
    }
}
