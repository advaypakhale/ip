public class Bob {

    private Boolean isActive;

    public Bob() {
        this.isActive = true;
    }

    public static void main(String[] args) {
        Bob bob = new Bob();
        bob.run();
    }

    public void run() {
        greet();
        exit();
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
        encapsulateSection(message);
    }
}