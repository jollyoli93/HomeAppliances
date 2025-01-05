package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Interface to define methods for initialising a database connection.
 */
public interface Connector {
    /**
     * Initialises and returns a database connection.
     * 
     * @return the initialised database connection
     */
    Connection initializeDBConnection();
}

/**
 * Implementation of the Connector interface for SQLite.
 * 
 * This class is responsible for establishing a connection to an SQLite database.
 * 
 * @author 24862664
 */
class SqlLiteConnection implements Connector {
    private String dbName;
    private String driver = "org.sqlite.JDBC";
    private Connection connect = null;

    /**
     * Constructs a SqlLiteConnection instance with the given database name.
     * 
     * @param dbName the name of the SQLite database file
     */
    public SqlLiteConnection(String dbName) {
        this.dbName = dbName;
    }

    /**
     * Initialises and returns a connection to the SQLite database.
     * If the connection is successful, it returns the connection object.
     * If there is an error in establishing the connection, it prints the error message.
     * 
     * @return the database connection object
     */
    public Connection initializeDBConnection() {
        String dbURL = "jdbc:sqlite:" + this.dbName + ".db";
    
        try {
            System.out.println();

            // Load the SQLite JDBC driver
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC Driver not found: " + e.getMessage());
        }
    
        try {
            // Establish connection to the database
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
