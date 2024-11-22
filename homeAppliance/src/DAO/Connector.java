package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface Connector {
	Connection initializeDBConnection();
}


class SqlLiteConnection implements Connector {
    private String path;
    private String driver = "org.sqlite.JDBC";
    private Connection connect = null;

	public SqlLiteConnection(String path) {
	    this.path = path;
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

