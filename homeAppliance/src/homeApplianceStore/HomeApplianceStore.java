package homeApplianceStore;

import java.util.ArrayList;

import DAO.ApplianceDao;

public class HomeApplianceStore {
	public static void main(String[] args ) {
//	ApplianceFactory entertainment = new EntertainmentFactory();
//		
//		Appliance tv = entertainment.selectAppliance("Basic Television");
//		
//		ArrayList<String> list = entertainment.listAllApplianceTypes();
//		
//		System.out.println(list.get(0));
		ApplianceDao testTable = new ApplianceDao("HomeAppliances", null);
		String table = "Testies";
		if (!testTable.checkTableExists(table)) {
			testTable.createTable(table);
		} 
	}
}
