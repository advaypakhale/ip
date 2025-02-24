package bob.task;

import java.time.LocalDate;

/**
 * Represents a task with a specific deadline date.
 * Extends the base Task class by adding due date functionality.
 */
public class Deadline extends Task {
    /**
     * The due date for this deadline task
     */
    private final LocalDate due;

    /**
     * Creates a new Deadline task with the specified description and due date.
     *
     * @param description the description of the task
     * @param due         the date by which the task must be completed
     */
    public Deadline(String description, LocalDate due) {
        super(description);
        this.due = due;
    }

    /**
     * Returns a string representation of this Deadline task.
     * The format is: [D][Status] Description (Deadline: formatted_date)
     *
     * @return formatted string representation of the deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (Deadline: " + formatDate(due) + ")";
    }

    /**
     * Converts this Deadline task to a string format suitable for file storage.
     * The format is: D | completion_status | description | ISO_date
     *
     * @return string representation for file storage
     */
    @Override
    public String toFileString() {
        return "D | " + (isComplete ? "Y" : "N") + " | " + description + " | " + due.toString();
    }

    /**
     * Returns the date to use for comparison when sorting tasks.
     * For Deadline tasks, this is the due date.
     *
     * @return the comparison date for sorting
     */
    @Override
    public LocalDate getComparisonDate() {
        return due;
    }

    /**
     * Compares this Deadline task with another task for sorting purposes.
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

            if (other instanceof Deadline) {
                return this.description.compareTo(other.description);
            } else {
                return -1; // other must be a Event task
            }
        }
    }
}