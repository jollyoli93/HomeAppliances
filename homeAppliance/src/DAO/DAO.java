package DAO;

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
	
	public abstract ArrayList<T> findAll();
	public abstract T getById(int id);
	public abstract boolean addById(int id);
	public abstract boolean deleteById(int id);
	public abstract boolean updateById(int id);
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