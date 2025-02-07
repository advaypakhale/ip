package bob.task;

import java.time.LocalDate;

public class Event extends Task {
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
