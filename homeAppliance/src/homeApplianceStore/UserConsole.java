package homeApplianceStore;

import java.sql.SQLException;
import java.util.ArrayList;

import DAO.UserDao;
import IOHandlers.ConsoleIOHandler;
import IOHandlers.InputOutputHandler;
import printer.AdminPrinter;
import printer.BusinessPrinter;
import printer.CustomerPrinter;
import users.AdminUser;
import users.BusinessUser;
import users.CustomerUser;
import users.User;
import users.UserFactory;

public class UserConsole {
    private UserDao userDAO;
    private InputOutputHandler handleInput;
    private String dbPath;
	private String consoleOutput = null;
	User customer;
	
	public UserConsole (String dbPath) {
		this.dbPath = dbPath;
		this.handleInput = new ConsoleIOHandler();
		
		this.userDAO = new UserDao(dbPath);
	}
	
	public void setHandler(InputOutputHandler handleInput) {
	    this.handleInput = handleInput;
	}

	
	public String userMenu () {
		String input = "0";
		boolean flag = true;
		
			do {
				System.out.println("------------------------");
				System.out.println("Choose from these options");
				System.out.println("------------------------");
				System.out.println("[1] List all users");
				System.out.println("[2] Search by the user ID");
				System.out.println("[3] Add a new user");
				System.out.println("[4] Update a user");
				System.out.println("[5] Delete a user by ID");
				System.out.println("[6] Back");
				System.out.println();
				input = handleInput.getInputString();
				
				switch (input) {
				case "1":
					getAllUsers();
					break;
				case "2":
					System.out.println("Search for user");
					break;
				case "3":
					addUserInterface();
					break;
				case "4":
					updateUserInformation();
					System.out.println();
					break;
				case "5":
					deleteByUserID();
					System.out.println();
					break;
				case "6":
					flag = false;
					System.out.println("Returning");
					break;
				default:
					System.out.println("Try again");
				}
			} while (flag);
		return consoleOutput;
		
	}
	
	private String updateUserInformation() {
		String input_user_id;
		String input;
		boolean updated = false;
		
		System.out.println("Enter user ID");
		input_user_id = handleInput.getInputString();
		
	    try {
	        int userId = Integer.parseInt(input_user_id); // Parse String to int
		    System.out.println("DEBUG: " + userId);
		    
	        updated = selectUpdateMethod(userId);
	    } catch (NumberFormatException e) {
	        System.out.println("Invalid user ID. Please enter a valid number.");
	    } 
	    if (updated) {
	        System.out.println("User updated successfully.");
	        consoleOutput = "User updated successfully.";
	        return consoleOutput;
	    } else {
	        System.out.println("Failed to update user.");
	        consoleOutput = "Failed to update user.";
	        return consoleOutput;
	    }
	}
	
	private boolean selectUpdateMethod (int userId) {
		System.out.println("Please select update method");
		String selectUpdate = "0";
		
		while (selectUpdate != "7") {
				System.out.println("------------------------");
				System.out.println("Choose from these options");
				System.out.println("------------------------");
				
				
				System.out.println("[1] Update first name");
				System.out.println("[2] Update last name");
				System.out.println("[3] Update email");
				System.out.println("[4] Update password");
				System.out.println("[5] Update address");
				System.out.println("[6] Update telephone number");

				System.out.println("[7] Back");
				System.out.println();
				
				selectUpdate = handleInput.getInputString();
				String userInput= null;
				
				switch (selectUpdate) {
					case "1": 
						System.out.println("Enter new first name");
						userInput = handleInput.getInputString();
						return userDAO.updateFirstNameById(userId, userInput);
					case "2":
						System.out.println("Enter new last name");
						userInput = handleInput.getInputString();
						return userDAO.updateLastNameById(userId, userInput);
					case "3":
						System.out.println("Enter new email");
						userInput = handleInput.getInputString();
						return userDAO.updateEmailById(userId, userInput);
					case "4":
						System.out.println("Enter new password");
						userInput = handleInput.getInputString();
						return userDAO.updatePasswordById(userId, userInput);
					case "5":
						System.out.println("Enter new business name");
						userInput = handleInput.getInputString();
						return userDAO.updateBusinessNameById(userId, userInput);
					case "6":
						System.out.println("Enter new business name");
						userInput = handleInput.getInputString();
						return userDAO.updateBusinessNameById(userId, userInput);
					case "7":
						System.out.println("update address");
						//need to select address ID to update
						
						//userInput = handleInput.getInputString();
						//return userDAO.updateBusinessNameById(userId, userInput);
					case "8":
						System.out.println("update user role");
						//need to select role
						
						//userInput = handleInput.getInputString();
						//return userDAO.updateBusinessNameById(userId, userInput);
					case "9":
						System.out.println();
						return false;
					default:
						System.out.println("Try again");
				}
		}
		return false; 
	}
	

	public void getAllUsers() {
	    ArrayList<User> userList = userDAO.findAll();

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

	
	public String addUserInterface () {
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
					}
					else {
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
				return userDAO.addNewCustomer(user);
				
			case "admin":
				user = new AdminUser(first_name, last_name, email, username, password);
				return userDAO.addNewAdmin(user);
				
			case "business":
				System.out.println("Telephone Number: ");
				telephoneNum = handleInput.getInputString();
				
				System.out.println("Business Name: ");
				businessName = handleInput.getInputString();
				
				user = new BusinessUser(first_name, last_name, email, username, password, telephoneNum, businessName);
				return userDAO.addNewBusiness(user);
			case "quit":
				return true;
			default:
				return false;
		}
	}
	
	private String deleteByUserID() {
	    System.out.println("Please enter the user ID number you wish to delete:");
	    String id = handleInput.getInputString();
	    System.out.println("DEBUG: " + id);
	    boolean deleted = false;

	    try {
	        int userId = Integer.parseInt(id); // Parse String to int
		    System.out.println("DEBUG: " + userId);
		    
	        deleted = userDAO.deleteById(userId); // Attempt to delete user
	    } catch (NumberFormatException e) {
	        System.out.println("Invalid user ID. Please enter a valid number.");
	    } 
	    
	    if (deleted) {
	        System.out.println("User deleted successfully.");
	        consoleOutput = "User deleted successfully.";
	        return consoleOutput;
	    } else {
	        System.out.println("Failed to delete user.");
	        consoleOutput = "Failed to delete user.";
	        return consoleOutput;
	    }
	}



}
