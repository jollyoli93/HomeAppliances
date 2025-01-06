package mainConsole;

import IOHandlers.ConsoleIOHandler;
import IOHandlers.InputOutputHandler;

/**
 * The StoreConsole class handles the main console interface of the Home Appliance Store application.
 * It displays the main menu and allows the user to navigate to different sections like managing products
 * or managing users.
 * @author 24862664
 */
public class StoreConsole {
    private String database;
    private InputOutputHandler handleInput;

    /**
     * Constructs a StoreConsole with the specified database.
     *
     * @param db the name of the database to use
     */
    public StoreConsole(String db) {
        this.database = db;
        this.handleInput = new ConsoleIOHandler();
    }

    /**
     * Displays the main menu and allows the user to choose between store products, manage users, or exit.
     */
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

    /**
     * Sets the InputOutputHandler.
     *
     * @param handleInput the InputOutputHandler to be used
     * @return the InputOutputHandler
     */
    public InputOutputHandler setHandler(InputOutputHandler handleInput) {
        return handleInput;
    }
}
