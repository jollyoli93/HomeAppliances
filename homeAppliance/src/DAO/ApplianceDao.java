//STUDENT NO. 24862664
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import homeApplianceStore.HomeAppliance;

public class ApplianceDao extends DAO<HomeAppliance> {
	String path;
	
	public ApplianceDao(String path) {
		connector = new SqlLiteConnection(path);
	}

	@Override
	public ArrayList<HomeAppliance> findAll() {
		ArrayList<HomeAppliance> applianceList = new ArrayList<>(); 
		Connection connect = connector.initializeDBConnection(); 
		
		String query = "SELECT * FROM appliances";
		
        try (Statement statement = connect.createStatement();
             ResultSet result = statement.executeQuery(query)) {

               if (result.next()) {
            	   System.out.println("Listing products");
				
            	   do {
                	   HomeAppliance product;
                	   
                       int id = result.getInt("id");
                       String sku = result.getString("sku");
                       String desc = result.getString("description");
                       String cat = result.getString("category");
                       int price = result.getInt("price");
                       
                       product = new HomeAppliance(id, sku, desc, cat, price);

                       applianceList.add(product);
                       
                   } while (result.next());
               } else {
                   System.out.println("No results found.");
               }


           } catch (SQLException e) {
               System.out.println("SQL Exception: " + e.getMessage());
           }
		
		return applianceList;
	}

	@Override
	public HomeAppliance getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addNew(int id) {
		//INSERT INTO appliances (id, sku, description, category, price)
		//VALUES (002, 'WM001', 'Washing machine', 'cleaning', 300.00)
		return false;
	}

	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
