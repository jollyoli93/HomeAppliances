package homeApplianceStoreDAO;
import java.sql.*;


public class DbConnection {
	    private String path;

    public DbConnection(String path) {
        this.path = path;
    }

    public Connection initializeDBConnection() {
	    Connection connect = null;
        String dbURL = "jdbc:sqlite:" + this.path;

        try {
            Class.forName("org.sqlite.JDBC");
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

//    void queryDB(String query) {
//        try (Statement statement = connect.createStatement();
//             ResultSet result = statement.executeQuery(query)) {
//
//            System.out.println("DBQuery = " + query);
//
//            // Checking if result has at least one row
//            if (result.next()) {
//                // Print first row's 'name'
//                System.out.println("First Row Name: " + result.getString("name"));
//            } else {
//                System.out.println("No results found.");
//            }
//
//            // Printing all the results starting from the second row
//            do {
//                System.out.println("ID: " + result.getInt("id"));
//                System.out.println("First Row Name: " + result.getString("name"));
//                System.out.println("Date of Birth: " + result.getString("dob"));
//                // You can print other columns here as needed
//            } while (result.next());
//
//        } catch (SQLException e) {
//            System.out.println("SQL Exception: " + e.getMessage());
//        }
//    }

}
