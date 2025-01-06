package mainConsole;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.UserDao;
import IOHandlers.ConsoleIOHandler;
import IOHandlers.InputOutputHandler;
import exceptions.InvalidUserIdException;
import printer.AdminPrinter;
import printer.BusinessPrinter;
import printer.CustomerPrinter;
import users.Address;
import users.AdminUser;
import users.BillingAddress;
import users.BusinessUser;
import users.CustomerUser;
import users.ShippingAddress;
import users.User;
import users.UserFactory;
import util.Util;

/**
 * UserConsole handles the user management interface for the Home Appliance Store.
 * It allows for user creation, updating, deletion, and searching as well as managing user roles and addresses.
 * 
 * Author: 24862664
 */
public class UserConsole {
    private UserDao userDAO;
    private InputOutputHandler handleInput;
    private String dbPath;
    private String consoleOutput = null;
    User customer;

    /**
     * Constructs a UserConsole object with the specified database path.
     *
     * @param dbPath the path to the database
     */
    public UserConsole(String dbPath) {
        this.dbPath = dbPath;
        this.handleInput = new ConsoleIOHandler();
        this.userDAO = new UserDao(dbPath);
    }

    /**
     * Sets the input handler for the console.
     *
     * @param handleInput the InputOutputHandler to set
     */
    public void setHandler(InputOutputHandler handleInput) {
        this.handleInput = handleInput;
    }

    /**
     * Displays the user management menu and processes user input for various options.
     *
     * @return a string message indicating the result of the operation
     */
    public String userMenu() {
        String input = "0";
        boolean flag = true;
        int count = 0;

        do {
            System.out.println("------------------------");
            System.out.println("Choose from these options");
            System.out.println("------------------------");
            System.out.println("[1] List all users");
            System.out.println("[2] Search by the user ID");
            System.out.println("[3] Add a new user");
            System.out.println("[4] Update a user");
            System.out.println("[5] Delete a user by ID");
            System.out.println("[6] Add a user address");
            System.out.println("[7] Manage admin");
            System.out.println("[q] Go Back");
            System.out.println();
            input = handleInput.getInputString();

            switch (input) {
                case "1":
                    getAllUsers();
                    System.out.println();
                    break;
                case "2":
                    getUserInterface();
                    break;
                case "3":
                    consoleOutput = addUserInterface();
                    break;
                case "4":
                    consoleOutput = selectUpdateMethod();
                    break;
                case "5":
                    consoleOutput = deleteByUserID();
                    System.out.println();
                    break;
                case "6":
                    consoleOutput = addAddressInterface();
                    System.out.println();
                    break;
                case "7":
                    consoleOutput = handleAdminStatus();
                    System.out.println(consoleOutput);
                    break;
                case "q":
                    flag = false;
                    consoleOutput = "Returning";
                    System.out.println(consoleOutput);
                    break;
                default:
                    if (count > 1) flag = false;
                    count++;
                    System.out.println("Try again " + (3 - count) + " trys left");
                    break;
            }

            if (flag) {
                System.out.println("Type q to exit or any key to continue:");
                input = handleInput.getInputString();
                if ("q".equalsIgnoreCase(input)) {
                    flag = false; // Exit if user types q
                }
            }
        } while (flag);

        System.out.println();
        return consoleOutput;
    }

