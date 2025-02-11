package bob.command;

import bob.exceptions.IllegalCommandException;
import bob.storage.Storage;
import bob.task.Deadline;
import bob.task.Event;
import bob.task.TaskList;
import bob.task.Todo;
import bob.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommandTest {
    @Mock
    private TaskList mockTaskList;
    @Mock
    private Ui mockUi;
    @Mock
    private Storage mockStorage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTodoCommand() throws IOException, IllegalCommandException {
        // Test valid todo creation
        String[] validInput = {"todo", "Buy groceries"};
        CreateTodoCommand command = new CreateTodoCommand(validInput);
        command.execute(mockTaskList, mockUi, mockStorage);

        verify(mockTaskList).addTask(any(Todo.class));
        verify(mockStorage).save();
        verify(mockUi).wrapText(any(StringBuilder.class));

        // Test empty todo description
        String[] emptyInput = {"todo"};
        CreateTodoCommand emptyCommand = new CreateTodoCommand(emptyInput);
        assertThrows(IllegalCommandException.class,
                () -> emptyCommand.execute(mockTaskList, mockUi, mockStorage));
    }

    @Test
    void testCreateDeadlineCommand() throws IOException, IllegalCommandException {
        // Test valid deadline creation
        String[] validInput = {"deadline", "Submit report", "/by", "2025-12-31"};
        CreateDeadlineCommand command = new CreateDeadlineCommand(validInput);
        command.execute(mockTaskList, mockUi, mockStorage);

        verify(mockTaskList).addTask(any(Deadline.class));
        verify(mockStorage).save();
        verify(mockUi).wrapText(any(StringBuilder.class));

        // Test invalid date format
        String[] invalidDateInput = {"deadline", "Submit report", "/by", "31-12-2025"};
        CreateDeadlineCommand invalidCommand = new CreateDeadlineCommand(invalidDateInput);
        assertThrows(IllegalCommandException.class,
                () -> invalidCommand.execute(mockTaskList, mockUi, mockStorage));

        // Test missing /by
        String[] missingByInput = {"deadline", "Submit report", "2025-12-31"};
        CreateDeadlineCommand missingByCommand = new CreateDeadlineCommand(missingByInput);
        assertThrows(IllegalCommandException.class,
                () -> missingByCommand.execute(mockTaskList, mockUi, mockStorage));
    }

    @Test
    void testCreateEventCommand() throws IOException, IllegalCommandException {
        // Test valid event creation
        String[] validInput = {"event", "Team meeting", "/from", "2025-12-01", "/to", "2025-12-02"};
        CreateEventCommand command = new CreateEventCommand(validInput);
        command.execute(mockTaskList, mockUi, mockStorage);

        verify(mockTaskList).addTask(any(Event.class));
        verify(mockStorage).save();
        verify(mockUi).wrapText(any(StringBuilder.class));

        // Test invalid date order (end before start)
        String[] invalidDateOrderInput = {"event", "Team meeting", "/from", "2025-12-02", "/to",
                "2025-12-01"};
        CreateEventCommand invalidCommand = new CreateEventCommand(invalidDateOrderInput);
        assertThrows(IllegalCommandException.class,
                () -> invalidCommand.execute(mockTaskList, mockUi, mockStorage));

        // Test missing /from or /to
        String[] missingFromToInput = {"event", "Team meeting", "2025-12-01", "2025-12-02"};
        CreateEventCommand missingCommand = new CreateEventCommand(missingFromToInput);
        assertThrows(IllegalCommandException.class,
                () -> missingCommand.execute(mockTaskList, mockUi, mockStorage));
    }

    @Test
    void testListCommand() throws IllegalCommandException {
        // Test empty list
        when(mockTaskList.size()).thenReturn(0);
        String[] input = {"list"};
        ListCommand command = new ListCommand(input);
        command.execute(mockTaskList, mockUi, mockStorage);

        verify(mockUi).wrapText(any(StringBuilder.class));

        // Test list with extra arguments
        String[] invalidInput = {"list", "extra", "args"};
        ListCommand invalidCommand = new ListCommand(invalidInput);
        assertThrows(IllegalCommandException.class,
                () -> invalidCommand.execute(mockTaskList, mockUi, mockStorage));
    }

    @Test
    void testMarkCommand() throws IOException, IllegalCommandException {
        // Test valid marking
        when(mockTaskList.size()).thenReturn(1);
        when(mockTaskList.markAsDone(0)).thenReturn(true);

        String[] validInput = {"mark", "1"};
        MarkCommand command = new MarkCommand(validInput);
        command.execute(mockTaskList, mockUi, mockStorage);

        verify(mockTaskList).markAsDone(0);
        verify(mockStorage).save();
        verify(mockUi).wrapText(any(StringBuilder.class));

        // Test marking already marked task
        when(mockTaskList.markAsDone(0)).thenReturn(false);
        assertThrows(IllegalCommandException.class, () -> command.execute(mockTaskList, mockUi, mockStorage));

        // Test invalid index
        String[] invalidInput = {"mark", "abc"};
        MarkCommand invalidCommand = new MarkCommand(invalidInput);
        assertThrows(IllegalCommandException.class,
                () -> invalidCommand.execute(mockTaskList, mockUi, mockStorage));
    }

    @Test
    void testUnmarkCommand() throws IOException, IllegalCommandException {
        // Test valid unmarking
        when(mockTaskList.size()).thenReturn(1);
        when(mockTaskList.markAsUndone(0)).thenReturn(true);

        String[] validInput = {"unmark", "1"};
        UnmarkCommand command = new UnmarkCommand(validInput);
        command.execute(mockTaskList, mockUi, mockStorage);

        verify(mockTaskList).markAsUndone(0);
        verify(mockStorage).save();
        verify(mockUi).wrapText(any(StringBuilder.class));

        // Test unmarking already unmarked task
        when(mockTaskList.markAsUndone(0)).thenReturn(false);
        assertThrows(IllegalCommandException.class, () -> command.execute(mockTaskList, mockUi, mockStorage));
    }

    @Test
    void testDeleteCommand() throws IOException, IllegalCommandException {
        // Test valid deletion
        when(mockTaskList.size()).thenReturn(1);
        when(mockTaskList.deleteTask(0)).thenReturn("Deleted task");

        String[] validInput = {"delete", "1"};
        DeleteCommand command = new DeleteCommand(validInput);
        command.execute(mockTaskList, mockUi, mockStorage);

        verify(mockTaskList).deleteTask(0);
        verify(mockStorage).save();
        verify(mockUi).wrapText(any(StringBuilder.class));

        // Test invalid index
        String[] invalidInput = {"delete", "0"};
        DeleteCommand invalidCommand = new DeleteCommand(invalidInput);
        when(mockTaskList.size()).thenReturn(0);
        assertThrows(IllegalCommandException.class,
                () -> invalidCommand.execute(mockTaskList, mockUi, mockStorage));
    }

    @Test
    void testExitCommand() throws IOException, IllegalCommandException {
        // Test valid exit
        String[] validInput = {"bye"};
        ExitCommand command = new ExitCommand(validInput);
        command.execute(mockTaskList, mockUi, mockStorage);

        verify(mockStorage).save();
        verify(mockUi).wrapText(any(StringBuilder.class));

        // Test exit with arguments
        String[] invalidInput = {"bye", "extra"};
        ExitCommand invalidCommand = new ExitCommand(invalidInput);
        assertThrows(IllegalCommandException.class,
                () -> invalidCommand.execute(mockTaskList, mockUi, mockStorage));
    }

    @Test
    void testEchoCommand() throws IllegalCommandException {
        String[] input = {"echo", "Hello", "World"};
        EchoCommand command = new EchoCommand(input);
        command.execute(mockTaskList, mockUi, mockStorage);

        verify(mockUi).wrapText("echo Hello World");
    }

    @Test
    void testEmptyInputCommand() {
        String[] input = {""};
        EmptyInputCommand command = new EmptyInputCommand(input);
        command.execute(mockTaskList, mockUi, mockStorage);

        verify(mockUi).wrapText("Please enter a bob.command. I'm happy to help!");
    }
}