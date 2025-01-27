import java.util.Scanner;
import java.util.ArrayList;

public class Bob {

    private Boolean isActive;
    private final Scanner sc;
    private ArrayList<Task> tasks;

    public Bob() {
        this.isActive = true;
        this.sc = new Scanner(System.in);
        this.tasks = new ArrayList<>();
    }

    public static void main(String[] args) {
        Bob bob = new Bob();
        bob.run();
    }

    public void run() {
        greet();

        while (isActive) {
            try {
                String[] userInput = getUserInput();
                processUserInput(userInput);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String[] getUserInput() {
        System.out.print(">>> ");

        String nextInput = sc.nextLine();

        String[] userInput;

        if (nextInput.isEmpty()) {
            userInput = new String[0];
        } else {
            userInput = nextInput.split(" ");
        }

        return userInput;
    }

    public void processUserInput(String[] userInput) throws IllegalCommandException {
        String message;

        if (userInput.length == 0) {
            message = "Please enter a command. I'm happy to help!";
            encapsulateSection(message);
            return;
        }

        String command = userInput[0];

        switch (command) {
            case "bye":
                message = exit(userInput);
                break;
            case "list":
                message = list(userInput);
                break;
            case "mark":
                message = mark(userInput);
                break;
            case "unmark":
                message = unmark(userInput);
                break;
            case "todo":
                message = addTodo(userInput);
                break;
            case "deadline":
                message = addDeadline(userInput);
                break;
            case "event":
                message = addEvent(userInput);
                break;
            default:
                throw new IllegalCommandException("I'm sorry, I don't understand that command. Please try with one of the following commands: bye, list, mark, unmark, todo, deadline, event.");
        }

        encapsulateSection(message);
    }

    public void encapsulateSection(String message) {
        System.out.println("-----------------------------");
        System.out.println(message);
        System.out.println("-----------------------------");
    }

    public void greet() {
        String message = "";

        String logo = """
                    ____        __
                   / __ )____  / /_\s
                  / __  / __ \\/ __ \\
                 / /_/ / /_/ / /_/ /
                /_____/\\____/_.___/\s
                """;
        message += logo;

        message += "Hello! I'm Bob, but you can call me Bob.\n";
        message += "What can I do for you on this fine day?";

        encapsulateSection(message);
    }

    public String exit(String[] userInput) throws IllegalCommandException {
        if (userInput.length != 1) {
            throw new IllegalCommandException("I'm sorry, the command 'bye' does not take any arguments. Please try again!");
        }

        String message = "Goodbye! Have a great day!";
        isActive = false;

        return message;
    }

    public String echo(String[] userInput) {
        return String.join(" ", userInput);
    }

    public String list(String[] userInput) throws IllegalCommandException {
        if (userInput.length != 1) {
            throw new IllegalCommandException("I'm sorry, the command 'list' does not take any arguments. Please try again!");
        }

        if (tasks.isEmpty()) {
            return "You have no tasks in your list! Use the 'todo', 'deadline', or 'event' commands to add a task.";
        }

        StringBuilder message = new StringBuilder("Here are the items in your list:\n");
        for (int i = 0; i < tasks.size(); ++i) {
            message.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return message.toString();
    }

    public String addTodo(String[] userInput) throws IllegalCommandException {
        String message = "";

        String description = "";

        for (int i = 0; i < userInput.length; i++) {
            if (i == 0) {
                continue;
            } else {
                description += userInput[i] + " ";
            }
        }

        description = description.trim();

        if (description.isEmpty()) {
            throw new IllegalCommandException("I'm sorry, the description of a to-do item cannot be empty. The proper usage of the todo command is 'todo <description>'. Please try again!");
        }

        Task newTask = new Todo(description);
        tasks.add(newTask);

        message = "I've added a to-do item: \n" + newTask.toString();
        return message;
    }

    public String addDeadline(String[] userInput) throws IllegalCommandException {

        String message = "";
        String description;
        String due;

        String arguments = "";

        for (int i = 0; i < userInput.length; i++) {
            if (i == 0) {
                continue;
            } else {
                arguments += userInput[i] + " ";
            }
        }

        arguments = arguments.trim();

        String[] splitArguments = arguments.split("/by");

        if (splitArguments.length != 2) {
            throw new IllegalCommandException("I'm sorry, the proper usage of the deadline command is 'deadline <description> /by <due>'. Please try again!");
        }

        description = splitArguments[0];
        due = splitArguments[1];

        Deadline newDeadline = new Deadline(description, due);
        tasks.add(newDeadline);

        message = "I have added a new deadline to your calendar: \n" + newDeadline.toString();
        return message;
    }

    public String addEvent(String[] userInput) throws IllegalCommandException {

        String message = "";
        String description;
        String start;
        String end;

        String arguments = "";

        for (int i = 0; i < userInput.length; i++) {
            if (i == 0) {
                continue;
            } else {
                arguments = userInput[i] + " ";
            }
        }

        arguments = arguments.trim();

        String[] splitArguments = arguments.split("/from");

        if (splitArguments.length != 2) {
            throw new IllegalCommandException("I'm sorry, the proper usage of the event command is 'event <description> /from <start> /to <end>'. Please try again!");
        }

        description = splitArguments[0];
        String[] startEnd = splitArguments[1].split("/to");

        if (startEnd.length != 2) {
            throw new IllegalCommandException("I'm sorry, the proper usage of the event command is 'event <description> /from <start> /to <end>'. Please try again!");
        }

        start = startEnd[0];
        end = startEnd[1];

        Event newEvent = new Event(description, start, end);
        tasks.add(newEvent);

        message = "I have added a new event to your calendar: \n" + newEvent.toString();
        return message;
    }

    public String mark(String[] userInput) throws IllegalCommandException {
        String message = "";

        if (userInput.length != 2) {
            throw new IllegalCommandException("I'm sorry, the proper usage of the mark command is 'mark <index>'. Please try again!");
        }

        int idx;

        try {
            idx = Integer.parseInt(userInput[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalCommandException("I'm sorry, the index of the task to mark must be a number. The proper usage of the mark command is 'mark <index>'. Please try again!");
        }

        try {
            boolean valid = tasks.get(idx).markAsDone();
            if (!valid) {
                throw new IllegalCommandException("I'm sorry, the task you are trying to mark as done is already done. You can mark it as undone or enter another command!");
            }
        } catch (IndexOutOfBoundsException e) {
            if (tasks.isEmpty()) {
                throw new IllegalCommandException("I'm sorry, you have no tasks in your list to mark as done. Please add some tasks first!");
            } else {
                throw new IllegalCommandException("I'm sorry, the number of the task to mark must be within 1 and " + tasks.size() + ". Please try again!");
            }
        }
        message = "Nice! I've marked this task as done:\n" + tasks.get(idx);

        return message;
    }

    public String unmark(String[] userInput) throws IllegalCommandException {
        String message = "";
        if (userInput.length != 2) {
            throw new IllegalCommandException("I'm sorry, the proper usage of the unmark command is 'unmark <index>'. Please try again!");
        }

        int idx;

        try {
            idx = Integer.parseInt(userInput[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalCommandException("I'm sorry, the index of the task to unmark must be a number. The proper usage of the unmark command is 'unmark <index>'. Please try again!");
        }

        try {
            boolean valid = tasks.get(idx).markAsUndone();
            if (!valid) {
                throw new IllegalCommandException("I'm sorry, the task you are trying to mark as undone is already not done. You can mark it as done or enter another command!");
            }
        } catch (IndexOutOfBoundsException e) {
            if (tasks.isEmpty()) {
                throw new IllegalCommandException("I'm sorry, you have no tasks in your list to mark as undone. Please add some tasks first!");
            } else {
                throw new IllegalCommandException("I'm sorry, the number of the task to mark must be within 1 and " + tasks.size() + ". Please try again!");
            }
        }
        message = "I have marked this task as not done, get on it!\n" + tasks.get(idx);

        return message;
    }
}