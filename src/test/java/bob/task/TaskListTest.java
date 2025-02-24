package bob.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void addTask_withValidTask_increasesSize() {
        Todo task = new Todo("Test task");
        taskList.addTask(task);
        assertEquals(1, taskList.size());
    }

    @Test
    void getTaskString_withValidIndex_returnsCorrectString() {
        Todo task = new Todo("Test task");
        taskList.addTask(task);
        String taskString = taskList.getTaskString(0);
        assertTrue(taskString.contains("Test task"));
        assertTrue(taskString.contains("[T]"));
    }

    @Test
    void markAsDone_withValidIndex_marksTask() {
        Todo task = new Todo("Test task");
        taskList.addTask(task);

        assertTrue(taskList.markAsDone(0));
        assertTrue(taskList.getTaskString(0).contains("[X]"));
    }

    @Test
    void markAsDone_withAlreadyDoneTask_returnsFalse() {
        Todo task = new Todo("Test task");
        taskList.addTask(task);

        taskList.markAsDone(0);
        assertFalse(taskList.markAsDone(0));
    }

    @Test
    void markAsUndone_withValidIndex_unmarksTask() {
        Todo task = new Todo("Test task");
        taskList.addTask(task);
        taskList.markAsDone(0);

        assertTrue(taskList.markAsUndone(0));
        assertTrue(taskList.getTaskString(0).contains("[ ]"));
    }

    @Test
    void markAsUndone_withAlreadyUndoneTask_returnsFalse() {
        Todo task = new Todo("Test task");
        taskList.addTask(task);

        assertFalse(taskList.markAsUndone(0));
    }

    @Test
    void deleteTask_withValidIndex_removesTask() {
        Todo task = new Todo("Test task");
        taskList.addTask(task);

        String deletedTask = taskList.deleteTask(0);
        assertEquals(0, taskList.size());
        assertTrue(deletedTask.contains("Test task"));
    }

    @Test
    void clear_withTasks_removesAllTasks() {
        taskList.addTask(new Todo("Task 1"));
        taskList.addTask(new Todo("Task 2"));
        taskList.addTask(new Todo("Task 3"));

        taskList.clear();
        assertEquals(0, taskList.size());
    }

    @Test
    void iterator_returnsCorrectNumberOfTasks() {
        taskList.addTask(new Todo("Task 1"));
        taskList.addTask(new Todo("Task 2"));

        int count = 0;
        for (Task task : taskList) {
            count++;
        }
        assertEquals(2, count);
    }
}