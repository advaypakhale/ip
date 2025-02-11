package bob.storage;

import bob.task.*;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the persistence of tasks to and from a file storage system.
 * Tasks are stored in a text file with one task per line using the following formats:
 * <ul>
 *   <li>Todo tasks: "T | [Y/N] | description"</li>
 *   <li>Deadline tasks: "D | [Y/N] | description | YYYY-MM-DD"</li>
 *   <li>Event tasks: "E | [Y/N] | description | YYYY-MM-DD | YYYY-MM-DD"</li>
 * </ul>
 * where Y/N indicates completion status (Y=completed, N=incomplete)
 */
public class Storage {
    /** The path to the file where tasks are stored */
    private final String filePath;
    
    /** The task list containing all tasks to be managed */
    private final TaskList tasks;

    /**
     * Creates a new Storage instance and immediately loads any existing tasks.
     * 
     * @param filePath the path to the file where tasks will be stored
     * @param tasks the TaskList that will contain the loaded tasks
     */
    public Storage(String filePath, TaskList tasks) {
        this.filePath = filePath;
        this.tasks = tasks;
        load();
    }

    /**
     * Saves all tasks from the task list to the storage file.
     * Creates any necessary parent directories if they don't exist.
     * Overwrites the existing file if it already exists.
     *
     * @throws IOException if there is an error writing to the file or creating directories
     */
    public void save() throws IOException {
        Path path = Path.of(filePath);
        Files.createDirectories(path.getParent());
        List<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(task.toFileString());
        }
        Files.write(path, lines, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Loads tasks from the storage file into the task list.
     * Skips any malformed lines in the file with a warning message.
     * The file should contain one task per line in the following format:
     * <pre>
     * TYPE | COMPLETION_STATUS | DESCRIPTION [| DATE1 [| DATE2]]
     * </pre>
     * where:
     * <ul>
     *   <li>TYPE is T (Todo), D (Deadline), or E (Event)</li>
     *   <li>COMPLETION_STATUS is Y (completed) or N (incomplete)</li>
     *   <li>DESCRIPTION is the task description</li>
     *   <li>DATE1 is required for Deadline (due date) and Event (start date)</li>
     *   <li>DATE2 is required for Event (end date)</li>
     * </ul>
     * 
     * @throws RuntimeException if there is an error reading the file
     */
    public void load() {
        // ... implementation remains the same ...
    }
}