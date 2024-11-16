//STUDENT NO. 24862664
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import homeApplianceStore.Appliance;
import homeApplianceStore.ApplianceFactory;


public class ApplianceDao extends DAO<Appliance> {
	String path;
	private Map<String, ApplianceFactory> categoryFactories;
	
   public ApplianceDao(String path, Map<String, ApplianceFactory> factories) {
        this.path = path;
        connector = new SqlLiteConnection(path);
        this.categoryFactories = factories;
    }
   
   public void initializeFactories () {
	   
   };

	@Override
	public ArrayList<Appliance> findAll() {
		ArrayList<Appliance> applianceList = new ArrayList<>(); 
		Connection connect = connector.initializeDBConnection(); 
		
		String query = "SELECT * FROM appliances";
		
		return null;
	}

//	@Override
//	public ArrayList<Appliance> findAll() {
//		ArrayList<Appliance> applianceList = new ArrayList<>(); 
//		Connection connect = connector.initializeDBConnection(); 
//		
//		String query = "SELECT * FROM appliances";
//		
//        try (Statement statement = connect.createStatement();
//             ResultSet result = statement.executeQuery(query)) {
//
//               if (result.next()) {
//            	   System.out.println("Listing products");
//				
//            	   do {
//            		   Appliance product;
//                	   
//                       int id = result.getInt("id");
//                       String sku = result.getString("sku");
//                       String desc = result.getString("description");
//                       String cat = result.getString("category");
//                       int price = result.getInt("price");
//                       
//                       product = new Appliance(id, sku, desc, cat, price);
//
//                       applianceList.add(product);
//                       
//                   } while (result.next());
//               } else {
//                   System.out.println("No results found.");
//               }
//
//
//           } catch (SQLException e) {
//               System.out.println("SQL Exception: " + e.getMessage());
//           }
//		
//		return applianceList;
//	}
//
//	@Override
//	public Appliance getById(int id) {
//	    String query = "SELECT sku, description, category, price FROM appliances WHERE id = ?";
//		Connection connect = connector.initializeDBConnection(); 
//		Appliance appliance = null;
//		
//		try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
//		        preparedStatement.setInt(1, id);
//		        ResultSet resultSet = preparedStatement.executeQuery();
//        	
//        	if (resultSet.next()) {
//        		String sku = resultSet.getString("sku");
//                String desc = resultSet.getString("description");
//                String cat = resultSet.getString("category");
//                double price = resultSet.getDouble("price");
//        		
//        		appliance = new Appliance(id, sku, desc, cat, price);
//        	} else {
//                System.out.println("No results found.");
//            }
//        	
//        } catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//    	return appliance;
//	}

	@Override
	public boolean addNew(Appliance newAppliance) {	
		String query =  "INSERT INTO appliances (id, sku, description, category, price) VALUES (?, ?, ?, ?, ?)";
		Connection connect = connector.initializeDBConnection(); 
		
		try (PreparedStatement preparedStatement = connect.prepareStatement(query)){
			preparedStatement.setInt(1, newAppliance.getId());
			preparedStatement.setString(2, newAppliance.getSku());
			preparedStatement.setString(3, newAppliance.getDescription());
			preparedStatement.setString(4, newAppliance.getCategory());
			preparedStatement.setDouble(5, newAppliance.getPrice());
					
			int executeRows = preparedStatement.executeUpdate();
			
			return executeRows > 0;
					 
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

    @Override
    public boolean deleteById(int id) {
        String query = "DELETE FROM appliances WHERE id = ?";
        Connection connect = connector.initializeDBConnection();
        
        try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int executeRows = preparedStatement.executeUpdate();
            
            return executeRows > 0;
        } catch (SQLException e) {
        	
            e.printStackTrace();
            return false;
        }
    }

    
    //Temporary
    
	@Override
	public boolean updateById(int id) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public Appliance getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
