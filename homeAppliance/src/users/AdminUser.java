package users;

/**
 * Represents an admin user, extending the User class. 
 * This class overrides the getRole method to return "admin".
 * 
 * @author 24862664
 */
public class AdminUser extends User {

    /**
     * Constructs an AdminUser with the given details.
     * 
     * @param firstName The first name of the admin user.
     * @param lastName The last name of the admin user.
     * @param emailAddress The email address of the admin user.
     * @param username The username for the admin user.
     * @param password The password for the admin user.
     */
    public AdminUser(String firstName, String lastName, String emailAddress, String username, String password) {
        super(firstName, lastName, emailAddress, username, password);
    }

    /**
     * Returns the role of the user as "admin".
     * 
     * @return The role of the user.
     */
    @Override
    public String getRole() {
        return "admin";
    }
}
