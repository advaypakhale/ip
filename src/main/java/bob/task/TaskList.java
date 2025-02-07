package bob.task;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskList implements Iterable<Task> {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public String getTaskString(int index) {
        return tasks.get(index).toString();
    }

    public int size() {
        return tasks.size();
    }

    public boolean markAsDone(int index) {
        return tasks.get(index).markAsDone();
    }

    public boolean markAsUndone(int index) {
        return tasks.get(index).markAsUndone();
    }

    public String deleteTask(int index) {
        return tasks.remove(index).toString();
    }

    public void clear() {
        tasks.clear();
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
