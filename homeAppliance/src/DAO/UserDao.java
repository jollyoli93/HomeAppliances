//STUDENT NO. 24862664
package DAO;

import users.AdminUser;
import users.BusinessUser;
import users.CustomerUser;
import users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
		    + "building_number INTEGER NOT NULL, "
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
        allowedTablesList.add("address");
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
//
//		@Override
//		public Appliance getById(int id) {
//		    String query = "SELECT sku, description, category, price FROM appliances WHERE id = ?";
//
//			Appliance appliance = null;
//			
//			try (Connection connect = connector.initializeDBConnection(); 
//				 PreparedStatement preparedStatement = connect.prepareStatement(query)) {
//			    	preparedStatement.setInt(1, id);
//			        ResultSet result = preparedStatement.executeQuery();
//	        	
//	        	if (result.next()) {     
//	                String desc = result.getString("description");
//	                String cat = result.getString("category");
//	                double price = result.getDouble("price");  
//	        		
//	                try {
//	                    // Get the appropriate factory for the category
//	                    ApplianceFactory factory = ApplianceFactory.selectApplianceFactory(cat);
//	                    
//	                    // Create the specific appliance using the factory
//	                    appliance = factory.selectAppliance(desc);
//	                    
//	                    // Set the common properties
//	                    appliance.setId(id);
//	                    appliance.setPrice(price);
//	                    
//	                    System.out.println("Created: " + appliance.getCategory() + " - " + appliance.getDescription());
//
//	                    
//	                } catch (IllegalArgumentException e) {
//	                    System.out.println("Error retrieving appliance: " + e.getMessage());
//	                }
//	        	}
//	        	
//	        } catch (SQLException e) {
//					System.out.println("Error connecting to the database");
//		               System.out.println("SQL Exception: " + e.getMessage());
//			}
//			
//	    	return appliance;
//		}

		@Override
		public boolean addNew(User user, Map<String, String> additionalFields) {
		    StringBuilder queryBuilder = new StringBuilder("INSERT INTO users (first_name, last_name, email_address, username, password");
		    StringBuilder valuesBuilder = new StringBuilder(" VALUES (?, ?, ?, ?, ?");		    
		    
		    // Add additional fields dynamically
		    if (additionalFields != null) {
		        for (String field : additionalFields.keySet()) {
		            queryBuilder.append(", ").append(field);
		            valuesBuilder.append(", ?");
		        }
		    }
		    queryBuilder.append(")").append(valuesBuilder).append(")");
		    
		    String query = queryBuilder.toString();
		    
		    try (Connection connect = connector.initializeDBConnection(); 
		         PreparedStatement preparedStatement = connect.prepareStatement(query)) {
		        
		        // Set common fields
		        preparedStatement.setString(1, user.getFirstName());
		        preparedStatement.setString(2, user.getLastName());
		        preparedStatement.setString(3, user.getEmailAddress());
		        preparedStatement.setString(4, user.getUsername());
		        preparedStatement.setString(5, user.getPassword());
		        
		        // Set additional fields dynamically
		        int index = 6; // Start after common fields
		        if (additionalFields != null) {
		            for (String fieldValue : additionalFields.values()) {
		                preparedStatement.setString(index++, fieldValue);
		            }
		        }
		        
		        //update database
		        int executeRows = preparedStatement.executeUpdate();

		        //add user role to user_roles table if correctly added
		        if (executeRows > 0) {
			        int last_id = getLastIdEntry();
			        String user_role = user.getRole();
			        addUserRole(last_id, user_role);
		        }
		        
		        return executeRows > 0;
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		        System.out.println("SQL Exception: " + e.getMessage());
		        return false;
		    }
		}

		public boolean addNewAdmin(User newAdmin) {
		    return addNew(newAdmin, null);
		}

		public boolean addNewCustomer(User newCustomer) {
		    Map<String, String> additionalFields = new HashMap<>();
		    additionalFields.put("telephone_num", newCustomer.getTelephoneNum());
		    return addNew(newCustomer, additionalFields);
		}

		public boolean addNewBusiness(User newBusiness) {
		    Map<String, String> additionalFields = new HashMap<>();
		    additionalFields.put("telephone_num", newBusiness.getTelephoneNum());
		    additionalFields.put("business_name", newBusiness.getBusinessName());
		    return addNew(newBusiness, additionalFields);
		}


	    @Override
	    public boolean deleteById(int id) {
	        String query = "DELETE FROM users WHERE user_id = ?";
	        
	        try (Connection connect = connector.initializeDBConnection();
	        	 PreparedStatement preparedStatement = connect.prepareStatement(query)) {
		            preparedStatement.setInt(1, id);
		            int executeRows = preparedStatement.executeUpdate();
		            
		            return executeRows > 0;
	        } catch (SQLException e) {
				System.out.println("Error connecting to the database");
	            System.out.println("SQL Exception: " + e.getMessage());
	            return false;
	        }
	    }

		@Override
		public boolean updateById(int id, String table, Map<String, String> updateFields) {
			System.out.println("DEBUG: update by ID method");
			if (!allowedTablesList.contains(table)) {
				System.out.println("Table not valid.");
				return false;
			}
			
			System.out.println("DEBUG: updating");
		    // Build the SQL query dynamically
		    StringBuilder queryBuilder = new StringBuilder("UPDATE " + table + " SET ");
		    
		    
		    updateFields.forEach((key, value) -> queryBuilder.append(key).append(" = ?, "));
		    
		    // Remove the trailing comma and space
		    queryBuilder.setLength(queryBuilder.length() - 2);
		    queryBuilder.append(" WHERE user_id = ?");
		    
		    String query = queryBuilder.toString();

		    try (Connection connect = connector.initializeDBConnection();
		         PreparedStatement preparedStatement = connect.prepareStatement(query)) {

		        // Set the values for the dynamic fields
		        int index = 1;
		        for (String value : updateFields.values()) {
		            preparedStatement.setString(index++, value);
		        }
		        // Set the ID parameter
		        preparedStatement.setInt(index, id);

		        // Execute the update
		        int updated = preparedStatement.executeUpdate();
		        return updated > 0;

		    } catch (SQLException e) {
		        System.out.println("Error connecting to the database");
		        System.out.println("SQL Exception: " + e.getMessage());
		        return false;
		    }
		}

		
