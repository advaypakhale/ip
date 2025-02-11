package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.Deadline;
import bob.task.Task;
import bob.task.TaskList;
import bob.ui.Ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a command to create a new deadline task.
 * This command expects the user input to contain a description and a due date in the format 'YYYY-MM-DD'.
 * The command will parse the user input, create a new Deadline task, add it to the task list, and save the updated task list to storage.
 * If the user input is invalid, an IllegalCommandException will be thrown.
 */
public class CreateDeadlineCommand extends Command {
    /**
     * Constructs a CreateDeadlineCommand with the specified user input.
     *
     * @param userInput The array of strings representing the user's input.
     */
    public CreateDeadlineCommand(String[] userInput) {
        super(userInput);
    }

    /**
     * Executes the CreateDeadlineCommand by parsing the user input to create a new Deadline task.
     * The command expects the input to be in the format: "deadline <description> /by <due>".
     * The due date must be in the format YYYY-MM-DD.
     *
     * @param tasks   The TaskList object that stores all tasks.
     * @param ui      The Ui object that handles user interaction.
     * @param storage The Storage object that handles saving and loading tasks from the file.
     * @throws IOException             If there is an error during saving tasks to the file.
     * @throws IllegalCommandException If the user input is invalid or the date format is incorrect.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        String arguments = "";
        for (int i = 1; i < userInput.length; i++) {
            arguments += userInput[i] + " ";
        }
        arguments = arguments.trim();

        String[] splitArguments = arguments.split("/by");
        if (splitArguments.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the deadline bob.command is 'deadline <description> /by <due>'. Please try again!");
        }

        String description = splitArguments[0].trim();
        String dateStr = splitArguments[1].trim();

        LocalDate dueDate;
        try {
            dueDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalCommandException(
                    "I'm sorry, the date must be in YYYY-MM-DD format. For example: 2025-12-31. Please try again!");
        }

        Task newTask = new Deadline(description, dueDate);
        tasks.addTask(newTask);

        message.append("I have added a new deadline to your calendar: \n").append(newTask);
        storage.save();
        ui.wrapText(message);
    }
}
