import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    protected String description;
    protected boolean isComplete;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public Task(String description) {
        this.description = description;
        this.isComplete = false;
    }

    protected String getStatusIcon() {
        return (isComplete ? "X" : " "); // mark done task with X
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public boolean markAsDone() {
        if (this.isComplete) {
            return false;
        } else {
            this.isComplete = true;
            return true;
        }
    }

    public boolean markAsUndone() {
        if (!this.isComplete) {
            return false;
        } else {
            this.isComplete = false;
            return true;
        }
    }

    protected String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public abstract String toFileString();
}

class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileString() {
        return "T | " + (isComplete ? "Y" : "N") + " | " + description;
    }
}

class Deadline extends Task {
    private LocalDate due;

    public Deadline(String description, LocalDate due) {
        super(description);
        this.due = due;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (Deadline: " + formatDate(due) + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (isComplete ? "Y" : "N") + " | " + description + " | " + due.toString(); // Store in ISO format
    }
}

class Event extends Task {
    private LocalDate begin;
    private LocalDate end;

    public Event(String description, LocalDate begin, LocalDate end) {
        super(description);
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (Event start: " + formatDate(begin) + " | Event end: " + formatDate(end)
                + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + (isComplete ? "Y" : "N") + " | " + description + " | " + begin.toString() + " | "
                + end.toString(); // Store in ISO format
    }
}
