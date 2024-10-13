//STUDENT NO. 24862664

package homeApplianceStoreDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import homeApplianceStore.HomeAppliance;

//import java.util.ArrayList;

public class HomeApplianceDAOImpl implements HomeApplianceDAO {
	private DbConnection connection;
	
    public HomeApplianceDAOImpl(DbConnection dbConnection) {
    	//connection type etc sqlite db
        connection = dbConnection;
    }
	
	@Override
	public ArrayList<HomeAppliance> findAllProducts() {
		ArrayList<HomeAppliance> applianceList = null; 
		HomeAppliance appliance;
	
		//use SQL statement to retrieve all items
		return applianceList;
	}
	
	@Override
	public boolean deleteProduct(int prod) {
		System.out.println("Deleting " + prod);
		return false;
	} 
	
	@Override
	public HomeAppliance getProductById(int id) {
		HomeAppliance appliance = null;
		return appliance;
	}

	@Override
	public boolean addProduct(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProduct(int id) {
		//Create query to update
		String query;
		
        // Try-with-resources for the Statement to ensure it's closed
        try (Connection	connection = this.connection.initializeDBConnection();
        	Statement statement = connection.createStatement()) {
//            int result = statement.executeUpdate();
//            System.out.println("DBUpdate = " + update);
//            System.out.println(result + " rows affected.");

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
		return false;
	}
}
