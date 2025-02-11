package bob.command;

import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;
import bob.ui.Ui;

import java.util.ArrayList;

public class FindCommand extends Command {
    public FindCommand(String[] userInput) {
        super(userInput);
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws IllegalArgumentException {
        String query = "";
        for (int i = 1; i < userInput.length; i++) {
            query += userInput[i] + " ";
        }
        query = query.trim();
        if (query.isEmpty()) {
            throw new IllegalArgumentException("I'm sorry, the proper usage of the find command is 'find <query>. Please try again.'");
        }

        ArrayList<Task> matchedTasks = tasks.findTask(query);

        if (matchedTasks.size() == 0) {
            message.append("I'm sorry, no tasks matched with the given query. Please try again with another query.");
        } else {
            message.append("I found " + matchedTasks.size() + " matching task(s), here they are below!\n");
            for (Task task : matchedTasks) {
                message.append(task.toString() + "\n");
            }
        }
        ui.wrapText(message);
    }
}
