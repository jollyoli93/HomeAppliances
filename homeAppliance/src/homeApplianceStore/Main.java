//STUDENT NO. 24862664

package homeApplianceStore;

public class Main {

	public static void main(String[] args) {
		String database = "HomeAppliances";
		
		//Could change to be hierachi of the other consoles
		InputOutputHandler handleInput = new ConsoleIOHandler();
	    
	    mainConsole(database, handleInput);
	    
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