package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.Event;
import bob.task.Task;
import bob.task.TaskList;
import bob.ui.Ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreateEventCommand extends Command {
    public CreateEventCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        String arguments = "";
        for (int i = 1; i < userInput.length; i++) {
            arguments += userInput[i] + " ";
        }
        arguments = arguments.trim();

        String[] splitArguments = arguments.split("/from");
        if (splitArguments.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the event bob.command is 'event <description> /from <start> /to <end>'. Please try again!");
        }

        String description = splitArguments[0].trim();
        String[] startEnd = splitArguments[1].split("/to");
        if (startEnd.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the event bob.command is 'event <description> /from <start> /to <end>'. Please try again!");
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

        message.append("I have added a new event to your calendar: \n").append(newTask.toString());
        storage.save();
        ui.wrapText(message);
    }
}
