package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface Connector {
	Connection initializeDBConnection();
}


class SqlLiteConnection implements Connector {
    private String dbName;
    private String driver = "org.sqlite.JDBC";
    private Connection connect = null;

	public SqlLiteConnection(String dbName) {
	    this.dbName = dbName;
	}
	
	public Connection initializeDBConnection() {
	    String dbURL = "jdbc:sqlite:" + this.dbName + ".db";
	
	    try {
			System.out.println();

	    	//System.out.println("Inititializing Connection");
	        Class.forName(driver);
	    } catch (ClassNotFoundException e) {
	        System.out.println("SQLite JDBC Driver not found: " + e.getMessage());
	    }
	
	    try {
	        connect = DriverManager.getConnection(dbURL);
	        //System.out.println("Connecting to " + dbURL);
	    } catch (SQLException e) {
	        System.out.println("Connection failed: " + e.getMessage());
	    }
	    System.out.println("Successfully connected.");
	    System.out.println();
		return connect;
	}
}