    /**
     * Selects the update method for updating user details.
     *
     * @return a string indicating the result of the update operation
     */
    private String selectUpdateMethod() {
        String inputIdString, selectUpdate;
        int userId = 0, updatedRows = 0;

        System.out.println("Enter user ID");
        inputIdString = handleInput.getInputString();

        try {
            userId = Util.parseUserId(inputIdString);
        } catch (InvalidUserIdException e) {
            System.out.println(e.getMessage());
            return "Failed to update user: " + e.getMessage();
        }

        System.out.println("Please select update method");
        selectUpdate = "0";

        while (!selectUpdate.equals("back")) {
            System.out.println("------------------------");
            System.out.println("Choose from these options");
            System.out.println("------------------------");
            System.out.println("[1] Update first name");
            System.out.println("[2] Update last name");
            System.out.println("[3] Update email");
            System.out.println("[4] Update password");
            System.out.println("[5] Update address");
            System.out.println("[6] Update telephone number");
            System.out.println("[7] Update business name");
            System.out.println("[back] Back");
            System.out.println();

            selectUpdate = handleInput.getInputString();
            String userInput = null;

            switch (selectUpdate) {
                case "1":
                    System.out.println("Enter new first name");
                    userInput = handleInput.getInputString();
                    updatedRows = userDAO.updateFieldById(userId, "first_name", userInput);
                    break;
                case "2":
                    System.out.println("Enter new last name");
                    userInput = handleInput.getInputString();
                    updatedRows = userDAO.updateFieldById(userId, "last_name", userInput);
                    break;
                case "3":
                    System.out.println("Enter new email");
                    userInput = handleInput.getInputString();
                    updatedRows = userDAO.updateFieldById(userId, "email_address", userInput);
                    break;
                case "4":
                    System.out.println("Enter new password");
                    userInput = handleInput.getInputString();
                    updatedRows = userDAO.updateFieldById(userId, "password", userInput);
                    break;
                case "5":
                    System.out.println("update address");
                    updatedRows = updateAddressHandler(userId);
                    break;
                case "6":
                    System.out.println("Enter new telephone number");
                    userInput = handleInput.getInputString();
                    updatedRows = userDAO.updateFieldById(userId, "telephone_num", userInput);
                    break;
                case "7":
                    System.out.println("Enter new business name");
                    userInput = handleInput.getInputString();
                    updatedRows = userDAO.updateFieldById(userId, "business_name", userInput);
                    break;
                case "back":
                    System.out.println("User not updated. Returning.");
                    System.out.println();
                    break;
                default:
                    System.out.println("Try again");
            }

            if (updatedRows > 0) {
                System.out.println("DEBUG: updated user.");
                return "Number of rows updated: " + updatedRows;
            } else {
                System.out.println("DEBUG: Failed to update user.");
                return "Number of rows updated: " + updatedRows;
            }
        }

        return "returning";
    }

    /**
     * Displays the user details for a specific user ID.
     */
    public void getUserInterface() {
        String userIdString = null;
        User user = null;
        int userId = 0;

        System.out.println("Enter User ID.");
        userIdString = handleInput.getInputString();
        try {
            userId = Util.parseUserId(userIdString);
        } catch (InvalidUserIdException e) {
            System.out.println(e.getMessage());
            return;
        }

        //fetch user profile
        user = userDAO.getUserWithAddresses(userId);

        if (user == null) {
            System.out.println("User doesn't exist");
            return;
        }

        switch (user.getRole()) {
            case "admin":
                new AdminPrinter(user).print();
                break;
            case "business":
                new BusinessPrinter(user).print();
                break;
            case "customer":
                new CustomerPrinter(user).print();
                break;
            default:
                System.out.println("Unknown role: " + user.getRole());
                break;
        }
    }

    /**
     * Retrieves and displays all users.
     */
    public void getAllUsers() {
        ArrayList<User> userList = userDAO.findAll(0, null);

        if (userList.isEmpty()) {
            return;
        }

        System.out.println("Looping through user list...");
        System.out.println();

        for (User user : userList) {
            switch (user.getRole()) {
                case "admin":
                    new AdminPrinter(user).print();
                    break;
                case "business":
                    new BusinessPrinter(user).print();
                    break;
                case "customer":
                    new CustomerPrinter(user).print();
                    break;
                default:
                    System.out.println("Unknown role: " + user.getRole());
                    break;
            }
        }
    }