//		public boolean updateFirstNameById (int id, String value) {
//			Map<String, String> update = new HashMap<>();
//			update.put("first_name", value);
//			
//			return updateById(id, "users", update );
//		}
		
		public boolean updateFieldById(int id, String field, String value) {
		    Map<String, String> update = new HashMap<>();
		    update.put(field, value);
		    return updateById(id, "users", update);
		}

		
//		public boolean updateLastNameById (int id, String value) {
//			Map<String, String> update = new HashMap<>();
//			update.put("last_name", value);
//			
//			return updateById(id, "users", update );
//		}
//		
//		public boolean updateEmailById (int id, String value) {
//			Map<String, String> update = new HashMap<>();
//			update.put("email_address", value);
//			
//			return updateById(id, "users", update);
//		}
//		
//		public boolean updatePasswordById (int id, String value) {
//			Map<String, String> update = new HashMap<>();
//			update.put("password", value);
//			
//			return updateById(id, "users", update);
//		}
//
//		public boolean updateBusinessNameById (int id, String value) {
//			Map<String, String> update = new HashMap<>();
//			update.put("business_name", value);
//			
//			return updateById(id, "users", update);
//		}
//		
//		public boolean updateTelehoneNumById (int id, String value) {
//			Map<String, String> update = new HashMap<>();
//			update.put("telephone_num", value);
//			
//			return updateById(id, "users", update);
//		}
		
		//address
		
		//update user role
		//need to check for count  1
	

		@Override
		public User getById(int id) {
			// TODO Auto-generated method stub
			return null;
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
