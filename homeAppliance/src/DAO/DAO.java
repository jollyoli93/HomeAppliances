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
	
	public int getUniqueId(String table) {
		Connection connect = connector.initializeDBConnection(); 
		int uniqueId = 0;
		
		String query = "SELECT id FROM ? ORDER BY id desc LIMIT 1";
		
		try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
	        preparedStatement.setString(1, table);
	        ResultSet resultSet = preparedStatement.executeQuery();

               if (resultSet.next()) {
            	   int last_id = resultSet.getInt("id");
            	   uniqueId = last_id + 1;
            	   return uniqueId;

               } else {
                   System.out.println("No results found.");
               }
           } catch (SQLException e) {
               System.out.println("SQL Exception: " + e.getMessage());
           }
        
		return 1;
	}
	
	public abstract ArrayList<T> findAll();
	public abstract T getById(int id);
	public abstract boolean addNew(T add);
	public abstract boolean deleteById(int id);
	public abstract boolean updateById(int id);

}