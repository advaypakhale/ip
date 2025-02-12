package bob.command;

import bob.exception.IllegalCommandException;
import bob.storage.Storage;
import bob.task.Event;
import bob.task.Task;
import bob.task.TaskList;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a command to create an event task.
 * This command parses user input to extract the event description, start date, and end date,
 * and then adds the event to the task list if the input is valid.
 */
public class CreateEventCommand extends Command {
    /**
     * Creates a new CreateEventCommand instance.
     *
     * @param userInput An array of strings containing the user's input parameters for creating an event
     */
    public CreateEventCommand(String[] userInput) {
        super(userInput);
    }

    /**
     * Executes the create event command by parsing the user input and creating a new Event task.
     * The event requires a description and start/end dates in ISO format (YYYY-MM-DD).
     *
     * @param tasks   The task list to add the new event to
     * @param storage The storage handler for saving tasks
     * @return A string containing the success message or an error message
     * @throws IOException             If there is an error saving to storage
     * @throws IllegalCommandException If the command format is invalid, dates are in wrong format,
     *                                 or end date is before start date
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws IOException, IllegalCommandException {
        String arguments = "";
        for (int i = 1; i < userInput.length; i++) {
            arguments += userInput[i] + " ";
        }
        arguments = arguments.trim();

        String[] splitArguments = arguments.split("/from");
        if (splitArguments.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the event command is 'event <description> /from <start> /to <end>'. Please try again!");
        }

        String description = splitArguments[0].trim();
        String[] startEnd = splitArguments[1].split("/to");
        if (startEnd.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the event command is 'event <description> /from <start> /to <end>'. Please try again!");
        }

        String startDateStr = startEnd[0].trim();
        String endDateStr = startEnd[1].trim();

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalCommandException(
                    "I'm sorry, the start date must be in YYYY-MM-DD format. For example: 2025-12-31. Please try again!");
        }

        try {
            endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalCommandException(
                    "I'm sorry, the end date must be in YYYY-MM-DD format. For example: 2025-12-31. Please try again!");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalCommandException(
                    "I'm sorry, the end date cannot be before the start date. Please try again!");
        }

        Task newTask = new Event(description, startDate, endDate);
        tasks.addTask(newTask);

        message.append("I have added a new event to your calendar: \n").append(newTask);
        storage.save();
        return message.toString();
    }
}
