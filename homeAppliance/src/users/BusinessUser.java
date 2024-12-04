package users;

public class BusinessUser extends User {
	 private String businessName;

	 public BusinessUser(String firstName, String lastName, String emailAddress, String username, String password, String telephoneNum, String businessName) {
	     super(firstName, lastName, emailAddress, username, password);
	     this.businessName = businessName;
	     this.setTelephoneNum(telephoneNum);
	 }

	 @Override
	 public String getRole() {
	     return "business";
	 }

	 public String getBusinessName() {
	     return businessName;
	 }

	}