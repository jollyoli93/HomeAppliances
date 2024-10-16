//STUDENT NO. 24862664

package homeApplianceStore;

import database.DbConnection;
import homeApplianceStoreDAO.HomeApplianceDAOImpl;

public class Main {

	public static void main(String[] args) {
//		MenuConsole menu = new MenuConsole();
		
		String driver = "org.sqlite.JDBC";
		String dbPath = "HomeAppliances.db";
		
		HomeApplianceDAOImpl homeDAO = new HomeApplianceDAOImpl(dbPath, driver);
		homeDAO.findAllProducts();
		
		
	}

}
