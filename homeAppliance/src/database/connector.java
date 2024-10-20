package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface connector {
	Connection initializeDBConnection();
}

class SqlLiteConnection implements connector {
    private String path;
    private String driver;
    private Connection connect = null;

	public SqlLiteConnection(String path, String driver) {
	    this.path = path;
	    this.driver = driver;
	}
	
	public Connection initializeDBConnection() {
	    String dbURL = "jdbc:sqlite:" + this.path;
	
	    try {
	        Class.forName(driver);
	    } catch (ClassNotFoundException e) {
	        System.out.println("SQLite JDBC Driver not found: " + e.getMessage());
	    }
	
	    try {
	        connect = DriverManager.getConnection(dbURL);
	        System.out.println("Connected to " + dbURL);
	    } catch (SQLException e) {
	        System.out.println("Connection failed: " + e.getMessage());
	    }
		return connect;
	}
	}
