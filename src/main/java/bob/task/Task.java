package bob.task;

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
        return (isComplete ? "X" : " "); // mark done bob.task with X
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

    public String getDescription() {
        return description;
    }

    protected String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public abstract String toFileString();
}

