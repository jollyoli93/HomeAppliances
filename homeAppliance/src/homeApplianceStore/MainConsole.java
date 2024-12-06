package homeApplianceStore;

//split off and make a seperate class like other consoles
public class MainConsole () {
	
	public MainConsole (String database, InputOutputHandler handler) {
		
	}
	
  InputOutputHandler handleInput = setHandler(handler);
  int input = 0;
	
	MenuConsole console = new MenuConsole(database);
	UserConsole userConsole = new UserConsole(database);
	
	while (input != 3) {
		input = handleInput.getInputInt();
		
		System.out.println("------------------------");
		System.out.println("Home Appliance Store");
		System.out.println("------------------------");
		
		System.out.println("[1] Store Products");
		System.out.println("[2] Manage Users");
		System.out.println("[3] Exit");
		
		switch (input) {
		case 1: 
			userConsole.userMenu();
			break;
		case 2:
			console.displayMenu();
			break;
		case 3:
			System.out.println("Exiting, see you again soon.");
		default:
			System.out.println("Incorrect input, please try again");
		}
	}
}

public InputOutputHandler setHandler(InputOutputHandler handleInput) {
	return handleInput;
}

