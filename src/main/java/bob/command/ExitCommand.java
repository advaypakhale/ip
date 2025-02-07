package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

import java.io.IOException;

public class ExitCommand extends Command {
    public ExitCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        if (userInput.length != 1) {
            throw new IllegalCommandException(
                    "I'm sorry, the bob.command 'bye' does not take any arguments. Please try again!");
        }
        message.append("Goodbye! Have a great day!");
        storage.save();
        ui.wrapText(message);
    }
}
