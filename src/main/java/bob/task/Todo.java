package bob.task;

import java.time.LocalDate;

/**
 * Represents a basic todo task without any date constraints.
 * Extends the base Task class with simple todo-specific formatting.
 */
public class Todo extends Task {

    private static final LocalDate DATE = LocalDate.MAX;

    /**
     * Creates a new Todo task with the specified description.
     *
     * @param description the description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of this Todo task.
     * The format is: [T][Status] Description
     *
     * @return formatted string representation of the todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Converts this Todo task to a string format suitable for file storage.
     * The format is: T | completion_status | description
     *
     * @return string representation for file storage
     */
    @Override
    public String toFileString() {
        return "T | " + (isComplete ? "Y" : "N") + " | " + description;
    }

    /**
     * Returns the date to use for comparison when sorting tasks.
     * For Todo tasks, this is the maximum possible date.
     *
     * @return the comparison date for sorting
     */
    @Override
    public LocalDate getComparisonDate() {
        return DATE;
    }

    /**
     * Compares this Todo task with another task for sorting purposes.
     *
     * @param other the task to compare with
     * @return a negative integer, zero, or a positive integer as this task is less
     *         than, equal to, or greater than the other task
     */
    @Override
    public int compareTo(Task other) {
        if (this.isComplete && !other.isComplete) {
            return 1;
        } else if (!this.isComplete && other.isComplete) {
            return -1;
        } else {
            int dateComparison = this.getComparisonDate().compareTo(other.getComparisonDate());

            if (dateComparison != 0) {
                return dateComparison;
            }
            return this.description.compareTo(other.description);
        }
    }
}