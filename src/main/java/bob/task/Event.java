package bob.task;

import java.time.LocalDate;

/**
 * Represents a task that occurs over a specific time period with start and end
 * dates. Extends the base Task class by adding event duration functionality.
 */
public class Event extends Task {
    /**
     * The start date of the event
     */
    private final LocalDate begin;

    /**
     * The end date of the event
     */
    private final LocalDate end;

    /**
     * Creates a new Event task with the specified description and date range.
     *
     * @param description the description of the event
     * @param begin       the start date of the event
     * @param end         the end date of the event
     */
    public Event(String description, LocalDate begin, LocalDate end) {
        super(description);
        this.begin = begin;
        this.end = end;
    }

    /**
     * Returns a string representation of this Event task. The format is:
     * [E][Status] Description (Event start: formatted_start_date | Event end:
     * formatted_end_date)
     *
     * @return formatted string representation of the event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (Event start: " + formatDate(begin) + " | Event end: " + formatDate(end)
                + ")";
    }

    /**
     * Converts this Event task to a string format suitable for file storage. The
     * format is: E | completion_status | description | ISO_start_date |
     * ISO_end_date
     *
     * @return string representation for file storage
     */
    @Override
    public String toFileString() {
        return "E | " + (isComplete ? "Y" : "N") + " | " + description + " | " + begin.toString() + " | "
                + end.toString();
    }

    /**
     * Returns the date to use for comparison when sorting tasks. For Event tasks,
     * this is the start date of the event.
     *
     * @return the date to use for comparison
     */
    @Override
    public LocalDate getComparisonDate() {
        return begin;
    }

    /**
     * Compares this Event task with another task for sorting purposes.
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

            if (other instanceof Event) {
                Event otherEvent = (Event) other;
                return this.end.compareTo(otherEvent.end) == 0 ? this.description.compareTo(other.description)
                        : this.end.compareTo(otherEvent.end);
            } else {
                return 1; // other must be a Deadline task
            }
        }
    }
}
