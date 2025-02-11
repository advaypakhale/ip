package bob.task;

import java.time.LocalDate;

/**
 * Represents a task that occurs over a specific time period with start and end dates.
 * Extends the base Task class by adding event duration functionality.
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
     * Returns a string representation of this Event task.
     * The format is: [E][Status] Description (Event start: formatted_start_date | Event end: formatted_end_date)
     *
     * @return formatted string representation of the event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (Event start: " + formatDate(begin) + " | Event end: " + formatDate(end)
                + ")";
    }

    /**
     * Converts this Event task to a string format suitable for file storage.
     * The format is: E | completion_status | description | ISO_start_date | ISO_end_date
     *
     * @return string representation for file storage
     */
    @Override
    public String toFileString() {
        return "E | " + (isComplete ? "Y" : "N") + " | " + description + " | " + begin.toString() + " | "
                + end.toString();
    }
}