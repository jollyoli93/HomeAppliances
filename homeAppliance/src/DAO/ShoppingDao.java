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

/**
 * Data Access Object (DAO) for managing shopping cart data in a database.
 * 
 * This class provides methods to interact with the shopping cart table, including adding,
 * retrieving, and removing items from the cart.
 */
public class ShoppingDao extends DAO<ShoppingCartItem> {
    String dbPath;
    ApplianceDao applianceDao;
    UserDao userDao;
    
    // SQL table creation statement for the shopping_cart table
    String cart_table = "CREATE TABLE \"shopping_cart\" (\n"
            + "    \"line_id\"    INTEGER NOT NULL UNIQUE,\n"
            + "    \"user_id\"    INTEGER NOT NULL,\n"
            + "    \"item_id\"    INTEGER NOT NULL,\n"
            + "    FOREIGN KEY(\"item_id\") REFERENCES \"appliances\"(\"id\") ON DELETE CASCADE,\n"
            + "    FOREIGN KEY(\"user_id\") REFERENCES \"users\"(\"user_id\") ON DELETE CASCADE\n"
            + " PRIMARY KEY(line_id AUTOINCREMENT) )";

    /**
     * Constructor for initializing the ShoppingDao with the database path.
     * 
     * @param dbPath the path to the database
     */
    public ShoppingDao(String dbPath) {
        this.dbPath = dbPath;
        this.tables = new HashMap<>();
        this.connector = new SqlLiteConnection(dbPath);
        this.allowedTablesList = new ArrayList<String>();
        this.userDao = new UserDao(dbPath);
        this.applianceDao = new ApplianceDao(dbPath);

        // Create the shopping_cart table if it doesn't already exist
        if (!checkTableExists("shopping_cart")) {
            createTable("shopping_cart", cart_table);
        }
        
        allowedTablesList.add("shopping_cart");
    }

    /**
     * Retrieves all items from the shopping cart for a specific user.
     * 
     * @param userId the ID of the user whose shopping cart items are to be retrieved
     * @param sortParams optional parameters to sort the results (currently not used)
     * @return a list of ShoppingCartItem objects representing the user's cart items
     */
    @Override
    public ArrayList<ShoppingCartItem> findAll(int userId, HashMap<String, Object> sortParams) {
        ArrayList<ShoppingCartItem> items = new ArrayList<>();
        String query = "SELECT line_id, user_id, item_id FROM shopping_cart WHERE user_id = ?;";

        // Get user from the database
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

                // Retrieve appliance from the database
                Appliance appliance = applianceDao.getAppliance(itemId);
                if (appliance == null) {
                    System.out.println("Appliance not found with id: " + itemId);
                    continue;
                }

                // Create a ShoppingCartItem and add it to the list
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

    /**
     * Adds a new line item to the shopping cart.
     * 
     * @param item the ShoppingCartItem to be added
     * @param additionalFields additional fields (if any) to be added to the new item
     * @return true if the item was added successfully, false otherwise
     */
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

    /**
     * Removes a line item from the shopping cart by its ID.
     * 
     * @param id the ID of the line item to be removed
     * @return the number of rows affected by the delete operation
     */
    public int removeLineItem(int id) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("line_id", id);

        try {
            return deleteById("shopping_cart", conditions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Updates a record in the shopping cart by its ID (not implemented in this class).
     * 
     * @param id the ID of the record to be updated
     * @param table the table name (shopping_cart)
     * @param updateFields a map of columns and their new values
     * @return 0 as the update is not supported for this class
     */
    @Override
    public int updateById(int id, String table, Map<String, Object> updateFields) {
        // This method is not implemented
        return 0;
    }
}
