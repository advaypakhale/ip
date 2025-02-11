package bob.task;

/**
* Represents a basic todo task without any date constraints.
* Extends the base Task class with simple todo-specific formatting.
*/
public class Todo extends Task {
   
   /**
    * Creates a new Todo task with the specified description.
    *
    * @param description the description of the todo task
    */
   public Todo(String description) {
       super(description);
   }
   
   /**
    * Returns a string representation of this Todo task.
    * The format is: [T][Status] Description
    *
    * @return formatted string representation of the todo task
    */
   @Override
   public String toString() {
       return "[T]" + super.toString();
   }
   
   /**
    * Converts this Todo task to a string format suitable for file storage.
    * The format is: T | completion_status | description
    *
    * @return string representation for file storage
    */
   @Override
   public String toFileString() {
       return "T | " + (isComplete ? "Y" : "N") + " | " + description;
   }
}