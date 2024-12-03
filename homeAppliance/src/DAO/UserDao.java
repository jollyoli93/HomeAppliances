package DAO;

import users.AdminUser;
import users.BusinessUser;
import users.CustomerUser;
import users.User;

public class UserDao {
	public User getUserFromDatabase(ResultSet rs) throws SQLException {
	    String roleName = rs.getString("role_name");
	    String firstName = rs.getString("first_name");
	    String lastName = rs.getString("last_name");
	    String emailAddress = rs.getString("email_address");
	    String username = rs.getString("username");
	    String passwordHash = rs.getString("password_hash");
	    String telephoneNum = rs.getString("telephone_num");
	    String businessName = rs.getString("business_name");

	    switch (roleName) {
	        case "Admin":
	            return new AdminUser(firstName, lastName, emailAddress, username, password);
	        case "Customer":
	            return new CustomerUser(firstName, lastName, emailAddress, username, password, telephoneNum);
	        case "Business":
	            return new BusinessUser(firstName, lastName, emailAddress, username, password, telephoneNum, businessName);
	        default:
	            throw new IllegalArgumentException("Unknown role: " + roleName);
	    }
	}

}
