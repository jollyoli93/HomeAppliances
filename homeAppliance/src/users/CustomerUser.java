package users;

public class CustomerUser extends User {
	public CustomerUser(String firstName, String lastName, String emailAddress, String username, String password) {
   super(firstName, lastName, emailAddress, username, password);
}

@Override
public String getRole() {
   return "Customer";
}
}