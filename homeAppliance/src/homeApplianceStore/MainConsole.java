package homeApplianceStore;

import IOHandlers.ConsoleIOHandler;
import IOHandlers.InputOutputHandler;

public class MainConsole {
    private String database;
    private InputOutputHandler handleInput;

    public MainConsole(String db) {
        this.database = db;
        this.handleInput = new ConsoleIOHandler();
    }

    public void displayMainMenu() {
        int input;
        do {
            System.out.println("------------------------");
            System.out.println("Home Appliance Store");
            System.out.println("------------------------");

            System.out.println("[1] Store Products");
            System.out.println("[2] Manage Users");
            System.out.println("[3] Exit");

            input = handleInput.getInputInt();

            switch (input) {
                case 1:
                    ApplianceConsole menuConsole = new ApplianceConsole(database);
                    menuConsole.displayMenu();
                    break;
                case 2:
                    UserConsole userConsole = new UserConsole(database);
                    userConsole.userMenu();
                    break;
                case 3:
                    System.out.println("Exiting, see you again soon.");
                    break;
                default:
                    System.out.println("Incorrect input, please try again.");
            }
        } while (input != 3);
    }

    public InputOutputHandler setHandler(InputOutputHandler handleInput) {
        return handleInput;
    }
}
