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
        } else {
            message = add(userInput);
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

    public String add(String[] userInput) {
        String message = String.join(" ", userInput);

        Task newTask = new Task(message);
        tasks.add(newTask);

        message = "Added: \n" + newTask.toString();
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