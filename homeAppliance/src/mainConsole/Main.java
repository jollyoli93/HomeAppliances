//STUDENT NO. 24862664

package mainConsole;

import IOHandlers.ConsoleIOHandler;
import IOHandlers.InputOutputHandler;

public class Main {
	public static void main(String[] args) {
		String database = "HomeAppliances";
		InputOutputHandler handleInput = new ConsoleIOHandler();
		
		StoreConsole consoleHandler = new StoreConsole(database);
		
		consoleHandler.displayMainMenu();
	    
	}
}