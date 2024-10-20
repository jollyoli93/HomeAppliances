package DAO;

//import java.sql.*;
//import java.sql.Connection;
//import java.sql.DatabaseMetaData;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;

public abstract class DAO<T> {
	Connector connector;
	
	public DAO(){
		
	}
	
	public Connector changeConnection(Connector connector) {
		this.connector = connector;
		return connector;
	}
	
	public void initializeDBConnection() {
		connector.initializeDBConnection();
		
	}
	
	public void closeDbConnection() {
		//TODO
	};	
	
	
	
//	Object getById(int id);
//	<T> ArrayList<T> findAll();
//	boolean addById(int id);
//	boolean deleteById(int id);
//	boolean updateById(int id);
//}
//
//class findAll () {
//	String path;
//	String driver;
//	
//	
//	ArrayList<T> list = new ArrayList<>(); 
//	DbConnection con = new DbConnection(String path, String driver);
//	
//	Connection connect= con.initializeDBConnection(); 
//	
//	String query = "SELECT * FROM appliances";
//	
//    try (Statement statement = connect.createStatement();
//         ResultSet result = statement.executeQuery(query)) {
//
//           if (result.next()) {
//        	   System.out.println("Listing products");
//			
//        	   do {
//            	   HomeAppliance product;
//            	   
//                   int id = result.getInt("id");
//                   String sku = result.getString("sku");
//                   String desc = result.getString("description");
//                   String cat = result.getString("category");
//                   int price = result.getInt("price");
//                   
//                   product = new HomeAppliance(id, sku, desc, cat, price);
//                   
//                   applianceList.add(product);
//                   
//               } while (result.next());
//           } else {
//               System.out.println("No results found.");
//           }
//
//
//       } catch (SQLException e) {
//           System.out.println("SQL Exception: " + e.getMessage());
//       }
//	
//	return applianceList;
}