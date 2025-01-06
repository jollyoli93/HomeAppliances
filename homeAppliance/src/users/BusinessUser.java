package users;

/**
 * Represents a business user in the system, extending the User class. 
 * A BusinessUser has additional details such as a business name and a telephone number.
 * This class is specifically used to handle users who operate a business, 
 * distinguishing them from other types of users like customers or admins.
 * 
 * @author 24862664
 */
public class BusinessUser extends User {
    private String businessName;

    /**
     * Constructs a BusinessUser with the specified details.
     * 
     * @param firstName The first name of the business user.
     * @param lastName The last name of the business user.
     * @param emailAddress The email address of the business user.
     * @param username The username for the business user.
     * @param password The password for the business user.
     * @param telephoneNum The telephone number of the business user.
     * @param businessName The name of the business the user operates.
     */
    public BusinessUser(String firstName, String lastName, String emailAddress, String username, String password, String telephoneNum, String businessName) {
        super(firstName, lastName, emailAddress, username, password);
        this.businessName = businessName;
        this.setTelephoneNum(telephoneNum);
    }

    /**
     * Returns the role of the user as "business".
     * 
     * @return The role of the user.
     */
    @Override
    public String getRole() {
        return "business";
    }

    /**
     * Returns the business name associated with this BusinessUser.
     * 
     * @return The name of the business.
     */
    public String getBusinessName() {
        return businessName;
    }
}
