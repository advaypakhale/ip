package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

public class EchoCommand extends Command {
    public EchoCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IllegalCommandException {
        ui.wrapText(String.join(" ", userInput));
    }
}
