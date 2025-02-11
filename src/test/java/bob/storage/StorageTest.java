package bob.storage;

import bob.task.Deadline;
import bob.task.Event;
import bob.task.TaskList;
import bob.task.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageTest {
    @TempDir
    Path tempDir;

    private Path storageFile;
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storageFile = tempDir.resolve("tasks.txt");
        taskList = new TaskList();
        storage = new Storage(storageFile.toString(), taskList);
    }

    @Test
    void save_withTodoTask_writesCorrectFormat() throws IOException {
        Todo todo = new Todo("Buy groceries");
        taskList.addTask(todo);

        storage.save();

        List<String> lines = Files.readAllLines(storageFile);
        assertEquals(1, lines.size());
        assertEquals("T | N | Buy groceries", lines.get(0));
    }

    @Test
    void save_withDeadlineTask_writesCorrectFormat() throws IOException {
        LocalDate deadline = LocalDate.of(2025, 2, 15);
        Deadline deadlineTask = new Deadline("Submit report", deadline);
        taskList.addTask(deadlineTask);

        storage.save();

        List<String> lines = Files.readAllLines(storageFile);
        assertEquals(1, lines.size());
        assertEquals("D | N | Submit report | 2025-02-15", lines.get(0));
    }

    @Test
    void save_withEventTask_writesCorrectFormat() throws IOException {
        LocalDate startDate = LocalDate.of(2025, 2, 15);
        LocalDate endDate = LocalDate.of(2025, 2, 16);
        Event event = new Event("Team meeting", startDate, endDate);
        taskList.addTask(event);

        storage.save();

        List<String> lines = Files.readAllLines(storageFile);
        assertEquals(1, lines.size());
        assertEquals("E | N | Team meeting | 2025-02-15 | 2025-02-16", lines.get(0));
    }

    @Test
    void load_withValidTodoTask_loadsCorrectly() throws IOException {
        Files.write(storageFile, List.of("T | N | Buy groceries"));

        storage.load();

        assertEquals(1, taskList.size());
        assertTrue(taskList.getTaskString(0).contains("Buy groceries"));
    }

    @Test
    void load_withValidDeadlineTask_loadsCorrectly() throws IOException {
        Files.write(storageFile, List.of("D | N | Submit report | 2025-02-15"));

        storage.load();

        assertEquals(1, taskList.size());
        assertTrue(taskList.getTaskString(0).contains("Submit report"));
        assertTrue(taskList.getTaskString(0).contains("15 Feb 2025"));
    }

    @Test
    void load_withValidEventTask_loadsCorrectly() throws IOException {
        Files.write(storageFile, List.of("E | N | Team meeting | 2025-02-15 | 2025-02-16"));

        storage.load();

        assertEquals(1, taskList.size());
        assertTrue(taskList.getTaskString(0).contains("Team meeting"));
        assertTrue(taskList.getTaskString(0).contains("15 Feb 2025"));
        assertTrue(taskList.getTaskString(0).contains("16 Feb 2025"));
    }

    @Test
    void load_withMalformedLine_skipsLine() throws IOException {
        Files.write(storageFile, List.of(
                "T | N | Valid todo",
                "Invalid line",
                "T | N | Another valid todo"
        ));

        storage.load();

        assertEquals(2, taskList.size());
    }

    @Test
    void load_withMalformedDate_skipsTask() throws IOException {
        Files.write(storageFile, List.of(
                "D | N | Report | invalid-date",
                "T | N | Valid todo"
        ));

        storage.load();

        assertEquals(1, taskList.size());
        assertTrue(taskList.getTaskString(0).contains("Valid todo"));
    }

    @Test
    void load_withCompletedTask_setsCorrectStatus() throws IOException {
        Files.write(storageFile, List.of("T | Y | Completed todo"));

        storage.load();

        assertEquals(1, taskList.size());
        assertTrue(taskList.getTaskString(0).contains("[X]"));
    }
}