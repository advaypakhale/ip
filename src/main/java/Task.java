public class Task {
    protected String description;
    protected boolean isComplete;

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

    public void markAsDone() {
        this.isComplete = true;
    }

    public void markAsUndone() {
        this.isComplete = false;
    }
}

class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public String toString() {
        return "[T]" + super.toString();
    }
}

class Deadline extends Task {
    private String due;

    public Deadline(String description, String due) {
        super(description);
        this.due = due;
    }

    public String toString() {
        return "[D]" + super.toString() + " (Deadline: " + due + ")";
    }
}

class Event extends Task {
    private String begin;
    private String end;

    public Event(String description, String begin, String end) {
        super(description);
        this.begin = begin;
        this.end = end;
    }

    public String toString() {
        return "[E]" + super.toString() + " (Event start: " + begin + " | Event end: " + end + ")";
    }
}