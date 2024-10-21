//STUDENT NO. 24862664

package homeApplianceStore;

import DAO.ApplianceDao;

public class Main {

	public static void main(String[] args) {
//		MenuConsole menu = new MenuConsole();
		
//		MenuConsole console = new MenuConsole();
//		
//		console.displayMenu();
		ApplianceDao dao = new ApplianceDao("HomeAppliances.db");
		dao.initializeDBConnection();
		
		
		System.out.println();
		
	}

}
