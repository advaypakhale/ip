import java.util.Scanner;
import java.util.ArrayList;

public class Bob {

    private Boolean isActive;
    private final Scanner sc;
    private ArrayList<String> inputs;

    public Bob() {
        this.isActive = true;
        this.sc = new Scanner(System.in);
        this.inputs = new ArrayList<>();
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

    public String greet() {
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

        return message;
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
        for (int i = 0; i < inputs.size(); ++i) {
            message.append((i + 1)).append(". ").append(inputs.get(i)).append("\n");
        }
        return message.toString();
    }

    public String add(String[] userInput) {
        String message = String.join(" ", userInput);
        inputs.add(message);

        message = "Added: " + message;
        return message;
    }
}