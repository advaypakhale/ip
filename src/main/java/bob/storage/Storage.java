package bob.storage;

import bob.task.*;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final String filePath;
    private final TaskList tasks;

    public Storage(String filePath, TaskList tasks) {
        this.filePath = filePath;
        this.tasks = tasks;
        load();
    }

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

    public void load() {
        Path path = Path.of(filePath);
        if (!Files.exists(path)) {
            return;
        }

        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String line : lines) {
            try {
                String[] parts = line.split(" \\| ");
                if (parts.length < 3) {
                    continue;
                }

                String type = parts[0];
                boolean isComplete = parts[1].equals("Y");
                String description = parts[2];

                Task task = switch (type) {
                    case "T" -> new Todo(description);
                    case "D" -> {
                        try {
                            LocalDate deadline = LocalDate.parse(parts[3]);
                            yield new Deadline(description, deadline);
                        } catch (DateTimeParseException e) {
                            System.err.println("Warning: Skipping malformed deadline date in file: " + parts[3]);
                            yield null;
                        }
                    }
                    case "E" -> {
                        try {
                            LocalDate startDate = LocalDate.parse(parts[3]);
                            LocalDate endDate = LocalDate.parse(parts[4]);
                            yield new Event(description, startDate, endDate);
                        } catch (DateTimeParseException e) {
                            System.err.println("Warning: Skipping malformed event dates in file: " +
                                    parts[3] + " to " + parts[4]);
                            yield null;
                        }
                    }
                    default -> null;
                };

                if (task != null) {
                    if (isComplete) {
                        task.markAsDone();
                    }
                    tasks.addTask(task);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Warning: Skipping malformed line in file: " + line);
            }
        }
    }
}
