package users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Abstract class representing a user. This class contains common attributes and 
 * methods that all user types (e.g., customer, business, admin) share. The 
 * password is hashed and can be validated. Users can also have multiple addresses.
 * 
 * @author 24862664
 */
public abstract class User {

    private String firstName;
    private String lastName;
    private String username;
    private String emailAddress;
    private String password;
    private int customerId;
    private String telephoneNum;
    private String businessName;

    private ArrayList<Address> addressList;

    /**
     * Constructs a User with the specified details.
     * 
     * @param firstname The first name of the user.
     * @param lastName The last name of the user.
     * @param emailAddress The email address of the user.
     * @param username The username of the user.
     * @param password The password of the user (will be hashed).
     */
    User(String firstname, String lastName, String emailAddress, String username, String password) {
        this.firstName = firstname;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Retrieves the user's telephone number.
     * 
     * @return The user's telephone number.
     */
    public String getTelephoneNum() {
        return this.telephoneNum;
    }

    /**
     * Sets the user's telephone number. The number must be 11 digits.
     * 
     * @param number The telephone number to set.
     * @throws IllegalArgumentException If the telephone number is invalid.
     */
    public void setTelephoneNum(String number) {
        if (number == null) {
            this.telephoneNum = null;
        } else if (number != null && number.matches("\\d{11}")) {
            this.telephoneNum = number;
        } else {
            throw new IllegalArgumentException("Invalid telephone number. It must be 11 digits.");
        }
    }

    /**
     * Adds an address to the user's address list.
     * 
     * @param address The address to add.
     * @throws IllegalArgumentException If the address is null.
     */
    public void addAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null.");
        }
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
        addressList.add(address);
    }

    /**
     * Retrieves a list of the user's addresses.
     * 
     * @return A list of addresses associated with the user.
     */
    public ArrayList<Address> getAddresses() {
        return addressList == null ? new ArrayList<>() : new ArrayList<>(addressList);
    }

    /**
     * Removes an address from the user's address list.
     * 
     * @param address The address to remove.
     */
    public void removeAddress(Address address) {
        if (addressList != null) {
            addressList.remove(address);
        }
    }

    /**
     * Retrieves the user's password (hashed).
     * 
     * @return The hashed password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets a new password for the user and hashes it.
     * 
     * @param password The password to set.
     */
    public void setPassword(String password) {
        String hashedPassword = hashPassword(password);
        this.password = hashedPassword;
    }

    /**
     * Validates if the provided password matches the user's stored password.
     * 
     * @param inputPassword The input password to validate.
     * @return True if the passwords match, otherwise false.
     */
    public boolean validatePassword(String inputPassword) {
        // Hash the input password and compare to stored hash
        String hashedInputPassword = hashPassword(inputPassword);
        return this.password.equals(hashedInputPassword);
    }

    /**
     * Hashes the provided password using the SHA-256 algorithm.
     * 
     * @param password The password to hash.
     * @return The hashed password as a hexadecimal string.
     */
    public String hashPassword(String password) {
        if (password != null) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashedBytes = digest.digest(password.getBytes());
                StringBuilder hexString = new StringBuilder();
                for (byte b : hashedBytes) {
                    hexString.append(String.format("%02x", b));
                }
                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error hashing password", e);
            }
        } else {
            return "Password cannot be null";
        }
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * Returns the role of the user. Subclasses must implement this method.
     * 
     * @return The role of the user (e.g., "customer", "admin", "business").
     */
    public abstract String getRole();
}
