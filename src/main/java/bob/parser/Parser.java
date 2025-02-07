package bob.parser;

import bob.command.*;
import bob.exceptions.IllegalCommandException;

public class Parser {
    public Command parseUserInput(String[] userInput) throws IllegalCommandException {
        if (userInput.length == 0) {
            return new EmptyInputCommand(userInput);
        }

        String commandString = userInput[0];

        return switch (commandString) {
            case "bye" -> new ExitCommand(userInput);
            case "list" -> new ListCommand(userInput);
            case "mark" -> new MarkCommand(userInput);
            case "unmark" -> new UnmarkCommand(userInput);
            case "todo" -> new CreateTodoCommand(userInput);
            case "deadline" -> new CreateDeadlineCommand(userInput);
            case "event" -> new CreateEventCommand(userInput);
            case "delete" -> new DeleteCommand(userInput);
            default -> throw new IllegalCommandException("I'm sorry, I don't understand that bob.command. Please try with one of the following commands: bye, list, mark, unmark, todo, deadline, event.");
        };
    }
}
