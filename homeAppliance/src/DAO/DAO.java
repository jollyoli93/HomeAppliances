package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public abstract class DAO<T> {
	Connector connector;
	
	public DAO(){
		
	}
	
	public void changeConnection(Connector connector) {
		this.connector = connector;
	}
	
	public void initializeDBConnection() {
		connector.initializeDBConnection();
		
	}
	
	
	public boolean createTable (String name, String schema) {
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
		String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + name + "' COLLATE NOCASE";

		
		try (Connection connect = connector.initializeDBConnection();
			 PreparedStatement preparedStatement = connect.prepareStatement(query)) {
			
			ResultSet result = preparedStatement.executeQuery();
			
			if (result.next()) {
				System.out.println("table exists.");
				return true;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error connecting to the database");
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
		}
		
		return false;
		
	}
	
	public abstract ArrayList<T> findAll();
	public abstract T getById(int id);
//	public abstract boolean addNew(T add);
	public abstract boolean addNew(T add, Map<String, String> additionalFields);
	public abstract boolean deleteById(int id);
	public abstract boolean updateById(int id, Object updateß);


}