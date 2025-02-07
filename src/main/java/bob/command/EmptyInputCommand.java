package bob.command;

import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

public class EmptyInputCommand extends Command {
    public EmptyInputCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.wrapText("Please enter a bob.command. I'm happy to help!");
    }
}
