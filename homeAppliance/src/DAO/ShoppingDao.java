package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import appliances.Appliance;
import shoppingCart.ShoppingCartItem;
import users.User;

public class ShoppingDao extends DAO<ShoppingCartItem> {
	String dbPath;
	ApplianceDao applianceDao;
	UserDao userDao;
	
	String cart_table = "CREATE TABLE \"shopping_cart\" (\n"
			+ "	\"line_id\"	INTEGER NOT NULL UNIQUE,\n"
			+ "	\"user_id\"	INTEGER NOT NULL,\n"
			+ "	\"item_id\"	INTEGER NOT NULL,\n"
			+ "	FOREIGN KEY(\"item_id\") REFERENCES \"appliances\"(\"id\") ON DELETE CASCADE,\n"
			+ "	FOREIGN KEY(\"user_id\") REFERENCES \"users\"(\"user_id\") ON DELETE CASCADE\n"
			+ " PRIMARY KEY(line_id AUTOINCREMENT) )";

	public ShoppingDao(String dbPath) {
        this.dbPath = dbPath;
        this.tables =  new HashMap<>();
        this.connector = new SqlLiteConnection(dbPath);
        this.allowedTablesList = new ArrayList<String>();
        this.userDao = new UserDao(dbPath);
        this.applianceDao = new ApplianceDao(dbPath);
        
		if (!checkTableExists("shopping_cart")) {
			createTable("shopping_cart", cart_table);
		}
        
        allowedTablesList.add("shopping_cart");
	}
	
	@Override
	public ArrayList<ShoppingCartItem> findAll(int userId, HashMap<String, Object> sortParams) {
	    ArrayList<ShoppingCartItem> items = new ArrayList<>();
	    String query = "SELECT line_id, user_id, item_id FROM shopping_cart WHERE user_id = ?;";

	    // Get user from db
	    User user = userDao.getUser(userId);
	    if (user == null) {
	        System.out.println("User not found with id: " + userId);
	        return null;
	    }

	    try (Connection connect = connector.initializeDBConnection();
	         PreparedStatement preparedStatement = connect.prepareStatement(query)) {

	        preparedStatement.setInt(1, userId);
	        ResultSet results = preparedStatement.executeQuery();

	        while (results.next()) {
	            int lineId = results.getInt("line_id");
	            int itemId = results.getInt("item_id");

	            // Retrieve appliance from db
	            Appliance appliance = applianceDao.getAppliance(itemId);
	            if (appliance == null) {
	                System.out.println("Appliance not found with id: " + itemId);
	                continue;
	            }

	            // Create a ShoppingCartItem and add to the list
	            ShoppingCartItem item = new ShoppingCartItem(user, appliance);
	            item.setId(lineId);
	            items.add(item);
	        }

	    } catch (SQLException e) {
	        System.out.println("SQL Exception in findAll: " + e.getMessage());
	        return null;
	    }

	    return items; // Return the populated list
	}

	
	public boolean addNewLineItem(ShoppingCartItem item, Map<String, String> additionalFields) {
	    Map<String, Object> fields = new HashMap<>();
	    fields.put("user_id", item.getUserId());
	    fields.put("item_id", item.getItemId());
	    
	    if (additionalFields != null) {
	        fields.putAll(additionalFields);
	    }
	    
	    boolean result = addNew("shopping_cart", fields);

	    return result;
	}
	
	public int removeLineItem (int id) {
    	Map<String, Object> conditions = new HashMap<>();
    	conditions.put("line_id", id);

		try {
			return deleteById("shopping_cart", conditions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateById(int id, String table, Map<String, Object> updateFields) {
		// TODO Auto-generated method stub
		return 0;
	}
}
