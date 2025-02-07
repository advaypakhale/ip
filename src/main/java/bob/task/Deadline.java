package bob.task;

import java.time.LocalDate;

public class Deadline extends Task {
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
