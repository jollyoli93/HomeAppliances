//STUDENT NO. 24862664
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import appliances.Appliance;
import appliances.ApplianceFactory;


public class ApplianceDao extends DAO<Appliance> {
	String tableName;
	String dbPath;
	String tableSchema;
	
	public ApplianceDao(String dbPath, Map<String, ApplianceFactory> factories) {
        this.dbPath = dbPath;
        connector = new SqlLiteConnection(dbPath);
        
    	this.tableSchema = 
    			"CREATE TABLE appliances "
    			+"( id	INTEGER NOT NULL UNIQUE,"
    			+"sku	TEXT NOT NULL,"
    			+"description	TEXT NOT NULL,"
    			+" category	TEXT NOT NULL,"
    			+"price	INTEGER NOT NULL,"
    			+"PRIMARY KEY(id AUTOINCREMENT))";
    }


	@Override
	public ArrayList<Appliance> findAll() {
		ArrayList<Appliance> applianceList = new ArrayList<>(); 
		
		if (!checkTableExists("appliances")) {
			createTable("appliances", tableSchema);
		}
		
		
		String query = "SELECT * FROM appliances";
		
        try (Connection connect = connector.initializeDBConnection();
        	 Statement statement = connect.createStatement();
        	 ResultSet result = statement.executeQuery(query)) {

               while (result.next()) {
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
                                       
                   } catch (IllegalArgumentException e) {
                       System.out.println("Error creating appliance: " + e.getMessage());
                   }
  
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

		Appliance appliance = null;
		
		try (Connection connect = connector.initializeDBConnection(); 
			 PreparedStatement preparedStatement = connect.prepareStatement(query)) {
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
	public boolean addNew(Appliance newAppliance, Map<String, String> additionalFields) {	
		String query =  "INSERT INTO appliances" + " (sku, description, category, price) VALUES (?, ?, ?, ?)";
		
		if (!checkTableExists("appliances")) {
			createTable("appliances", tableSchema);
		}
		
		try (Connection connect = connector.initializeDBConnection(); 
			 PreparedStatement preparedStatement = connect.prepareStatement(query)){
				preparedStatement.setString(1, newAppliance.getSku());
				preparedStatement.setString(2, newAppliance.getDescription());
				preparedStatement.setString(3, newAppliance.getCategory());
				preparedStatement.setDouble(4, newAppliance.getPrice());
						
				int executeRows = preparedStatement.executeUpdate();
				
				return executeRows > 0;
						 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error connecting to the database");
            System.out.println("SQL Exception: " + e.getMessage());
			return false;
		}

	}

    @Override
    public boolean deleteById(int id) {
        String query = "DELETE FROM appliances WHERE id = ?";
        
        try (Connection connect = connector.initializeDBConnection();
        	 PreparedStatement preparedStatement = connect.prepareStatement(query)) {
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
		
		try (Connection connect = connector.initializeDBConnection();
			PreparedStatement preparedStatement = connect.prepareStatement(query)) {
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

}
