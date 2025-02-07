package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

import java.io.IOException;

public abstract class Command {
    protected StringBuilder message;
    protected String[] userInput;

    public Command(String[] userInput) {
        this.message = new StringBuilder();
        this.userInput = userInput;
    }

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException;
}

