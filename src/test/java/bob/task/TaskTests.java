package bob.task;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskTests {
    @Nested
    class TodoTest {
        @Test
        void constructor_setsDescription() {
            Todo todo = new Todo("Test todo");
            assertTrue(todo.toString().contains("Test todo"));
        }

        @Test
        void toString_containsCorrectFormat() {
            Todo todo = new Todo("Test todo");
            String expected = "[T][ ] Test todo";
            assertEquals(expected, todo.toString());
        }

        @Test
        void toFileString_unmarkedTask_returnsCorrectFormat() {
            Todo todo = new Todo("Test todo");
            assertEquals("T | N | Test todo", todo.toFileString());
        }

        @Test
        void toFileString_markedTask_returnsCorrectFormat() {
            Todo todo = new Todo("Test todo");
            todo.markAsDone();
            assertEquals("T | Y | Test todo", todo.toFileString());
        }
    }

    @Nested
    class DeadlineTest {
        @Test
        void constructor_setsDescriptionAndDate() {
            LocalDate deadline = LocalDate.of(2025, 2, 15);
            Deadline task = new Deadline("Test deadline", deadline);
            String taskString = task.toString();
            assertTrue(taskString.contains("Test deadline"));
            assertTrue(taskString.contains("15 Feb 2025"));
        }

        @Test
        void toString_containsCorrectFormat() {
            LocalDate deadline = LocalDate.of(2025, 2, 15);
            Deadline task = new Deadline("Test deadline", deadline);
            String expected = "[D][ ] Test deadline (Deadline: 15 Feb 2025)";
            assertEquals(expected, task.toString());
        }

        @Test
        void toFileString_unmarkedTask_returnsCorrectFormat() {
            LocalDate deadline = LocalDate.of(2025, 2, 15);
            Deadline task = new Deadline("Test deadline", deadline);
            assertEquals("D | N | Test deadline | 2025-02-15", task.toFileString());
        }

        @Test
        void toFileString_markedTask_returnsCorrectFormat() {
            LocalDate deadline = LocalDate.of(2025, 2, 15);
            Deadline task = new Deadline("Test deadline", deadline);
            task.markAsDone();
            assertEquals("D | Y | Test deadline | 2025-02-15", task.toFileString());
        }
    }

    @Nested
    class EventTest {
        private final LocalDate startDate = LocalDate.of(2025, 2, 15);
        private final LocalDate endDate = LocalDate.of(2025, 2, 16);

        @Test
        void constructor_setsDescriptionAndDates() {
            Event event = new Event("Test event", startDate, endDate);
            String eventString = event.toString();
            assertTrue(eventString.contains("Test event"));
            assertTrue(eventString.contains("15 Feb 2025"));
            assertTrue(eventString.contains("16 Feb 2025"));
        }

        @Test
        void toString_containsCorrectFormat() {
            Event event = new Event("Test event", startDate, endDate);
            String expected = "[E][ ] Test event (Event start: 15 Feb 2025 | Event end: 16 Feb 2025)";
            assertEquals(expected, event.toString());
        }

        @Test
        void toFileString_unmarkedTask_returnsCorrectFormat() {
            Event event = new Event("Test event", startDate, endDate);
            assertEquals("E | N | Test event | 2025-02-15 | 2025-02-16", event.toFileString());
        }

        @Test
        void toFileString_markedTask_returnsCorrectFormat() {
            Event event = new Event("Test event", startDate, endDate);
            event.markAsDone();
            assertEquals("E | Y | Test event | 2025-02-15 | 2025-02-16", event.toFileString());
        }
    }

    @Nested
    class TaskCommonBehaviorTest {
        @Test
        void markAsDone_unmarkedTask_returnsTrue() {
            Task task = new TestTask("Test task");
            assertTrue(task.markAsDone());
        }

        @Test
        void markAsDone_alreadyMarkedTask_returnsFalse() {
            Task task = new TestTask("Test task");
            task.markAsDone();
            assertFalse(task.markAsDone());
        }

        @Test
        void markAsUndone_markedTask_returnsTrue() {
            Task task = new TestTask("Test task");
            task.markAsDone();
            assertTrue(task.markAsUndone());
        }

        @Test
        void markAsUndone_unmarkedTask_returnsFalse() {
            Task task = new TestTask("Test task");
            assertFalse(task.markAsUndone());
        }

        @Test
        void toString_reflectsCompletionStatus() {
            Task task = new TestTask("Test task");
            assertTrue(task.toString().contains("[ ]"));
            task.markAsDone();
            assertTrue(task.toString().contains("[X]"));
        }

        private static class TestTask extends Task {
            public TestTask(String description) {
                super(description);
            }

            @Override
            public String toFileString() {
                return null;
            }
        }
    }
}