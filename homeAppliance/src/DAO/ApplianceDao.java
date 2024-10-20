package DAO;

import homeApplianceStore.HomeAppliance;

public class ApplianceDao extends DAO<HomeAppliance> {
	String path;
	
	public ApplianceDao(String path) {
		connector = new SqlLiteConnection(path);
	}

}
