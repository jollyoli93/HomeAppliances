//STUDENT NO. 24862664
package DAO;

import users.Address;
import users.AdminUser;
import users.BillingAddress;
import users.BusinessUser;
import users.CustomerUser;
import users.ShippingAddress;
import users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import appliances.Appliance;


/**
 * This class provides the user database access functionality
 * 
 * @author 24862664
 */
public class UserDao extends DAO<User> {
	String dbPath;
	
	String userSchema = "CREATE TABLE \"users\" ("
	        + "\"first_name\" TEXT NOT NULL, "
	        + "\"last_name\" TEXT NOT NULL, "
	        + "\"username\" TEXT NOT NULL UNIQUE, "
	        + "\"email_address\" TEXT NOT NULL UNIQUE, "
	        + "\"telephone_num\" TEXT, "
	        + "\"user_id\" INTEGER NOT NULL UNIQUE, "
	        + "\"password\" TEXT NOT NULL, "
	        +"\n"
	        + "	\"business_name\"	TEXT,"
	        + "PRIMARY KEY(\"user_id\" AUTOINCREMENT)"
	        + ");";
	
	String rolesSchema = "CREATE TABLE roles ("
		    +"role_id INTEGER PRIMARY KEY AUTOINCREMENT,"
		    +"role_name TEXT NOT NULL UNIQUE );";
	
	String userRolesSchema = "CREATE TABLE user_roles ("
		    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
		    + "user_id INTEGER NOT NULL,"
		    + "role_id INTEGER NOT NULL,"
		    + "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,"
		    + "FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE"
		    + ");";
	
	String addressesSchema =  "CREATE TABLE addresses ("
		    + "building_number TEXT NOT NULL, "
		    + "street TEXT NOT NULL, "
		    + "city TEXT NOT NULL, "
		    + "postcode TEXT NOT NULL, "
		    + "country TEXT NOT NULL, "
		    + "isPrimary TEXT NOT NULL, "
		    + "user_id INTEGER NOT NULL, "
		    + "address_type INTEGER NOT NULL, "
		    + "address_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
		    + "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE"
		    + ");";

    /**
     * Constructor for UserDao to initialise database connection and table schemas.
     * 
     * @param dbPath The path to the database file.
     */
	public UserDao(String dbPath) {
        this.dbPath = dbPath;
        this.tables =  new HashMap<>();
        this.connector = new SqlLiteConnection(dbPath);
        this.allowedTablesList = new ArrayList<String>();
        
        allowedTablesList.add("users");
        allowedTablesList.add("addresses");
        allowedTablesList.add("user_roles");
        
        
        addTableMap("users", userSchema);
        addTableMap("roles", rolesSchema);
        addTableMap("user_roles", userRolesSchema);
        addTableMap("addresses", addressesSchema);
        
        initAllTables(tables);
        populateRolesTable();

    }	

	/**
	 * Retrieves all users from the database, optionally sorted by specified parameters.
	 * 
	 * @param id The user ID for filtering results (not used in this method).
	 * @param sortParams A map of column names and sorting directions (ASC/DESC).
	 * @return A list of users matching the criteria, potentially sorted.
	 */
	@Override
	public ArrayList<User> findAll(int id, HashMap<String, Object> sortParams) {
	    ArrayList<User> userList = new ArrayList<>();             

	    // Initialize query
	    StringBuilder queryBuilder = new StringBuilder("SELECT * FROM users");

	    // Add ORDER BY clause dynamically if sortParams are provided
	    if (sortParams != null && !sortParams.isEmpty()) {
	        queryBuilder.append(" ORDER BY ");
	        int i = 0;
	        for (String column : sortParams.keySet()) {
	            queryBuilder.append(column).append(" ").append(sortParams.get(column));  // Column and order (ASC or DESC)
	            if (i < sortParams.size() - 1) {
	                queryBuilder.append(", ");
	            }
	            i++;
	        }
	    }

	    String query = queryBuilder.toString();

	    try (Connection connect = connector.initializeDBConnection();
	         Statement statement = connect.createStatement();
	         ResultSet result = statement.executeQuery(query)) {

	        while (result.next()) {
	            User user = null;

	            int user_id = result.getInt("user_id");
	            String firstName = result.getString("first_name");
	            String lastName = result.getString("last_name");
	            String emailAddress = result.getString("email_address");
	            String username = result.getString("username");
	            String password = result.getString("password");
	            String telephoneNum = result.getString("telephone_num");
	            String businessName = result.getString("business_name");
	            String role = getRoleDesc(user_id);

	            if (user_id != 0) {
	                // Change to lambda
	                switch (role) {
	                    case "admin":
	                        user = new AdminUser(firstName, lastName, emailAddress, username, password);
	                        break;
	                    case "customer":
	                        user = new CustomerUser(firstName, lastName, emailAddress, username, password, telephoneNum);
	                        break;
	                    case "business":
	                        user = new BusinessUser(firstName, lastName, emailAddress, username, password, telephoneNum, businessName);
	                        break;
	                    default:
	                        throw new IllegalArgumentException("Unknown role: " + role);
	                }
	            } else {
	                throw new IllegalArgumentException("No role found");
	            }

	            user.setCustomerId(user_id);
	            userList.add(user);
	        }

	    } catch (SQLException e) {
	        System.out.println("Error connecting to database");
	        System.out.println("SQL Exception: " + e.getMessage());
	    }

	    if (userList.isEmpty()) {
	        System.out.println("No users found");
	    }

	    return userList;
	}

