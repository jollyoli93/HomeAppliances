//STUDENT NO. 24862664

package mainConsole;

import IOHandlers.ConsoleIOHandler;
import IOHandlers.InputOutputHandler;

/**
 * The Main class is the entry point for the 'Home Appliance Store ' - Console application.
 * It initialises the required components and starts the application by displaying the main menu to the user.
 * 
 * @author 24862664
 */
public class Main {

    /**
     * The main method is the entry point of the program.
     * It initialises the database and input/output handler, and then displays the main menu using the StoreConsole.
     *
     * @param args command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        // Initialise database and input/output handler
        String database = "HomeAppliances";
        InputOutputHandler handleInput = new ConsoleIOHandler();

        // Create StoreConsole instance and display main menu
        StoreConsole consoleHandler = new StoreConsole(database);
        consoleHandler.displayMainMenu();
    }
}
