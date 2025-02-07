package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

import java.io.IOException;

public class MarkCommand extends Command {
    public MarkCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        if (userInput.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the mark bob.command is 'mark <index>'. Please try again!");
        }

        int idx;
        try {
            idx = Integer.parseInt(userInput[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalCommandException(
                    "I'm sorry, the index of the bob.task to mark must be a number. The proper usage of the mark bob.command is 'mark <index>'. Please try again!");
        }

        try {
            boolean valid = tasks.markAsDone(idx);
            if (!valid) {
                throw new IllegalCommandException(
                        "I'm sorry, the bob.task you are trying to mark as done is already done. You can mark it as undone or enter another bob.command!");
            }
        } catch (IndexOutOfBoundsException e) {
            if (tasks.size() == 0) {
                throw new IllegalCommandException(
                        "I'm sorry, you have no tasks in your list to mark as done. Please add some tasks first!");
            } else {
                throw new IllegalCommandException("I'm sorry, the number of the bob.task to mark must be within 1 and "
                        + tasks.size() + ". Please try again!");
            }
        }

        message.append("Nice! I've marked this bob.task as done:\n").append(tasks.getTaskString(idx));
        storage.save();
        ui.wrapText(message);
    }
}
