package bob.command;

import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;

import java.util.ArrayList;

/**
 * Represents a command to search for tasks matching a given query string.
 * This command extends the base Command class and implements functionality
 * to search through a task list for specific tasks.
 */
public class FindCommand extends Command {

    /**
     * Constructs a new FindCommand with the given user input array.
     * The first element of the array is expected to be the command name "find",
     * followed by the search query terms.
     *
     * @param userInput An array of strings containing the command and search terms
     */
    public FindCommand(String[] userInput) {
        super(userInput);
    }

    /**
     * Executes the find command by searching for tasks that match the given query.
     * The query is constructed by joining all words after the command name.
     * If matching tasks are found, they are displayed in a list.
     *
     * @param tasks   The TaskList object containing all tasks to search through
     * @param storage The Storage object (unused in this implementation)
     * @return A string containing the search results or an error message
     * @throws IllegalArgumentException if the search query is empty
     */
    public String execute(TaskList tasks, Storage storage) throws IllegalArgumentException {
        String query = "";
        for (int i = 1; i < userInput.length; i++) {
            query += userInput[i] + " ";
        }
        query = query.trim();

        if (query.isEmpty()) {
            throw new IllegalArgumentException(
                    "I'm sorry, the proper usage of the find command is 'find <query>. Please try again.'");
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
        return message.toString();
    }
}
