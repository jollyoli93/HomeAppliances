//STUDENT NO. 24862664

package homeApplianceStore;

import IOHandlers.ConsoleIOHandler;
import IOHandlers.InputOutputHandler;

public class Main {

	public static void main(String[] args) {
		String database = "HomeAppliances";
		InputOutputHandler handleInput = new ConsoleIOHandler();
		
		MainConsole mainConsole = new MainConsole(database);
		
		mainConsole.displayMainMenu();
	    
	}
}

//private void createDatabase() {
//	  String sql = "CREATE TABLE password" +
//	               "(passwordId INTEGER not NULL, " +
//	               " password VARCHAR(20), " + 
//	               " PRIMARY KEY ( passwordId ))"; 
//	  try {
//		  
//	  }
//	  
//	  
//	  stmt.executeUpdate(sql);
//}