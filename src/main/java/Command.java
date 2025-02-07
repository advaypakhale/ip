import java.io.IOException;

public abstract class Command {
    protected StringBuilder message;
    protected String[] userInput;
    public Command(String[] userInput) {
        this.message = new StringBuilder();
        this.userInput = userInput;
    }
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException;
}
class ExitCommand extends Command {
    public ExitCommand(String[] userInput) {
        super(userInput);
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        if (userInput.length != 1) {
            throw new IllegalCommandException("I'm sorry, the command 'bye' does not take any arguments. Please try again!");
        }
        message.append("Goodbye! Have a great day!");
        storage.save();
        ui.wrapText(message);
    }
}
class EchoCommand extends Command {
    public EchoCommand(String[] userInput) {
        super(userInput);
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IllegalCommandException {
        ui.wrapText(String.join(" ", userInput));
    }
}

class ListCommand extends Command {
    public ListCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IllegalCommandException {
        if (userInput.length != 1) {
            throw new IllegalCommandException("I'm sorry, the command 'list' does not take any arguments. Please try again!");
        }

        if (tasks.size() == 0) {
            message.append("You have no tasks in your list! Use the 'todo', 'deadline', or 'event' commands to add a task.");
        } else {
            message.append("Here are the items in your list:\n");
            for (int i = 0; i < tasks.size(); ++i) {
                message.append(i + 1).append(". ").append(tasks.getTaskString(i)).append("\n");
            }
        }
        ui.wrapText(message);
    }
}

class MarkCommand extends Command {
    public MarkCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        if (userInput.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the mark command is 'mark <index>'. Please try again!");
        }

        int idx;
        try {
            idx = Integer.parseInt(userInput[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalCommandException(
                    "I'm sorry, the index of the task to mark must be a number. The proper usage of the mark command is 'mark <index>'. Please try again!");
        }

        try {
            boolean valid = tasks.markAsDone(idx);
            if (!valid) {
                throw new IllegalCommandException(
                        "I'm sorry, the task you are trying to mark as done is already done. You can mark it as undone or enter another command!");
            }
        } catch (IndexOutOfBoundsException e) {
            if (tasks.size() == 0) {
                throw new IllegalCommandException(
                        "I'm sorry, you have no tasks in your list to mark as done. Please add some tasks first!");
            } else {
                throw new IllegalCommandException("I'm sorry, the number of the task to mark must be within 1 and "
                        + tasks.size() + ". Please try again!");
            }
        }

        message.append("Nice! I've marked this task as done:\n").append(tasks.getTaskString(idx));
        storage.save();
        ui.wrapText(message);
    }
}

class UnmarkCommand extends Command {
    public UnmarkCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        if (userInput.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the unmark command is 'unmark <index>'. Please try again!");
        }

        int idx;
        try {
            idx = Integer.parseInt(userInput[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalCommandException(
                    "I'm sorry, the index of the task to unmark must be a number. The proper usage of the unmark command is 'unmark <index>'. Please try again!");
        }

        try {
            boolean valid = tasks.markAsUndone(idx);
            if (!valid) {
                throw new IllegalCommandException(
                        "I'm sorry, the task you are trying to mark as undone is already not done. You can mark it as done or enter another command!");
            }
        } catch (IndexOutOfBoundsException e) {
            if (tasks.size() == 0) {
                throw new IllegalCommandException(
                        "I'm sorry, you have no tasks in your list to mark as undone. Please add some tasks first!");
            } else {
                throw new IllegalCommandException("I'm sorry, the number of the task to mark must be within 1 and "
                        + tasks.size() + ". Please try again!");
            }
        }

        message.append("I have marked this task as not done, get on it!\n").append(tasks.getTaskString(idx));
        storage.save();
        ui.wrapText(message);
    }
}

class CreateTodoCommand extends Command {
    public CreateTodoCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        String description = "";
        for (int i = 1; i < userInput.length; i++) {
            description += userInput[i] + " ";
        }
        description = description.trim();

        if (description.isEmpty()) {
            throw new IllegalCommandException(
                    "I'm sorry, the description of a to-do item cannot be empty. The proper usage of the todo command is 'todo <description>'. Please try again!");
        }

        Task newTask = new Todo(description);
        tasks.addTask(newTask);

        message.append("I've added a to-do item: \n").append(newTask.toString());
        storage.save();
        ui.wrapText(message);
    }
}

class CreateDeadlineCommand extends Command {
    public CreateDeadlineCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        String arguments = "";
        for (int i = 1; i < userInput.length; i++) {
            arguments += userInput[i] + " ";
        }
        arguments = arguments.trim();

        String[] splitArguments = arguments.split("/by");
        if (splitArguments.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the deadline command is 'deadline <description> /by <due>'. Please try again!");
        }

        String description = splitArguments[0].trim();
        String due = splitArguments[1].trim();

        Task newTask = new Deadline(description, due);
        tasks.addTask(newTask);

        message.append("I have added a new deadline to your calendar: \n").append(newTask.toString());
        storage.save();
        ui.wrapText(message);
    }
}

class CreateEventCommand extends Command {
    public CreateEventCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        String arguments = "";
        for (int i = 1; i < userInput.length; i++) {
            arguments += userInput[i] + " ";
        }
        arguments = arguments.trim();

        String[] splitArguments = arguments.split("/from");
        if (splitArguments.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the event command is 'event <description> /from <start> /to <end>'. Please try again!");
        }

        String description = splitArguments[0].trim();
        String[] startEnd = splitArguments[1].split("/to");
        if (startEnd.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the event command is 'event <description> /from <start> /to <end>'. Please try again!");
        }

        String start = startEnd[0].trim();
        String end = startEnd[1].trim();

        Task newTask = new Event(description, start, end);
        tasks.addTask(newTask);

        message.append("I have added a new event to your calendar: \n").append(newTask.toString());
        storage.save();
        ui.wrapText(message);
    }
}

class DeleteCommand extends Command {
    public DeleteCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, IllegalCommandException {
        if (userInput.length != 2) {
            throw new IllegalCommandException(
                    "I'm sorry, the proper usage of the delete command is 'delete <index>'. Please try again!");
        }

        int idx;
        try {
            idx = Integer.parseInt(userInput[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalCommandException(
                    "I'm sorry, the index of the task to delete must be a number. The proper usage of the delete command is 'delete <index>'. Please try again!");
        }

        try {
            String removedTask = tasks.deleteTask(idx);
            message.append("I have removed this task from your list:\n").append(removedTask);
        } catch (IndexOutOfBoundsException e) {
            if (tasks.size() == 0) {
                throw new IllegalCommandException(
                        "I'm sorry, you have no tasks in your list to delete. Please add some tasks first!");
            } else {
                throw new IllegalCommandException("I'm sorry, the number of the task to delete must be within 1 and "
                        + tasks.size() + ". Please try again!");
            }
        }

        storage.save();
        ui.wrapText(message);
    }
}

class EmptyInputCommand extends Command {
    public EmptyInputCommand(String[] userInput) {
        super(userInput);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.wrapText("Please enter a command. I'm happy to help!");
    }
}



                    
