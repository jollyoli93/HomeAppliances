//STUDENT NO. 24862664

package homeApplianceStoreDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DbConnection;
import homeApplianceStore.HomeAppliance;

//import java.util.ArrayList;

public class HomeApplianceDAOImpl implements HomeApplianceDAO {
	private DbConnection connection;
	String dbPath;
	String driver;

	
    public HomeApplianceDAOImpl(DbConnection connection, String dbPath, String driver) {
        this.connection = connection;
        this.dbPath = dbPath;
        this.driver = driver;
    }
	
	@Override
	public ArrayList<HomeAppliance> findAllProducts() {
		ArrayList<HomeAppliance> applianceList = null; 
		DbConnection con = new DbConnection(this.dbPath, this.driver);
		
		con.initializeDBConnection(); 
		
		String query = "SELECT * FROM appliances";
		
        try (Statement statement = ((Connection) con).createStatement();
                ResultSet result = statement.executeQuery(query)) {

               System.out.println("DBQuery = " + query);

               if (result.next()) {
                   System.out.println("First Row Name: " + result.getString("id"));
               } else {
                   System.out.println("No results found.");
               }

               do {
                   System.out.println("ID: " + result.getInt("id"));
                   System.out.println("SKU: " + result.getString("sku"));
                   System.out.println("Description " + result.getString("description"));
                   System.out.println("Category " + result.getString("category"));
               } while (result.next());

           } catch (SQLException e) {
               System.out.println("SQL Exception: " + e.getMessage());
           }
		
		
		
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
