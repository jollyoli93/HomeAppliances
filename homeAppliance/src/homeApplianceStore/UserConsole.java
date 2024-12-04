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
	
	public UserConsole (String dbPath) {
		this.dbPath = dbPath;
		this.handleInput = new ConsoleIOHandler(); 
		
		this.userDAO = new UserDao(dbPath);
	}
	
	public void setHandler(InputOutputHandler handleInput) {
		this.handleInput = handleInput;
	}
	
	public void userMenu () {
		String input = null;
		User customer;
		System.out.println("Would you like to add a user?");
		
		input = handleInput.getInputString();
		System.out.println(input);
		
		if (input.equalsIgnoreCase("yes")) {
			Boolean success = null;
			customer = new CustomerUser("Jimmy", "Grimble", "jimmygrimble2@woohoo.com","jimbob98-2", "ilovefootball", "07498352989");
			
			try {
				success = userDAO.addNew(customer);
				
				if (success == true) {
					System.out.println("Succesfully added to the database");
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
