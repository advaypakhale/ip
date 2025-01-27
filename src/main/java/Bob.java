import java.util.Scanner;

public class Bob {

    private Boolean isActive;
    private final Scanner sc;

    public Bob() {
        this.isActive = true;
        this.sc = new Scanner(System.in);
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

        String command = userInput[0];

        if (command.equals("bye")) {
            exit();
        } else {
            echo(userInput);
        }
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

    public void exit() {
        String message = "Goodbye! Have a great day!";
        isActive = false;
        encapsulateSection(message);
    }

    public void echo(String[] userInput) {
        String message = String.join(" ", userInput);
        encapsulateSection(message);
    }
}