//STUDENT NO. 24862664
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import appliances.Appliance;
import appliances.ApplianceDepartments;
import util.FactoryRegistry;


public class ApplianceDao extends DAO<Appliance> {
	String tableName;
	String dbPath;
	String tableSchema;
	Map<String, ApplianceDepartments> departments;
	FactoryRegistry registerAppliances = new FactoryRegistry();
	
	public ApplianceDao(String dbPath) {
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
    	
		if (!checkTableExists("appliances")) {
			createTable("appliances", tableSchema);
		}
    	
    	allowedTablesList.add("appliances");
    	registerAppliances.initApplianceFactories();
    	departments = registerAppliances.getDepartments();    	
    }


	@Override
	public ArrayList<Appliance> findAll(int optionalId, HashMap<String, Object> sortParams) {
	    ArrayList<Appliance> applianceList = new ArrayList<>();
	    
	    // Initialize query
	    StringBuilder queryBuilder = new StringBuilder("SELECT * FROM appliances");

	    // Add ORDER BY clause dynamically if sortParams are provided
	    if (sortParams != null && !sortParams.isEmpty()) {
	        queryBuilder.append(" ORDER BY ");
	        int i = 0;
	        for (String column : sortParams.keySet()) {
	            queryBuilder.append(column).append(" ").append(sortParams.get(column));  // Column and order (ASC or DESC)
	            if (i < sortParams.size() - 1) {
	                queryBuilder.append(", ");
	            }
	            i++;
	        }
	    }

	    String query = queryBuilder.toString();

	    try (Connection connect = connector.initializeDBConnection();
	         Statement statement = connect.createStatement();
	         ResultSet result = statement.executeQuery(query)) {

	        while (result.next()) {
	            Appliance product;

	            int userId = result.getInt("id");
	            String desc = result.getString("description");
	            String cat = result.getString("category");
	            double price = result.getDouble("price");

	            try {
	                // Get the appropriate department for the category
	                ApplianceDepartments department = ApplianceDepartments.selectApplianceDepartment(cat.toLowerCase());

	                // Create the specific appliance using the factory
	                product = department.selectAppliance(desc.toLowerCase());

	                // Set the common properties
	                product.setId(userId);
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
	
	public ArrayList<Appliance> getAppliancesByPriceDesc() {
		ArrayList<Appliance> appliances;
		HashMap<String, Object> sortParams = new HashMap<String, Object>();
		sortParams.put("price", "DESC");
		appliances = findAll(0, sortParams);
		
		if (!appliances.isEmpty()) {
			return appliances;
		}
		return null;
	}
	
	public ArrayList<Appliance> getAppliancesByPriceAsc() {
		ArrayList<Appliance> appliances;
		HashMap<String, Object> sortParams = new HashMap<String, Object>();
		sortParams.put("price", "ASC");
		appliances = findAll(0, sortParams);
		
		if (!appliances.isEmpty()) {
			return appliances;
		}
		return null;
	}
 

	public Appliance getAppliance(int id) {
	    String query = "SELECT sku, description, category, price FROM appliances WHERE id = ?";
	    Appliance appliance = null;

	    try (ResultSet result = getById(query, id)) {
	        if (result.next()) {
	            String desc = result.getString("description");
	            String cat = result.getString("category");
	            double price = result.getDouble("price");

	            try {
	                // Get the appropriate factory for the category
	                ApplianceDepartments department = ApplianceDepartments.selectApplianceDepartment(cat.toLowerCase());

	                // Create the specific appliance using the factory method
	                appliance = department.selectAppliance(desc.toLowerCase());

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
	
	public Appliance getApplianceByType(String description) {
	    String query = "SELECT id, sku, description, category, price "
	    		+ "FROM appliances "
	    		+ "WHERE description = ? "
	    		+ "LIMIT 1;";
	    
	    Appliance appliance = null;

	    try (Connection connect = connector.initializeDBConnection();
		     PreparedStatement preparedStatement = connect.prepareStatement(query)) {

	        preparedStatement.setString(1, description);
	        ResultSet result = preparedStatement.executeQuery();
		        
	        if (result.next()) {
	        	int id = result.getInt("id");
	        	String sku = result.getString("sku");
	            String cat = result.getString("category");
	            double price = result.getDouble("price");

	            try {
	                // Get the appropriate factory for the category
	                ApplianceDepartments department = ApplianceDepartments.selectApplianceDepartment(cat.toLowerCase());

	                // Create the specific appliance using the factory method
	                appliance = department.selectAppliance(description.toLowerCase());

	                // Set the common properties
	                appliance.setId(id);
	                appliance.setPrice(price);
	                
	                System.out.println("Returning appliance no. " + appliance.getId());

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

	
	public boolean addNewAppliance(Appliance appliance, Map<String, String> additionalFields) {
	    Map<String, Object> fields = new HashMap<>();
	    fields.put("sku", appliance.getSku());
	    fields.put("description", appliance.getDescription().toLowerCase());
	    fields.put("category", appliance.getCategory().toLowerCase());
	    fields.put("price", appliance.getPrice());
	    
	    if (additionalFields != null) {
	        fields.putAll(additionalFields);
	    }
	    
	    boolean result = addNew("appliances", fields);

	    return result;
	}

    public int deleteApplianceById(int id) {
    	Map<String, Object> conditions = new HashMap<>();
    	conditions.put("id", id);

		try {
			return deleteById("appliances", conditions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
    }
    
    @Override
    public int updateById(int id, String table, Map<String, Object> updateFields) {
        if (!allowedTablesList.contains(table)) {
            System.out.println("Table not valid.");
            return 0;
        }

        // Build the SQL query dynamically
        StringBuilder queryBuilder = new StringBuilder("UPDATE ").append(table).append(" SET ");
        updateFields.forEach((key, value) -> queryBuilder.append(key).append(" = ?, "));
        
        // Remove the trailing comma and space
        queryBuilder.setLength(queryBuilder.length() - 2);
        queryBuilder.append(" WHERE id = ?");
        
        String query = queryBuilder.toString();

        try (Connection connect = connector.initializeDBConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(query)) {

            // Set the values for the dynamic fields
            int index = 1;
            for (Object value : updateFields.values()) {
                if (value instanceof String) {
                    preparedStatement.setString(index++, (String) value);
                } else if (value instanceof Integer) {
                    preparedStatement.setInt(index++, (Integer) value);
                } else if (value instanceof Double) {
                    preparedStatement.setDouble(index++, (Double) value);
                } else {
                    throw new IllegalArgumentException("Unsupported data type: " + value.getClass().getSimpleName());
                }
            }
            // Set the ID parameter
            preparedStatement.setInt(index, id);

            // Execute the update
            int numRows = preparedStatement.executeUpdate();
            return numRows;

        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            System.out.println("SQL Exception: " + e.getMessage());
            return 0;
        }
    }
	
	public String updateFieldById(int id, String field, Object value) {
	    Map<String, Object> update = new HashMap<>();
	    update.put(field, value);
	    int updatedRows = updateById(id, "appliances", update);
	    return "Rows updated: " + updatedRows;
	}
	
	// For testing purposes
	public String dropApplianceTable(String table) {
	    // Ensure only test-related tables are dropped
	    Set<String> allowedTables = Set.of("HomeApplianceTest");

	    if (!allowedTables.contains(table)) {
	        return "Invalid table";
	    }

	    // SQLite-specific queries
	    String disableFKQuery = "PRAGMA foreign_keys = OFF;";
	    String enableFKQuery = "PRAGMA foreign_keys = ON;";
	    String dropApplianceTable = "DROP TABLE IF EXISTS appliances;";
	    String dropCartTable = "DROP TABLE IF EXISTS shopping_cart;";

	    try (Connection conn = connector.initializeDBConnection();
	         Statement stmt = conn.createStatement()) {

	        // Disable foreign key constraints temporarily
	        stmt.executeUpdate(disableFKQuery);

	        // Drop tables
	        stmt.executeUpdate(dropCartTable); // Optional if shopping_cart always exists
	        stmt.executeUpdate(dropApplianceTable);

	        // Re-enable foreign key constraints
	        stmt.executeUpdate(enableFKQuery);

	        return "Tables dropped successfully.";

	    } catch (SQLException e) {
	        String errorMessage = "Failed to drop tables: " + e.getMessage();
	        System.out.println(errorMessage);
	        return errorMessage;
	    }
	}

	
}
