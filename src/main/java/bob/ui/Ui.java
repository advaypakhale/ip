package bob.ui;

import java.util.Scanner;

/**
 * Handles all user interface interactions including input/output formatting
 * and display of the application's ASCII art logo.
 */
public class Ui {
    /**
     * Scanner object for reading user input
     */
    private final Scanner sc;

    /**
     * Creates a new UI instance with a Scanner for System.in.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Reads and processes the next line of user input.
     * Displays a prompt (">>> ") and waits for user input.
     *
     * @return array of strings split on spaces, or empty array if input is empty
     */
    public String[] getUserInput() {
        String[] userInput;
        System.out.print(">>> ");
        String nextInput = sc.nextLine();
        if (nextInput.isEmpty()) {
            userInput = new String[0];
        } else {
            userInput = nextInput.split(" ");
        }
        return userInput;
    }

    /**
     * Displays a message surrounded by decorative lines.
     *
     * @param message the StringBuilder containing the text to display
     */
    public void wrapText(StringBuilder message) {
        System.out.println("-----------------------------");
        System.out.println(message.toString());
        System.out.println("-----------------------------");
    }

    /**
     * Displays a message surrounded by decorative lines.
     *
     * @param message the String containing the text to display
     */
    public void wrapText(String message) {
        System.out.println("-----------------------------");
        System.out.println(message);
        System.out.println("-----------------------------");
    }

    /**
     * Displays the welcome message with ASCII art logo.
     * Shows the application's logo and a greeting message to the user.
     */
    public void greet() {
        String message = "";
        String logo = """
                    ____        __
                   / ** )**__  / /_\s
                  / **  / ** \\/ __ \\
                 / /_/ / /_/ / /_/ /
                /_____/\\____/_.___/\s
                """;
        message += logo;
        message += "Hello! I'm bob.Bob, but you can call me bob.Bob.\n";
        message += "What can I do for you on this fine day?";
        wrapText(message);
    }
}