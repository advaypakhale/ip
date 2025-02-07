package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;
import bob.task.Todo;
import bob.ui.Ui;

import java.io.IOException;

public class CreateTodoCommand extends Command {
    public CreateTodoCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        String description = "";
        for (int i = 1; i < userInput.length; i++) {
            description += userInput[i] + " ";
        }
        description = description.trim();

        if (description.isEmpty()) {
            throw new IllegalCommandException(
                    "I'm sorry, the description of a to-do item cannot be empty. The proper usage of the todo bob.command is 'todo <description>'. Please try again!");
        }

        Task newTask = new Todo(description);
        tasks.addTask(newTask);

        message.append("I've added a to-do item: \n").append(newTask.toString());
        storage.save();
        ui.wrapText(message);
    }
}
