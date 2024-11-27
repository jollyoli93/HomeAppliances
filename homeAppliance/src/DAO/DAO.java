package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	
	public boolean createTable (String name) {
		//check name contains only letters
		
		String query = "CREATE TABLE " + name
				+"( id	INTEGER NOT NULL UNIQUE,"
				+"sku	TEXT NOT NULL,"
				+"description	TEXT NOT NULL,"
				+" category	TEXT NOT NULL,"
				+"price	INTEGER NOT NULL,"
				+"PRIMARY KEY(id AUTOINCREMENT))";
		
		try (Connection connect = connector.initializeDBConnection();
			 PreparedStatement preparedStatement = connect.prepareStatement(query)) {
			
			int updated = preparedStatement.executeUpdate();
			
			System.out.println("Table test created");
			
	        return updated > 0;
		} catch (SQLException e) {
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
			System.out.println("Error connecting to the database");
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
		}
		
		return false;
		
	}
	
	public abstract ArrayList<T> findAll(String table);
	public abstract T getById(int id, String table);
	public abstract boolean addNew(T add, String table);
	public abstract boolean deleteById(int id, String table);
	public abstract boolean updateById(int id, Object update, String table);


}