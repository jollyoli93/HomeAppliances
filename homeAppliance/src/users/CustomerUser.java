package users;

public class CustomerUser extends User {
	public CustomerUser(String firstName, String lastName, String emailAddress, String username, String password, String telephoneNum) {
		super(firstName, lastName, emailAddress, username, password);
			this.setTelephoneNum(telephoneNum);
		}
	
	@Override
	public String getRole() {
	   return "customer";
	}

}