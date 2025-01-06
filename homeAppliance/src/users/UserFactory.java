package users;

/**
 * Abstract factory class responsible for creating users of different roles. 
 * This class provides the common method selectUserRole to create instances 
 * of User subclasses based on the specified role.
 * 
 * @author 24862664
 */
public abstract class UserFactory {
	
    /**
     * Selects the appropriate user role and creates the corresponding user instance.
     * 
     * @param role The role of the user to create (e.g., "customer", "admin", "business").
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param emailAddress The email address of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param telephoneNum The telephone number of the user (optional, may be null for admins).
     * @param businessName The business name (only required for "business" users).
     * @return The created User instance based on the specified role.
     * @throws IllegalArgumentException If the role is invalid.
     */
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

/**
 * Factory class responsible for creating instances of CustomerUser.
 * This class implements the createUser method to instantiate and return a CustomerUser.
 */
class CustomerFactory extends UserFactory {
    /**
     * Creates a new CustomerUser instance.
     * 
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param emailAddress The email address of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param telephoneNum The telephone number of the user.
     * @return A new CustomerUser instance.
     */
	User createUser(String firstName, String lastName, String emailAddress, String username, String password,
			String telephoneNum) {
		return new CustomerUser(firstName, lastName, emailAddress, username, password, telephoneNum);
	}
}

/**
 * Factory class responsible for creating instances of AdminUser.
 * This class implements the createUser method to instantiate and return an AdminUser.
 */
class AdminFactory extends UserFactory {
    /**
     * Creates a new AdminUser instance.
     * 
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param emailAddress The email address of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param telephoneNum The telephone number of the user (not required for admins).
     * @return A new AdminUser instance.
     */
	User createUser(String firstName, String lastName, String emailAddress, String username, String password,
			String telephoneNum) {
		return new AdminUser(firstName, lastName, emailAddress, username, password);
	}
}

/**
 * Factory class responsible for creating instances of BusinessUser.
 * This class implements the createUser method to instantiate and return a BusinessUser.
 */
class BusinessFactory extends UserFactory {
	
    /**
     * Creates a new BusinessUser instance.
     * 
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param emailAddress The email address of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param telephoneNum The telephone number of the user.
     * @param businessName The business name of the user.
     * @return A new BusinessUser instance.
     */
	User createUser(String firstName, String lastName, String emailAddress, String username, String password,
			String telephoneNum, String businessName) {
		return new BusinessUser(firstName, lastName, emailAddress, username, password, telephoneNum, businessName);
	}
}
