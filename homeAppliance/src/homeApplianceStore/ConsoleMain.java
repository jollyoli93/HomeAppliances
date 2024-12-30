//STUDENT NO. 24862664

package homeApplianceStore;

import IOHandlers.ConsoleIOHandler;
import IOHandlers.InputOutputHandler;

public class ConsoleMain {
	public static void main(String[] args) {
		String database = "HomeAppliances";
		InputOutputHandler handleInput = new ConsoleIOHandler();
		
		ConsoleHandler consoleHandler = new ConsoleHandler(database);
		
		consoleHandler.displayMainMenu();
	    
	}
}