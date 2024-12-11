package users;


public abstract class UserFactory {

    public static User selectUserRole(String role, String firstName, String lastName, String emailAddress, String username, String password, String telephoneNum, String businessName) {
        switch (role.toLowerCase()) {
            case "customer":
                return new CustomerFactory().createUser(firstName, lastName, emailAddress, username, password, telephoneNum);
            case "admin":
                return new AdminFactory().createUser(firstName, lastName, emailAddress, username, password, null);
            case "business":
                return new BusinessFactory().createUser(firstName, lastName, emailAddress, username, password, telephoneNum, businessName);
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}


class CustomerFactory extends UserFactory {
	User createUser(String firstName, String lastName, String emailAddress, String username, String password,
			String telephoneNum) {
		// TODO Auto-generated method stub
		return new CustomerUser(firstName, lastName, emailAddress, username, password, telephoneNum);
	}
}

class AdminFactory extends UserFactory {
	User createUser(String firstName, String lastName, String emailAddress, String username, String password,
			String telephoneNum) {
		// TODO Auto-generated method stub
		return new AdminUser(firstName, lastName, emailAddress, username, password);
	}
}

class BusinessFactory extends UserFactory {
	User createUser(String firstName, String lastName, String emailAddress, String username, String password,String telephoneNum, String businessName) {
		// TODO Auto-generated method stub
		return new BusinessUser(firstName, lastName, emailAddress, username, password, telephoneNum, businessName);
	}
}
