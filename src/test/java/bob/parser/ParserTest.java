package bob.parser;

import bob.command.*;
import bob.exceptions.IllegalCommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(result instanceof EmptyInputCommand);
    }

    @Test
    void parseUserInput_exitCommand_returnsExitCommand() throws IllegalCommandException {
        String[] input = {"bye"};
        Command result = parser.parseUserInput(input);
        assertTrue(result instanceof ExitCommand);
    }

    @Test
    void parseUserInput_listCommand_returnsListCommand() throws IllegalCommandException {
        String[] input = {"list"};
        Command result = parser.parseUserInput(input);
        assertTrue(result instanceof ListCommand);
    }

    @Test
    void parseUserInput_markCommand_returnsMarkCommand() throws IllegalCommandException {
        String[] input = {"mark", "1"};
        Command result = parser.parseUserInput(input);
        assertTrue(result instanceof MarkCommand);
    }

    @Test
    void parseUserInput_unmarkCommand_returnsUnmarkCommand() throws IllegalCommandException {
        String[] input = {"unmark", "1"};
        Command result = parser.parseUserInput(input);
        assertTrue(result instanceof UnmarkCommand);
    }

    @Test
    void parseUserInput_todoCommand_returnsCreateTodoCommand() throws IllegalCommandException {
        String[] input = {"todo", "Buy groceries"};
        Command result = parser.parseUserInput(input);
        assertTrue(result instanceof CreateTodoCommand);
    }

    @Test
    void parseUserInput_deadlineCommand_returnsCreateDeadlineCommand() throws IllegalCommandException {
        String[] input = {"deadline", "Submit report", "2025-02-15"};
        Command result = parser.parseUserInput(input);
        assertTrue(result instanceof CreateDeadlineCommand);
    }

    @Test
    void parseUserInput_eventCommand_returnsCreateEventCommand() throws IllegalCommandException {
        String[] input = {"event", "Team meeting", "2025-02-15", "2025-02-16"};
        Command result = parser.parseUserInput(input);
        assertTrue(result instanceof CreateEventCommand);
    }

    @Test
    void parseUserInput_deleteCommand_returnsDeleteCommand() throws IllegalCommandException {
        String[] input = {"delete", "1"};
        Command result = parser.parseUserInput(input);
        assertTrue(result instanceof DeleteCommand);
    }

    @Test
    void parseUserInput_invalidCommand_throwsIllegalCommandException() {
        String[] input = {"invalid"};
        assertThrows(IllegalCommandException.class, () -> parser.parseUserInput(input));
    }
}