package bob.ui;

import java.util.Scanner;


public class Ui {

    private final Scanner sc;

    public Ui() {
        sc = new Scanner(System.in);
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

    public void wrapText(StringBuilder message) {
        System.out.println("-----------------------------");
        System.out.println(message.toString());
        System.out.println("-----------------------------");
    }
    public void wrapText(String message) {
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

        message += "Hello! I'm bob.ui.Bob, but you can call me bob.ui.Bob.\n";
        message += "What can I do for you on this fine day?";

        wrapText(message);
    }
}
