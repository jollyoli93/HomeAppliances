package DAO;

public class ApplianceDao extends DAO {
	String path;
	
	public ApplianceDao(String path) {
		connector = new SqlLiteConnection(path);
	}

}
