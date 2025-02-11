package bob.task;

import java.time.LocalDate;

/**
 * Represents a task with a specific deadline date.
 * Extends the base Task class by adding due date functionality.
 */
public class Deadline extends Task {
    /** The due date for this deadline task */
    private LocalDate due;
    
    /**
     * Creates a new Deadline task with the specified description and due date.
     *
     * @param description the description of the task
     * @param due the date by which the task must be completed
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
}