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
            String[] userInput = getUserInput();
            processUserInput(userInput);
        }
    }

    public String[] getUserInput() {
        System.out.print(">>> ");
        return sc.nextLine().split(" ");
    }

    public void processUserInput(String[] userInput) {

        String message;
        String command = userInput[0];

        if (command.equals("bye") && userInput.length == 1) {
            message = exit();
        } else if (command.equals("list") && userInput.length == 1) {
            message = list();
        } else if (command.equals("mark") && userInput.length == 2) {
            message = mark(userInput);
        } else if (command.equals("unmark") && userInput.length == 2) {
            message = unmark(userInput);
        } else if (command.equals("todo")) {
            message = addTodo(userInput);
        } else if (command.equals("deadline")) {
            message = addDeadline(userInput);
        } else if (command.equals("event")) {
            message = addEvent(userInput);
        } else {
            message = echo(userInput);
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

    public String exit() {
        String message = "Goodbye! Have a great day!";
        isActive = false;
        return message;
    }

    public String echo(String[] userInput) {
        String message = String.join(" ", userInput);
        return message;
    }

    public String list() {
        StringBuilder message = new StringBuilder("Here are the items in your list:\n");
        for (int i = 0; i < tasks.size(); ++i) {
            message.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return message.toString();
    }

    public String addTodo(String[] userInput) {
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

        Task newTask = new Todo(description);
        tasks.add(newTask);

        message = "I've added a to-do item: \n" + newTask.toString();
        return message;
    }

    public String addDeadline(String[] userInput) {

        String message = "";
        String description = "";
        String due = "";

        Boolean isDescription = true;

        for (int i = 0; i < userInput.length; i++) {
            if (i == 0) {
                continue;
            } else if (isDescription && !userInput[i].equals("/by")) {
                description += userInput[i] + " ";
            } else if (userInput[i].equals("/by")) {
                isDescription = false;
            } else {
                due += userInput[i] + " ";
            }
        }

        description = description.trim();
        due = due.trim();

        Deadline newDeadline = new Deadline(description, due);
        tasks.add(newDeadline);

        message = "I have added a new deadline to your calendar: \n" + newDeadline.toString();
        return message;
    }

    public String addEvent(String[] userInput) {

        String message = "";
        String description = "";
        String start = "";
        String end = "";

        Boolean isDescription = true;
        Boolean isStart = false;

        for (int i = 0; i < userInput.length; i++) {
            if (i == 0) {
                continue;
            } else if (isDescription && !userInput[i].equals("/from")) {
                description += userInput[i] + " ";
            } else if (userInput[i].equals("/from")) {
                isDescription = false;
                isStart = true;
            } else if (isStart && !userInput[i].equals("/to")) {
                start += userInput[i] + " ";
            } else if (userInput[i].equals("/to")) {
                isStart = false;
            } else {
                end += userInput[i] + " ";
            }
        }

        description = description.trim();
        start = start.trim();
        end = end.trim();

        Event newEvent = new Event(description, start, end);
        tasks.add(newEvent);

        message = "I have added a new event to your calendar: \n" + newEvent.toString();
        return message;
    }

    public String mark(String[] userInput) {
        String message = "";
        int idx = Integer.parseInt(userInput[1]) - 1;

        tasks.get(idx).markAsDone();
        message = "Nice! I've marked this task as done:\n" + tasks.get(idx);

        return message;
    }

    public String unmark(String[] userInput) {
        String message = "";
        int idx = Integer.parseInt(userInput[1]) - 1;

        tasks.get(idx).markAsUndone();
        message = "I have marked this task as not done, get on it!\n" + tasks.get(idx);

        return message;
    }
}