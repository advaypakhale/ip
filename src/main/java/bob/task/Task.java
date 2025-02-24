package bob.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Abstract base class for all task types in the system.
 * Provides common functionality for task management including completion
 * status,
 * description, and date formatting.
 */
public abstract class Task implements Comparable<Task> {
    /**
     * Formatter for converting dates to string representation
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    /**
     * The description of the task
     */
    protected String description;
    /**
     * The completion status of the task
     */
    protected boolean isComplete;

    /**
     * Creates a new task with the given description.
     * Tasks are initialized as incomplete.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isComplete = false;
    }

    /**
     * Returns the status icon for the task's completion state.
     * "X" for completed tasks, space for incomplete tasks.
     *
     * @return the status icon string
     */
    protected String getStatusIcon() {
        return (isComplete ? "✓" : " ");
    }

    /**
     * Returns a string representation of the task.
     * The format is: [Status] Description
     *
     * @return formatted string representation of the task
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Marks the task as complete if it is not already completed.
     *
     * @return true if the task was successfully marked as complete,
     *         false if it was already complete
     */
    public boolean markAsDone() {
        if (this.isComplete) {
            return false;
        } else {
            this.isComplete = true;
            return true;
        }
    }

    /**
     * Marks the task as incomplete if it is currently completed.
     *
     * @return true if the task was successfully marked as incomplete,
     *         false if it was already incomplete
     */
    public boolean markAsUndone() {
        if (!this.isComplete) {
            return false;
        } else {
            this.isComplete = false;
            return true;
        }
    }

    /**
     * Formats a date using the system's standard date format (dd MMM yyyy).
     *
     * @param date the date to format
     * @return formatted string representation of the date
     */
    protected String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * Converts the task to a string format suitable for file storage.
     * Implementation varies by task type.
     *
     * @return string representation for file storage
     */
    public abstract String toFileString();

    /**
     * Returns the description of the task.
     *
     * @return description of task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a comparison date for implementing compareTo method.
     * Implementation varies by task type.
     *
     * @return comparison date for task
     */
    public abstract LocalDate getComparisonDate();
}
