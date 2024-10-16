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
	String dbPath;
	String driver;

	
    public HomeApplianceDAOImpl(String dbPath, String driver) {
        this.dbPath = dbPath;
        this.driver = driver;
    }
	
	@Override
	public ArrayList<HomeAppliance> findAllProducts() {
		ArrayList<HomeAppliance> applianceList = new ArrayList<>(); 
		DbConnection con = new DbConnection(this.dbPath, this.driver);
		
		Connection connect= con.initializeDBConnection(); 
		
		String query = "SELECT * FROM appliances";
		
        try (Statement statement = connect.createStatement();
             ResultSet result = statement.executeQuery(query)) {

               System.out.println("DBQuery = " + query);

               if (result.next()) {
                   System.out.println("TEST ID: " + result.getInt("id"));
               } else {
                   System.out.println("No results found.");
               }

               do {
            	   HomeAppliance product;
            	   
                   int id = result.getInt("id");
                   String sku = result.getString("sku");
                   String desc = result.getString("description");
                   String cat = result.getString("category");
                   int price = result.getInt("price");
                   
                   product = new HomeAppliance(id, sku, desc, cat, price);
                   
                   applianceList.add(product);
                   
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
		DbConnection con = new DbConnection(this.dbPath, this.driver);
		//Create query to update
		String query;
		
        // Try-with-resources for the Statement to ensure it's closed
        try (Connection	connection = con.initializeDBConnection();
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
