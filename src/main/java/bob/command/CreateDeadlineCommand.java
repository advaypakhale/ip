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

public class CreateDeadlineCommand extends Command {
    public CreateDeadlineCommand(String[] userInput) {
        super(userInput);
    }

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

        message.append("I have added a new deadline to your calendar: \n").append(newTask.toString());
        storage.save();
        ui.wrapText(message);
    }
}
