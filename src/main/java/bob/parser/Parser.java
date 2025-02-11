package bob.parser;

import bob.command.*;
import bob.exceptions.IllegalCommandException;

/**
* Parses user input strings into executable Command objects.
* Validates and interprets user commands, creating the appropriate command object
* based on the command type specified in the input.
*/
public class Parser {

   /**
    * Converts user input into a concrete Command object for execution.
    * The first word in the input array determines the type of command created:
    * <ul>
    *   <li>bye - Creates ExitCommand to terminate the program</li>
    *   <li>list - Creates ListCommand to display all tasks</li>
    *   <li>mark - Creates MarkCommand to mark a task as complete</li>
    *   <li>unmark - Creates UnmarkCommand to mark a task as incomplete</li>
    *   <li>todo - Creates CreateTodoCommand to add a new todo task</li>
    *   <li>deadline - Creates CreateDeadlineCommand to add a new deadline task</li>
    *   <li>event - Creates CreateEventCommand to add a new event task</li>
    *   <li>delete - Creates DeleteCommand to remove a task</li>
    * </ul>
    * Empty input creates an EmptyInputCommand.
    *
    * @param userInput array containing the command type as first element followed by any parameters
    * @return appropriate Command object based on the input
    * @throws IllegalCommandException if the command type is not recognized
    */
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