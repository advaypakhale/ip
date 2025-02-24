package bob.tui;

import java.util.Scanner;

/**
 * Handles all user interface interactions including input/output formatting and
 * display of the application's ASCII art logo.
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
     * Reads and processes the next line of user input. Displays a prompt (">>> ")
     * and waits for user input.
     *
     * @return String input from stdin
     */
    public String getUserInput() {
        System.out.print(">>> ");
        return sc.nextLine();
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
     * Displays the welcome message with ASCII art logo. Shows the application's
     * logo and a greeting message to the user.
     */
    public void greet() {
        String message = "";
        String logo = "     ____        __   \n" + "    / __ )____  / /_  \n" + "   / __  / __ \\/ __ \\ \n"
                + "  / /_/ / /_/ / /_/ / \n" + " /_____/\\____/_.___/  \n";
        message += logo;
        message += "Hello! I'm Bob, but you can call me Bob.\n";
        message += "What can I do for you on this fine day?";
        wrapText(message);
    }
}