    /**
     * Adds a new user via the console interface.
     *
     * @return a string message indicating the result of the user addition
     */
    public String addUserInterface() {
        Boolean success = false;

        while (success == false) {
            System.out.println("Please enter user role or type quit to exit");
            System.out.println("admin, customer, business");

            //get roles dynamically;?
            String role = handleInput.getInputString();

            try {
                success = addUserByRole(role.toLowerCase());

                if (success == true) {
                    System.out.println();
                    System.out.println("Succesfully added to the database");

                    consoleOutput = "Succesfully added to the database";
                    return consoleOutput;
                } else {
                    System.out.println();
                    System.out.println("Error adding user - try again");
                    consoleOutput = "Error adding user - try again";
                    return consoleOutput;
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("CATCH: Error adding user - try again");
                e.printStackTrace();
            }
        }
        return consoleOutput;
    }

    /**
     * Adds a new user based on the provided role.
     *
     * @param role the role of the user to add (e.g., admin, customer, business)
     * @return true if the user was successfully added, false otherwise
     */
    private boolean addUserByRole(String role) {
        String first_name = null;
        String last_name = null;
        String email = null;
        String username = null;
        String password = null;
        String telephoneNum = null;
        String businessName = null;
        User user;

        System.out.println("First Name: ");
        first_name = handleInput.getInputString();

        System.out.println("Last Name: ");
        last_name = handleInput.getInputString();

        System.out.println("Email: ");
        email = handleInput.getInputString();

        System.out.println("username: ");
        username = handleInput.getInputString();

        System.out.println("password: ");
        password = handleInput.getInputString();

        switch (role) {
            case "customer":
                System.out.println("Telephone Number: ");
                telephoneNum = handleInput.getInputString();

                user = new CustomerUser(first_name, last_name, email, username, password, telephoneNum);
                return userDAO.addNewCustomer(user, null);

            case "admin":
                user = new AdminUser(first_name, last_name, email, username, password);
                return userDAO.addNewAdmin(user);

            case "business":
                System.out.println("Telephone Number: ");
                telephoneNum = handleInput.getInputString();

                System.out.println("Business Name: ");
                businessName = handleInput.getInputString();

                user = new BusinessUser(first_name, last_name, email, username, password, telephoneNum, businessName);
                return userDAO.addNewBusiness(user, null);
            case "quit":
                return true;
            default:
                return false;
        }
    }

    /**
     * Deletes a user based on the provided user ID.
     * 
     * @return A string message indicating the success or failure of the deletion process.
     */
    private String deleteByUserID() {
        System.out.println("Please enter the user ID number you wish to delete:");
        String inputIdString = handleInput.getInputString();
        System.out.println("DEBUG: " + inputIdString);
        int deleted = 0;
        int userId = 0;

        try {
            userId = Util.parseUserId(inputIdString);
        } catch (InvalidUserIdException e) {
            System.out.println(e.getMessage());
            return "Failed to update user: " + e.getMessage();
        }
        
        deleted = userDAO.deleteUserById(userId); // Attempt to delete user

        if (deleted > 0) {
            System.out.println("User deleted successfully.");
            consoleOutput = "User deleted successfully.";
            return consoleOutput;
        } else {
            System.out.println("Failed to delete user.");
            consoleOutput = "Failed to delete user.";
            return consoleOutput;
        }
    }

    /**
     * Provides an interface to add an address to a user.
     * 
     * @return A string message indicating the success or failure of the address addition process.
     */
    public String addAddressInterface() {
        Boolean success = false;
        int userId = 0;
        
        while (success == false) {                
            try {
                System.out.println("Enter user ID.");
                String userIdString = handleInput.getInputString();
                
                try {
                    userId = Util.parseUserId(userIdString);
                } catch (InvalidUserIdException e) {
                    System.out.println(e.getMessage());
                    return "Failed to update user: " + e.getMessage();
                }
                
                success = addAddressHandler(userId);

                if (success == true) {
                    System.out.println();
                    System.out.println("Successfully added to the database");
                    
                    consoleOutput = "Successfully added to the database";
                    return consoleOutput;
                }
                else {
                    System.out.println();
                    System.out.println("Error adding user - try again");
                    consoleOutput = "Error adding user - try again";
                    return consoleOutput;
                }
                
            } catch (Exception e) {
                System.out.println("CATCH: Error adding user - try again");
                e.printStackTrace();
            }
        }
        return consoleOutput;
    }

    /**
     * Collects address details from the user and returns an Address object.
     * 
     * @param user_id The ID of the user to which the address will be linked.
     * @return An Address object with the provided details.
     */
    private Address userAddressHandler(int user_id) {
        String number;
        String street;
        String city;
        String country;
        String postcode;
        boolean isPrimary;
        Address address;
        String addressType;
        
        System.out.println("Enter Building Number: ");
        number = handleInput.getInputString();
        
        System.out.println("Enter Street: ");
        street = handleInput.getInputString();

        System.out.println("Enter City: ");
        city = handleInput.getInputString();
        
        System.out.println("Enter Country: ");
        country = handleInput.getInputString();
        
        System.out.println("Enter Postcode: ");
        postcode = handleInput.getInputString();
        
        System.out.println("Is it the primary address? true or false: ");
        String isPrimaryInput = handleInput.getInputString();
        
        isPrimary = isPrimaryAddress(isPrimaryInput);
        
        System.out.println("Enter address type: ");
        System.out.println("[1] Shipping");
        System.out.println("[2] Billing");
        addressType = handleInput.getInputString();
        
        address = selectAddressType(addressType, number, street, city, country, postcode, user_id, isPrimary);
        
        return address;
    }

    /**
     * Handles the process of adding an address for a user.
     * 
     * This method calls the userAddressHandler to collect address details and then attempts to add the address to the database.
     * 
     * @param user_id The ID of the user to which the address will be added.
     * @return A boolean value indicating whether the address was successfully added.
     */
    private boolean addAddressHandler(int user_id) {
        Address address = userAddressHandler(user_id);
        return userDAO.addAddress(address, null);
    }

    /**
     * Handles the process of updating a user's address.
     * 
     * This method calls the userAddressHandler to collect updated address details and then attempts to update the user's address in the database.
     * 
     * @param user_id The ID of the user whose address is being updated.
     * @return An integer indicating the number of rows affected by the update.
     */
    private int updateAddressHandler(int user_id) {
        Address address = userAddressHandler(user_id);

        try {
            return userDAO.updateUserAddress(user_id, address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Selects the address type (Shipping or Billing) based on the user input and creates the appropriate address object.
     * 
     * @param addressType The type of address selected by the user ("1" for shipping, "2" for billing).
     * @param number The building number of the address.
     * @param street The street of the address.
     * @param city The city of the address.
     * @param country The country of the address.
     * @param postCode The postcode of the address.
     * @param customerId The ID of the user to whom the address belongs.
     * @param isPrimary Whether the address is primary or not.
     * @return An Address object of the appropriate type (Shipping or Billing).
     */
    private Address selectAddressType(String addressType, String number, String street, String city, String country, String postCode, int customerId, boolean isPrimary) {
        if ("1".equalsIgnoreCase(addressType)) {
            return new ShippingAddress(number, street, city, country, postCode, customerId, isPrimary);
        }
        
        if ("2".equalsIgnoreCase(addressType)) {
            return new BillingAddress(number, street, city, country, postCode, customerId, isPrimary);
        }
        
        System.out.println("No such address type");
        return null;
    }

    /**
     * Determines whether the address is a primary address based on the user's input.
     * 
     * @param isPrimaryInput The user's input indicating whether the address is primary or not.
     * @return A boolean value indicating whether the address is primary.
     */
    private boolean isPrimaryAddress(String isPrimaryInput) {
        boolean flag = false;
        
        do {
            if (isPrimaryInput.equalsIgnoreCase("true")) {
                flag = true;
                return true;
            } else if (isPrimaryInput.equalsIgnoreCase("false")) {
                flag = true;
                return false;
            } else {
                System.out.println("Incorrect input");
            }
        } while (flag == false);
        
        return false;
    }

    /**
     * Handles the process of updating the admin status for a user.
     * @return A string message indicating the result of the admin status operation.
     */
    private String handleAdminStatus() {
        Boolean success = false, hasAdminStatus = false;
        String userIdString = null, input = null;
        int userId = 0, output = 0;
        ArrayList<String> userRoles = new ArrayList<String>();    
        
        do {
            System.out.println("Enter user ID");
            userIdString = handleInput.getInputString();
            try {
                userId = Util.parseUserId(userIdString);
            } catch (InvalidUserIdException e) {
                System.out.println(e.getMessage());
                return "Failed to update user: " + e.getMessage();
            }
            
            // Get user role status
            hasAdminStatus = userDAO.isUserAdmin(userId);
            
            if (hasAdminStatus) {

                System.out.println("------------------------");
                System.out.println("Choose from these options");
                System.out.println("------------------------");
                System.out.println("[1] Remove admin status");
                System.out.println("[2] Return");
                System.out.println();
                
                input = handleInput.getInputString();
                
                switch (input) {
                case "1":                    
                    try {
                        output = userDAO.removeAdminById(userId);
                        return "Number of rows updated: " + output;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "Failed to remove admin status.";
                    }
                    
                case "2":
                    return "Returning.";
                default:
                    System.out.println("Invalid input");
                    break;
                }
            } 
            else if (!hasAdminStatus) {
                System.out.println("------------------------");
                System.out.println("Choose from these options");
                System.out.println("------------------------");
                System.out.println("[1] Give admin status");
                System.out.println("[2] Return");
                System.out.println();
                
                input = handleInput.getInputString();
                
                
                switch (input) {
                case "1":
                    try {
                        output = userDAO.giveAdminStatus(userId);
                        return "Number of rows updated: " + output;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "Failed to give admin status.";
                    }
                case "2":
                    return "Returning.";
                default:
                    System.out.println("Invalid input");
                    break;
                }
                
            }
            
        } while (success == false);
        
        return "No change to user roles";
    }

	
	
}
