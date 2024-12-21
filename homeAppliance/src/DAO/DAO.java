package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class DAO<T> {
	Connector connector;
	protected HashMap<String, String> tables = new HashMap<String, String>();
	protected ArrayList<String> allowedTablesList = new  ArrayList<String>();
	
	public DAO(){
		
	}
	
	public void changeConnection(Connector connector) {
		this.connector = connector;
	}
	
	public void initializeDBConnection() {
		connector.initializeDBConnection();
		
	}
	
	public void setAllowedTables(String table) {
		allowedTablesList.add(table);
	}
	
	
	public boolean createTable (String name, String schema) {
	    if (checkTableExists(name)) {
	        System.out.println("DEBUG: Table " + name + " already exists.");
	        return false; // Exit early if the table exists
	    }
	    
		try (Connection connect = connector.initializeDBConnection();
			 PreparedStatement preparedStatement = connect.prepareStatement(schema)) {
			
			int updated = preparedStatement.executeUpdate();
			
			System.out.println("Table created");
			
	        return updated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error connecting to the database");
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
		}
	}


	public boolean checkTableExists(String name) {
		String query = "SELECT name FROM sqlite_master WHERE type='table' AND name=? COLLATE NOCASE";
		try (Connection connect = connector.initializeDBConnection();
		     PreparedStatement preparedStatement = connect.prepareStatement(query)) {
		     
		    preparedStatement.setString(1, name); // Set the table name dynamically
		    ResultSet result = preparedStatement.executeQuery();
		    
		    if (result.next()) {
		        System.out.println("Table " + name + " exists.");
		        return true;
		    }
		    
		} catch (SQLException e) {
		    e.printStackTrace();
		    System.out.println("Error checking table existence for: " + name);
		    System.out.println("SQL Exception: " + e.getMessage());
		    return false;
		}

		return false;
	}
	
	protected void addTableMap(String tableName, String schema) {
	    if (tables.containsKey(tableName)) {
	        System.out.println("DEBUG: Table " + tableName + " already exists in the map.");
	        return;
	    }
	    tables.put(tableName, schema);
	}


	protected void initAllTables(HashMap<String, String> tables) {
	    tables.forEach((tableName, schema) -> {
	        if (!checkTableExists(tableName)) {
	            createTable(tableName, schema);
	        } else {
	            System.out.println("DEBUG: Table " + tableName + " already exists.");
	        }
	    });
	}
	
	public boolean addNew(String tableName, Map<String, Object> fields) {
	    StringBuilder queryBuilder = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
	    StringBuilder valuesBuilder = new StringBuilder(" VALUES (");
	    
	    // Dynamically construct column names and placeholders
	    int fieldCount = fields.size();
	    int i = 0;
	    for (String field : fields.keySet()) {
	        queryBuilder.append(field);
	        valuesBuilder.append("?");
	        if (i < fieldCount - 1) {
	            queryBuilder.append(", ");
	            valuesBuilder.append(", ");
	        }
	        i++;
	    }
	    queryBuilder.append(")").append(valuesBuilder).append(")");
	    
	    String query = queryBuilder.toString();
	    
	    try (Connection connect = connector.initializeDBConnection();
	         PreparedStatement preparedStatement = connect.prepareStatement(query)) {
	         
	        // Set the values dynamically
	        int index = 1;
	        for (Object value : fields.values()) {
	            preparedStatement.setObject(index++, value);
	        }
	        
	        // Execute the query
	        int executeRows = preparedStatement.executeUpdate();
	        return executeRows > 0;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("SQL Exception: " + e.getMessage());
	        return false;
	    }
	}
	
	public abstract ArrayList<T> findAll();
	protected abstract ResultSet getById(String query, int id) throws SQLException;
	public abstract int deleteById(int id);
	public abstract int updateById(int id, String table, Map<String, Object> updateFields);

}