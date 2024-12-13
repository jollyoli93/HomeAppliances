package homeApplianceStore;

import java.util.ArrayList;

import DAO.UserDao;
import IOHandlers.ConsoleIOHandler;
import IOHandlers.InputOutputHandler;
import printer.AdminPrinter;
import printer.AppliancePrinter;
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
	
	public void userMenu () {
		int input = 0;
		
		while (input != 6) {
			
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
			
			input = handleInput.getInputInt();
			
			switch (input) {
				case 1: 
					getAllUsers();
					break;
				case 2:
					System.out.println("Search for user");
					break;
				case 3:
					addUserInterface();
					break;
				case 4:
					System.out.println();
					break;
				case 5:
					System.out.println();
					break;
				case 6:
					System.out.println();
					break;
				default:
					System.out.println("Try again");
			}
		}
		
	}
	
	public void getAllUsers () {
		try {
			ArrayList<User> userList = userDAO.findAll();
			System.err.println("Loop through user list");
			
			for (User user : userList) {
				switch (user.getRole()) {
				case "admin":
					AdminPrinter printAdmin = new AdminPrinter(user);
					printAdmin.print();
					break;
				case "business":
					BusinessPrinter printBusiness = new BusinessPrinter(user);
					printBusiness.print();
					break;
				case "customer":
					CustomerPrinter printCustomer = new CustomerPrinter(user);
					printCustomer.print();
					break;
				
				}
			}
		} catch (NullPointerException e) {
			System.out.println("No products in the database");
			System.out.println();
			e.printStackTrace();
		}
	}

	
	public void addUserInterface () {
		System.out.println("Would you like to add a user? Y/N ");
		String input = handleInput.getInputString();
		
		//DEBUG
		System.out.println(input);
		
		Boolean success = false;
		
		if (input.equalsIgnoreCase("Y")) {			
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
					}
					else {
						System.out.println();
						System.out.println("Error adding user - try again");
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("CATCH: Error adding user - try again");
					e.printStackTrace();
				}
			}
		}
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
}
