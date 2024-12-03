package users;

public class AdminUser extends User {
	 public AdminUser(String firstName, String lastName, String emailAddress, String username, String password) {
	     super(firstName, lastName, emailAddress, username, password);
	 }

	 @Override
	 public String getRole() {
	     return "Admin";
	 }
	}