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
import java.util.Map;

/**
 * This class provides the user database access functionality
 * 
 * @author 24862664
 */
public class UserDao extends DAO<User> {
	String dbPath;
	String userSchema;
	String rolesSchema;
	String user_rolesSchema;
	
	public UserDao(String dbPath) {
        this.dbPath = dbPath;
        connector = new SqlLiteConnection(dbPath);
        
    	this.userSchema = 
    			"CREATE TABLE \"users\" ("
    			        + "\"first_name\" TEXT NOT NULL, "
    			        + "\"last_name\" TEXT NOT NULL, "
    			        + "\"username\" TEXT NOT NULL UNIQUE, "
    			        + "\"email_address\" TEXT NOT NULL UNIQUE, "
    			        + "\"telephone_num\" TEXT, "
    			        + "\"user_id\" INTEGER NOT NULL UNIQUE, "
    			        + "\"password\" TEXT NOT NULL, "
    			        + "PRIMARY KEY(\"user_id\" AUTOINCREMENT)"
    			        + ");";
    	this.rolesSchema = 
    			"CREATE TABLE roles ("
    				    +"role_id INTEGER PRIMARY KEY AUTOINCREMENT,"
    				    +"role_name TEXT NOT NULL UNIQUE );";
    	
    	this.user_rolesSchema =
    			"CREATE TABLE user_roles ("
    				    +"user_id INTEGER NOT NULL,"
    				    +"role_id INTEGER NOT NULL,"
    				    +"FOREIGN KEY (user_id) REFERENCES users(user_id),"
    				    +"FOREIGN KEY (role_id) REFERENCES roles(role_id),"
    				    +"PRIMARY KEY (user_id, role_id));";


    	
    }	
	
		@Override
		public ArrayList<User> findAll() {
			ArrayList<User> userList = new ArrayList<>(); 
			
			if (!checkTableExists("users")) {
				createTable("users", userSchema);
			}
			
			
			String query = "SELECT * FROM users ";
			
	        try (Connection connect = connector.initializeDBConnection();
	        	 Statement statement = connect.createStatement();
	        	 ResultSet result = statement.executeQuery(query)) {

	               while (result.next()) {
	            	   User user = null;
	            	   
	                   String firstName = result.getString("first_name");
	                   String lastName = result.getString("last_name");
	                   String emailAddress = result.getString("email_address");
	                   String username = result.getString("username");
	                   String password = result.getString("password");
	                   String role = result.getString("role");
	                   String telephoneNum = result.getString("telephone_num");
	                   String businessName = result.getString("business_name");
	                   
	                   try {
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
	                                       
	                   } catch (IllegalArgumentException e) {
	                       System.out.println("Error creating appliance: " + e.getMessage());
	                   }
	                   
	                   userList.add(user);
	               } 

	           } catch (SQLException e) {
	   				System.out.println("Error connecting to the database");
	               System.out.println("SQL Exception: " + e.getMessage());
	               
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
//
		@Override
		public boolean addNew(User newUser) {	
			String query =  "INSERT INTO users " + " (first_name, last_name, email_address, username, password) VALUES (?, ?, ?, ?, ?)";
			
			//generates additional connection comments
			if (!checkTableExists("users")) {
				createTable("users", userSchema);
			}
			
			try (Connection connect = connector.initializeDBConnection(); 
				 PreparedStatement preparedStatement = connect.prepareStatement(query)){
					preparedStatement.setString(1, newUser.getFirstName());
					preparedStatement.setString(2, newUser.getLastName());
					preparedStatement.setString(3, newUser.getEmailAddress());
					preparedStatement.setString(4, newUser.getUsername());
					preparedStatement.setString(5, newUser.getPassword());
					//preparedStatement.setString(5, newUser.getTelephoneNum());
					
							
					int executeRows = preparedStatement.executeUpdate();
					
					return executeRows > 0;
							 
			} catch (SQLException e) {
				e.printStackTrace();
	            System.out.println("SQL Exception: " + e.getMessage());
				return false;
			}

		}
//
//	    @Override
//	    public boolean deleteById(int id) {
//	        String query = "DELETE FROM appliances WHERE id = ?";
//	        
//	        try (Connection connect = connector.initializeDBConnection();
//	        	 PreparedStatement preparedStatement = connect.prepareStatement(query)) {
//		            preparedStatement.setInt(1, id);
//		            int executeRows = preparedStatement.executeUpdate();
//		            
//		            return executeRows > 0;
//	        } catch (SQLException e) {
//				System.out.println("Error connecting to the database");
//	            System.out.println("SQL Exception: " + e.getMessage());
//	            return false;
//	        }
//	    }
//
//		@Override
//		public boolean updateById(int id, Object update) {
//			String query = "UPDATE appliances SET price = ? WHERE id = ?";
//			
//			try (Connection connect = connector.initializeDBConnection();
//				PreparedStatement preparedStatement = connect.prepareStatement(query)) {
//					preparedStatement.setDouble(1, (Double) update);
//					preparedStatement.setInt(2, id);
//				
//			        int updated = preparedStatement.executeUpdate();
//			        return updated > 0;
//				
//			} catch (SQLException e) {
//				System.out.println("Error connecting to the database");
//	            System.out.println("SQL Exception: " + e.getMessage());
//				return false;
//			}
//		}
//
//	}
//
//	

		@Override
		public User getById(int id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean deleteById(int id) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean updateById(int id, Object updateß) {
			// TODO Auto-generated method stub
			return false;
		}
	
}
