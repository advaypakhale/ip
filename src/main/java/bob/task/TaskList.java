package bob.task;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A container class that manages a collection of tasks.
 * Provides methods for adding, removing, and manipulating tasks.
 * Implements Iterable to allow iteration over the contained tasks.
 */
public class TaskList implements Iterable<Task> {
    /**
     * The underlying list storing all tasks
     */
    private final ArrayList<Task> tasks;

    /**
     * Creates a new empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Adds a new task to the list.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Gets the string representation of a task at the specified index.
     *
     * @param index the index of the task
     * @return string representation of the task
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public String getTaskString(int index) {
        return tasks.get(index).toString();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Marks the task at the specified index as complete.
     *
     * @param index the index of the task to mark as complete
     * @return true if the task was successfully marked as complete,
     * false if it was already complete
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public boolean markAsDone(int index) {
        return tasks.get(index).markAsDone();
    }

    /**
     * Marks the task at the specified index as incomplete.
     *
     * @param index the index of the task to mark as incomplete
     * @return true if the task was successfully marked as incomplete,
     * false if it was already incomplete
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public boolean markAsUndone(int index) {
        return tasks.get(index).markAsUndone();
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index the index of the task to remove
     * @return string representation of the removed task
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public String deleteTask(int index) {
        return tasks.remove(index).toString();
    }

    /**
     * Removes all tasks from the list.
     */
    public void clear() {
        tasks.clear();
    }

    /**
     * Returns an iterator over the tasks in this list.
     *
     * @return an iterator over the tasks
     */
    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }

    /**
     * Searches for tasks whose descriptions contain the specified query string.
     * The search is case-insensitive.
     *
     * @param query The search string to match against task descriptions
     * @return An ArrayList containing all tasks whose descriptions contain the query string
     * Returns an empty ArrayList if no matching tasks are found
     * @throws NullPointerException if the query parameter is null
     * @see Task
     */
    public ArrayList<Task> findTask(String query) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(query.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}
