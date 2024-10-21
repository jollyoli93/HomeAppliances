package DAO;

import java.util.ArrayList;

import homeApplianceStore.HomeAppliance;

public class ApplianceDao extends DAO<HomeAppliance> {
	String path;
	
	public ApplianceDao(String path) {
		connector = new SqlLiteConnection(path);
	}

	@Override
	public ArrayList<HomeAppliance> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HomeAppliance getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
