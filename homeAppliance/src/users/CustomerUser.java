package users;

/**
 * Represents a customer user in the system, extending the User class.
 * A CustomerUser has details such as a name, email, username, password, and telephone number.
 * 
 * @author 24862664
 */
public class CustomerUser extends User {

    /**
     * Constructs a CustomerUser with the specified details.
     * 
     * @param firstName The first name of the customer.
     * @param lastName The last name of the customer.
     * @param emailAddress The email address of the customer.
     * @param username The username for the customer.
     * @param password The password for the customer.
     * @param telephoneNum The telephone number of the customer.
     */
    public CustomerUser(String firstName, String lastName, String emailAddress, String username, String password, String telephoneNum) {
        super(firstName, lastName, emailAddress, username, password);
        this.setTelephoneNum(telephoneNum);
    }

    /**
     * Returns the role of the user as "customer".
     * 
     * @return The role of the user.
     */
    @Override
    public String getRole() {
        return "customer";
    }
}
