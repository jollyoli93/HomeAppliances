//STUDENT NO. 24862664
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import homeApplianceStore.HomeAppliance;

public class ApplianceDao extends DAO<HomeAppliance> {
	String path;
	
	public ApplianceDao(String path) {
		connector = new SqlLiteConnection(path);
	}

	@Override
	public ArrayList<HomeAppliance> findAll() {
		ArrayList<HomeAppliance> applianceList = new ArrayList<>(); 
		Connection connect = connector.initializeDBConnection(); 
		
		String query = "SELECT * FROM appliances";
		
        try (Statement statement = connect.createStatement();
             ResultSet result = statement.executeQuery(query)) {

               if (result.next()) {
            	   System.out.println("Listing products");
				
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
               } else {
                   System.out.println("No results found.");
               }


           } catch (SQLException e) {
               System.out.println("SQL Exception: " + e.getMessage());
           }
		
		return applianceList;
	}

	@Override
	public HomeAppliance getById(int id) {
	    String query = "SELECT sku, description, category, price FROM appliances WHERE id = ?";
		Connection connect = connector.initializeDBConnection(); 
		HomeAppliance appliance = null;
		
		try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
		        preparedStatement.setInt(1, id);
		        ResultSet resultSet = preparedStatement.executeQuery();
        	
        	if (resultSet.next()) {
        		String sku = resultSet.getString("sku");
                String desc = resultSet.getString("description");
                String cat = resultSet.getString("category");
                double price = resultSet.getDouble("price");
        		
        		appliance = new HomeAppliance(id, sku, desc, cat, price);
        	} else {
                System.out.println("No results found.");
            }
        	
        } catch (SQLException e) {
			e.printStackTrace();
		}
		
    	return appliance;
	}

	@Override
	public boolean addNew(HomeAppliance newAppliance) {
		//connect to the db, prepare statement using ? as placeholders. In the try catch block run the preparedstatement on the query. In the block use the setter methods to update from the object
		// execute rows returns the number of rows that has been updated, returning true if successful.
		
		String query =  "INSERT INTO appliances (id, sku, description, category, price) VALUES (?, ?, ?, ?, ?)";
		Connection connect = connector.initializeDBConnection(); 
		
		try (PreparedStatement preparedStatement = connect.prepareStatement(query)){
			preparedStatement.setInt(1, newAppliance.getId());
			preparedStatement.setString(2, newAppliance.getSku());
			preparedStatement.setString(3, newAppliance.getDescription());
			preparedStatement.setString(4, newAppliance.getCategory());
			preparedStatement.setDouble(5, newAppliance.getPrice());
					
			int executeRows = preparedStatement.executeUpdate();
			
			return executeRows > 0;
					 
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

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
