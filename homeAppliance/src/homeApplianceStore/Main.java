//STUDENT NO. 24862664

package homeApplianceStore;

public class Main {

	public static void main(String[] args) {
		
		MenuConsole console = new MenuConsole("HomeAppliances", "appliances");

		console.displayMenu();		
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