	/**
	 * Retrieves all users sorted by username in descending order.
	 * 
	 * @return A list of users sorted by username in descending order, or null if no users found.
	 */
	public ArrayList<User> getUsersSortedByUsernameDesc() {
	    ArrayList<User> users;
	    HashMap<String, Object> sortParams = new HashMap<>();
	    sortParams.put("username", "DESC");
	    users = findAll(0, sortParams);

	    if (!users.isEmpty()) {
	        return users;
	    }
	    return null;
	}

	/**
	 * Retrieves all users sorted by username in ascending order.
	 * 
	 * @return A list of users sorted by username in ascending order, or null if no users found.
	 */
	public ArrayList<User> getUsersSortedByUsernameAsc() {
	    ArrayList<User> users;
	    HashMap<String, Object> sortParams = new HashMap<>();
	    sortParams.put("username", "ASC");
	    users = findAll(0, sortParams);

	    if (!users.isEmpty()) {
	        return users;
	    }
	    return null;
	}

	/**
	 * Retrieves a user by their unique ID.
	 * 
	 * @param userId The ID of the user to fetch.
	 * @return The user with the specified ID.
	 * @throws IllegalArgumentException If no user is found with the given ID.
	 * @throws SQLException If an error occurs while fetching the user.
	 */
	public User getUser(int userId) {
	    String userQuery = "SELECT user_id, first_name, last_name, username, email_address, telephone_num, password, business_name " +
	                       "FROM users WHERE user_id = ?";

	    try (ResultSet userResult = getById(userQuery, userId)) {
	        if (userResult.next()) {
	            String firstName = userResult.getString("first_name");
	            String lastName = userResult.getString("last_name");
	            String emailAddress = userResult.getString("email_address");
	            String username = userResult.getString("username");
	            String password = userResult.getString("password");
	            String telephoneNum = userResult.getString("telephone_num");
	            String businessName = userResult.getString("business_name");
	            String role = getRoleDesc(userId);

	            User user;
	            // Create user based on role
	            if (userId != 0) {
	                // Change to lambda
	                switch (role) {
	                    case "admin":
	                        user = new AdminUser(firstName, lastName, emailAddress, username, password);
	                        break;
	                    case "customer":
	                        user = new CustomerUser(firstName, lastName, emailAddress, username, password, telephoneNum);
	                        break;
	                    case "business":
	                        user = new BusinessUser(firstName, lastName, emailAddress, username, password, telephoneNum, businessName);
	                        break;
	                    default:
	                        throw new IllegalArgumentException("Unknown role: " + role);
	                }
	            } else {
	                throw new IllegalArgumentException("No role found");
	            }

	            user.setCustomerId(userId);
	            return user;

	        } else {
	            throw new IllegalArgumentException("User not found for ID: " + userId);
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException("Error fetching user by ID", e);
	    }
	}


	/**
	 * Retrieves a list of addresses for a given user.
	 * 
	 * @param userId The ID of the user whose addresses are to be fetched.
	 * @return A list of Address objects associated with the given user.
	 */
	public List<Address> getAddresses(int userId) {
	    String addressQuery = "SELECT building_number, street, city, postcode, country, isPrimary, address_type, address_id " +
	                          "FROM addresses WHERE user_id = ?";
	    List<Address> addressList = new ArrayList<>();

	    try (ResultSet addressResult = getById(addressQuery, userId)) {
	        while (addressResult.next()) {
	            String buildingNumber = addressResult.getString("building_number");
	            String street = addressResult.getString("street");
	            String city = addressResult.getString("city");
	            String postcode = addressResult.getString("postcode");
	            String country = addressResult.getString("country");
	            boolean isPrimary = addressResult.getBoolean("isPrimary");
	            String addressType = addressResult.getString("address_type");
	            int addressId = addressResult.getInt("address_id");
	            
	            Address address;
	            switch (addressType.toLowerCase()) {
	                case "billing":
	                    address = new BillingAddress(buildingNumber, street, city, country, postcode, userId, isPrimary);
	                    break;
	                case "shipping":
	                    address = new ShippingAddress(buildingNumber, street, city, country, postcode, userId, isPrimary);
	                    break;
	                default:
	                    throw new IllegalArgumentException("Unknown address type: " + addressType);
	            }
	            
	            address.setAddress_id(addressId);

	            addressList.add(address);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Error fetching addresses by user ID", e);
	    }

	    return addressList;
	}

	/**
	 * Retrieves a user along with their associated addresses.
	 * 
	 * @param userId The ID of the user to fetch.
	 * @return The user with their associated addresses, or null if the user is not found.
	 */
	public User getUserWithAddresses(int userId) {
	    User user = null;
	    List<Address> addressList = new ArrayList<>();
	    
	    try {
	        user = getUser(userId); 
	        
	        if (user == null) {
	            System.out.println("User not found for ID: " + userId);
	            return null;  // Early exit
	        }

	        // Fetch addresses for the user
	        addressList = getAddresses(userId);
	        
	    } catch (IllegalArgumentException e) {
	        System.out.println("Error: " + e.getMessage());
	        return null; 
	    }

	    // Assign addresses to the user
	    for (Address address : addressList) {
	        if (user instanceof CustomerUser) {
	            ((CustomerUser) user).addAddress(address);
	        } else if (user instanceof BusinessUser) {
	            ((BusinessUser) user).addAddress(address);
	        }
	    }
	    
	    return user;
	}

	/**
	 * Retrieves a specific address for a given user by address ID.
	 * 
	 * @param userId The ID of the user to fetch the address for.
	 * @param addressId The ID of the address to fetch.
	 * @return The Address object matching the provided user and address IDs, or null if not found.
	 */
	public Address getAddress(int userId, int addressId) {
	    String query = "SELECT building_number, street, city, postcode, country, isPrimary, address_type " +
	            "FROM addresses WHERE user_id = ? AND address_id = ?";

	    try (Connection connect = connector.initializeDBConnection();
	         PreparedStatement preparedStatement = connect.prepareStatement(query)){
	        
	        preparedStatement.setInt(1, userId);
	        preparedStatement.setInt(2, addressId);
	        
	        try (ResultSet userResult = preparedStatement.executeQuery()) {
	            if (userResult.next()) {
	                String buildingNumber = userResult.getString("building_number");
	                String street = userResult.getString("street");
	                String city = userResult.getString("city");
	                String postcode = userResult.getString("postcode");
	                String country = userResult.getString("country");
	                boolean isPrimary = userResult.getBoolean("isPrimary");
	                String addressType = userResult.getString("address_type");

	                Address address;
	                switch (addressType.toLowerCase()) {
	                    case "billing":
	                        address = new BillingAddress(buildingNumber, street, city, country, postcode, userId, isPrimary);
	                        break;
	                    case "shipping":
	                        address = new ShippingAddress(buildingNumber, street, city, country, postcode, userId, isPrimary);
	                        break;
	                    default:
	                        throw new IllegalArgumentException("Unknown address type: " + addressType);
	                }
	                
	                address.setAddress_id(addressId);
	                return address;
	            }
	        }
	    } catch (SQLException e) {
	       System.out.println("Failed to fetch address " + e);
	       return null;
	    }
	    return null;
	}


	/**
	 * Adds a new user to the database.
	 *
	 * @param user The user object containing user details.
	 * @param additionalFields Additional fields to be added to the user.
	 * @return true if the user is added successfully, false otherwise.
	 */
	public boolean addNewUser(User user, Map<String, String> additionalFields) {
	    Map<String, Object> fields = new HashMap<>();
	    fields.put("first_name", user.getFirstName());
	    fields.put("last_name", user.getLastName());
	    fields.put("email_address", user.getEmailAddress());
	    fields.put("username", user.getUsername());
	    
	    // Ensure the password is hashed before storing it
	    String plainPassword = user.getPassword();
	    user.setPassword(plainPassword);
	    String hashedPassword = user.getPassword();
	    
	    fields.put("password", hashedPassword);
	    
	    if (additionalFields != null) {
	        fields.putAll(additionalFields);
	    }
	    
	    boolean result = addNew("users", fields);

	    // Add user role if the user is successfully added
	    if (result) {
	        int lastId = getLastIdEntry();
	        addUserRole(lastId, user.getRole());
	    }
	    return result;
	}

	/**
	 * Adds a new admin user.
	 *
	 * @param newAdmin The admin user object to be added.
	 * @return true if the admin is added successfully, false otherwise.
	 */
	public boolean addNewAdmin(User newAdmin) {
	    return addNewUser(newAdmin, null);
	}

	/**
	 * Adds a new customer user.
	 *
	 * @param newCustomer The customer user object to be added.
	 * @param additionalFields Additional fields for the customer user.
	 * @return true if the customer is added successfully, false otherwise.
	 */
	public boolean addNewCustomer(User newCustomer, Map<String, String> additionalFields) {
	    if (additionalFields == null) {
	        additionalFields = new HashMap<>();
	    }
	    
	    additionalFields.put("telephone_num", newCustomer.getTelephoneNum());
	    return addNewUser(newCustomer, additionalFields);
	}

	/**
	 * Adds a new business user.
	 *
	 * @param newBusiness The business user object to be added.
	 * @param additionalFields Additional fields for the business user.
	 * @return true if the business is added successfully, false otherwise.
	 */
	public boolean addNewBusiness(User newBusiness, Map<String, String> additionalFields) {
	    if (additionalFields == null) {
	        additionalFields = new HashMap<>();
	    }

	    additionalFields.put("telephone_num", newBusiness.getTelephoneNum());
	    additionalFields.put("business_name", newBusiness.getBusinessName());
	    return addNewUser(newBusiness, additionalFields);
	}

	/**
	 * Adds an address to the database.
	 *
	 * @param address The address to be added.
	 * @param additionalFields Additional fields for the address.
	 * @return true if the address is added successfully, false otherwise.
	 * @throws IllegalArgumentException if the address is null.
	 */
	public boolean addAddress(Address address, Map<String, String> additionalFields) {
	    if (address == null) {
	        throw new IllegalArgumentException("Address cannot be null.");
	    }

	    if (additionalFields == null) {
	        additionalFields = new HashMap<>();
	    }

	    Map<String, Object> fields = new HashMap<>();
	    fields.put("building_number", address.getNumber());
	    fields.put("street", address.getStreet());
	    fields.put("city", address.getCity());
	    fields.put("country", address.getCountry());
	    fields.put("postcode", address.getPostCode());
	    fields.put("user_id", address.getCustomerId());
	    fields.put("address_type", address.getAddressType());
	    fields.put("isPrimary", address.isPrimary());
	    
	    if (additionalFields != null) {
	        fields.putAll(additionalFields);
	    }

	    return addNew("addresses", fields);
	}

	/**
	 * Updates a row in the specified table by ID.
	 *
	 * @param id The ID of the row to update.
	 * @param table The name of the table.
	 * @param updateFields A map of fields to be updated.
	 * @return The number of rows affected by the update.
	 */
	@Override
	public int updateById(int id, String table, Map<String, Object> updateFields) {
	    if (!allowedTablesList.contains(table)) {
	        System.out.println("Table not valid.");
	        return 0;
	    }

	    // Build the SQL query dynamically
	    StringBuilder queryBuilder = new StringBuilder("UPDATE ").append(table).append(" SET ");
	    updateFields.forEach((key, value) -> queryBuilder.append(key).append(" = ?, "));
	    
	    // Remove the trailing comma and space
	    queryBuilder.setLength(queryBuilder.length() - 2);
	    queryBuilder.append(" WHERE user_id = ?");
	    
	    String query = queryBuilder.toString();

	    try (Connection connect = connector.initializeDBConnection();
	         PreparedStatement preparedStatement = connect.prepareStatement(query)) {

	        // Set the values for the dynamic fields
	        int index = 1;
	        for (Object value : updateFields.values()) {
	            if (value instanceof String) {
	                preparedStatement.setString(index++, (String) value);
	            } else if (value instanceof Integer) {
	                preparedStatement.setInt(index++, (Integer) value);
	            } else if (value instanceof Double) {
	                preparedStatement.setDouble(index++, (Double) value);
	            } else if (value instanceof Boolean) {
	                preparedStatement.setBoolean(index++, (Boolean) value);
	            } else {
	                throw new IllegalArgumentException("Unsupported data type: " + value.getClass().getSimpleName());
	            }
	        }

	        // Set the ID parameter
	        preparedStatement.setInt(index, id);

	        // Execute the update
	        int numRows = preparedStatement.executeUpdate();
	        return numRows;

	    } catch (SQLException e) {
	        System.out.println("Error connecting to the database");
	        System.out.println("SQL Exception: " + e.getMessage());
	        return 0;
	    }
	}

	/**
	 * Updates a specific field in the user table by ID.
	 *
	 * @param id The ID of the user to update.
	 * @param field The field to update.
	 * @param value The new value of the field.
	 * @return The number of rows affected by the update.
	 */
	public int updateFieldById(int id, String field, Object value) {
	    Map<String, Object> update = new HashMap<>();
	    update.put(field, value);
	    
	    int updatedRows = updateById(id, "users", update);
	    return updatedRows;
	}

	/**
	 * Updates the address of a user by ID.
	 *
	 * @param id The ID of the user.
	 * @param address The new address.
	 * @return The number of rows affected by the update.
	 * @throws IllegalArgumentException if the address type is invalid.
	 */
	public int updateUserAddress(int id, Address address) {
	    String addressType = address.getAddressType();
	    // Validate the address type
	    if (!addressType.equals("shipping") && !addressType.equals("billing")) {
	        throw new IllegalArgumentException("Invalid address type. Use 'shipping' or 'billing'.");
	    }

	    // Map to hold the update fields
	    Map<String, Object> updateFields = new HashMap<>();
	    updateFields.put("building_number", address.getNumber());
	    updateFields.put("street", address.getStreet());
	    updateFields.put("city", address.getCity());
	    updateFields.put("country", address.getCountry());
	    updateFields.put("postCode", address.getPostCode());
	    updateFields.put("isPrimary", address.isPrimary());
	    updateFields.put("address_type", addressType);

	    // Call the updateById method for the appropriate table
	    return updateById(id, "addresses", updateFields);
	}

	/**
	 * Deletes a user by ID.
	 *
	 * @param id The ID of the user to delete.
	 * @return The number of rows affected by the delete.
	 */
	public int deleteUserById (int id) {
	    Map<String, Object> update = new HashMap<>();
	    update.put("user_id", id);
	    
	    try {
	        return deleteById("users", update);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return 0;
	}

	
	/**
	 * Retrieves the role description (role name) for a given user ID.
	 *
	 * @param id the user ID for which the role description is being fetched
	 * @return the role name associated with the user, or null if no role is found
	 */
	private String getRoleDesc(int id) {
	    String getRoleQuery = 
	            "SELECT roles.role_name " +
	            "FROM users " +
	            "LEFT OUTER JOIN user_roles " +
	            "ON users.user_id = user_roles.user_id " +
	            "LEFT OUTER JOIN roles " +
	            "ON user_roles.role_id = roles.role_id " +
	            "WHERE users.user_id = ?;";
	    
	    try (Connection connect = connector.initializeDBConnection(); 
	         PreparedStatement preparedStatement = connect.prepareStatement(getRoleQuery)) {
	        preparedStatement.setInt(1, id);
	        ResultSet result = preparedStatement.executeQuery();
	        
	        while (result.next()) {
	            return result.getString("role_name");
	        }
	    
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    
	    return null;
	}

	/**
	 * Retrieves the role ID corresponding to a given role name.
	 *
	 * @param role the name of the role (e.g., "admin", "customer", etc.)
	 * @return the role ID associated with the given role name, or 0 if not found
	 */
	private int getRoleId(String role) {
	    String getRoleQuery = "SELECT role_id FROM roles WHERE role_name = ?";

	    try (Connection connect = connector.initializeDBConnection();
	         PreparedStatement preparedStatement = connect.prepareStatement(getRoleQuery)) {
	       
	        preparedStatement.setString(1, role);
	        ResultSet result = preparedStatement.executeQuery();

	        while (result.next()) {
	            int role_id = result.getInt("role_id");
	            System.out.println(role_id);
	            return role_id;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    System.out.println("Failed to get role_id");
	    return 0;
	}

	/**
	 * Adds a user to the user_roles table by associating the user with a specific role.
	 *
	 * @param id the user ID to be added to the user_roles table
	 * @param role the role name to be assigned to the user
	 * @return the number of rows affected by the insert operation (should be 1 if successful, 0 if failed)
	 */
	private int addUserRole(int id, String role) {            
	    String query = "INSERT INTO user_roles (user_id, role_id)"
	                    + "VALUES (?, ?);";
	    
	    try (Connection connect = connector.initializeDBConnection(); 
	         PreparedStatement preparedStatement = connect.prepareStatement(query)) {
	        
	        int role_id = getRoleId(role);
	        
	        preparedStatement.setInt(1, id);
	        preparedStatement.setInt(2, role_id);
	        
	        int executeRows = preparedStatement.executeUpdate();
	        return executeRows;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("SQL Exception: " + e.getMessage());
	        return 0;
	    }
	}

	/**
	 * Retrieves the last inserted user ID from the users table.
	 *
	 * @return the last user ID inserted into the users table, or 0 if no entries exist
	 */
	private int getLastIdEntry () {
	    String query = "SELECT seq\n"
	                    + "FROM sqlite_sequence\n"
	                    + "WHERE name = 'users';";
	        
	    try (Connection connect = connector.initializeDBConnection(); 
	         PreparedStatement preparedStatement = connect.prepareStatement(query)) {
	            ResultSet result = preparedStatement.executeQuery();

	            while (result.next()) {
	                return result.getInt("seq");
	            }
	        
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    return 0; 
	}

	/**
	 * Populates the roles table with predefined roles: 'customer', 'admin', and 'business'.
	 *
	 * @return true if the roles were successfully inserted into the table, false if an error occurred
	 */
	private boolean populateRolesTable() {
	    String query = "INSERT INTO roles (role_name) VALUES ('customer'),\n"
	            + "('admin'),\n"
	            + "('business');";
	    
	    try (Connection conn = connector.initializeDBConnection()) {
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM roles LIMIT 1");
	        
	        if (!rs.next()) {

	            try (Connection connect = connector.initializeDBConnection();
	                 Statement statement = connect.createStatement()) {
	                
	                // Execute the SQL query
	                statement.executeUpdate(query);

	                return true;
	            } catch (SQLException e) {
	                e.printStackTrace();
	                System.out.println("SQL Exception: " + e.getMessage());
	                return false;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("SQL Exception: " + e.getMessage());
	    }
	    return false;
	}

	/**
	 * Drops the user-related tables from the database. This includes dropping
	 * the user_roles, users, and roles tables.
	 *
	 * @param table the table name to validate before dropping (for security)
	 * @return a message indicating whether the table was dropped or an error occurred
	 */
	public String dropUserTable(String table) {
	    String allowedTable = "HomeApplianceTest";
	    
	    if (!table.equals(allowedTable)) {
	        return "Invalid table";
	    }
	    
	    String disableFKQuery = "PRAGMA foreign_keys = OFF;";  // Disable foreign key checks temporarily
	    String dropUserRolesTable = "DROP TABLE IF EXISTS user_roles;";  // Drop child table first
	    String dropUsersTable = "DROP TABLE IF EXISTS users;";  // Drop parent table
	    String dropRolesTable = "DROP TABLE IF EXISTS roles;";  // Drop roles table

	    try (Connection conn = connector.initializeDBConnection();
	         Statement stmt = conn.createStatement()) {

	        // Disable foreign key constraints temporarily
	        stmt.executeUpdate(disableFKQuery);

	        // Drop the user_roles table first to avoid foreign key constraint issues
	        stmt.executeUpdate(dropUserRolesTable);

	        // Drop the users and roles tables
	        stmt.executeUpdate(dropUsersTable);
	        stmt.executeUpdate(dropRolesTable);

	        // Optionally, you can enable foreign key checks again after the tables are dropped (SQLite-specific)
	        // stmt.executeUpdate("PRAGMA foreign_keys = ON;");

	        return "Table dropped.";

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("SQL Exception: " + e.getMessage());
	        return "Failed to drop table";
	    }
	}

	/**
	 * Retrieves all the roles assigned to a specific user by user ID.
	 *
	 * @param id the user ID whose roles are being fetched
	 * @return a list of role names associated with the user
	 */
	public ArrayList<String> getUserRoles(int id) {
	    ArrayList<String> userRoles = new ArrayList<String>();
	    
	    String query = "SELECT roles.role_name\n"
	                + "FROM user_roles\n"
	                + "LEFT OUTER JOIN roles\n"
	                + "   ON user_roles.role_id = roles.role_id\n"
	                + "WHERE user_roles.user_id = ?;";
	    
	    try (Connection connect = connector.initializeDBConnection(); 
	         PreparedStatement preparedStatement = connect.prepareStatement(query)) {
	        
	        preparedStatement.setInt(1, id);
	        
	        ResultSet result = preparedStatement.executeQuery();

	        while (result.next()) {
	            userRoles.add(result.getString("role_name"));
	        }
	        return userRoles;
	    } catch (SQLException e) {
	        System.out.println("Failed to get user roles");
	        e.printStackTrace();
	    }
	    return userRoles;
	}

	/**
	 * Checks if a user has the 'admin' role.
	 *
	 * @param id the user ID to be checked for the 'admin' role
	 * @return true if the user has the 'admin' role, false otherwise
	 */
	public boolean isUserAdmin(int id) {
	    ArrayList<String> userRoles = new ArrayList<String>();
	    
	    userRoles = getUserRoles(id);
	    
	    if (userRoles.contains("admin")) {
	        System.out.println("User is admin.");
	        return true;
	    }
	    System.out.println("User does not have admin status.");
	    return false;
	}

	/**
	 * Grants the 'admin' role to the specified user.
	 *
	 * @param id the user ID to whom the 'admin' role will be assigned
	 * @return the number of rows affected by the operation (should be 1 if successful, 0 if failed)
	 */
	public int giveAdminStatus(int id) {
	    int updatedRows = 0;

	    try {
	        updatedRows = addUserRole(id, "admin");
	        return updatedRows;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0;
	}

	/**
	 * Removes the 'admin' role from a user, if they have other roles as well. 
	 * If the user only has the 'admin' role, the removal is prevented.
	 *
	 * @param id the user ID from which the 'admin' role will be removed
	 * @return the number of rows affected by the operation (should be 1 if successful, 0 if failed)
	 */
	public int removeAdminById(int id) {
	    ArrayList<String> userRoles = new ArrayList<>();
	    Map<String, Object> update = new HashMap<>();
	    
	    //check if user had additional roles
	    userRoles = getUserRoles(id);
	    
	    if (userRoles.contains("admin") && (userRoles.size() == 1) ) {
	        System.out.println("User has primary admin status. Cannot remove admin status.");
	        return 0;
	    } else if (!userRoles.contains("admin")) {
	        System.out.println("User does not have admin status.");
	        return 0;
	    } else if (userRoles.contains("admin") && (userRoles.size() > 1)) {
	        //select admin role
	        update.put("role_id", 2);
	        //where
	        update.put("user_id", id);
	        
	        try {
	            return deleteById("user_roles", update);
	        } catch (Exception e) {
	            System.out.println("Failed to give admin status.");
	            return 0;
	        }    
	    }
	    return 0;
	}

	/**
	 * Retrieves the user ID associated with a given username.
	 *
	 * @param username the username of the user whose ID is being fetched
	 * @return the user ID associated with the provided username
	 * @throws IllegalArgumentException if no user is found with the given username
	 */
	public int getUserIdFromUsername(String username) {
	    String query = "SELECT user_id FROM users WHERE username = ?";
	    
	    try (Connection connect = connector.initializeDBConnection(); 
	         PreparedStatement preparedStatement = connect.prepareStatement(query)) {
	        
	        preparedStatement.setString(1, username);
	        
	        ResultSet result = preparedStatement.executeQuery();

	        if (result.next()) {
	            int id = result.getInt("user_id");
	            return id;
	        } else {
	            throw new IllegalArgumentException("User not found: " + username);
	        }
	    } catch (SQLException e) {
	       
	        throw new RuntimeException("Error fetching user by username", e);
	    }
	}

}
