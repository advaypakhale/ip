package bob.command;

import bob.exception.IllegalCommandException;
import bob.storage.Storage;
import bob.task.Task;
import bob.task.TaskList;
import bob.task.Todo;

import java.io.IOException;

/**
 * Represents a command to create a new Todo task.
 * This command processes user input to create and add a Todo task to the task
 * list.
 */
public class CreateTodoCommand extends Command {
    /**
     * Constructs a new CreateTodoCommand with the given user input.
     *
     * @param userInput Array of strings containing the command and task description
     */
    public CreateTodoCommand(String[] userInput) {
        super(userInput);
    }

    /**
     * Executes the create todo command by creating a new Todo task and adding it to
     * the task list.
     * The task description is constructed from the user input, excluding the
     * command word.
     * The new task is then saved to storage and a confirmation message is displayed
     * to the user.
     *
     * @param tasks   The task list to add the new todo task to
     * @param storage The storage system to save the updated task list
     * @return A string containing the success message or an error message
     * @throws IOException             If there's an error saving to storage
     * @throws IllegalCommandException If the todo description is empty
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws IOException, IllegalCommandException {
        String description = "";
        for (int i = 1; i < userInput.length; i++) {
            description += userInput[i] + " ";
        }
        description = description.trim();

        if (description.isEmpty()) {
            throw new IllegalCommandException(
                    "I'm sorry, the description of a to-do item cannot be empty. The proper usage of the todo command is 'todo <description>'. Please try again!");
        }

        Task newTask = new Todo(description);
        tasks.addTask(newTask);

        message.append("I've added a to-do item: \n").append(newTask);
        storage.save();
        return message.toString();
    }
}
