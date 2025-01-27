public class Task {
    protected String description;
    protected boolean isComplete;

    public Task(String description) {
        this.description = description;
        this.isComplete = false;
    }

    public String getStatusIcon() {
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
