package database;

import java.sql.*;


public class DbConnection {
	    private String path;
	    private String driver;
	    private Connection connect = null;

    public DbConnection(String path, String driver) {
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

    public void queryDB(String query) {
//        try (Statement statement = connect.createStatement();
//             ResultSet result = statement.executeQuery(query)) {
//
//            System.out.println("DBQuery = " + query);
//
//            // Checking if result has at least one row
//            if (result.next()) {
//                System.out.println("First Row Name: " + result.getString("id"));
//            } else {
//                System.out.println("No results found.");
//            }
//
//            // Printing all the results starting from the second row
//            do {
//                System.out.println("ID: " + result.getInt("id"));
//                System.out.println("SKU: " + result.getString("sku"));
//                System.out.println("Description " + result.getString("description"));
//                System.out.println("Category " + result.getString("category"));
//            } while (result.next());
//
//        } catch (SQLException e) {
//            System.out.println("SQL Exception: " + e.getMessage());
//        }
    }

}
