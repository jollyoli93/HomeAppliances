package users;

public class BusinessUser extends User {
	 private String businessName;

	 public BusinessUser(String firstName, String lastName, String emailAddress, String username, String password, String businessName) {
	     super(firstName, lastName, emailAddress, username, password);
	     this.businessName = businessName;
	 }

	 @Override
	 public String getRole() {
	     return "Business";
	 }

	 public String getBusinessName() {
	     return businessName;
	 }
	}