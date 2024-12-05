package homeApplianceStore;

import DAO.UserDao;
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
			System.out.println("Home Appliance Store");
			System.out.println("Choose from these options");
			System.out.println("------------------------");
			
			
			System.out.println("[1] List all users");
			System.out.println("[2] Search by the user ID");
			System.out.println("[3] Add a new user");
			System.out.println("[4] Update a user");
			System.out.println("[5] Delete a user by ID");
			System.out.println("[6] Exit");
			System.out.println();
			
			input = handleInput.getInputInt();
			
			switch (input) {
				case 1: 
					getAllUsers();
					break;
				case 2:
					addUser();
					break;
				default:
					System.out.println("Try again");
			}
		}
		
	}
	
	public void getAllUsers () {
		userDAO.findAll();
	}
	
	public void addUser () {
		System.out.println("Would you like to add a user?");
		String input = handleInput.getInputString();
		System.out.println(input);
		
		if (input.equalsIgnoreCase("yes")) {
			Boolean success = null;
			customer = new CustomerUser("Jimmy", "Grimble", "jimmygrimble3@woohoo.com","jimbob98-3", "ilovefootball", "07498352989");
			
			try {
				success = userDAO.addNew(customer);
				
				if (success == true) {
					System.out.println("Succesfully added to the database");
					userDAO.findAll();
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Empty");
		}
	}
}
