//STUDENT NO. 24862664
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import homeApplianceStore.Appliance;
import homeApplianceStore.ApplianceFactory;


public class ApplianceDao extends DAO<Appliance> {
	String path;
	public ApplianceDao(String path, Map<String, ApplianceFactory> factories) {
        this.path = path;
        connector = new SqlLiteConnection(path);
    }
   
   public void initializeFactories () {
	   
   };

	@Override
	public ArrayList<Appliance> findAll() {
		ArrayList<Appliance> applianceList = new ArrayList<>(); 
		Connection connect = connector.initializeDBConnection();
		
		String query = "SELECT * FROM appliances";
		
        try (Statement statement = connect.createStatement();
             ResultSet result = statement.executeQuery(query)) {

               if (result.next()) {
            	   System.out.println("Listing products");
				
            	   do {
            		   Appliance product;
                	   
                       int id = result.getInt("id");
                       String desc = result.getString("description");
                       String cat = result.getString("category");
                       double price = result.getDouble("price");
                       
                       try {
                           // Get the appropriate factory for the category
                           ApplianceFactory factory = ApplianceFactory.selectApplianceFactory(cat);
                           
                           // Create the specific appliance using the factory
                           product = factory.selectAppliance(desc);
                           
                           // Set the common properties
                           product.setId(id);
                           product.setPrice(price);
                           
                           applianceList.add(product);
                           
                           System.out.println("Created: " + product.getCategory() + " - " + product.getDescription());

                           
                       } catch (IllegalArgumentException e) {
//                    	   e.printStackTrace();
                           System.out.println("Error creating appliance: " + e.getMessage());
                       }
                       
                   } while (result.next());
               } else {
                   System.out.println("No results found.");
               }


           } catch (SQLException e) {
   				System.out.println("Error connecting to the database");
               System.out.println("SQL Exception: " + e.getMessage());
               
           }
		
		return applianceList;
	}

	@Override
	public Appliance getById(int id) {
	    String query = "SELECT sku, description, category, price FROM appliances WHERE id = ?";
		Connection connect = connector.initializeDBConnection(); 
		Appliance appliance = null;
		
		try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
		        preparedStatement.setInt(1, id);
		        ResultSet result = preparedStatement.executeQuery();
        	
        	if (result.next()) {     
                String desc = result.getString("description");
                String cat = result.getString("category");
                double price = result.getDouble("price");  
        		
                try {
                    // Get the appropriate factory for the category
                    ApplianceFactory factory = ApplianceFactory.selectApplianceFactory(cat);
                    
                    // Create the specific appliance using the factory
                    appliance = factory.selectAppliance(desc);
                    
                    // Set the common properties
                    appliance.setId(id);
                    appliance.setPrice(price);
                    
                    System.out.println("Created: " + appliance.getCategory() + " - " + appliance.getDescription());

                    
                } catch (IllegalArgumentException e) {
                    System.out.println("Error retrieving appliance: " + e.getMessage());
                }
        	}
        	
        } catch (SQLException e) {
				System.out.println("Error connecting to the database");
	               System.out.println("SQL Exception: " + e.getMessage());
		}
		
    	return appliance;
	}

	@Override
	public boolean addNew(Appliance newAppliance) {	
		String query =  "INSERT INTO appliances (id, sku, description, category, price) VALUES (?, ?, ?, ?, ?)";
		Connection connect = connector.initializeDBConnection(); 
		
		try (PreparedStatement preparedStatement = connect.prepareStatement(query)){
			preparedStatement.setString(2, newAppliance.getSku());
			preparedStatement.setString(3, newAppliance.getDescription());
			preparedStatement.setString(4, newAppliance.getCategory());
			preparedStatement.setDouble(5, newAppliance.getPrice());
					
			int executeRows = preparedStatement.executeUpdate();
			
			return executeRows > 0;
					 
		} catch (SQLException e) {
				System.out.println("Error connecting to the database");
               System.out.println("SQL Exception: " + e.getMessage());
			return false;
		}

	}

    @Override
    public boolean deleteById(int id) {
        String query = "DELETE FROM appliances WHERE id = ?";
        Connection connect = connector.initializeDBConnection();
        
        try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int executeRows = preparedStatement.executeUpdate();
            
            return executeRows > 0;
        } catch (SQLException e) {
			System.out.println("Error connecting to the database");
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
        }
    }

	@Override
	public boolean updateById(int id, Object update) {
		String query = "UPDATE appliances SET price = ? WHERE id = ?";
		Connection connect = connector.initializeDBConnection();
		
		try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
			preparedStatement.setDouble(1, (Double) update);
			preparedStatement.setInt(2, id);
			
	        int updated = preparedStatement.executeUpdate();
	        return updated > 0;
			
		} catch (SQLException e) {
			System.out.println("Error connecting to the database");
            System.out.println("SQL Exception: " + e.getMessage());
			return false;
		}
	}
	
	protected boolean createDB (String name) {
		String query = "CREATE TABLE test";
		Connection connect = connector.initializeDBConnection();
		
		try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
			
			int updated = preparedStatement.executeUpdate();
			
	        return updated > 0;
		} catch (SQLException e) {
			System.out.println("Error connecting to the database");
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
		}
	}

}
