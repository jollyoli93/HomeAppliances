package homeApplianceStore;

import java.util.ArrayList;

import DAO.ApplianceDao;

public class HomeApplianceStore {
	public static void main(String[] args ) {
		ApplianceDao testTable = new ApplianceDao("HomeApplianceTest", null);
		
		testTable.findAll();
	}
}
