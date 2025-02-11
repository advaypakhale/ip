package bob;

import bob.command.Command;
import bob.command.ExitCommand;
import bob.parser.Parser;
import bob.storage.Storage;
import bob.task.TaskList;
import bob.ui.Ui;

public class Bob {
    private Boolean isActive;
    private static final String FILE_PATH = "./data/tasks.txt";
    private final TaskList tasks;
    private final Ui ui;
    private final Parser parser;
    private final Storage storage;

    public Bob() {
        this.isActive = true;
        this.tasks = new TaskList();
        this.ui = new Ui();
        this.parser = new Parser();
        this.storage = new Storage(FILE_PATH, tasks);
    }

    public static void main(String[] args) {
        Bob bob = new Bob();
        bob.run();
    }

    public void run() {
        ui.greet();

        while (isActive) {
            try {
                String[] userInput = ui.getUserInput();
                Command cmd = parser.parseUserInput(userInput);
                cmd.execute(tasks, ui, storage);

                if (cmd instanceof ExitCommand) {
                    isActive = false;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
