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

import javax.management.relation.Role;

/**
 * This class provides the user database access functionality
 * 
 * @author 24862664
 */
public class UserDao extends DAO<User> {
	String dbPath;
	ArrayList<String> allowedTablesList;
	
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
		    + "post_code TEXT NOT NULL, "
		    + "country TEXT NOT NULL, "
		    + "isPrimary TEXT NOT NULL, "
		    + "address_type TEXT NOT NULL, "
		    + "customer_id INTEGER NOT NULL, "
		    + "address_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
		    + "FOREIGN KEY (customer_id) REFERENCES users(user_id) ON DELETE CASCADE"
		    + ");";

	
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
        populateRoles();

    }	

		@Override
		public ArrayList<User> findAll() {
			ArrayList<User> userList = new ArrayList<>(); 			
			
			String query = "SELECT * FROM users ";
			
	        try (Connection connect = connector.initializeDBConnection();
	        	 Statement statement = connect.createStatement();
	        	 ResultSet result = statement.executeQuery(query)) {
	        	
	        		System.out.println("DEBUG: parse results");
	        		
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
	                   
	                   System.out.println("DEBUG: results added ");
	                   System.out.println("Debug user id: "  + user_id);
	                   System.out.println("Debug user role: "  + role);
	                   
                	   if (!(user_id == 0)) {
							//change to lambda
							switch (role) {
							case "admin":
								user = new AdminUser(firstName, lastName, emailAddress, username, password);
								break;
							case "customer":
								user = new CustomerUser(firstName, lastName, emailAddress, username, password,
										telephoneNum);
								break;
							case "business":
								user = new BusinessUser(firstName, lastName, emailAddress, username, password,
										telephoneNum, businessName);
								break;
							default:
								throw new IllegalArgumentException("Unknown role: " + role);
							}
						} else {
							throw new IllegalArgumentException("No role found");
						}                 
	                   
	                   System.out.println("DEBUG: adding users to list");
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

		            // Create user based on role
		            switch (role) {
		                case "admin":
		                    return new AdminUser(firstName, lastName, emailAddress, username, password);
		                case "customer":
		                    return new CustomerUser(firstName, lastName, emailAddress, username, password, telephoneNum);
		                case "business":
		                    return new BusinessUser(firstName, lastName, emailAddress, username, password, telephoneNum, businessName);
		                default:
		                    throw new IllegalArgumentException("Unknown role: " + role);
		            }
		        } else {
		            throw new IllegalArgumentException("User not found for ID: " + userId);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw new RuntimeException("Error fetching user by ID", e);
		    }
		}
		
		public List<Address> getAddresses(int userId) {
		    String addressQuery = "SELECT building_number, street, city, postcode, country, isPrimary, addressType, address_id " +
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
		            String addressType = addressResult.getString("addressType");

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

		            addressList.add(address);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw new RuntimeException("Error fetching addresses by user ID", e);
		    }

		    return addressList;
		}

		public User getUserWithAddresses(int userId) {
		    User user = getUser(userId);
		    List<Address> addressList = getAddresses(userId);

		    for (Address address : addressList) {
			    // Assign addresses to the user
			    if (user instanceof CustomerUser) {
			        ((CustomerUser) user).addAddress(address);
			    } else if (user instanceof BusinessUser) {
			        ((BusinessUser) user).addAddress(address);
			    }
		    }
		    return user;
		}

		
		public Address getUserAddresses (int id) {
			
			return null;
			
		}
		
		public boolean addNewUser(User user, Map<String, String> additionalFields) {
		    Map<String, Object> fields = new HashMap<>();
		    fields.put("first_name", user.getFirstName());
		    fields.put("last_name", user.getLastName());
		    fields.put("email_address", user.getEmailAddress());
		    fields.put("username", user.getUsername());
		    fields.put("password", user.getPassword());
		    
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

		public boolean addNewAdmin(User newAdmin) {
		    return addNewUser(newAdmin, null);
		}

		public boolean addNewCustomer(User newCustomer, Map<String, String> additionalFields) {
		    Map<String, String> fields = new HashMap<>();
		    additionalFields.put("telephone_num", newCustomer.getTelephoneNum());
		    if (additionalFields != null) {
		        fields.putAll(additionalFields);
		    }
		    return addNewUser(newCustomer, additionalFields);
		}

		public boolean addNewBusiness(User newBusiness, Map<String, String> additionalFields) {
		    Map<String, String> fields = new HashMap<>();
		    additionalFields.put("telephone_num", newBusiness.getTelephoneNum());
		    additionalFields.put("business_name", newBusiness.getBusinessName());
		    if (additionalFields != null) {
		        fields.putAll(additionalFields);
		    }
		    return addNewUser(newBusiness, additionalFields);
		}

		public boolean addAddress(Address address, Map<String, String> additionalFields) {
		    Map<String, Object> fields = new HashMap<>();
		    fields.put("building_number", address.getNumber());
		    fields.put("street", address.getStreet());
		    fields.put("city", address.getCity());
		    fields.put("country", address.getCountry());
		    fields.put("post_code", address.getPostCode());
		    fields.put("customer_id", address.getCustomerId());
		    fields.put("address_type", address.getAddressType());
		    fields.put("isPrimary", address.isPrimary());
		    
		    if (additionalFields != null) {
		        fields.putAll(additionalFields);
		    }
		    
		    return addNew("addresses", fields);
		}



	    @Override
	    public int deleteById(int id) {
	        String query = "DELETE FROM users WHERE user_id = ?";
	        
	        try (Connection connect = connector.initializeDBConnection();
	        	 PreparedStatement preparedStatement = connect.prepareStatement(query)) {
		            preparedStatement.setInt(1, id);
		            int executedRows = preparedStatement.executeUpdate();
		            
		            return executedRows;
	        } catch (SQLException e) {
				System.out.println("Error connecting to the database");
	            System.out.println("SQL Exception: " + e.getMessage());
	            return 0;
	        }
	    }

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

	    public int updateFieldById(int id, String field, Object value) {
	        Map<String, Object> update = new HashMap<>();
	        update.put(field, value);
	        
	        int updatedRows = updateById(id, "users", update);
	        return updatedRows;
	    }

		
		private String getRoleDesc (int id) {
			System.out.println(id);
			
			
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
		
		private int getRoleId(String role) {
		    System.out.println(role);

		    String getRoleQuery = "SELECT role_id FROM roles WHERE role_name = ?";

		    try (Connection connect = connector.initializeDBConnection();
		         PreparedStatement preparedStatement = connect.prepareStatement(getRoleQuery)) {
		    	
		    	System.out.println(role);
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
		
		private boolean addUserRole(int id, String role) {			
			String query = "INSERT INTO user_roles (user_id, role_id)\n"
							+ "VALUES (?, ?);";
			
		    try (Connection connect = connector.initializeDBConnection(); 
		         PreparedStatement preparedStatement = connect.prepareStatement(query)) {
		        
				int role_id = getRoleId(role);
				System.out.println("DEBUG: role id is: " + role_id);
				
		        preparedStatement.setInt(1, id);
		        preparedStatement.setInt(2, role_id);
		        
		        int executeRows = preparedStatement.executeUpdate();
		        return executeRows > 0;
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		        System.out.println("SQL Exception: " + e.getMessage());
		        return false;
		    }
		    
		}
		
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
		
		private boolean populateRoles() {
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
						System.out.println("DEBUG: Populating roles");
				    	statement.executeUpdate(query);

				        return true;
				    } catch (SQLException e) {
				        e.printStackTrace();
						System.out.println("DEBUG: Error inserting roles into table");
				        System.out.println("SQL Exception: " + e.getMessage());
				        return false;
				    }
		        }
		    } catch (SQLException e) {
				System.out.println("DEBUG: Error populating roles during SELECT Statement");
				e.printStackTrace();
		        System.out.println("SQL Exception: " + e.getMessage());
		    }
			return false;
		   
		}
		
		public boolean dropUserTable() {
		    String query = "DROP TABLE Users";

		    try (Connection conn = connector.initializeDBConnection();
		         Statement stmt = conn.createStatement()) {

		        // Execute the SQL query
		        stmt.executeUpdate(query);
		        return true;

		    } catch (SQLException e) {
		        e.printStackTrace();
		        System.out.println("SQL Exception: " + e.getMessage());
		        return false;
		    }
		}
	

}
