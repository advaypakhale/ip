package bob.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bob.command.Command;
import bob.command.CreateDeadlineCommand;
import bob.command.CreateEventCommand;
import bob.command.CreateTodoCommand;
import bob.command.DeleteCommand;
import bob.command.EmptyInputCommand;
import bob.command.ExitCommand;
import bob.command.ListCommand;
import bob.command.MarkCommand;
import bob.command.UnmarkCommand;
import bob.exception.IllegalCommandException;

public class ParserTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    @Test
    void parseUserInput_emptyInput_returnsEmptyInputCommand() throws IllegalCommandException {
        String[] emptyInput = new String[0];
        Command result = parser.parseUserInput(emptyInput);
        assertInstanceOf(EmptyInputCommand.class, result);
    }

    @Test
    void parseUserInput_exitCommand_returnsExitCommand() throws IllegalCommandException {
        String[] input = { "bye" };
        Command result = parser.parseUserInput(input);
        assertInstanceOf(ExitCommand.class, result);
    }

    @Test
    void parseUserInput_listCommand_returnsListCommand() throws IllegalCommandException {
        String[] input = { "list" };
        Command result = parser.parseUserInput(input);
        assertInstanceOf(ListCommand.class, result);
    }

    @Test
    void parseUserInput_markCommand_returnsMarkCommand() throws IllegalCommandException {
        String[] input = { "mark", "1" };
        Command result = parser.parseUserInput(input);
        assertInstanceOf(MarkCommand.class, result);
    }

    @Test
    void parseUserInput_unmarkCommand_returnsUnmarkCommand() throws IllegalCommandException {
        String[] input = { "unmark", "1" };
        Command result = parser.parseUserInput(input);
        assertInstanceOf(UnmarkCommand.class, result);
    }

    @Test
    void parseUserInput_todoCommand_returnsCreateTodoCommand() throws IllegalCommandException {
        String[] input = { "todo", "Buy groceries" };
        Command result = parser.parseUserInput(input);
        assertInstanceOf(CreateTodoCommand.class, result);
    }

    @Test
    void parseUserInput_deadlineCommand_returnsCreateDeadlineCommand() throws IllegalCommandException {
        String[] input = { "deadline", "Submit report", "2025-02-15" };
        Command result = parser.parseUserInput(input);
        assertInstanceOf(CreateDeadlineCommand.class, result);
    }

    @Test
    void parseUserInput_eventCommand_returnsCreateEventCommand() throws IllegalCommandException {
        String[] input = { "event", "Team meeting", "2025-02-15", "2025-02-16" };
        Command result = parser.parseUserInput(input);
        assertInstanceOf(CreateEventCommand.class, result);
    }

    @Test
    void parseUserInput_deleteCommand_returnsDeleteCommand() throws IllegalCommandException {
        String[] input = { "delete", "1" };
        Command result = parser.parseUserInput(input);
        assertInstanceOf(DeleteCommand.class, result);
    }

    @Test
    void parseUserInput_invalidCommand_throwsIllegalCommandException() {
        String[] input = { "invalid" };
        assertThrows(IllegalCommandException.class, () -> parser.parseUserInput(input));
    }
}